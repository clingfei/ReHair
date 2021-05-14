package com.example.rehair.service.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import com.example.rehair.model.*;
import org.springframework.util.ResourceUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;


// base64编码处理类
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

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

    //图片转换为base64编码，参数为图片路径
    private  String imgToBase64(String path) {
        System.out.println(path);
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
            createDir(userName);
        }

        return data;
    }

    public ReturnData login(String userName, String passWd) {
        String passWord = userDao.queryUserByName(userName);

        if (match(passWd, passWord)) {
            return new ReturnData(true, "");
        }
        return new ReturnData(false, "UserName or PassWord is Error.");
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

    // 进行base64解码的私有函数
    private static String base64StrToFile(String b64encodeImg, String fileName, String parentPath) {
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
            return ex.getMessage();
        }finally {
            try {
                if(out != null)
                    out.close(); // 就不进行完整的异常处理了
            } catch( java.io.IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return "";
    }

    public ReturnData createShare(String userName, String textContent, String likeCount, String time) {
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



        int status = userDao.createShare(userName, textContent, date);

        if(status == 1) return new ReturnData(true, "");
        else return new ReturnData(false, "Error.");

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

        String pathToPic = userDao.findArticlePhotoPath(userName, date);

        String prePath = null;
        String path = "";
        try {
            path = getPath();
            prePath = getPath();
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
            userDao.reloadArticlePhotoPath(userName, date, pathToPic);
            String parentPath = prePath + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\sharePhoto\\" + "time-" + time;

            res.setErrorMsg(base64StrToFile(img, docName, parentPath));

            ++i;
        }
        return res;
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
    public Image getHead(String userName) {
        String path;
        try {
            path = getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Image("");
        }
        try {
            String userpath =  path + "\\src\\main\\resources\\ReHairSource\\" + userName + "\\headPhoto\\headPhoto.jpg";
            File file = new File(userpath);
            if (!file.exists())
                throw  new FileNotFoundException();
            return new Image(imgToBase64(userpath));
        } catch (FileNotFoundException e) {
            String defaultPath  = path + "\\src\\main\\resources\\ReHairSource\\defaultHeadPhoto.jpg";
            return new Image(imgToBase64(defaultPath));
        }
    }

    public ArrayList<Article> getArticle(String userName, int start, int bias) {
        ArrayList<Map<String, Object>> result = userDao.queryArticleByName(userName);
        if (result == null) {
            return new ArrayList<Article>();
        }
        ArrayList<Article> res = new ArrayList<Article>();
        System.out.println(result);
        for (int i = start; i < start+bias && i < result.size(); ++i) {
            String path = (String) result.get(i).get("photopath");;
            ArrayList<String> image = new ArrayList<String>();
            for (String imgpath : path.split(";")) {

                //System.out.println(imgToBase64(imgpath));
                image.add(imgToBase64(imgpath));
            }
            Article article = new Article(
                    (String)result.get(i).get("username"),
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

    // 以下就是整个处理图片类的实现，还是可以的
    // 每一个算法都需要使用网络通信和某个服务器获取下一步的消息
    // 毕竟，实现的算法是使用python写的，还有很多工作需要继续做
    public String modifyPicture(String userName, String sourcePhotoName, String targetPhotoName,
                                String modifyType, String otherOptions) {

        switch (modifyType) {
            case FaceAlgorithm.exchangeFaceChoose: {
                FaceAlgorithm.exchangeFace(sourcePhotoName, targetPhotoName);
                break;
            }
            case FaceAlgorithm.recognizeFaceTypeChoose: {
                FaceAlgorithm.recognizeFaceType(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.changeHairColorFaceChoose: {
                FaceAlgorithm.changeHairColorFace(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.cutoutFaceChoose: {
                FaceAlgorithm.cutoutFace(sourcePhotoName);
                break;
            }
            case FaceAlgorithm.scoreFaceChoose: {
                FaceAlgorithm.scoreFace(sourcePhotoName);
                break;
            }
        }
        return "yes";
    }

}
