package com.mymemefolder.mmfgateway.repositories;

import com.mymemefolder.mmfgateway.controllers.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.ActionResult;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByName(String name);
    User getUserByName(String name) throws DataNotFoundException;
    ActionResult registerUser(String username, String password, String email);
}
