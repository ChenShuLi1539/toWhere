package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Course {
    private int Id;
    private String Name;
    private int teacherId;
    private int departmentId;
    private String departmentName;
    private Teacher teacher;
}
