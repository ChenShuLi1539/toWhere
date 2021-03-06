package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;
import com.gangoffive.project.demo.mapper.UserMapper;
import com.gangoffive.project.demo.tool.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetUserimpl implements GetUserBiz {
    @Autowired
    UserMapper userMapper;

    @Override
    public Administer getAdminsterById(int Id) {
        return userMapper.getAdminsterById(Id);
    }

    @Override
    public Administer getAdminsterByEmail(String email) {
        return userMapper.getAdminsterByEmail(email);
    }

    @Override
    public Student getStudentById(int Id) {
        return userMapper.getStudentById(Id);
    }


    @Override
    public Teacher getTeacherById(int Id) {
        return userMapper.getTeacherById(Id);
    }

    @Override
    public int getIdByEmail(String email) {
        return userMapper.getIdByEmail(email);
    }

    @Override
    public ArrayList<Administer> getAdminstersByCoutryId(int id) {
        id=userMapper.getCourtyIdByManageId(id);
        return userMapper.getAdminstersByCoutryId(id);
    }

    @Override
    public int getCourtyIdByManageId(int id) {
        return userMapper.getCourtyIdByManageId(id);
    }

    @Override
    public void registMachine(int userId, String password) {
        System.out.println("UserId:"+userId);
        System.out.println("password:"+password);
        String pass=MD5Util.MD5Pwd(String.valueOf(userId),password);
        System.out.println("UserId:"+userId);
        System.out.println("pass:"+pass);
        userMapper.registMachine(userId,pass);
    }


}
