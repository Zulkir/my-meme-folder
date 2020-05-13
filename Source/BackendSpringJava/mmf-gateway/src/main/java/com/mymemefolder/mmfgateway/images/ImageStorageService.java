package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithLength;

public interface ImageStorageService {
    InputStreamWithLength readImageByKey(String key) throws DataNotFoundException;
}

