package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllByUserFolderId(int userId, int folderId);
    Optional<Image> getByKey(String key);
    Image create(int userId, int folderId, String name, InputStream stream) throws InvalidOperationException;
    void update(Image image);
    void delete(String key);
}
