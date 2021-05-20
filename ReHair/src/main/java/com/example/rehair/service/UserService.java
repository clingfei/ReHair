package com.example.rehair.service;


import com.example.rehair.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// 接口向下转型，有点让人难以理解emm
public interface UserService {
    //设置头像
    ReturnData setHead(String userName, String image);

    //获取头像
    Image getHead(String userName);

    //注册
    ReturnData register(String userName, String passWd, String email);

    //登录
    ReturnData login(String userName, String passWd);

    //添加好友
    ReturnData addFriend(String userName, String futureFriendName);

    //返回个人主页信息
    UserInfo personalPage(String userName);

    //修改照片
    String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName, String modifyType, String otherOptions);
}


