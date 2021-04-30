package com.example.rehair.service;


import org.springframework.beans.factory.annotation.Autowired;

public interface Service {
    public String queryUserById(String q);

    int insertUser(String userName, String passWd, String email);
}
