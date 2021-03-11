package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.AccountBiz;
import com.gangoffive.project.demo.entity.Account;
import com.gangoffive.project.demo.mapper.AccountMapper;
import com.gangoffive.project.demo.tool.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Accountimpl implements AccountBiz {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public String regist(Map<String, String> map) {
        String state="通过";
        if (accountMapper.getPlayerByAccount(map.get("account"))!=null) {
            state="该账号已被使用！";
        } else if (accountMapper.getPlayerByName(map.get("name"))!=null) {
            state="该昵称已被使用！";
        }
        if (state.equals("通过")) {
            Account account=new Account();
            account.setAccount(map.get("account"));
            account.setPassword(MD5Util.MD5Pwd(map.get("account"),map.get("password")));
            account.setName(map.get("name"));
            accountMapper.regist(account);
        } else {
            return state;
        }
        return "注册成功";
    }

    @Override
    public Account login(Map<String, String> map) {
        return accountMapper.login(map.get("account"),MD5Util.MD5Pwd(map.get("account"),map.get("password")));
    }


}
