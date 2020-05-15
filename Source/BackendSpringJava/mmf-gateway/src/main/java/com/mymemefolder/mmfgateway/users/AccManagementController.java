package com.mymemefolder.mmfgateway.users;

import com.mymemefolder.mmfgateway.security.UnauthorizedActionException;
import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AccManagementController {
    private final UserService userService;

    public AccManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user-info")
    @ResponseBody
    public UserInfoViewData getUserInfo(Principal principal) {
        var user = userService.findUserByName(principal.getName());
        return user
            .map(value -> new UserInfoViewData(
                    value.getUsername(),
                    value.getEmail(),
                    value.getFolderIsPublic(),
                    value.getImagesArePublic()))
            .orElse(null);
    }

    @PutMapping("/api/user-info")
    public void updateUserInfo(Principal principal, @RequestBody UserInfoViewData userInfo)
            throws UnauthorizedActionException, DataNotFoundException {
        if (principal == null)
            throw new UnauthorizedActionException("Cannot modify user info while not logged in");
        var user = userService.findUserByName(principal.getName())
                .orElseThrow(() -> new DataNotFoundException("User was not found"));
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getEmail());
        user.setFolderIsPublic(userInfo.getFolderIsPublic());
        user.setImagesArePublic(userInfo.getImagesArePublic());
        userService.updateUser(user);
    }

    @PutMapping("/api/change-password")
    public void changePassword(Principal principal, @RequestBody PasswordChangeData passwordChangeData)
            throws UnauthorizedActionException, DataNotFoundException {
        if (principal == null)
            throw new UnauthorizedActionException("Cannot modify user info while not logged in");
        var user = userService.findUserByName(principal.getName())
                .orElseThrow(() -> new DataNotFoundException("User was not found"));
        userService.changePassword(user, passwordChangeData.getPassword(), passwordChangeData.getNewPassword());
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody RegistrationInfo registrationInfo) {
        var result = userService.registerUser(
            registrationInfo.getUsername(),
            registrationInfo.getPassword(),
            registrationInfo.getEmail());
        return result.isSuccessful()
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(400).body(result.getErrorMessage());
    }
}
