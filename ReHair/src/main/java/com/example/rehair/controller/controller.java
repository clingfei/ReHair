package com.example.rehair.controller;

import com.example.rehair.service.UserService;
import com.example.rehair.service.ShareService;
import com.example.rehair.model.*;

import net.bytebuddy.asm.Advice;
import net.sf.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import java.net.URLDecoder;
import java.util.List;

@Controller
class controller {

    @Resource
    public UserService userService;

    @Resource
    public ShareService shareService;

    //for web
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(){
        return "login.html";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnData postLogin(HttpServletRequest req,
                                @RequestParam("username") String userName,
                                @RequestParam("password") String passWd) {
        ReturnData data = userService.login(userName, passWd);
        if (data.getFlag())
            req.getSession().setAttribute("username", userName);
        return data;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegist() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnData register (@RequestParam("username") String userName,
                                @RequestParam("password") String passWd,
                                @RequestParam("email")String email) {
        System.out.println(userName);
        System.out.println(passWd);
        System.out.println(email);

        ReturnData data = userService.register(userName, passWd, email);
        System.out.println(data);
        return data;
    }

    // 涉及到了一点用户界面，暂时还不清楚要如何美化？
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String personalPage (@PathVariable("username") String username,
                                HashMap<String, Object> map) {
        map.put("username", username);
        UserInfo userInfo = userService.personalPage(username);
        map.put("email", userInfo.getEmail());
        map.put("headPhoto", userInfo.getImage());
        return "userPage";
    }

    @ResponseBody
    @RequestMapping(value = "/setHead", method = RequestMethod.POST)
    public ReturnData setHead(HttpServletRequest req, @RequestBody String list) throws UnsupportedEncodingException {
        System.out.println(list);
        String image = URLDecoder.decode(list, "utf-8");

        image = image.substring(6);
        System.out.println(image);
        String userName = (String) req.getSession().getAttribute("username");
        ReturnData data = userService.setHead(userName, image);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/showFriend", method = RequestMethod.GET)
    public List<String> showFriend(HttpServletRequest req,
                           @RequestParam int start,
                           @RequestParam int bias) {
        //System.out.println(list);
        //int start = 0, bias = 10;
        String userName = (String) req.getSession().getAttribute("username");
        List<String> res = userService.showFriend(userName, start, bias);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/unfollow", method = RequestMethod.GET)
    public ReturnData unfollow(HttpServletRequest req,
                               @RequestParam String name) {
        String userName = (String) req.getSession().getAttribute("username");
        ReturnData res = userService.unfollow(userName, name);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/appRegister", method = RequestMethod.POST)
    public ReturnData dealRegister (@RequestBody String list) throws JSONException{
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        System.out.println(userName);
        System.out.println(passWd);
        System.out.println(email);

        ReturnData data = userService.register(userName, passWd, email);
        System.out.println(data);
        return data;
    }

    //for app
    @ResponseBody
    @RequestMapping(value = "/appLogin", method = RequestMethod.POST)
    public ReturnData dealLogin (HttpServletRequest req, @RequestBody String list) throws JSONException{
        //暂不使用Session
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("password");
        ReturnData data = userService.login(userName, passWd);

        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/getHead", method = RequestMethod.GET)
    public Image getHead(HttpServletRequest req) {
       // String userName = req.getSession().getAttribute("username").toString();
        //String userName = "clf";
        return userService.getHead("clf");
    }

    @ResponseBody
    @RequestMapping(value = "/appSetHead", method = RequestMethod.POST)
    public ReturnData appSetHead(HttpServletRequest req, @RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        /*
         * 这里理论上应该可以通过Session来获取用户名
         * 但是现在POST方法获取不到Session，先凑合一下
         */
        //String userName = (String) req.getSession().getAttribute("username");
        String userName = jsonObject.getString("username");
        String image = jsonObject.getString("image");
        //System.out.println(image);
        ReturnData data = userService.setHead(userName, image);
        return data;
    }

    @RequestMapping(value = "/delAct", method = RequestMethod.GET)
    public void delAct(HttpServletRequest req, @RequestParam("username") String userName) {
        //String userName = req.getSession().getAttribute("username").toString();
        userService.delAct(userName);
    }



    // 添加某个好友，可以直接使用name进行处理的
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public ReturnData addFriend(@RequestBody String list) throws JSONException{
        // 登录状态无法确定，服务器应该是可以处理高并发的
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String futureFriendName = jsonObject.getString("futureFriendName");

        // res就是添加是否成功的典型例子
        return userService.addFriend(userName, futureFriendName);
        // 某处的好友列表需要动态更新的吧
    }
    // 上传图片貌似要和这里的内容分开的，不知道具体应当如何处理呢？
    // 这里涉及到复杂的图片处理？

    // 本函数time的格式为 2015-10-27-10:00:00  精确到ms，可以用date类实现的
    // 创建文件夹不能包含:字符，所以规定死time的结构为: 2016-10-27-10-00-00
    @ResponseBody
    @RequestMapping(value = "/createShare", method = RequestMethod.POST)
    public ShareReturn createShare(@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String content = jsonObject.getString("content");
        String time = jsonObject.getString("time");// time规定为字符串类型
        // String picCount = jsonObject.getString("time");

        ShareReturn res = shareService.createShare(userName, content, time);

        return res;
    }
    // 配套的传送图片的服务，和上面的createshare相辅相成
    // 使用一个定死的时间戳+传送的文件？这个时间戳和用户名应当如何获取呢？暂时是未知的
    // 对方会直接调用这里的传送功能
    @ResponseBody
    @RequestMapping(value = "/uploadArticlePhoto", method = RequestMethod.POST)
    public ReturnData uploadArticlePhoto(@RequestBody  String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);

        String userName = jsonObject.getString("username");
        String imgType = jsonObject.getString("imgType");
        String time = jsonObject.getString("time");
        String image = jsonObject.getString("image");
        image = image.substring(1, image.length()-1);

        return shareService.uploadArticlePhoto(userName, time, image, imgType);
    }

    @ResponseBody
    @RequestMapping(value = "/getArticle", method = RequestMethod.GET)
    public JSONArray getArticle(@RequestParam("username") String userName, @RequestParam("start") int start, @RequestParam("bias") int bias) {
        System.out.println(userName);
        System.out.println(start);
        System.out.println(bias);
        JSONArray result = JSONArray.fromObject(shareService.getArticle(userName, start, bias));
        System.out.println(result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    public ReturnData deleteArticle(HttpServletRequest req, @RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);

        //可以考虑 用session来直接获取用户名
        //String userName = req.getSession().getAttribute("username");
        String userName = jsonObject.getString("username");
        int seqid = jsonObject.getInt("seqid");
        ReturnData res = shareService.deleteArticle(userName, seqid);
        return res;
    }

    // 假设图片已经完全上传了上来，不需要进行图片的处理过程？
    // 图片的所有信息都来自数据库，最后return一个图片路径，想要加载，再调用别的函数即可
    // 返回图片的标准字符串？或者返回被编码的图片？
    // 这里的modifyType为了方便操作，直接规定接口标准：
    // exchangeFace 换脸 0
    // recognizeFaceType 识别脸型 1
    // changeHairColorFace 换头发颜色 2
    // cutoutFace 抠图 3
    // scoreFace 打分4
    @ResponseBody
    @RequestMapping(value = "/modifyPicture", method = RequestMethod.POST)
    public String modifyPicture(@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        // 源图片文件，希望修改or打分的图片
        String sourcePhotoName = jsonObject.getString("sourcePhotoName");
        // 目标图片文件，希望将图片如何处理的目标图片，这个会在后方有所告知
        String targetPhotoName = jsonObject.getString("targetPhotoName");

        String modifyType = jsonObject.getString("modifyType");
        String otherOptions = jsonObject.getString("otherOptions");

        String res = userService.modifyPicture(userName, sourcePhotoName, targetPhotoName, modifyType, otherOptions);

        // 返回的应当是文件的名字？还是直接返回图片呢？暂时是未知的
        return res;
    }



    @RequestMapping(value = "/base", method = RequestMethod.GET)
    public String getBase(){
        return "base.html";
    }
}
