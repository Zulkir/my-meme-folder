package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithSize;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;

public interface ImageStorageService {
    InputStreamWithSize readByKey(int userId, String key) throws DataNotFoundException;
    void save(int userId, String key, InputStreamWithSize streamWithLength) throws InvalidOperationException;
    void delete(int userId, String key) throws DataNotFoundException;
}

