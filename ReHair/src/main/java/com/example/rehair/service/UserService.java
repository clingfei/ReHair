package com.example.rehair.service;


import com.example.rehair.model.LoginData;
import com.example.rehair.model.RegisterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




public interface UserService {
    String queryUserById(String q);

    public RegisterData insertUser(String userName, String passWd, String email);

    public LoginData queryUserByName(String userName, String passWd);
}
