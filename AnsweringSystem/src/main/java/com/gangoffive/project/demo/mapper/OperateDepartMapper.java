package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface OperateDepartMapper {
    ArrayList<Department> showCourtyDepartByCoutryId(int id);

}
