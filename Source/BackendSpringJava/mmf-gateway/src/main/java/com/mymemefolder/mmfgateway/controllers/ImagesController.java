package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.ImageService;
import com.mymemefolder.mmfgateway.repositories.User;
import com.mymemefolder.mmfgateway.repositories.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ImageWithThumbnail> getImageList(Principal principal, @PathVariable String username, String path)
            throws IOException, DataNotFoundException, DataIsPrivateException {
        var user = getAuthorizedUser(principal, username);
        return imageService.getAllByPath(user, path).stream()
                .map(ImageWithThumbnail::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/images/{key}")
    public ResponseEntity<InputStreamResource> getFullImage(Principal principal, @PathVariable String key)
            throws DataNotFoundException {
        var image = imageService.getByKey(key).orElseThrow(() -> new DataNotFoundException("Image was not found"));
        // todo: check user privacy settings
        var streamWithLength = imageStorageService.readImageByKey(image.getKey());
        var stream = streamWithLength.getStream();
        var length = streamWithLength.getLength();
        var inputStreamResource = new InputStreamResource(stream);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
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
