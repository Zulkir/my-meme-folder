package com.mymemefolder.mmfgateway.repositories;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaImageService implements ImageService {
    private final ImageRepository repository;

    public JpaImageService(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Image> findByPath(String path) {
        return repository.findByPath(path);
    }
}
