package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.GetUserBiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetUserimplTest {

    @Autowired
    GetUserBiz getUserBiz;
    @Test
    void getAdminsterById() {
        System.out.println(getUserBiz.getAdminsterById(3));
    }

    @Test
    void getStudentById() {
        System.out.println(getUserBiz.getStudentById(2));

    }

    @Test
    void getTeacherById() {
        System.out.println(getUserBiz.getTeacherById(1));
    }
}
