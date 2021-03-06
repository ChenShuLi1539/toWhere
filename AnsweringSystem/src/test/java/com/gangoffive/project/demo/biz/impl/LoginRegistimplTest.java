package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.biz.LoginRegistBiz;
import com.gangoffive.project.demo.tool.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginRegistimplTest {

    @Autowired
    LoginRegistBiz loginRegistBiz;
    @Autowired
    GetUserBiz getUserBiz;

    @Test
    void login() {
        Map map=new HashMap();

        map.put("IdOrEmail","1546@qq.com");
        System.out.println(loginRegistBiz.login(map));
        map.put("IdOrEmail","154126@qq.com");
        System.out.println(loginRegistBiz.login(map));
    }


}
