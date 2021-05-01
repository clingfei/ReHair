package com.example.rehair.model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class User {
    private String userName;
    private String passWd;
    private String email;


    public User(String userName, String passWd, String email) {
        this.userName = userName;
        this.passWd = passWd;
        this.email = email;
    }

    public User() {

    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWd() {
        return this.passWd;
    }

    public Object getEmail() {
        return this.email;
    }
}


