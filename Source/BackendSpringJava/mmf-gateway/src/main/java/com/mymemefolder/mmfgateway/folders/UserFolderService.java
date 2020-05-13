package com.mymemefolder.mmfgateway.folders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymemefolder.mmfgateway.users.User;
import com.mymemefolder.mmfgateway.users.UserService;
import com.mymemefolder.mmfgateway.utils.UncheckedWrapperException;
import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFolderService implements FolderService {
    private final UserService userService;
    private final ObjectMapper jsonMapper;

    public UserFolderService(UserService userService) {
        this.userService = userService;
        jsonMapper = new ObjectMapper();
    }

    @Override
    public List<Folder> getStructure(User user) {
        return getRootFolders(user);
    }

    @Override
    public List<Folder> newFolder(User user, String path, String newFolderName) throws InvalidOperationException {
        var rootFolders = getRootFolders(user);
        var childFolders = rootFolders;
        path = path.substring(1);
        while (path.length() > 0) {
            var nextSlashIndex = path.indexOf('/');
            if (nextSlashIndex < 0)
                nextSlashIndex = path.length();
            var childName = path.substring(0, nextSlashIndex);
            path = path.equals(childName) ? "" : path.substring(nextSlashIndex + 1);
            childFolders = childFolders.stream()
                    .filter(f -> f.getName().equals(childName))
                    .findAny()
                    .orElseThrow(() -> new InvalidOperationException("Invalid path"))
                    .getChildren();
        }
        childFolders.add(new Folder(newFolderName));
        setRootFolders(user, rootFolders);
        return rootFolders;
    }

    @Override
    public List<Folder> renameFolder(User user, String path, String newName) throws InvalidOperationException {
        var rootFolders = getRootFolders(user);
        var folder = (Folder)null;
        var childFolders = rootFolders;
        path = path.substring(1);
        while (path.length() > 0) {
            var nextSlashIndex = path.indexOf('/');
            if (nextSlashIndex < 0)
                nextSlashIndex = path.length();
            var childName = path.substring(0, nextSlashIndex);
            path = path.equals(childName) ? "" : path.substring(nextSlashIndex + 1);
            folder = childFolders.stream()
                    .filter(f -> f.getName().equals(childName))
                    .findAny()
                    .orElseThrow(() -> new InvalidOperationException("Invalid path"));
            childFolders = folder.getChildren();
        }
        if (folder == null)
            throw new InvalidOperationException("Invalid path");
        folder.setName(newName);
        setRootFolders(user, rootFolders);
        return rootFolders;
    }

    @Override
    public List<Folder> deleteFolder(User user, String path) throws InvalidOperationException {
        var rootFolders = getRootFolders(user);
        var siblings = (List<Folder>)null;
        var childFolders = rootFolders;
        var childName = (String)null;
        path = path.substring(1);
        while (path.length() > 0) {
            var nextSlashIndex = path.indexOf('/');
            if (nextSlashIndex < 0)
                nextSlashIndex = path.length();
            childName = path.substring(0, nextSlashIndex);
            var childNameLoc = childName;
            path = path.equals(childName) ? "" : path.substring(nextSlashIndex + 1);
            siblings = childFolders;
            childFolders = childFolders.stream()
                    .filter(f -> f.getName().equals(childNameLoc))
                    .findAny()
                    .orElseThrow(() -> new InvalidOperationException("Invalid path"))
                    .getChildren();
        }
        if (siblings == null)
            throw new InvalidOperationException("Invalid path");
        var folderNameToRemove = childName;
        siblings.removeIf(f -> f.getName().equals(folderNameToRemove));
        setRootFolders(user, rootFolders);
        return rootFolders;
    }

    private List<Folder> getRootFolders(User user) {
        try {
            return jsonMapper.readValue(user.getFolderStructure(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new UncheckedWrapperException(e);
        }
    }

    private void setRootFolders(User user, List<Folder> rootFolders) {
        try {
            var newFolderStructure = jsonMapper.writeValueAsString(rootFolders);
            user.setFolderStructure(newFolderStructure);
            userService.updateUser(user);
        } catch (JsonProcessingException e) {
            throw new UncheckedWrapperException(e);
        }
    }
}
