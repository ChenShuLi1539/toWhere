package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;
import com.gangoffive.project.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface loginMapper {

   String LoginById(int Id);
   String LoginByIEmail(String email);

   void studentRegist(UserInfo userInfo);
   void teacherRegist(UserInfo userInfo);

}
