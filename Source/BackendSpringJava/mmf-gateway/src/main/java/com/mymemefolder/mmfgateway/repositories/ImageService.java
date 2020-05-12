package com.mymemefolder.mmfgateway.repositories;

import java.util.List;

public interface ImageService {
    List<Image> findByPath(String path);
}
