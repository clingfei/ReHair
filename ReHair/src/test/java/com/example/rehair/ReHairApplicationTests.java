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

    public void testGetMangasByTitle() throws IOException {

        ReturnData returnData;
        returnData = userService.register("yyy", "123456", "123456@qq.com");
        assertThat(returnData.getFlag(), is(true));
    }



    @Test
    void contextLoads() {
    }

}
