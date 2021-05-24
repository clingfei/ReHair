package com.example.rehair.service;


import com.example.rehair.model.Article;
import com.example.rehair.model.Image;
import com.example.rehair.model.ReturnData;
import com.example.rehair.model.ShareReturn;
import org.springframework.stereotype.Service;
import com.example.rehair.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

// 接口向下转型，有点让人难以理解emm
@Service
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

    //删除账号
    void delAct(String userName);

    //返回好友列表
    List<String> showFriend(String userName, int start, int bias);

    //取关，友尽
    ReturnData unfollow(String userName, String name);

    boolean isFriend(String userName, String friendname);

    List<String> getTepPhoto();

    List<String> getHairType(int faceType);

    ModData modPic(String userName, String faceType, String hairType, String image, String imgType);
}


