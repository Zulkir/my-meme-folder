package com.mymemefolder.mmfgateway.folders;

import com.mymemefolder.mmfgateway.utils.InvalidOperationException;
import com.mymemefolder.mmfgateway.users.User;

import java.util.List;

public interface FolderService {
    List<Folder> getStructure(User user);
    List<Folder> newFolder(User user, String path, String newFolderName) throws InvalidOperationException;
    List<Folder> renameFolder(User user, String path, String newName) throws InvalidOperationException;
    List<Folder> deleteFolder(User user, String path) throws InvalidOperationException;
}
