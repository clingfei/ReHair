package com.example.rehair.model;

public class UserInfo {
    String userName;
    String email;
    String image;

    public UserInfo(String userName, String email, String image) {
        this.userName = userName;
        this.email = email;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getImage() {
        return this.image;
    }

    public String getEmail() {
        return this.email;
    }
}
