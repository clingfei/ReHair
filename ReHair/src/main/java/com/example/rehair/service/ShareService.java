package com.example.rehair.service;

import com.example.rehair.model.Article;
import com.example.rehair.model.ReturnData;
import com.example.rehair.model.ShareReturn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface ShareService {

    //删除动态
    ReturnData deleteArticle(String userName, int seqid);

    //上传动态照片
    // String res = userService.uploadArticlePhoto(userName, time, b64encodeImg);
    ReturnData uploadArticlePhoto(String userName, String time, String image, String imgType);

    //获取动态
    ArrayList<Article> getArticle(String userName, int start, int bias);

    ArrayList<Article> getUserArticle(String userName, int start, int bias);
    // 创建动态，同时发送到目标角落？
    // String res = userService.createShare(userName, content, likeCount, time);
    ShareReturn createShare(String userName, String textContent, String time);
}
