package com.example.rehair.service.impl;

import com.example.rehair.dao.ShareDao;
import com.example.rehair.utils.Utils;
import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import org.apache.commons.io.FileUtils;

// base64编码处理类
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Math.min;

@Service
public class UserImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private ShareDao shareDao;

    private Boolean match(String password, String encryptedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.matches(password, encryptedPassword);
    }

    private String hashEncode(String passWd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(passWd);
    }

    public ReturnData register(String userName, String passWd, String email) {
        if (userName.length() < 1 || userName.length() > 10)
            return new ReturnData(false, "UserName Length is too short or too long.");
        if (passWd.length() < 6 || passWd.length() > 15)
            return new ReturnData(false, "PassWord length is too short or too long.");
        if (!(email.endsWith("@qq.com") || email.endsWith("@gmail.com") || email.endsWith("@sjtu.edu.cn")))
            return new ReturnData(false, "Email type error.");
        System.out.println(passWd);
        passWd = hashEncode(passWd);
        // 仅仅存放hash code，长度超过限制
        System.out.println(passWd);

        User user = new User(userName, passWd, email);
        ReturnData data = userDao.insertUser(user);
        if (data.getFlag() == true) {
            Utils.createDir(userName);
        }

        return data;
    }

    public ReturnData login(String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);
        if (passWord.equals("Please Signup first."))
            return new ReturnData(false, passWord);
        if (match(passWd, passWord)) {
            return new ReturnData(true, "");
        }
        return new ReturnData(false, "UserName or PassWord is Error.");
    }

    public UserInfo personalPage(String username) {
        Map<String, Object> res = userDao.queryUserPageByName(username);
        System.out.println(res);
        Image image = getHead(username);
        UserInfo userInfo = new UserInfo((String) res.get("username"), (String) res.get("email"), image.getImage());
        System.out.println(userInfo);
        return userInfo;
    }

    public ReturnData addFriend(String userName, String futureFriendName) {
        // 如何判断是否为朋友呢？可以设置在前台，一开始初始化时，需要请求好友列表信息
        // 同时需要给出每个好友的头像等信息
        // 还有对应的朋友圈信息？还算是比较高级的哦

        int status = userDao.addFriend(userName, futureFriendName);
        // 直接调用了数据库而已
        // 添加朋友是比较简单的
        if(status == 1) return new ReturnData(true, "");
        else return new ReturnData(false, "Error.");
    }

    public List<String> showFriend(String userName, int start, int bias) {
        List<Map<String, Object>> result = userDao.queryFriendByName(userName);
        List<String> res = new ArrayList<>();
        for (int i = start; i < min(result.size(), bias); i++) {
            res.add((String) result.get(i).get("friendname"));
        }
        System.out.println(res);
        return res;
    }

    public boolean isFriend(String userName, String friendname) {
        return userDao.isFriend(userName, friendname);
    }

    public ReturnData unfollow(String userName, String friendName) {
        int res = userDao.unfollow(userName, friendName);
        if (res == 0) {
            return new ReturnData(false, "Errors occured.");
        }
        return new ReturnData(true, "");
    }

    public ReturnData setHead(String userName, String image) {
        if(image.startsWith("data:")) {
            image = image.substring(23);
            System.out.println(image);
        }
        try{
            boolean flag = Utils.saveImg(userName, image);
            if (flag) return new ReturnData(true, "");
            else return new ReturnData(false, "setHead Error.");
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return new ReturnData(false, "Error occured!");
        }
    }

    /*
     * 首先去用户名目录下查找头像是否存在
     * 如果不存在，则返回默认的头像
     */
    public Image getHead(String userName) {
        String path;
        try {
            path = Utils.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Image("");
        }
        try {
            String userpath =  path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\headPhoto\\headPhoto.jpg";
            File file = new File(userpath);
            if (!file.exists())
                throw  new FileNotFoundException();
            return new Image(Utils.imgToBase64(userpath));
        } catch (FileNotFoundException e) {
            String defaultPath  = path + "\\src\\main\\resources\\ReHairSource\\defaultHeadPhoto.jpg";
            return new Image(Utils.imgToBase64(defaultPath));
        }
    }

    public void delAct(String userName) {
        //删除数据库
        userDao.deleteUser(userName);
        userDao.deleteFriend(userName);
        shareDao.deleteArticle(userName);
        shareDao.deletePhoto(userName);

        //删除文件夹
        try {
            String path = Utils.getPath();
            path = path + "\\src\\main\\resources\\ReHairSource\\" + userName;
            System.out.println(path);
            File file = new File(path);
            FileUtils.deleteDirectory(file);
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    // 以下就是整个处理图片类的实现，还是可以的
    // 每一个算法都需要使用网络通信和某个服务器获取下一步的消息
    // 毕竟，实现的算法是使用python写的，还有很多工作需要继续做
    public String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName,
                                String modifyType, String otherOptions) {
        FaceAlgorithm faceAlgorithm = new FaceAlgorithm(sourcePhotoName, targetPhotoName);
        String res = null;
        switch (modifyType) {
            case FaceAlgorithm.exchangeFaceChoose: {
                res = faceAlgorithm.exchangeFace(sourcePhotoName, targetPhotoName);
                break;
            }
            case FaceAlgorithm.recognizeFaceTypeChoose: {
                res = faceAlgorithm.recognizeFaceType(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.changeHairColorFaceChoose: {
                res = faceAlgorithm.changeHairColorFace(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.cutoutFaceChoose: {
                res = faceAlgorithm.cutoutFace(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.scoreFaceChoose: {
                res = faceAlgorithm.scoreFace(sourcePhotoName);
                break;
            }
        }
        return res;
    }

    public List<String> getTepPhoto() {
        String prePath = null;
        try {
            prePath = Utils.getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String basePath = "\\src\\main\\resources\\ReHairSource\\templatePhoto\\";
        List<String> res = new ArrayList<String>();
        for (int i=1; i<6; ++i) {
            res.add(Utils.imgToBase64(prePath + basePath + String.valueOf(i) + ".jpg"));
        }
        return res;
    }

    public List<String> getHairType(int faceType){
        String prePath = null;
        try {
            prePath = Utils.getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String basePath = "\\src\\main\\resources\\ReHairSource\\templatePhoto\\";
        List<String> res = new ArrayList<String>();
        for (int i=1; i<=10; ++i) {
            res.add(Utils.imgToBase64(prePath + basePath + String.valueOf(faceType) + '\\' + String.valueOf(i) + ".jpg"));
        }
        return res;
    }

   public ModData modPic(String userName,
                      String faceType, String hairType,
                      String image, String imgType) {
        int seqid = userDao.querySeqId(userName);
        System.out.println(seqid);
        if (seqid == -1) {
            System.out.println("Error");
        }
        else {
            try {
                String path = Utils.getPath();
                path = path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\Photo\\";
                Utils.base64StrToFile(image, seqid+"."+imgType, path);
                path = path + seqid + "." + imgType;
                userDao.savePhoto(userName, seqid, faceType, hairType, path);

            } catch(FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int score = 0;
        userDao.insertScore(score, userName, seqid);
        return new ModData(0, image);
    }

    public List<PostReHair> postReHair(String userName) {
        List<Map<String, Object>> res = userDao.queryPostReHair(userName);
        List<PostReHair> result = new ArrayList<PostReHair>();
        for (int i=0; i<res.size(); ++i) {
            String path = (String) res.get(i).get("photopath");
            result.add(new PostReHair(res.get(i).get("facetype").toString(),
                    res.get(i).get("hairtype").toString(),
                    Utils.imgToBase64(path)));
        }
        return result;
    }
}
