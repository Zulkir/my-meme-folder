package com.mymemefolder.mmfgateway.images;

public class ImageViewData {
    private String id;
    private String key;
    private String name;
    private String tags;
    private String thumbnailSrc;
    private String fullImageSource;

    public ImageViewData() { }

    public ImageViewData(Image image) {
        id = String.valueOf(image.getId());
        key = image.getKey();
        name = image.getName();
        tags = image.getTags();
        thumbnailSrc = image.getThumbnailSource();
        fullImageSource = image.getFullImageSource();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
