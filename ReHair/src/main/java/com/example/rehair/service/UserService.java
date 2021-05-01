package com.example.rehair.service;


import com.example.rehair.model.LoginData;
import com.example.rehair.model.RegisterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface UserService {
    String queryUserById(String q);

    public RegisterData register(String userName, String passWd, String email);

    public LoginData queryUserByName(String userName, String passWd);
}
