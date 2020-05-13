package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.users.User;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllByPath(User user, String folderPath);
    Optional<Image> getByKey(String key);
}
