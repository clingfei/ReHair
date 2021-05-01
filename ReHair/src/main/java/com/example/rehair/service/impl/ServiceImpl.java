package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ServiceImpl implements UserService {
    @Resource
    public UserDao userDao;

    public String queryUserById(String q) {
        return "Hello";
    }

    private String getPath() throws FileNotFoundException {
        File file = new File(ResourceUtils.getURL("classpath:").getPath());
        return file.getParentFile().getParent();
    }

    public RegisterData register(String userName, String passWd, String email) {
        System.out.println(userName);
        User user = new User(userName, passWd, email);
        RegisterData data = userDao.insertUser(user);
        if (data.getFlag() == true) {
            String path = "";
            try {
                path = getPath();
            } catch(FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            path = path + "\\src\\main\\resources\\ReHairSource\\" + userName;
            String[] subPath = new String[] {"\\headPhoto", "\\Photo", "\\sharePhoto", "\\temp"};
            File file = new File(path);
            file.mkdir();
            for(int i=0; i<4; ++i ) {
                File file1 = new File(path + subPath[i]);
                file1.mkdir();
            }
        }

        return data;
    }

    public LoginData queryUserByName(String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);
        if (passWd.equals(passWord)) {
            return new LoginData(true, "");
        }
        return new LoginData(false, "UserName or PassWord is Error.");
    }
}
