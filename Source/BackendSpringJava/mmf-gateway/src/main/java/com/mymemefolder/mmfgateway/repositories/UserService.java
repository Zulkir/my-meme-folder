package com.mymemefolder.mmfgateway.repositories;

import com.mymemefolder.mmfgateway.utils.ActionResult;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByName(String name);
    ActionResult registerUser(String username, String password, String email);
}
