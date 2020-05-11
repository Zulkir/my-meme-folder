package com.mymemefolder.mmfgateway.controllers;

import com.mymemefolder.mmfgateway.repositories.User;

import java.io.IOException;
import java.util.List;

public interface FolderService {
    List<Folder> getStructure(User user);
    List<Folder> newFolder(User user, String path, String newFolderName) throws InvalidOperationException;
    List<Folder> renameFolder(User user, String path, String newName) throws InvalidOperationException;
    List<Folder> deleteFolder(User user, String path) throws InvalidOperationException;

    List<ImageWithThumbnail> getImageList(User user, String path) throws IOException;
}
