package com.example.rehair.service;


import com.example.rehair.model.Article;
import com.example.rehair.model.Image;
import com.example.rehair.model.ReturnData;
import com.example.rehair.model.ShareReturn;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// 接口向下转型，有点让人难以理解emm
public interface UserService {

    //注册
    ReturnData register(String userName, String passWd, String email);

    //登录
    ReturnData login(String userName, String passWd);

    //添加好友
    ReturnData addFriend(String userName, String futureFriendName);

    //设置头像
    ReturnData setHead(String userName, String image);

    //获取头像
    Image getHead(String userName);

    // 创建动态，同时发送到目标角落？
    // String res = userService.createShare(userName, content, likeCount, time);
    ShareReturn createShare(String userName, String textContent, String time);

    //上传动态照片
    // String res = userService.uploadArticlePhoto(userName, time, b64encodeImg);
    ReturnData uploadArticlePhoto(String userName, String time, String image, String imgType);

    //获取动态
    ArrayList<Article> getArticle(String userName, int start, int bias);

    //修改照片
    String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName, String modifyType, String otherOptions);

    //删除动态
    ReturnData deleteArticle(String userName, int seqid);

}


