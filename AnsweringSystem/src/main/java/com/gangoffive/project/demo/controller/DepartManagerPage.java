package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.CSOperateBiz;
import com.gangoffive.project.demo.biz.GetCourseBiz;
import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.tool.Analysis;
import com.gangoffive.project.demo.tool.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/departManagerPage")
@CrossOrigin()
public class DepartManagerPage {

    @Autowired
    GetCourseBiz getCourseBiz;
    @Autowired
    OperateLiveTimeBiz operateLiveTimeBiz;
    @Autowired
    CSOperateBiz csOperateBiz;
    @GetMapping("/showDepartManagedClass")
    @ResponseBody
    public ArrayList<Course> QueryCoursesByDepartManegerId(String id) {
        return getCourseBiz.QueryCoursesByDepartManegerId(Integer.parseInt(id));
    }

    @RequestMapping("/addFile")
    @ResponseBody
    public Map uploadFile(MultipartFile file){
        Map<String,Object> map = new HashMap<>(16);
        //解析excel文件
        ArrayList<ArrayList<String>> table = Analysis.analysis(file);
        //打印信息
        //注意这里表是存一行一行的arraylist，所以是从行开始

//        for (int i = 0;i<table.size();i++){
//            List<String> row = table.get(i);
//            for (int j = 0;j<row.size();j++){
//                System.out.print(row.get(j)+" ");
//            }
//            System.out.println();
//        }


        //返回二维数组，其中，第一个数组是重复的Id，第二个是没注册学生的Id,第三个是成功的数量
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")));
        if (!isNum.matches()){
            map.put("status","表名错误");
            return map;
        }else {
            ArrayList<ArrayList<String>> returnform=csOperateBiz.addCsTable(table,file.getOriginalFilename());
            map.put("status",returnform);
            return map;
        }
    }

    @PostMapping("/addLiveTime")
    @ResponseBody
    public int addLiveTime(@RequestBody Map<String, String> map)  {
        Timestamp startTime=new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        String date1= DateUtil.timeCycle(map.get("Date1"));
        String date2= DateUtil.timeCycle(map.get("Date2"));
        try {
            startTime = Timestamp.valueOf(date1);
            System.out.println(startTime);
            endTime= Timestamp.valueOf(date2);
            System.out.println(endTime);
        } catch (Exception e) {
            System.out.println("格式不对，请联系后台开发人员");
            return 0;
        }
        return operateLiveTimeBiz.addLiveTime(map.get("CourseId"),map.get("TeacherId"),startTime,endTime);

    }

}
