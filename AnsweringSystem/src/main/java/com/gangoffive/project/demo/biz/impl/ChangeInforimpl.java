package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.ChangeInforBiz;
import com.gangoffive.project.demo.mapper.ChangeInformapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeInforimpl implements ChangeInforBiz {

    @Autowired
    ChangeInformapper changeInformapper;
    @Override
    public int courtyDeleteManageDepart(int id) {
        return changeInformapper.courtyDeleteManageDepart(id);
    }

    @Override
    public int allocateDepart(int id,int departmentId) {
        return changeInformapper.allocateDepart(id,departmentId);
    }
}
