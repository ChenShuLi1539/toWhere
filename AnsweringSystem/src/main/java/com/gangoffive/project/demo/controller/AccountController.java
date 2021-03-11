package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/account")
@CrossOrigin()
public class AccountController {
    @Autowired
    AccountBiz accountBiz;

    @PostMapping("/regist")
    @ResponseBody
    public String regist(@RequestBody Map<String, String> map){
        return accountBiz.regist(map);
    }
}
