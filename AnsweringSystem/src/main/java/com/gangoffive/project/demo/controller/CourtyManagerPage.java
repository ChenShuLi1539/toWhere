package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.ChangeInforBiz;
import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.biz.OperateDepartBiz;
import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Department;
import com.gangoffive.project.demo.mapper.ChangeInformapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/courtyManagerPage")
@CrossOrigin()
public class CourtyManagerPage {
    @Autowired
    GetUserBiz getUserBiz;
    @Autowired
    ChangeInforBiz changeInforBiz;
    @Autowired
    OperateDepartBiz operateDepartBiz;

    @GetMapping("/showCourtyDepartManage")
    @ResponseBody
    public ArrayList<Administer> showCourtyDepartManage(int id){
        return getUserBiz.getAdminstersByCoutryId(id);
    }

    @GetMapping("/courtyDeleteManageDepart")
    @ResponseBody
    public int courtyDeleteManageDepart(int id){
        return changeInforBiz.courtyDeleteManageDepart(id);
    }

    @GetMapping("/showCourtyDepartByCoutryId")
    @ResponseBody
    public ArrayList<Department> showCourtyDepart(int id){
        return operateDepartBiz.showCourtyDepartByCoutryId(id);
    }

    @GetMapping("/allocateDepart")
    @ResponseBody
    public int allocateDepart(int id,int departmentId){
        return changeInforBiz.allocateDepart(id,departmentId);
    }
}
