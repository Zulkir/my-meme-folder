package com.mymemefolder.mmfgateway.users;

public class UserInfoViewData {
    private String username;
    private String email;
    private Boolean folderIsPublic;
    private Boolean imagesArePublic;

    public UserInfoViewData(String username, String email, Boolean folderIsPublic, Boolean imagesArePublic) {
        this.username = username;
        this.email = email;
        this.folderIsPublic = folderIsPublic;
        this.imagesArePublic = imagesArePublic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFolderIsPublic() {
        return folderIsPublic;
    }

    public void setFolderIsPublic(Boolean folderIsPublic) {
        this.folderIsPublic = folderIsPublic;
    }

    public Boolean getImagesArePublic() {
        return imagesArePublic;
    }

    public void setImagesArePublic(Boolean imagesArePublic) {
        this.imagesArePublic = imagesArePublic;
    }
}
