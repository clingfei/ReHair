package com.example.rehair.model;

public class PostReHair {
    String faceType;
    String hairType;
    String image;
    public PostReHair(String faceType, String hairType, String image) {
        this.faceType = faceType;
        this.hairType = hairType;
        this.image = image;
    }

    public String getFaceType() {
        return this.faceType;
    }

    public String getHairType() {
        return this.hairType;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFaceType(String faceType) {
        this.faceType = faceType;
    }

    public void setHairType(String hairType) {
        this.hairType = hairType;
    }
}
