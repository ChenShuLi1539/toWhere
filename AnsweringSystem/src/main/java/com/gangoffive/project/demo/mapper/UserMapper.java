package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserMapper {

    Administer getAdminsterById(int Id);
    Administer getAdminsterByEmail(String email);

    Student getStudentById(int Id);

    Teacher getTeacherById(int Id);

    ArrayList<Administer> getAdminstersByCoutryId(int id);

    int getCourtyIdByManageId(int id);

    int getIdByEmail(String email);

    int registMachine(int userId, String password);

    ArrayList<Integer> getallId();
    int updateImg(int Id,String url);

}
