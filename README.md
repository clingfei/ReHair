# ReHair
## 环境需求
java 16 2021-03-16

Java(TM) SE Runtime Environment (build 16+36-2231)

Java HotSpot(TM) 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)

Spring CLI v2.5.0-snap-shot

[Redis 5.0.10 for Windows](https://github.com/tporadowski/redis/releases)

MySQL Ver 8.0.24 for Win64 on x86_64

## 通信
HTTPS  8081端口
HTTP 8080端口

文本格式： JSON

图片： image?
## 接口
| URL | Method | 格式 | Key 
| --- | --- | --- | --- |
| /register | POST | JSON | username <br> password <br> email
| /login | POST | JSON | username <br> password

## Model
1. User 
```
class User () {
    public String userName;
    public String passWd;
    public String email;
}
```
2. Article
```aidl
class Article(){
    public String userName;
    public String content;
    public String picturePath;
    public 时间 date;
    public int count;
    list commentList;
}
```
3. Algorithm
```aidl
class Algorithm {
    public static String exchangeFace(String sourcePic, string targetPic;
    public static int recognizeFace(String sourcePic);
    public static String changeColorFace(String sourcePic);
    public static String cutoutFace(String sourcePic);
    public static double scoreFace(String sourcePic);
} 
```


## 数据库
1. User

| username | password | email |
| ---   | --- |---|
| varchar(15) <br> PRIMARY KEY  | varchar(15)  | varchar(100) <br> UNIQUE|

2. FriendList

| id | username | friendname |
|--- | --- | ---|
| AUTO_INCREMENT <br> PRIMARY KEY | varchar(15) | varchar(15)

3. Photo

|id | username | photopath |
| --- | --- | --- |
| AUTO_INCREMENT <br> PRIMARY KEY | varchar(15) | varchar(100) <br> UNIQUE |

4. Article 

|id | username | content | photopath | count | time
| --- | --- | --- | --- | --- | --- |
| AUTO_INCREMENT <br> PRIMARY KEY | varchar(15) | TEXT | varchar(100)<br> UNIQUE | int | DATETIME

## 图片存储路径
```
├─ReHairSource
│  └─admin
│      ├─headPhoto
│      ├─Photo
│      ├─sharePhoto
│      │  └─2021-05-01
│      └─temp
├─static
└─templates
```
