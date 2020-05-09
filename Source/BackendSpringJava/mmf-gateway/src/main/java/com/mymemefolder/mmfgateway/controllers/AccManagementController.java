package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccManagementController {
    private final UserService userService;

    public AccManagementController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> Register(@RequestBody RegistrationInfo registrationInfo) {
        var result = userService.registerUser(
            registrationInfo.getUsername(),
            registrationInfo.getPassword(),
            registrationInfo.getEmail());
        return result.isSuccessful()
            ? ResponseEntity.ok().build()
            : ResponseEntity.status(400).body(result.getErrorMessage());
    }
}
