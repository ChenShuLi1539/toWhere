package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CourseMapper {
    ArrayList<Course> QueryCoursesByStuId(int Id);
    ArrayList<Course> QueryCoursesByDepartManegerId(int Id);

    ArrayList<Course> QueryCoursesByTeachId(int Id);

}
