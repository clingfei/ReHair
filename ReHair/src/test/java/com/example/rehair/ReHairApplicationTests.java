package com.example.rehair;

import com.example.rehair.model.ReturnData;
import com.example.rehair.service.UserService;

import org.assertj.core.api.Assert;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

class ReHairApplicationTests {
    @Autowired
    private UserService userService;

    @MockBean
    private RestTemplate template;

    @Test
    public void testRegisterService() {
        ReturnData returnData;
        returnData = userService.register("yyy", "123456", "123456@qq.com");
        assertThat(returnData.getFlag(), is(true));
        assertThat(returnData.getErrorMsg(), is(""));

        returnData = userService.register("", "123456", "");
        assertThat(returnData.getErrorMsg(), is("UserName Length is too short or too long."));
        assertThat(returnData.getFlag(), is(false));

        returnData = userService.register("yy", "123", "");
        assertThat(returnData.getFlag(), is(false));
        assertThat(returnData.getErrorMsg(), is("PassWord length is too short or too long."));

        returnData = userService.register("yyy", "123456", "123456@qq.com");
        assertThat(returnData.getFlag(), is(false));
        assertThat(returnData.getErrorMsg(), is("other errors"));

        returnData = userService.register("xx", "123456", "123456@qq.com");
        assertThat(returnData.getFlag(), is(false));
        assertThat(returnData.getErrorMsg(), is("duplicate email"));
    }

    @Test
    public void testLoginService() {
        ReturnData returnData = userService.login("clf", "123456");
        assertThat(returnData.getFlag(), is(true));
        assertThat(returnData.getErrorMsg(), is(""));

        returnData = userService.login("clf", "122334");
        assertThat(returnData.getFlag(), is(false));
        assertThat(returnData.getErrorMsg(), is("UserName or PassWord is Error."));

    }


    @Test
    void contextLoads() {
    }

}
