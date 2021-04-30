package com.example.rehair.controller;

import com.example.rehair.service.*;
import com.example.rehair.model.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
class controller {

    @Resource
    public UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User dealRegister (@RequestBody String list) throws JSONException{
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("passwd");
        String email = jsonObject.getString("email");
        System.out.println(userName);
        System.out.println(passWd);
        System.out.println(email);

        User user = new User(userName, passWd, email);
        int result = userService.insertUser(userName, passWd, email);
        System.out.println(result);
        //userService.queryUserById("sss");
        return user;
    }

}
