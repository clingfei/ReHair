import base64
import uuid
import re

from django.http import HttpResponse, JsonResponse
from FaceSwap.faceswap import return_haircut, exchangeFace
from django.shortcuts import render
from django.http import HttpResponse
import json

from ScoringFace.code.pic_process import calFaceScore

def hello(request):
    response = request.body
    json_object = json.loads(response.decode())

    sourcePicPath = './'
    targetPicPath = './'
    srcName = 'src'
    tarName = 'tar'


    # 暂时还没有把选择传递过来
    soucePic = json_object.get('sourcePic', 0)
    targetPic = json_object.get('targetPic', 0)
    picType = json_object.get('picType', 0)

    faceType = json_object.get('faceType', 0)
    hairType = json_object.get('hairType', 0)


    print(faceType) # 这个种类使用什么标识呢？
    print(hairType)

    target_dict = {}

    imgSrc = base64.urlsafe_b64decode(soucePic)
    imgTar = base64.urlsafe_b64decode(targetPic)

    # 涉及到了很多轮的编码和解码
    filename1 = srcName + '.' + picType

    with open(filename1, "wb") as f:
        f.write(imgSrc)
    filename2 = tarName + '.' + picType
    with open(filename2, "wb") as f:
        f.write(imgTar)

    # 暂定type为0
    # 这样搞肯定无法处理并发了
    output = exchangeFace(filename2, filename1, 0, faceType, hairType)

    # 计算的是文件名字，这点谨记
    # res = calFaceScore(filename1) #规定总是jpg？不清楚的
    f = open(output, 'rb')  # 二进制方式打开图文件
    ls_f = base64.b64encode(f.read())  # 读取文件内容，转换为base64编码
    ls_f = ls_f.decode('utf-8')

    f.close()

    res = calFaceScore(output)

    target_dict['score'] = res
    target_dict['outPic'] = ls_f
    target_dict['picType'] = 'jpg'

    response = JsonResponse(target_dict) # 这里的response就是那张简单的图片，写法是非常简单的
    # 其实生成时间戳tmp文件，之后解码为内存，同时删除，将内存块处理可以有效预防并发冲突

    # 这里的dict需要抽象一下
    # 这两边连接了很多服务器？是合理的
    return response

# 专门用于打分的函数，直接完成抽象
def hello_plus(request):
    response = request.body
    json_object = json.loads(response.decode())

    sourcePicPath = './'
    srcName = 'src'
    # 暂时还没有把选择传递过来
    soucePic = json_object.get('sourcePic', 0)
    picType = json_object.get('picType', 0)

    imgSrc = base64.urlsafe_b64decode(soucePic)

    # 涉及到了很多轮的编码和解码
    filename1 = srcName + '.' + picType
    with open(filename1, "wb") as f:
        f.write(imgSrc)

    res = calFaceScore(filename1)

    return HttpResponse(res)

