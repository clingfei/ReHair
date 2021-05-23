package com.example.rehair.utils;

import com.example.rehair.model.ShareReturn;
import io.netty.util.internal.StringUtil;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Utils {

    //输出为ReHair这一级目录
    public static String getPath() throws FileNotFoundException {
        File file = new File(ResourceUtils.getURL("classpath:").getPath());
        return file.getParentFile().getParent();
    }

    public static Boolean saveImg(String userName, String image) throws IOException {
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

    public static void createDir(String userName) {
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

    // 提供给上面的createShare函数使用的
    // time是规格化好的内容
    public static void createShareDir(String userName, String time) {
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

    //图片转换为base64编码，参数为图片路径
    public static String imgToBase64(String path) {
        System.out.println(path);
        try{
            File img = new File(path);
            FileInputStream fos = new FileInputStream(img);
            return new String(Base64.getEncoder().encode(fos.readAllBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error.";
    }

    public static boolean base64StrToFile(String image, String fileName, String parentPath) throws IOException {
        String path = parentPath + "\\" + fileName;
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
}
