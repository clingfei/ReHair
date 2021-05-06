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

图片： base64编码
## 接口
1. 登录
* URL: /login 
* METHOD: POST
* 请求体：
```
  {
      "username" : 用户名，
      "password" : 密码
  }
 ```
* 返回值： 
``` 
{
    "flag" : true/false,
    "errorMsg" : "" //若成功为空，否则为错误信息
}
```
2. 注册
* URL : /register
* METHOD: POST
* 请求体： 
```
{
    "username" : 用户名,
    "password" : 密码,
    "email" : 邮箱
}
```
* 返回值： 
```
{
    "flag" : true/false,
    "errorMsg" : "" //若成功为空，否则为错误信息
}
```
3. 获取用户头像
* URL: /getHead
* METHOD: GET
* 返回值：`{ "image" : 图片的base64编码 }`

4. 设置用户头像
* URL： /setHead
* METHOD: POST
* 请求体：
```
{
    "username" : 用户名,
    "image" : 图片的Base64编码
}
```

5. 添加好友
* URL： /addFriend
* METHOD: POST
* 请求体：
```
{
    "username" : 用户名,
    "futureFriendName" : 不知道是什么，问wzd
}
```
* 返回值：
```
{
    "flag" : true/false,
    "errorMsg" : "" //若成功为空，否则为错误信息
}
```

6. 发动态
* URL: /createShare
* METHOD: POST
* 请求体：
```
{
    "username" : "用户名",
    "content" : 动态内容,
    "likeCount" : 点赞数,初始置0,
    "time" : 发布时间，字符串类型， 这种格式："yyyy-MM-dd-hh-mm-ss"
}
```
* 返回值：
```
{
    "flag" : true/false,
    "errorMsg" : "" //若成功为空，否则为错误信息
}
```

7. 动态的图片
* URL: /uploadArticlePhoto
* METHOD: POST
* 请求体:
```
{
    "image" : ["base64编码"， ”base64编码", ....etc]
    
    //将多张图片的base64编码组织成列表的形式
}
```
* 返回值：
```
{
    "flag" : true/false,
    "errorMsg" : "" //若成功为空，否则为错误信息
}
```
8. 获取指定动态：
* URL : /getArticle
* METHOD : GET
* GET参数：`/getArticle?username=clf&start=0&bias=10` 
  
    其中username为用户名，start为起始动态位置，bias是请求额度动态数量，可动态请求数据，类似ajax，只需更改start和bias值
* 返回值：
```
[
    {
        "count": 4,
        "photos": [
            "error.",
            "error."
        ],
        "text": "ssssss",
        "time": "2016-10-27 10:00:00",
        "userName": "clf"
    },
    {
        "count": 1,
        "photos": [
            base64编码
        ],
        "text": "xxx",
        "time": "2000-10-27 10:00:00",
        "userName": "clf"
    }
]
```
其中： count为点赞数， photos为图片的base64编码组成的列表，text是文本内容，username是用户名，time是发布时间， 返回值为上述五个参数组成的JSON对象的列表

9. 修改图片算法               等待与算法部分对接
* URL: /modifyPicture
* METHOD: POST
* 请求体：
```
{
    "username" : 用户名,
    "sourcePhotoName": 源图片名,
    "targetPhotoName": 目标图片名？
    "modifyType" : 修改类型？
    "otherOptions" : 
```

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
| varchar(15) <br> PRIMARY KEY  | varchar(200)  | varchar(100) <br> UNIQUE|

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
| AUTO_INCREMENT <br> PRIMARY KEY | varchar(15) | TEXT | TEXT | int | varchar(100)

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
