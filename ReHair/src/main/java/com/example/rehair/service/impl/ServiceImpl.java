package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

// import com.fasterxml.jackson.annotation.JsonFormat;
// 日期处理类
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



import javax.imageio.ImageIO;


@Service
public class ServiceImpl implements UserService {
    @Resource
    public UserDao userDao;

    public String queryUserById(String q) {
        return "Hello";
    }

    private String getPath() throws FileNotFoundException {
        File file = new File(ResourceUtils.getURL("classpath:").getPath());
        return file.getParentFile().getParent();
    }

    private void createDir(String userName) {
        String path = "";
        try {
            path = getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        path = path + "\\src\\main\\resources\\ReHairSource\\" + userName;
        String[] subPath = new String[] {"\\headPhoto", "\\Photo", "\\sharePhoto", "\\temp"};
        File file = new File(path);
        file.mkdir();
        for(int i=0; i<4; ++i ) {
            File file1 = new File(path + subPath[i]);
            file1.mkdir();
        }
    }

    private Boolean match(String password, String encryptedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.matches(password, encryptedPassword);
    }

    private String hashEncode(String passWd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(passWd);
    }

    public RegisterData register(String userName, String passWd, String email) {
        System.out.println(passWd);
        passWd = hashEncode(passWd);
        // 仅仅存放hash code，长度超过限制
        System.out.println(passWd);

        User user = new User(userName, passWd, email);
        RegisterData data = userDao.insertUser(user);
        if (data.getFlag() == true) {
            createDir(userName);
        }

        return data;
    }

    public LoginData login(HttpServletRequest req, String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);

        if (match(passWd, passWord)) {
            return new LoginData(true, "");
        }
        return new LoginData(false, "UserName or PassWord is Error.");
    }


    @Override
    public String addFriend(String userName, String futureFriendName) {
        // 如何判断是否为朋友呢？可以设置在前台，一开始初始化时，需要请求好友列表信息
        // 同时需要给出每个好友的头像等信息
        // 还有对应的朋友圈信息？还算是比较高级的哦

        int status = userDao.addFriend(userName, futureFriendName);
        // 直接调用了数据库而已
        // 添加朋友是比较简单的
        if(status == 1) return "succeed";
        else return "fail";
    }
    @Override
    public String createShare(String userName, String textContent, String likeCount, String time) {
        createShareDir(userName, time);
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

        int status = userDao.createShare(userName, textContent, likeCount, date);

        if(status == 1) return "succeed";
        else return "fail";

    }
    // 提供给上面的createShare函数使用的
    // time是规格化好的内容
    private void createShareDir(String userName, String time) {
        String path = "";
        try {
            path = getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        path = path + "\\src\\main\\resources\\ReHairSource\\" + userName;
        path = path + "\\sharePhoto\\";
        // String[] subPath = new String[] {"\\headPhoto", "\\Photo", "\\sharePhoto", "\\temp"};
        // 创建最终以time结尾的文件夹
        path = path + "time-" + time;
        // 最终只需要创建一个文件夹即可
        File file = new File(path);
        if(file.mkdir()) {
            System.out.println("make dir successfully in Servicelmpl.java createShareDir");
        }
        else {
            System.out.println("make dir faily in Servicelmpl.java createShareDir");
        }
        // System.out.println(path);
        return;
    }

    @Override
    public String uploadArticlePhoto(String userName, String time, String b64encodeImg, String imgType) {
        // time首先要转义
        Date date = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        try {
            // 忘记了异常处理
            date = sdf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pathToPic = userDao.findArticlePhotoPath(userName, date);
        // 找到了路径，重要的路径

        String path = "";
        try {
            path = getPath();
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        path = path + "\\src\\main\\resources\\ReHairSource\\" + userName;
        path = path + "\\sharePhoto\\";
        // String[] subPath = new String[] {"\\headPhoto", "\\Photo", "\\sharePhoto", "\\temp"};
        // 创建最终以time结尾的文件夹
        path = path + "time-" + time + "\\";


        //创建一个Date类，得到当前系统时间
        Date qq=new Date();
        //日期格式函数
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        //将当前时间转换格式，返回String类型
        String docName=df.format(qq)+"." + imgType;

        path = path + docName;

        pathToPic = pathToPic + path + ";";
        userDao.reloadArticlePhotoPath(userName, date, pathToPic);
        // 文件链表路径更新即可

        // 解码编码文件，并且存储为某一个文件，下面进行处理
        // 生成doc文件？
        // 具体写法就是解码base64编码的图片文件string数组，存储在对应的本地目录下面
        // 最终生成较好的效果哦


        return pathToPic;

    }

}
