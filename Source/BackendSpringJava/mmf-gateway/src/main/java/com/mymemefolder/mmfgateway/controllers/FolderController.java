package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.UserService;
import com.mymemefolder.mmfgateway.security.NeedsLoginException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FolderController {
    UserService userService;

    public FolderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/myfolder/structure")
    @ResponseBody
    public List<Folder> getFolderStructure(Principal principal) {
        var user = userService.getUserByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        //return user.getFolderStructure();

        var root = new File("C:/temp/mmf-root/");
        var folder = folderFromFileSystem(root);
        return folder.getChildren();
    }

    private static Folder folderFromFileSystem(File realFolder) {
        var subDirectories = Optional.ofNullable(realFolder.listFiles(File::isDirectory)).orElseGet(() -> new File[0]);
        return new Folder(
            realFolder.getName(),
            Arrays.stream(subDirectories)
                .map(FolderController::folderFromFileSystem)
                .collect(Collectors.toList()));
    }

    @GetMapping("/api/myfolder/list")
    @ResponseBody
    public List<ImageWithThumbnail> getImageList(Principal principal, String path) throws IOException, NeedsLoginException {
        if (principal == null)
            throw new NeedsLoginException();
        var root = new File("C:/temp/mmf-root" + path);
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
