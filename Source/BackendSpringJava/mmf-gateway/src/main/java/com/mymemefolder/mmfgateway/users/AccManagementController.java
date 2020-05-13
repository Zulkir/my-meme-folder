package com.mymemefolder.mmfgateway.users;

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
            .map(value -> new UserInfoViewData(value.getUsername(), value.getEmail()))
            .orElse(null);
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
