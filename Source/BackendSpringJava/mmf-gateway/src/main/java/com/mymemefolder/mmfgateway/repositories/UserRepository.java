package com.mymemefolder.mmfgateway.repositories;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String name);
}
