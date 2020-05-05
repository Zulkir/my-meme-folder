package com.mymemefolder.mmfgateway.controllers;

public class ImageWithThumbnail {
    private String name;
    private String url;
    private byte[] thumbnail;

    public ImageWithThumbnail(String name, String url, byte[] thumbnail) {
        this.name = name;
        this.url = url;
        this.thumbnail = thumbnail;
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
    //endregion
}
