package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;

import java.util.ArrayList;

public interface GetUserBiz {

    Administer getAdminsterById(int Id);
    Administer getAdminsterByEmail(String email);

    Student getStudentById(int Id);
    Teacher getTeacherById(int Id);

    int getIdByEmail(String email);

    ArrayList<Administer> getAdminstersByCoutryId(int id);              //院管理员获得可以被管理的系教务老师
    int getCourtyIdByManageId(int id);              //通过管理员Id找到对应的院


    void registMachine(int userId,String password);

}
