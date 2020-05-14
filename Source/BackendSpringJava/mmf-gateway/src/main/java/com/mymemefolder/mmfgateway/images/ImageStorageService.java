package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithLength;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;

import java.io.InputStream;

public interface ImageStorageService {
    InputStreamWithLength readImageByKey(int userId, String key) throws DataNotFoundException;
    void saveImage(int userId, String key, InputStream stream) throws InvalidOperationException;
}

