package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.User;
import com.mymemefolder.mmfgateway.repositories.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
public class FolderController {
    private final UserService userService;
    private final FolderService folderService;

    public FolderController(UserService userService, FolderService folderService) {
        this.userService = userService;
        this.folderService = folderService;
    }

    @GetMapping("/api/folder/{username}/structure")
    @ResponseBody
    public List<Folder> getFolderStructure(Principal principal, @PathVariable String username)
            throws DataNotFoundException, DataIsPrivateException {
        var user = getAuthorizedUser(principal, username);
        return folderService.getStructure(user);
    }

    @PostMapping("/api/folder/{username}/structure")
    @ResponseBody
    public List<Folder> newFolder(Principal principal, @PathVariable String username, String path)
            throws UnauthorizedActionException, DataNotFoundException, InvalidOperationException {
        var user = getPrincipalUser(principal, username);
        return folderService.newFolder(user, path);
    }

    @PutMapping("/api/folder/{username}/structure")
    @ResponseBody
    public List<Folder> renameFolder(Principal principal, @PathVariable String username, String path, String newName)
            throws UnauthorizedActionException, DataNotFoundException, InvalidOperationException {
        var user = getPrincipalUser(principal, username);
        return folderService.renameFolder(user, path, newName);
    }

    @DeleteMapping("/api/folder/{username}/structure")
    @ResponseBody
    public List<Folder> setFolderStructure(Principal principal, @PathVariable String username, String path)
            throws UnauthorizedActionException, DataNotFoundException, InvalidOperationException {
        var user = getPrincipalUser(principal, username);
        return folderService.deleteFolder(user, path);
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

    private User getPrincipalUser(Principal principal, String requestedUsername)
            throws DataNotFoundException, UnauthorizedActionException {
        if (principal == null)
            throw new UnauthorizedActionException("Can only be modified by user himself.");
        var principalUser = userService.getUserByName(principal.getName());
        if (!principalUser.getUsername().equals(requestedUsername))
            throw new UnauthorizedActionException("Can only be modified by user himself.");
        return principalUser;
    }
}
