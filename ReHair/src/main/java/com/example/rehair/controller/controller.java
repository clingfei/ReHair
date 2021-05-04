package com.example.rehair.controller;

import com.example.rehair.service.*;
import com.example.rehair.model.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
class controller {

    @Resource
    public UserService userService;

    // 需要存一个全局变量？
    public String globalUserName;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RegisterData dealRegister (@RequestBody String list) throws JSONException{
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        System.out.println(userName);
        System.out.println(passWd);
        System.out.println(email);

        RegisterData data = userService.register(userName, passWd, email);
        System.out.println(data);
        return data;
    }

    // 对象的自动序列化，可以直接return进行返回的
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginData dealLogin (HttpServletRequest req, @RequestBody String list) throws JSONException{
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("password");
        LoginData data = userService.login(req, userName, passWd);

        return data;
    }

    @RequestMapping(value = "/getHead", method = RequestMethod.GET)
    public String getHead(HttpServletRequest req) {
        String userName = req.getSession().getAttribute("username").toString();
        return userService.getHead(userName);
    }


    @RequestMapping(value = "/setHead", method = RequestMethod.POST)
    public ReturnData setHead(@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        /*
         * 这里理论上应该可以通过Session来获取用户名
         * 但是现在POST方法获取不到Session，先凑合一下
         */
        String userName = jsonObject.getString("username");
        String image = jsonObject.getString("image");
        ReturnData data = userService.setHead(userName, image);
        return data;
    }
    // 添加某个好友，可以直接使用name进行处理的
    @RequestMapping(value = "/addfriend", method = RequestMethod.POST)
    public String addFriend(@RequestBody String list) throws JSONException{
        // 登录状态无法确定，服务器应该是可以处理高并发的
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String futureFriendName = jsonObject.getString("futureFriendName");

        // res就是添加是否成功的典型例子
        String res = userService.addFriend(userName, futureFriendName);
        // 某处的好友列表需要动态更新的吧

        return res;
    }
    // 上传图片貌似要和这里的内容分开的，不知道具体应当如何处理呢？
    // 这里涉及到复杂的图片处理？

    // 本函数time的格式为 2015-10-27-10:00:00  精确到ms，可以用date类实现的
    // 创建文件夹不能包含:字符，所以规定死time的结构为: 2016-10-27-10-00-00
    @RequestMapping(value = "/createshare", method = RequestMethod.POST)
    public String createShare(@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String content = jsonObject.getString("content");
        String likeCount = jsonObject.getString("likeCount");
        String time = jsonObject.getString("time");// time规定为字符串类型
        // String picCount = jsonObject.getString("time");

        String res = userService.createShare(userName, content, likeCount, time);

        return res;
    }
    // 配套的传送图片的服务，和上面的createshare相辅相成
    // 使用一个定死的时间戳+传送的文件？这个时间戳和用户名应当如何获取呢？暂时是未知的
    // 对方会直接调用这里的传送功能
    @RequestMapping(value = "/uploadArticlePhoto", method = RequestMethod.POST)
    public String uploadArticlePhoto(@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");

        String time = jsonObject.getString("time");// time规定为字符串类型
        // 获取图片路径 easy
        String b64encodeImg = jsonObject.getString("b64encodeImg");

        String imgType = jsonObject.getString("imgType");
        // String picCount = jsonObject.getString("time");
        // 可以顺着请求发送图片，倘若传输图片，效率就太低了
        // 可以把文件服务器当做一个ftp服务器，从而进行文件传输的策略
        // 但这个不是现在需要考虑的事情

        // 返回报文并没有设置规范，等价于自己建立了一个交流规范？
        // 是显得非常不规范的
        String res = userService.uploadArticlePhoto(userName, time, b64encodeImg, imgType);


        return res;
    }

}
