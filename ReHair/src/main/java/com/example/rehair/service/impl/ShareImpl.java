package com.example.rehair.service.impl;

import com.example.rehair.dao.ShareDao;
import com.example.rehair.dao.UserDao;
import com.example.rehair.model.Article;
import com.example.rehair.model.ShareReturn;
import com.example.rehair.service.ShareService;
import com.example.rehair.utils.Utils;
import com.example.rehair.model.ReturnData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class ShareImpl implements ShareService {
    @Resource
    private ShareDao shareDao;

    public ReturnData deleteArticle(String userName, int id) {
        int seqid = shareDao.getShareCount(userName);
        if(id > seqid || id <= 0)
            return new ReturnData(false, "Illegal seqid.");

        return shareDao.deleteArticle(userName, id);
    }

    public ShareReturn createShare(String userName, String textContent, String time) {
        Utils.createShareDir(userName, time);
        // 此时暂时还没有上传动态的照片，可以将数据分开处理？
        // 这里面有很多处理格式化序列的内容，开发的稍微有点太混乱了
        // time需要转义处理
        Date date = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        try {
            // 忘记了异常处理
            date = sdf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int seqid = shareDao.getShareCount(userName) + 1;

        int status = shareDao.createShare(userName, textContent, date, seqid);

        if(status != -1) return new ShareReturn(true, status);
        else return new ShareReturn(false, 0);
    }

    public ReturnData uploadArticlePhoto(String userName, String time, String image, String imgType) {
        image = image.replace("\\", "");
        // time首先要转义
        Date date = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        try {
            date = sdf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pathToPic = shareDao.findArticlePhotoPath(userName, date);

        String prePath = null;
        String path = "";
        try {
            path = Utils.getPath();
            prePath = Utils.getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        path = path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\sharePhoto\\" + "time-" + time + "\\";

        image = image.substring(1, image.length()-1);

        Date qq=new Date();
        //日期格式函数
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        //将当前时间转换格式，返回String类型
        ReturnData res = new ReturnData();

        int i = 1;
        for (String img : image.split(",")) {
            img = img.replace("\"", "");
            String docName=df.format(qq) + "-" + i + "." + imgType;
            String newpath = path + docName;

            // 2020.5.4 完成了基本文件路径的更新了
            pathToPic = pathToPic + newpath + ";";
            shareDao.reloadArticlePhotoPath(userName, date, pathToPic);
            String parentPath = prePath + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\sharePhoto\\" + "time-" + time;

            try {
                res.setFlag(Utils.base64StrToFile(img, docName, parentPath));
                if(!res.getFlag()) {
                    res.setErrorMsg("Error occured.Please check your input.");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            ++i;
        }
        return res;
    }

    public ArrayList<Article> getArticle(String userName, int start, int bias) {
        ArrayList<Map<String, Object>> result = shareDao.queryArticleByName(userName);
        if (result == null) {
            return new ArrayList<Article>();
        }
        ArrayList<Article> res = new ArrayList<Article>();
        for (int i = start; i < start+bias && i < result.size(); ++i) {
            String path = (String) result.get(i).get("photopath");;
            ArrayList<String> image = new ArrayList<String>();
            for (String imgpath : path.split(";")) {

                //System.out.println(imgToBase64(imgpath));
                image.add(Utils.imgToBase64(imgpath));
            }
            System.out.println(result.get(i).get("seqid"));
            Article article = new Article(
                    (String) result.get(i).get("username"),
                    (String) result.get(i).get("time"),
                    (String) result.get(i).get("content"),
                    image,
                    (int)result.get(i).get("count"),
                    (int)result.get(i).get("seqid")
            );
            res.add(article);
        }
        System.out.println(res.get(0).getUserName());
        return res;
    }
}
