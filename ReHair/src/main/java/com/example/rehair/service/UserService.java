package com.example.rehair.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




public interface UserService {
    String queryUserById(String q);

    public int insertUser(String userName, String passWd, String email);
}
