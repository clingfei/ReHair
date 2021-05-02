package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

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

    private void createDir(String userName) {
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

    private Boolean match(String password, String encryptedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.matches(password, encryptedPassword);
    }

    private String hashEncode(String passWd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(passWd);
    }

    public RegisterData register(String userName, String passWd, String email) {
        System.out.println(passWd);
        passWd = hashEncode(passWd);
        // 仅仅存放hash code，长度超过限制
        System.out.println(passWd);

        User user = new User(userName, passWd, email);
        RegisterData data = userDao.insertUser(user);
        if (data.getFlag() == true) {
            createDir(userName);
        }

        return data;
    }

    public LoginData login(HttpServletRequest req, String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);

        if (match(passWd, passWord)) {
            return new LoginData(true, "");
        }
        return new LoginData(false, "UserName or PassWord is Error.");
    }


    @Override
    public String addFriend(String userName, String futureFriendName) {
        // 如何判断是否为朋友呢？可以设置在前台，一开始初始化时，需要请求好友列表信息
        // 同时需要给出每个好友的头像等信息
        // 还有对应的朋友圈信息？还算是比较高级的哦

        int status = userDao.addFriend(userName, futureFriendName);
        // 直接调用了数据库而已
        // 添加朋友是比较简单的
        if(status == 1) return "succeed";
        else return "fail";
    }
}
