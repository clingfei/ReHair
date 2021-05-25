package com.example.rehair.model;

// 这里是model的部分，可以定义不同的service吗？应当是可以的

import org.springframework.web.client.RestTemplate;
import com.example.rehair.utils.Utils;

class RequestBean {
    public String sourcePic;
    public String targetPic;
    public String picType;
    RequestBean() {}
    public String getPicType() {return this.picType; }
    public String getSourcePic() {
        return this.sourcePic;
    }
    public String getTargetPic() {
        return this.targetPic;
    }
    public void setPicType(String picty) { this.picType = picty; }
    public void setSourcePic(String srrc) {
        this.sourcePic = srrc;
    }
    public void setTargetPic(String ttar) {
        this.targetPic = ttar;
    }
}
class ReturnPicSet {
    public String sourcePic;
    public String score;
    public String picType;
    ReturnPicSet() {}
    public String getPicType() {return this.picType; }
    public String getSourcePic() {
        return this.sourcePic;
    }
    public String getScore() {return this.score;}
    public void setPicType(String picty) { this.picType = picty; }
    public void setSourcePic(String srrc) {
        this.sourcePic = srrc;
    }
    public void setScore(String tar) {this.score = tar;}
}
public class FaceAlgorithm {
    private final String prefix_url = "http://127.0.0.1:8000/";
    public FaceAlgorithm(String path, String sourcePic, String targetPic) {
    }
    // 所有流程都来一遍
    public  String exchangeFace(String sourcePicPath, String targetPicPath, String picType){
        // 目标是把sourcePic换到targetPic上面去
        // 这个算法是静态的，也不知道合不合理呢？
        // 使用静态方法是否合理呢？咱们也不知道

        RequestBean dataBean = new RequestBean();
        dataBean.setSourcePic(Utils.imgToBase64(sourcePicPath));
        // 如何把图片转码？
        dataBean.setTargetPic(Utils.imgToBase64(targetPicPath));
        dataBean.setPicType(picType);

        RestTemplate restTemplate = new RestTemplate();
        // 返回一张换脸后的base64编码图片，同一个格式
        String res = restTemplate.postForObject(this.prefix_url, dataBean, String.class);

        // 有很多信息没有存储进去，我直接裂开了
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

