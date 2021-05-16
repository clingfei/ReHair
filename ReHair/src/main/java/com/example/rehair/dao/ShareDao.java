package com.example.rehair.dao;

import com.example.rehair.model.ReturnData;

import java.util.ArrayList;
import java.util.Date;

public interface ShareDao {
    // int status = userDao.addFriend(userName, textContent, likeCount, date);
    // userDao.createShare(userName, textContent, likeCount, date);
    int createShare(String userName, String textContent, Date date, int seqid);

    // String pathToPic = userDao.findArticlePhotoPath(userName, time);
    String findArticlePhotoPath(String userName, Date time);
    void reloadArticlePhotoPath(String userName, Date date, String pathToPic);

    ArrayList queryArticleByName(String userName);

    ReturnData deleteArticle(String userName, int seqid);

    int getShareCount(String userName);
}
