package com.mymemefolder.mmfgateway.images;

public class ImageWithThumbnail {
    private String name;
    private String tags;
    private String thumbnailSrc;
    private String fullImageSource;

    public ImageWithThumbnail(String name, String tags, String thumbnailSrc, String fullImageSource) {
        this.name = name;
        this.tags = tags;
        this.thumbnailSrc = thumbnailSrc;
        this.fullImageSource = fullImageSource;
    }

    public ImageWithThumbnail(Image image) {
        name = image.getName();
        tags = image.getTags();
        thumbnailSrc = image.getThumbnailSource();
        fullImageSource = image.getFullImageSource();
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

    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }

    public String getFullImageSource() {
        return fullImageSource;
    }

    public void setFullImageSource(String fullImageSource) {
        this.fullImageSource = fullImageSource;
    }
}
