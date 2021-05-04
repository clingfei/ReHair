package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import io.netty.util.internal.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

// import com.fasterxml.jackson.annotation.JsonFormat;
// 日期处理类
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// base64编码处理类
import java.util.Base64;


import javax.imageio.ImageIO;


@Service
public class ServiceImpl implements UserService {
    @Resource
    public UserDao userDao;

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

    private Boolean saveImg(String userName, String image) throws IOException {
        String path = "";
        try{
            path = getPath();
        } catch(FileNotFoundException e) {
            return false;
        }
        path =  path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\headPhoto\\headPhoto.jpg";
        if (StringUtil.isNullOrEmpty(image)) {
            return false;
        }
        File img = new File(path);
        FileOutputStream fos = new FileOutputStream(img);
        byte[] bytes = Base64.getDecoder().decode(image.replace("\r\n", ""));
        fos.write(bytes);
        fos.flush();
        fos.close();
        return true;
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

        String prePath = null;
        String path = "";
        try {
            path = getPath();
            prePath = getPath();
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

        // 2020.5.4 完成了基本文件路径的更新了
        pathToPic = pathToPic + path + ";";
        userDao.reloadArticlePhotoPath(userName, date, pathToPic);
        // 文件链表路径更新即可

        // 解码编码文件，并且存储为某一个文件，下面进行处理
        // 生成doc文件？
        // 具体写法就是解码base64编码的图片文件string数组，存储在对应的本地目录下面
        // 最终生成较好的效果哦
        // String b64encodeImg就是我们需要解码的字符串，这一点是要谨记的
        // 一看就是static方法，直接从类名调用

        String parentPath = prePath + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\sharePhoto\\" + "time-" + time;
        base64StrToFile(b64encodeImg, docName, parentPath);

        // 至此完成数据的调用，可以稍微试一下了
        // 成功完成了上传图片，并且更新路径的服务
        return pathToPic;

    }
    // 进行base64解码的私有函数
    private static void base64StrToFile(String b64encodeImg, String fileName, String parentPath) {
        File file = new File(parentPath, fileName);
        FileOutputStream out = null;

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes1 = decoder.decode(b64encodeImg);

            ByteArrayInputStream in = new ByteArrayInputStream(bytes1);
            byte[] buffer = new byte[1024];
            // 异常就不处理了
            out = new FileOutputStream(file);
            int byteSum = 0;
            int byteRead = 0;
            while((byteRead = in.read(buffer)) != -1) {
                byteSum += byteRead;
                out.write(buffer, 0, byteRead);
            }

        }catch (Exception ex){
            throw new RuntimeException("transform base64 String into file 出错",ex);
        }finally {
            try {
                if(out != null)
                    out.close(); // 就不进行完整的异常处理了
            } catch( java.io.IOException e) {
                System.out.println(e.getMessage());
            }
        }
        // return file;
        return;
    }

    public ReturnData setHead(String userName, String image) {
        try{
            boolean flag = saveImg(userName, image);
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
    public String getHead(String userName) {
        String path;
        try {
            path = getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        try {
            path =  path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\headPhoto\\headPhoto.jpg";
            File file = new File(path);
            if (!file.exists())
                throw  new FileNotFoundException();
            return imgToBase64(path);
        } catch (FileNotFoundException e) {
            path  = path + "\\src\\main\\resources\\ReHairSource\\defaultHeadPhoto.jpg";
            return imgToBase64(path);
        }
    }

    //图片转换为base64编码，参数为图片路径
    private  String imgToBase64(String path) {
        try{
            File img = new File(path);
            FileInputStream fos = new FileInputStream(img);
            return new String(Base64.getEncoder().encode(fos.readAllBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error.";
    }

}
