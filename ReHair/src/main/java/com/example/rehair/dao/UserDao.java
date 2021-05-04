package com.example.rehair.dao;
import com.example.rehair.model.*;
import java.util.Date;
import java.util.Map;

// 持久层，专门用于配置数据库的
// 需要在数据库中开启信息的操作，效果还算是ok的
// 接口专门用于数据库的各种处理过程的

public interface UserDao {
    ReturnData insertUser(User user);

    String queryUserByName(String userName);

    int addFriend(String userName, String futureFriendName);

    // int status = userDao.addFriend(userName, textContent, likeCount, date);
    // userDao.createShare(userName, textContent, likeCount, date);
    int createShare(String userName, String textContent, String likeCount, Date date);

    // String pathToPic = userDao.findArticlePhotoPath(userName, time);
    String findArticlePhotoPath(String userName, Date time);
    void reloadArticlePhotoPath(String userName, Date date, String pathToPic);
}