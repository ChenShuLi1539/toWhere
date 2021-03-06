package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.GetCourseBiz;
import com.gangoffive.project.demo.biz.GetNoticeAndVideoBiz;
import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.entity.Notice;
import com.gangoffive.project.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/stuHomePage")
@CrossOrigin()
public class StuHomePage {

    @Autowired
    GetCourseBiz getCourseBiz;
    @Autowired
    GetUserBiz getUserBiz;
    @Autowired
    GetNoticeAndVideoBiz getNoticeAndVideoBiz;
    @Autowired
    OperateLiveTimeBiz operateLiveTimeBiz;

    @GetMapping("/QueryClassesByStuId")
    @ResponseBody
    public ArrayList<Course> QueryCoursesByStuId(int StuId) {
        return getCourseBiz.QueryCoursesByStuId(StuId);
    }


    @GetMapping("/showAliveTimeByCourseId")
    @ResponseBody
    public Notice showAliveTimeByCourseId(int CouresId) {
        return operateLiveTimeBiz.showAliveTimeByCourseId(CouresId);
    }

    @GetMapping("/showAliveVideoByCourseId")
    @ResponseBody
    public ArrayList<Notice> showAliveVideoByCourseId(int CouresId) {
        return getNoticeAndVideoBiz.showAliveVideoByCourseId(CouresId);
    }
}
