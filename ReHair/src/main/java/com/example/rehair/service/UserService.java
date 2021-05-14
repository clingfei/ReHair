package com.example.rehair.service;


import com.example.rehair.model.Article;
import com.example.rehair.model.Image;
import com.example.rehair.model.ReturnData;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// 接口向下转型，有点让人难以理解emm
public interface UserService {

    ReturnData register(String userName, String passWd, String email);

    ReturnData login(String userName, String passWd);

    ReturnData addFriend(String userName, String futureFriendName);

    ReturnData setHead(String userName, String image);

    Image getHead(String userName);

    // 创建动态，同时发送到目标角落？
    // String res = userService.createShare(userName, content, likeCount, time);
    ReturnData createShare(String userName, String textContent, String likeCount, String time);

    // String res = userService.uploadArticlePhoto(userName, time, b64encodeImg);
    ReturnData uploadArticlePhoto(String userName, String time, String image, String imgType);

    ArrayList<Article> getArticle(String userName, int start, int bias);

    String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName, String modifyType, String otherOptions);

}


