# ReHair
## 环境需求
java 16 2021-03-16

Java(TM) SE Runtime Environment (build 16+36-2231)

Java HotSpot(TM) 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)

Spring CLI v2.5.0-snap-shot

[Redis 5.0.10 for Windows](https://github.com/tporadowski/redis/releases)

MySQL Ver 8.0.24 for Win64 on x86_64

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
