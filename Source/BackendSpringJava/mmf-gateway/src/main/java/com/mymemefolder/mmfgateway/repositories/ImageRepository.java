package com.mymemefolder.mmfgateway.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    Image findByKey(String key);
    List<Image> findByUserFolderPath(String userFolderPath);
}
