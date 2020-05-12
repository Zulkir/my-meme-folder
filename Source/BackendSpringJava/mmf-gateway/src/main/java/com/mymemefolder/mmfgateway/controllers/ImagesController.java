package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.User;
import com.mymemefolder.mmfgateway.repositories.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class ImagesController {
    private final UserService userService;
    private final FolderService folderService;

    public ImagesController(UserService userService, FolderService folderService) {
        this.userService = userService;
        this.folderService = folderService;
    }

    @GetMapping("/api/folder/{username}/images")
    @ResponseBody
    public List<ImageWithThumbnail> getImageList(Principal principal, @PathVariable String username, String path)
            throws IOException, DataNotFoundException, DataIsPrivateException {
        var user = getAuthorizedUser(principal, username);
        return folderService.getImageList(user, path);
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
