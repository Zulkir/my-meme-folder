package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.users.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaImageService implements ImageService {
    private final ImageRepository repository;

    public JpaImageService(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Image> getAllByPath(User user, String path) {
        return repository.findByUserFolderPath(user + path);
    }

    @Override
    public Optional<Image> getByKey(String key) {
        return Optional.empty();
    }
}
