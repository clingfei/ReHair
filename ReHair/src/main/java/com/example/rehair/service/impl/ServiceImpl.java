package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;

import javax.annotation.Resource;

@Service
public class ServiceImpl implements UserService {

    @Resource
    public UserDao userDao;

    public String queryUserById(String q) {
        return "Hello";
    }

    public RegisterData insertUser(String userName, String passWd, String email) {
        System.out.println(userName);
        User user = new User(userName, passWd, email);
        return userDao.insertUser(user);
    }

    public LoginData queryUserByName(String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);
        if (passWd.equals(passWord)) {
            return new LoginData(true, "");
        }
        return new LoginData(false, "UserName or PassWord is Error.");
    }
}
