package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import com.mymemefolder.mmfgateway.utils.UncheckedWrapperException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

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
    public List<Image> getAllByPath(User user, String path) {
        return repository.findByUserFolderPath(user.getId() + path);
    }

    @Override
    public Optional<Image> getByKey(String key) {
        return Optional.ofNullable(repository.findByKey(key));
    }

    @Override
    public Image addNew(User user, String path, String name, InputStream stream) throws InvalidOperationException {
        try {
            var maxSize = 1 << 22;
            var data = stream.readNBytes(maxSize);
            if (data.length >= maxSize)
                throw new InvalidOperationException("Image data too large.");
            var thumbnailBase64 = fileToThumbnailBase64(data);
            var key = UUID.randomUUID().toString();
            try (var dataStream = new ByteArrayInputStream(data)) {
                storageService.saveImage(key, dataStream);
            }
            var image = new Image();
            image.setKey(key);
            image.setName(name);
            image.setTags("");
            image.setUserFolderPath(user.getId() + path);
            image.setThumbnailSource("data:image/jpg;base64, " + thumbnailBase64);
            image.setFullImageSource("/images/" + key);
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
    public void delete(String key) {
        // todo
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
