package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.security.DataIsPrivateException;
import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.users.UserService;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImagesController {
    private final UserService userService;
    private final ImageService imageService;
    private final ImageStorageService imageStorageService;

    public ImagesController(UserService userService, ImageService imageService, ImageStorageService imageStorageService) {
        this.userService = userService;
        this.imageService = imageService;
        this.imageStorageService = imageStorageService;
    }

    @GetMapping("/api/folder/{username}/images")
    @ResponseBody
    public List<ImageViewData> getImageList(Principal principal, @PathVariable String username, String path)
            throws DataNotFoundException, DataIsPrivateException {
        var user = getAuthorizedUser(principal, username);
        return imageService.getAllByPath(user, path).stream()
                .map(ImageViewData::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/images/{key}")
    public ResponseEntity<InputStreamResource> getFullImage(Principal principal, @PathVariable String key)
            throws DataNotFoundException {
        var image = imageService.getByKey(key).orElseThrow(() -> new DataNotFoundException("Image was not found"));
        // todo: check user privacy settings
        var streamWithLength = imageStorageService.readImageByKey(image.getUserId(), image.getKey());
        var stream = streamWithLength.getStream();
        var length = streamWithLength.getLength();
        var inputStreamResource = new InputStreamResource(stream);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/api/images/{key}")
    public void updateImageFields(Principal principal, @PathVariable String key, @RequestBody ImageViewData updatedData)
            throws DataNotFoundException {
        var image = imageService.getByKey(key).orElseThrow(() -> new DataNotFoundException("Image was not found"));
        // todo: check user privacy settings
        image.setName(updatedData.getName());
        image.setTags(updatedData.getTags());
        imageService.update(image);
    }

    @PostMapping("/api/images/")
    @ResponseBody
    public ImageViewData UploadImage(Principal principal, String path, String name, @RequestParam("file") MultipartFile file)
            throws DataIsPrivateException, DataNotFoundException, IOException, InvalidOperationException {
        if (principal == null)
            throw new DataIsPrivateException("Must be logged in to upload images");
        var user = userService.getUserByName(principal.getName());
        try (var stream = file.getInputStream()) {
            var image = imageService.create(user, path, name, stream);
            return new ImageViewData(image);
        }
    }

    private User getAuthorizedUser(Principal principal, String requestedUsername)
            throws DataNotFoundException, DataIsPrivateException {
        var requestedUser = userService.getUserByName(requestedUsername);
        var principalUser = principal != null
                ? userService.getUserByName(principal.getName())
                : null;
        var isRequestingSelf = principalUser != null && principalUser.getId().equals(requestedUser.getId());
        if (!requestedUser.getFolderIsPublic() && !isRequestingSelf)
            throw new DataIsPrivateException("The folder is private.");
        return requestedUser;
    }
}
