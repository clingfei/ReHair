package com.example.rehair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
class controller {
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public entity.User dealRegister (@RequestBody String list) throws JSONException, JsonProcessingException {
        System.out.println(list);
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("passwd");
        String email = jsonObject.getString("email");
        System.out.println(userName);
        System.out.println(passWd);
        System.out.println(email);

        entity.User user = new entity.User(userName, passWd, email);
        queryUserById("sss");
        return user;
    }
    /*
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public entity.User dealLogin (@RequestBody String list) throws JSONException {
        JSONObject jsonObject = new JSONObject(list);
        String userName = jsonObject.getString("username");
        String passWd = jsonObject.getString("passwd");

    }
*/
}
