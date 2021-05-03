package com.example.rehair.service;


import com.example.rehair.model.LoginData;
import com.example.rehair.model.RegisterData;

import javax.servlet.http.HttpServletRequest;

// 接口向下转型，有点让人难以理解emm
public interface UserService {
    String queryUserById(String q);

    public RegisterData register(String userName, String passWd, String email);

    public LoginData login(HttpServletRequest req, String userName, String passWd);

    public String addFriend(String userName, String futureFriendName);

    // 创建动态，同时发送到目标角落？
    // String res = userService.createShare(userName, content, likeCount, time);
    public String createShare(String userName, String textContent, String likeCount, String time);

    // String res = userService.uploadArticlePhoto(userName, time, b64encodeImg);
    public String uploadArticlePhoto(String userName, String time, String b64encodeImg, String imgType);
}


