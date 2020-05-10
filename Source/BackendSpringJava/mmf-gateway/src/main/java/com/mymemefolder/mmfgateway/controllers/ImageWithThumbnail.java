package com.mymemefolder.mmfgateway.controllers;

public class ImageWithThumbnail {
    private String name;
    private String url;
    private String thumbnailSrc;

    public ImageWithThumbnail(String name, String url, String thumbnailSrc) {
        this.name = name;
        this.url = url;
        this.thumbnailSrc = thumbnailSrc;
    }

    //region Getters Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }
    //endregion
}
