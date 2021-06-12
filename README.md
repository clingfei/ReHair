# ReHair
## 环境需求
java 16 2021-03-16

Java(TM) SE Runtime Environment (build 16+36-2231)

Java HotSpot(TM) 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)

Spring CLI v2.5.0-snap-shot

[Redis 5.0.10 for Windows](https://github.com/tporadowski/redis/releases)

MySQL Ver 8.0.24 for Win64 on x86_64

Before run our project, make sure you have started redis & mysql!

## 传输格式

JSON

图片采用base64编码
## 代码结构
```
├─APP
│  ├─app
│  │  └─src
│  │      ├─androidTest
│  │      │  └─java
│  │      │      └─com
│  │      │          └─example
│  │      │              └─app
│  │      ├─main
│  │      │  ├─java
│  │      │  │  └─com
│  │      │  │      └─example
│  │      │  │          └─app
│  │      │  │              └─DRVinterface
│  │      │  └─res
│  │      └─test
│  │          └─java
│  │              └─com
│  │                  └─example
│  │                      └─app
│  └─gradle
│      └─wrapper
├─faceshape_rec
│  └─dataspider
├─faceswap
└─ReHair
    ├─.mvn
    │  └─wrapper
    ├─src
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─example
        │  │          └─rehair
        │  │              ├─config
        │  │              ├─controller
        │  │              ├─dao
        │  │              │  └─impl
        │  │              ├─model
        │  │              ├─service
        │  │              │  └─impl
        │  │              └─utils
        │  └─resources
        │      ├─ReHairSource
        │      ├─static
        │      └─templates
        │          └─layout
        └─test
            └─java
                └─com
                    └─example
                        └─rehair


```
## Description

Backend part developed based on [Spring Boot](https://spring.io/projects/spring-boot), using MySQL as database to save user data, photos uploaded, etc. We use session to track user status, which stored in redis to shorten the query latency to some extent.

For test, we use unit testing and  [Postman | The Collaboration Platform for API Development](https://www.postman.com/) to ensure that our function works as it designed. After our backend finished, we deployed it on a cloud server, and use  [Web Bench Homepage (tiscali.cz)](http://home.tiscali.cz/~cz210552/webbench.html) to test the maximum load that the server can support.

## Framework and tools 

- Spring Boot
- MySQL
- Redis
- webbench
- sqlite
- Django
- OpenCV
- dlib
- Android
- Postman

## WEB 访问路径

#### 1. 主页面

#### 2. /login登录页面

#### 3. /register注册页面

#### 4./user/{username}用户个人主页

#### 5./rehair换脸页面

#### 6./share查看分享

#### 7./share/{username}查看指定用户的分享

#### 8.unfollow按钮，取关指定用户

#### 9.follow按钮，关注指定用户

web完整功能的演示参见  `/演示/ReHair_demo.mp4`

app完整功能的演示参见`/演示/ReHair_Android.mp4`

## 部署文件

rehair\ReHair\out\artifacts\ReHair_jar

## Team Members

Thank the contributions from all team members

- [clingfei (程凌飞) (github.com)](https://github.com/clingfei)
- [wzd-sjtu (武照东) (github.com)](https://github.com/wzd-sjtu)
- [Ninoric (范佳妮)(github.com)](https://github.com/Ninoric)
- [Bizzle917(何新蕾) (github.com)](https://github.com/Bizzle917)
- [R2-D2isatrashcan (王屿轩)(github.com)](https://github.com/R2-D2isatrashcan)
- [one-cup-sugar(赵硕) (github.com)](https://github.com/one-cup-sugar)
- [jackyu0001 (于凯泽)(github.com)](https://github.com/jackyu0001)

