package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.OperateDepartBiz;
import com.gangoffive.project.demo.entity.Department;
import com.gangoffive.project.demo.mapper.OperateDepartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OperateDepartimpl implements OperateDepartBiz {
    @Autowired
    OperateDepartMapper operateDepartMapper;

    @Override
    public ArrayList<Department> showCourtyDepartByCoutryId(int id) {
        return operateDepartMapper.showCourtyDepartByCoutryId(id);
    }


}
