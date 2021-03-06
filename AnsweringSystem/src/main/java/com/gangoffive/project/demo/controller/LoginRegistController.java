package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.biz.LoginRegistBiz;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/loginRegist")
@CrossOrigin()
public class LoginRegistController {
    @Autowired
    LoginRegistBiz loginRegistBiz;
    @Autowired
    GetUserBiz getUserBiz;



    @PostMapping("/login")
    @ResponseBody
    public String Login(@RequestBody Map<String, String> map){
        return loginRegistBiz.login(map);
    }

    @PostMapping("/regist")
    @ResponseBody
    public String regist(@RequestBody Map<String, String> map){
        return loginRegistBiz.regist(map);
    }


    @GetMapping("/registmachine")
    @ResponseBody
    public void registmachine(int userId,String password){
        getUserBiz.registMachine(userId,password);
    }
}
