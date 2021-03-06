package com.gangoffive.project.demo.controller;

import com.gangoffive.project.demo.biz.GetCourseBiz;
import com.gangoffive.project.demo.biz.GetNoticeAndVideoBiz;
import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.biz.OperateVideoBiz;
import com.gangoffive.project.demo.entity.Course;
import com.gangoffive.project.demo.entity.Notice;
import com.gangoffive.project.demo.mapper.OperateVideomapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/teachHomePage")
@CrossOrigin()
public class TeachHomePage {
    @Autowired
    GetCourseBiz getCourseBiz;
    @Autowired
    OperateLiveTimeBiz operateLiveTimeBiz;
    @Autowired
    GetNoticeAndVideoBiz getNoticeAndVideoBiz;
    @Autowired
    OperateVideoBiz operateVideoBiz;

    @GetMapping("/showTeachClass")
    @ResponseBody
    public ArrayList<Course> QueryCoursesByTeachId(int teacherId) {
        return getCourseBiz.QueryCoursesByTeachId(teacherId);
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

    @GetMapping("/changeNameByVideoId")
    @ResponseBody
    public String changeNameByVideoId(int VideoId,String VideoName) {
        return operateVideoBiz.changeNameByVideoId(VideoId,VideoName);
    }

    @GetMapping("/deleteByVideoId")
    @ResponseBody
    public String deleteByVideoId(int VideoId) {
        return operateVideoBiz.deleteByVideoId(VideoId);
    }

    @GetMapping("/changeVideoStatue")
    @ResponseBody
    public String changeVideoStatue(int noticeId) {
        return operateVideoBiz.changeVideoStatue(noticeId);
    }
}
