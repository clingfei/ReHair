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
}
