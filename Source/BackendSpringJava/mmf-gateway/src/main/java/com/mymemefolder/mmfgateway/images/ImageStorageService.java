package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithLength;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;

import java.io.InputStream;

public interface ImageStorageService {
    InputStreamWithLength readByKey(int userId, String key) throws DataNotFoundException;
    void save(int userId, String key, InputStream stream) throws InvalidOperationException;
    void delete(int userId, String key) throws DataNotFoundException;
}

