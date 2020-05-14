package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllByPath(User user, String folderPath);
    Optional<Image> getByKey(String key);
    Image addNew(User user, String path, String name, InputStream stream) throws InvalidOperationException;
    void delete(String key);
}
