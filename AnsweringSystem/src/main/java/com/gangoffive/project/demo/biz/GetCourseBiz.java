package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.Course;

import java.util.ArrayList;

public interface GetCourseBiz {
    ArrayList<Course> QueryCoursesByStuId(int Id);              //通过学生id得到课程信息
    ArrayList<Course> QueryCoursesByDepartManegerId(int Id);              //通过系管理员id得到课程信息

    ArrayList<Course> QueryCoursesByTeachId(int Id);              //通过老师id得到课程信息
}
