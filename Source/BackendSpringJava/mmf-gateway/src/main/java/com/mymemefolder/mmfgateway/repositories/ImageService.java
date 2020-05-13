package com.mymemefolder.mmfgateway.repositories;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllByPath(User user, String folderPath);
    Optional<Image> getByKey(String key);
}
