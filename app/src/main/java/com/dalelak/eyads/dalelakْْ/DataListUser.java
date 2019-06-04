package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/20/2018.
 */

public class DataListUser {
    private String id;
    private String faceID;
    private String firstName;
    private String link;
    private String lastName;
    private String imageUri;
    private String ImageUrlThumb;
    private String showMyprofile;
    private String lastseen;
    private String showLastname;
    private String Status;

    public DataListUser() {
    }

    public DataListUser(String id, String faceID, String firstName, String link, String lastName, String imageUri, String imageUrlThumb, String showMyprofile, String lastseen, String showLastname, String status) {
        this.id = id;
        this.faceID = faceID;
        this.firstName = firstName;
        this.link = link;
        this.lastName = lastName;
        this.imageUri = imageUri;
        ImageUrlThumb = imageUrlThumb;
        this.showMyprofile = showMyprofile;
        this.lastseen = lastseen;
        this.showLastname = showLastname;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getShowLastname() {
        return showLastname;
    }

    public void setShowLastname(String showLastname) {
        this.showLastname = showLastname;
    }

    public String getShowMyprofile() {
        return showMyprofile;
    }

    public void setShowMyprofile(String showMyprofile) {
        this.showMyprofile = showMyprofile;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaceID() {
        return faceID;
    }

    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUrlThumb() {
        return ImageUrlThumb;
    }

    public void setImageUrlThumb(String imageUrlThumb) {
        ImageUrlThumb = imageUrlThumb;
    }
}
