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
        return Optional.of(repository.findByUsername(name));
    }

    public ActionResult registerUser(String username, String password, String email) {
        // todo validate better
        if (username == null)
            return ActionResult.failure("username cannot be null.");
        if (password == null)
            return ActionResult.failure("password cannot be null.");
        if (email == null)
            return ActionResult.failure("email cannot be null.");
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
