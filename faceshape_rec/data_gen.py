import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)

# Input data files are available in the read-only "../input/" directory
# For example, running this (by clicking run or pressing Shift+Enter) will list all files under the input directory

#import os
'''
for dirname, _, filenames in os.walk('.'):
    for filename in filenames:
        print(os.path.join(dirname, filename))
'''
import cv2,os

data_path='./training_set'
categories=os.listdir(data_path)
categories.remove('desktop.ini')

labels=[i for i in range(len(categories))]
print("labels",labels)
label_dict=dict(zip(categories,labels)) 

print(label_dict)
print(categories)
print(labels)

from keras.preprocessing.image import ImageDataGenerator
import keras
from keras.models import *
from keras.layers import *
from keras.optimizers import *


img_size=100
data=[]
target=[]


for category in categories:
    folder_path=os.path.join(data_path,category)
    
    img_names=os.listdir(folder_path)
        
    for img_name in img_names:
        img_path=os.path.join(folder_path,img_name)
        img=cv2.imread(img_path)
       
        try:
            gray=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)           
            
            resized=cv2.resize(gray,(img_size,img_size))
            data.append(resized)
            target.append(label_dict[category])
           

        except Exception as e:
            print('Exception:',e)
data=np.array(data)/255.0
data=np.reshape(data,(data.shape[0],img_size,img_size,1))
target=np.array(target)

from keras.utils import np_utils

new_target=np_utils.to_categorical(target)
np.save('data',data)
np.save('target',new_target)