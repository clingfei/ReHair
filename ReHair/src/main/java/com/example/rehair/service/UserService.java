package com.example.rehair.service;


import com.example.rehair.model.LoginData;
import com.example.rehair.model.RegisterData;

import javax.servlet.http.HttpServletRequest;


public interface UserService {
    String queryUserById(String q);

    public RegisterData register(String userName, String passWd, String email);

    public LoginData login(HttpServletRequest req, String userName, String passWd);

}

