from __future__ import print_function

import numpy as np
import os
import torch
import torch.nn as nn
import torchvision
import torchvision.transforms as transforms
from PIL import Image
import os
os.environ['KMP_DUPLICATE_LIB_OK']='True'
import ScoringFace.code.net_model as net_model
# 在这里，我需要加载出网络后台，并且进行图片处理

class pic_process:
    net = None
    def __init__(self):
        self.is_initialed = 0 # 标志网络是否初始化
        self.transform = None
    # 这个是别人写的函数，直接调用
    def load_model(self,pretrained_dict, new):
        model_dict = new.state_dict()
        # 1. filter out unnecessary keys
        pretrained_dict = {k: v for k, v in pretrained_dict['state_dict'].items() if k in model_dict}
        # 2. overwrite entries in the existing state dict
        model_dict.update(pretrained_dict)
        new.load_state_dict(model_dict)

    def initial_net(self, choice):
        if choice == 1:
            self.net = net_model.AlexNet()
            # 路径使用的都是绝对路径，我裂开
            self.load_model(torch.load('./ScoringFace/source/alexnet.pth', encoding="latin1", map_location=torch.device('cpu')), self.net)
        elif choice == 2:
            self.net = net_model.ResNet(block = net_model.BasicBlock, layers = [2, 2, 2, 2], num_classes = 1)
            self.load_model(torch.load('./ScoringFace/source/resnet18.pth', encoding="latin1", map_location=torch.device('cpu')), self.net)
        else:
            print("model is not right!")
            exit()
        self.net.eval()
        self.is_initialed = 1

        # 这里定义了图像变换的方式
        self.transform = transforms.Compose([
            transforms.Resize(256),
            transforms.CenterCrop(224),
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]), ])
    def calculate_scores(self, dir):
        addr = dir
        img = Image.open(os.path.join(addr)).convert('RGB')
        if self.transform is not None:
            img = self.transform(img)

        # 不进行梯度变化，提高运行的efficiency
        with torch.no_grad():
            # 增加一个维度
            img = img.unsqueeze(0)
            output = self.net(img)
            # print(output)
            return output

def calFaceScore(pic_choice):
    net_choice = 1
    # pic_choice = "lyf.jpg"
    pic = pic_process()

    pic.initial_net(net_choice)
    # pic.calculate_scores("lyf.jpg")
    scores = pic.calculate_scores(pic_choice)
    scores_matrix = scores.numpy()
    print(scores_matrix[0][0]*2)
    res = scores_matrix[0][0]*2
    return str(res)