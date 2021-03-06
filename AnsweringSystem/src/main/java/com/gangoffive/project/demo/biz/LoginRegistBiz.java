package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.UserInfo;

import java.util.Map;

public interface LoginRegistBiz {
    String login (Map<String,String> map);
    String regist (Map<String,String> map);
}
