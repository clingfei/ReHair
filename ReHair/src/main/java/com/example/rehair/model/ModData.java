package com.example.rehair.model;

public class ModData {
    int score;
    String image;

    public ModData(int score, String image) {
        this.score = score;
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public int getScore() {
        return this.score;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
