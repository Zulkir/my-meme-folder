package com.mymemefolder.mmfgateway.controllers;

public interface ImageStorageService {
    InputStreamWithLength readImageByKey(String key) throws DataNotFoundException;
}

