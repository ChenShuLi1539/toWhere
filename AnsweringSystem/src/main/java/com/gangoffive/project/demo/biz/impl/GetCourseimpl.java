package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.GetCourseBiz;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetCourseimpl implements GetCourseBiz {
    @Autowired
    CourseMapper courseMapper;

    @Override
    public ArrayList<Course> QueryCoursesByStuId(int Id) {
        return courseMapper.QueryCoursesByStuId(Id);
    }

    @Override
    public ArrayList<Course> QueryCoursesByDepartManegerId(int Id) {
        return courseMapper.QueryCoursesByDepartManegerId(Id);
    }

    @Override
    public ArrayList<Course> QueryCoursesByTeachId(int Id) {
        return courseMapper.QueryCoursesByTeachId(Id);
    }


}
