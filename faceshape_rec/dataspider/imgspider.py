#作者：王屿轩
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import requests
from PIL import Image
import time
import re
from selenium.common.exceptions import TimeoutException
import os
import shutil

target=input("查找内容：")
iters=int(input("轮数："))
rootdirectory=r'D:\imgfiles'
if not os.path.exists(rootdirectory):
    os.mkdir(rootdirectory)

#创建文件暂存目录
if not os.path.exists(rootdirectory+'\\temp'):
    os.mkdir(rootdirectory+'\\temp')

#设置Chrome参数
option = webdriver.ChromeOptions()
option.add_experimental_option("excludeSwitches", ['enable-automation', 'enable-logging']) # 防止打印一些无用的日志
prefs = {'profile.default_content_settings.popups': 0, 'download.default_directory': r'd:\ocfiles\temp'}
option.add_experimental_option('prefs', prefs)
#option.add_argument('headless')  #不显示浏览器
browser = webdriver.Chrome(executable_path =r'C:\Program Files\Google\Chrome\Application\chromedriver.exe',chrome_options=option)

url = "https://image.baidu.com"

while True:
    try:
        browser.get(url)
    except:
        continue
    time.sleep(3)
    break

target_input_location=browser.find_element_by_id('kw')
target_input_location.send_keys(target)
browser.find_element_by_xpath('//*[@id="homeSearchForm"]/span[2]/input').click()
time.sleep (10)
first_pic_url=browser.find_element_by_xpath('//*[@id="imgid"]/div[1]/ul/li[5]/div/a').get_attribute('href')

while True:
    try:
        browser.get(first_pic_url)
    except:
        continue
    time.sleep(3)
    break

downloadurls=[]
downloadurl=browser.find_element_by_xpath('//*[@id="toolbar"]/span[7]').get_attribute('href')
f = open('d:\\imgfiles\\files'+target+'.csv','a')
#f.write(downloadurl+'\n')
#downloadurls.append(downloadurl)

for i in range(iters):
    browser.find_element_by_xpath('//*[@id="container"]/span[2]').click()
    time.sleep(2)
    downloadurl=browser.find_element_by_xpath('//*[@id="toolbar"]/span[7]').get_attribute('href')
    f.write(downloadurl+'\n')
    downloadurls.append(downloadurl)