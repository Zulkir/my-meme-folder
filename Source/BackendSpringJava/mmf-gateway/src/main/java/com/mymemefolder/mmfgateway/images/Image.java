package com.mymemefolder.mmfgateway.images;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="img_key")
    private String key;
    private String userFolderPath;
    private String name;
    private String tags;
    private String fullImageSource;
    @Column(columnDefinition = "TEXT")
    private String thumbnailSource;
    private Integer fileSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String path) {
        this.key = path;
    }

    public String getUserFolderPath() {
        return userFolderPath;
    }

    public void setUserFolderPath(String userFolderPath) {
        this.userFolderPath = userFolderPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFullImageSource() {
        return fullImageSource;
    }

    public void setFullImageSource(String fullImageSource) {
        this.fullImageSource = fullImageSource;
    }

    public String getThumbnailSource() {
        return thumbnailSource;
    }

    public void setThumbnailSource(String thumbnailSource) {
        this.thumbnailSource = thumbnailSource;
    }

    public int getUserId() {
        var userFolderPath = getUserFolderPath();
        return Integer.parseInt(userFolderPath.substring(0, userFolderPath.indexOf("-")));
    }

    public static String userFolderId(int userId, int folderId) {
        return String.format("%d-%d", userId, folderId);
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
}
