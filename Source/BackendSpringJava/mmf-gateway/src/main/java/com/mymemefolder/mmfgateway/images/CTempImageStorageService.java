package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithLength;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import com.mymemefolder.mmfgateway.utils.UncheckedWrapperException;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CTempImageStorageService implements ImageStorageService {
    @Override
    public InputStreamWithLength readByKey(int userId, String key) throws DataNotFoundException {
        try {
            var file = new File("C:/Temp/mmf-sss/" + userId + "/" + key);
            if (!file.exists())
                throw new DataNotFoundException("Image was not found");
            var size = file.length();
            var stream = new FileInputStream(file);
            return new InputStreamWithLength(stream, size);
        } catch (FileNotFoundException e) {
            throw new DataNotFoundException("Image was not found");
        }
    }

    @Override
    public void save(int userId, String key, InputStream stream) throws InvalidOperationException {
        try {
            var folderPath = "C:/Temp/mmf-sss/" + userId + "/";
            var folder = new File(folderPath);
            if (!folder.exists() && !folder.mkdirs())
                throw new IOException("Failed to create a folder");
            var file = new File(folderPath + key);
            if (file.exists())
                throw new InvalidOperationException("File with this key already exists");
            try (var outputStream = new FileOutputStream(file)) {
                stream.transferTo(outputStream);
            }
        } catch (IOException e) {
            throw new UncheckedWrapperException(e);
        }
    }

    @Override
    public void delete(int userId, String key) throws DataNotFoundException {
        try {
            var file = new File("C:/Temp/mmf-sss/" + userId + "/" + key);
            if (!file.exists())
                throw new DataNotFoundException("Image was not found");
            if (!file.delete())
                throw new IOException("Failed to delete the file");
        } catch (FileNotFoundException e) {
            throw new DataNotFoundException("Image was not found");
        }
        catch (IOException e) {
            throw new UncheckedWrapperException(e);
        }
    }
}
