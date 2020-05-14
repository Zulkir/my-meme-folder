package com.mymemefolder.mmfgateway.folders;

import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
public class CTempFolderService implements FolderService {
    @Override
    public List<Folder> getStructure(User user) {
        var realFolder = new File("C:/Temp/mmf-root/" + user.getUsername());
        var folder = folderFromFileSystem(realFolder);
        return folder.getChildren();
    }

    @Override
    public List<Folder> newFolder(User user, String path, String newFolderName) throws InvalidOperationException {
        var realFolder = new File("C:/Temp/mmf-root/" + user.getUsername() + path + "/" + newFolderName);
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

    private static Folder folderFromFileSystem(File realFolder) {
        var subDirectories = Optional.ofNullable(realFolder.listFiles(File::isDirectory)).orElseGet(() -> new File[0]);
        return new Folder(
                realFolder.getName().hashCode(),
                realFolder.getName(),
                Arrays.stream(subDirectories)
                        .map(CTempFolderService::folderFromFileSystem)
                        .collect(Collectors.toList()));
    }
}
