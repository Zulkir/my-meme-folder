package com.mymemefolder.mmfgateway.users;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.ActionResult;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByName(String name);
    User getUserByName(String name) throws DataNotFoundException;
    ActionResult registerUser(String username, String password, String email);
    void updateUser(User user);
}
