package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import com.example.rehair.model.*;

import javax.annotation.Resource;

@Service
public class ServiceImpl implements UserService {

    @Resource
    public UserDao userDao;

    public String queryUserById(String q) {
        return "Hello";
    }

    public int insertUser(String userName, String passWd, String email) {
        System.out.println(userName);
        User user = new User(userName, passWd, email);
        return userDao.insertUser(user);
    }

}
