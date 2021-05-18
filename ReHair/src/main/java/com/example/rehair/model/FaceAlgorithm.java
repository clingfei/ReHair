package com.example.rehair.model;

// 这里是model的部分，可以定义不同的service吗？应当是可以的

import org.springframework.web.client.RestTemplate;

class RequestBean {
    public String sourcePicPath;
    public String targetPicPath;
    RequestBean() {}
    public String getSourcePicPath() {
        return this.sourcePicPath;
    }
    public String getTargetPicPath() {
        return this.targetPicPath;
    }
    public void setSourcePicPath(String srrc) {
        this.sourcePicPath = srrc;
    }
    public void setTargetPicPath(String ttar) {
        this.targetPicPath = ttar;
    }
}
public class FaceAlgorithm {

    public FaceAlgorithm(String sourcePic, String targetPic) {
    }
    public  String exchangeFace(String sourcePic, String targetPic){
        // 目标是把sourcePic换到targetPic上面去
        // 这个算法是静态的，也不知道合不合理呢？
        // 使用静态方法是否合理呢？咱们也不知道

        String url = "http://127.0.0.1:8000/";
        RequestBean dataBean = new RequestBean();
        dataBean.setSourcePicPath(sourcePic);
        dataBean.setTargetPicPath(targetPic);

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, dataBean, String.class);

        System.out.println(res);

        return res;
    }
    public String recognizeFaceType(String sourcePic) {
        return null;
    }
    public String changeHairColorFace(String sourcePic) {
        return null;
    }
    public String cutoutFace(String sourcePic) {
        return null;
    }
    public String scoreFace(String sourcePic) {
        return null;
    }

    public static final String exchangeFaceChoose = "0";
    public static final String recognizeFaceTypeChoose = "1";
    public static final String changeHairColorFaceChoose = "2";
    public static final String cutoutFaceChoose = "3";
    public static final String scoreFaceChoose = "4";

}

