package com.example.rehair.service;


import com.example.rehair.model.Article;
import com.example.rehair.model.Image;
import com.example.rehair.model.ReturnData;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// 接口向下转型，有点让人难以理解emm
public interface UserService {

    public ReturnData register(String userName, String passWd, String email);

    public ReturnData login(HttpServletRequest req, String userName, String passWd);

    public ReturnData addFriend(String userName, String futureFriendName);

    ReturnData setHead(String userName, String image);

    Image getHead(String userName);

    // 创建动态，同时发送到目标角落？
    // String res = userService.createShare(userName, content, likeCount, time);
    public ReturnData createShare(String userName, String textContent, String likeCount, String time);

    // String res = userService.uploadArticlePhoto(userName, time, b64encodeImg);
    public ReturnData uploadArticlePhoto(String userName, String time, String image, String imgType);

    public ArrayList<Article> getArticle(String userName, int start, int bias);

    public String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName, String modifyType, String otherOptions);

}


