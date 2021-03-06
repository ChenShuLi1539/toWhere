package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.LoginRegistBiz;
import com.gangoffive.project.demo.entity.*;
import com.gangoffive.project.demo.mapper.UserMapper;
import com.gangoffive.project.demo.mapper.loginMapper;
import com.gangoffive.project.demo.tool.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginRegistimpl implements LoginRegistBiz {
    @Autowired
    loginMapper loginMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public String login(Map<String,String> map) {
        String role;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(map.get("IdOrEmail"));

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(map.get("IdOrEmail"), map.get("password"));

        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return "用户名不存在";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "用户名或密码错误次数过多";
        } catch (Exception ae) {
            return "用户名不存在";
        }


        if (!isNum.matches()) {
            role = loginMapper.LoginByIEmail(map.get("IdOrEmail"));
        } else {
            role = loginMapper.LoginById(Integer.parseInt(map.get("IdOrEmail")));
        }
        if (role == null || role.equals("")) {
            role = "用户名错误";
            return role;
        } else {
            if (role.equals("教务老师")) {
                if (!isNum.matches()) {
                    if (userMapper.getAdminsterByEmail(map.get("IdOrEmail")).getIdentity() == 1) {
                        role = "院教务老师";
                    } else {
                        role = "系教务老师";
                    }
                } else {
                    if (userMapper.getAdminsterById(Integer.parseInt(map.get("IdOrEmail"))).getIdentity() == 1) {
                        role = "院教务老师";
                    } else {
                        role = "系教务老师";
                    }
                }
            }
            return role;
        }
    }


    @Override
    public String regist(Map<String, String> map) {
        String junction="通过";
        if (loginMapper.LoginByIEmail(map.get("email"))!=null){
            junction="邮箱重复";
            return junction;
        }
        if (loginMapper.LoginById(Integer.parseInt(map.get("id")))!=null){
            junction="工号重复";
            return junction;
        }

        if (junction.equals("通过")){
            UserInfo userInfo=new UserInfo();
            userInfo.setName(map.get("name"));
            userInfo.setPassword(MD5Util.MD5Pwd(map.get("id"),map.get("password")));
            userInfo.setEmail(map.get("email"));
            userInfo.setId(Integer.parseInt(map.get("id")));
            userInfo.setRole(map.get("role"));
            if (map.get("role").equals("老师")){
                loginMapper.teacherRegist(userInfo);
            }else {
                loginMapper.studentRegist(userInfo);
            }
            return "注册成功";
        }else{
            return "操作不规范";
        }
    }
}
