package com.example.rehair.model;

import java.util.ArrayList;

public class Article {
    String userName;
    String time;
    String text;
    ArrayList<String> photos;
    int count;
    int seqid;


    public Article(String userName, String time, String text, ArrayList<String> photos, int count, int seqid) {
        this.userName = userName;
        this.time = time;
        this.text = text;
        this.photos = photos;
        this.count = count;
        this.seqid = seqid;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getTime() {
        return this.time;
    }

    public String getText() {
        return this.text;
    }

    public ArrayList<String> getPhotos() {
        return this.photos;
    }

    public int getCount() {
        return this.count;
    }

    public int getSeqid() {
        return this.seqid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }
}
