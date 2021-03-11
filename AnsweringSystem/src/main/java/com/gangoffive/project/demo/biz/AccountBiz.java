package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.Account;

import java.util.Map;

public interface AccountBiz {
    String regist (Map<String,String> map);
    Account login (Map<String,String> map);
}
