package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/AllPage")
@CrossOrigin()
public class AllPage {
    @Autowired
    GetUserBiz getUserBiz;

    @GetMapping("/getInforByIdRole")
    @ResponseBody
    public Map QueryCoursesByDepartManegerId(int Id,String role) {
        Map<String,String>map=new HashMap<>();
        switch (role){
            case "学生":
                Student student=getUserBiz.getStudentById(Id);
                if (student==null){
                    map.put("information","没有这个人");
                }else{
                    map.put("img",student.getImg());
                    map.put("Name",student.getName());
                }
                break;
            case "老师":
                Teacher teacher=getUserBiz.getTeacherById(Id);
                if (teacher==null){
                    map.put("information","没有这个人");
                }else {
                    map.put("img",teacher.getImg());
                    map.put("Name",teacher.getName());
                }
                break;
            case "教务老师":
                Administer administer=getUserBiz.getAdminsterById(Id);
                if (administer==null){
                    map.put("information","没有这个人");
                }else{
                    map.put("Name",administer.getName());
                }
                break;
        }
        return map;
    }
}
