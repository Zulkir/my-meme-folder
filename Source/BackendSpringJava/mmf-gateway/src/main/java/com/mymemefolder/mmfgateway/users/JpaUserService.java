package com.mymemefolder.mmfgateway.users;

import com.mymemefolder.mmfgateway.security.UnauthorizedActionException;
import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.ActionResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserService implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUserByName(String name) {
        return Optional.ofNullable(repository.findByUsername(name));
    }

    @Override
    public User getUserById(int id) throws DataNotFoundException {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException("User was not found."));
    }

    @Override
    public User getUserByName(String name) throws DataNotFoundException {
        var user = repository.findByUsername(name);
        if (user == null)
            throw new DataNotFoundException("User was not found.");
        return user;
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

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword)
            throws UnauthorizedActionException {
        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new UnauthorizedActionException("Password is incorrect");
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }
}
