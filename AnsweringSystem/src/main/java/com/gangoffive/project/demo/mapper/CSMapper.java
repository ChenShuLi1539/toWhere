package com.gangoffive.project.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CSMapper {
    int addCSTabbleByline(int courseId,int studentId);
}
