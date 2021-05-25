package com.example.rehair.model;

public class PostReHair {
    String faceType;
    String hairType;
    String image;
    int score;
    public PostReHair(int score, String faceType, String hairType, String image) {
        this.score = score;
        this.faceType = faceType;
        this.hairType = hairType;
        this.image = image;
    }

    public int getScore() {
        return this.score;
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

    public void setScore(int score) {
        this.score = score;
    }
}
