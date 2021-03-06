package com.gangoffive.project.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChangeInformapper {
    int courtyDeleteManageDepart(int id);
    int allocateDepart(int id,int departmentId);
}
