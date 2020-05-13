package com.mymemefolder.mmfgateway.images;

import com.mymemefolder.mmfgateway.utils.DataNotFoundException;
import com.mymemefolder.mmfgateway.utils.InputStreamWithLength;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class CTempImageStorageService implements ImageStorageService {
    @Override
    public InputStreamWithLength readImageByKey(String key) throws DataNotFoundException {
        try {
            var file = new File("C:/Temp/mmf-sss/" + key);
            if (!file.exists())
                throw new DataNotFoundException("Image was not found");
            var size = file.length();
            var stream = new FileInputStream(file);
            return new InputStreamWithLength(stream, size);
        } catch (FileNotFoundException e) {
            throw new DataNotFoundException("Image was not found");
        }
    }
}
