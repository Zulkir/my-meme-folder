package com.mymemefolder.mmfgateway.repositories;

import com.mymemefolder.mmfgateway.utils.ActionResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceJpa implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJpa(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(repository.findByUsername(name));
    }

    public ActionResult registerUser(String username, String password, String email) {
        // todo validate better
        if (username == null || username.length() == 0)
            return ActionResult.failure("Username cannot be empty.");
        if (password == null || password.length() == 0)
            return ActionResult.failure("Password cannot be empty.");
        if (email == null || email.length() == 0)
            return ActionResult.failure("Email cannot be empty.");
        if (repository.findByUsername(username) != null)
            return ActionResult.failure("User with this name already exists.");
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        repository.save(user);
        return ActionResult.success();
    }
}
