package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import com.mymemefolder.mmfgateway.utils.UncheckedWrapperException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
public class JpaImageService implements ImageService {
    private final ImageRepository repository;
    private final ImageStorageService storageService;

    public JpaImageService(ImageRepository repository, ImageStorageService storageService) {
        this.repository = repository;
        this.storageService = storageService;
    }

    @Override
    public List<Image> getAllByUserFolderId(int userId, int folderId) {
        return repository.findByUserFolderPath(Image.userFolderId(userId, folderId));
    }

    @Override
    public Optional<Image> getByKey(String key) {
        return Optional.ofNullable(repository.findByKey(key));
    }

    @Override
    public Image create(int userId, int folderId, String name, InputStream stream) throws InvalidOperationException {
        try {
            var maxSize = 4 * (1 << 20);
            var data = stream.readNBytes(maxSize);
            if (data.length >= maxSize)
                throw new InvalidOperationException("Image data too large.");
            var thumbnailBase64 = fileToThumbnailBase64(data);
            var key = UUID.randomUUID().toString();
            try (var dataStream = new ByteArrayInputStream(data)) {
                storageService.save(userId, key, dataStream);
            }
            var image = new Image();
            image.setKey(key);
            image.setName(name);
            image.setTags("");
            image.setUserFolderPath(Image.userFolderId(userId, folderId));
            image.setThumbnailSource("data:image/jpg;base64, " + thumbnailBase64);
            image.setFullImageSource("/api/images/" + key);
            image.setFileSize(data.length);
            repository.save(image);
            return image;
        } catch (IOException e) {
            throw new UncheckedWrapperException(e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Image image) {
        repository.save(image);
    }

    //@Transactional
    @Override
    public void delete(String key) throws DataNotFoundException {
        var image = repository.findByKey(key);
        if (image == null)
            throw new DataNotFoundException("Image not found");
        repository.deleteById(image.getId());
        storageService.delete(image.getUserId(), key);
    }

    @Transactional
    @Override
    public void deleteAllByUserFolderId(int userId, int folderId) {
        var imagesToDelete = getAllByUserFolderId(userId, folderId);
        for (var image : imagesToDelete) {
            try {
                storageService.delete(userId, image.getKey());
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        }
        repository.deleteByUserFolderPath(Image.userFolderId(userId, folderId));
    }

    private static String fileToThumbnailBase64(byte[] fileData) throws IOException {
        try (var inStream = new ByteArrayInputStream(fileData);
             var outStream = new ByteArrayOutputStream()) {
            Thumbnails.of(inStream)
                    .size(196, 196)
                    .keepAspectRatio(true)
                    .outputFormat("jpg")
                    .toOutputStream(outStream);
            return Base64.getEncoder().encodeToString(outStream.toByteArray());
        }
    }
}
