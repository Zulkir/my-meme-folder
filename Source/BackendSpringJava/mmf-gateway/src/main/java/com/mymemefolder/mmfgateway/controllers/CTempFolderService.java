package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.User;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CTempFolderService implements FolderService {
    @Override
    public List<Folder> getStructure(User user) {
        var realFolder = new File("C:/Temp/mmf-root/" + user.getUsername());
        var folder = folderFromFileSystem(realFolder);
        return folder.getChildren();
    }

    @Override
    public List<Folder> newFolder(User user, String path) throws InvalidOperationException {
        var realFolder = new File("C:/Temp/mmf-root/" + user.getUsername() + path);
        if (!realFolder.mkdirs())
            throw new InvalidOperationException("Failed to create the folder");
        return getStructure(user);
    }

    @Override
    public List<Folder> renameFolder(User user, String path, String newName) throws InvalidOperationException {
        var realPath = "C:/Temp/mmf-root/" + user.getUsername() + path;
        var renamedRealPath = realPath.substring(0, realPath.lastIndexOf('/') + 1) + newName;
        if (!new File(realPath).renameTo(new File(renamedRealPath)))
            throw new InvalidOperationException("Failed to rename the folder");
        return getStructure(user);
    }

    @Override
    public List<Folder> deleteFolder(User user, String path) throws InvalidOperationException {
        var realFolder = new File("C:/Temp/mmf-root/" + user.getUsername() + path);
        if (!realFolder.delete())
            throw new InvalidOperationException("Failed to delete the folder");
        return getStructure(user);
    }

    @Override
    public List<ImageWithThumbnail> getImageList(User user, String path) throws IOException {
        var root = new File("C:/temp/mmf-root/" + user.getUsername() + path);
        var list = new ArrayList<ImageWithThumbnail>();
        var files = root.listFiles();
        if (files != null)
            for (var file : files) {
                if (file.getName().endsWith(".png") ||
                        file.getName().endsWith(".jpg")) {
                    var thumbnailBase64 = fileToThumbnailBase64(file);
                    var iwt = new ImageWithThumbnail(
                            file.getName(),
                            path + "/" + file.getName(),
                            "data:image/jpg;base64, " + thumbnailBase64);
                    list.add(iwt);
                }
            }
        return list;
    }

    private static Folder folderFromFileSystem(File realFolder) {
        var subDirectories = Optional.ofNullable(realFolder.listFiles(File::isDirectory)).orElseGet(() -> new File[0]);
        return new Folder(
                realFolder.getName(),
                Arrays.stream(subDirectories)
                        .map(CTempFolderService::folderFromFileSystem)
                        .collect(Collectors.toList()));
    }

    private static String fileToThumbnailBase64(File file) throws IOException {
        try (var stream = new ByteArrayOutputStream()) {
            Thumbnails.of(file)
                    .size(196, 196)
                    .keepAspectRatio(true)
                    .outputFormat("jpg")
                    .toOutputStream(stream);
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        }
    }
}
