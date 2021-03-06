package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.AliveTime;
import com.gangoffive.project.demo.entity.Notice;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public interface OperateLiveTimeBiz {
    int addLiveTime(String CourseId, String TeacherId, Timestamp startTime,Timestamp endTime);
    ArrayList<AliveTime> getTeacherTimesByteacherId(int Id);
    ArrayList<AliveTime> getTeacherTodayTimesByteacherId(int Id);

    ArrayList<AliveTime> getTodayTimesByCourseId(int CourseId);

    Notice showAliveTimeByCourseId(int CouresId);
}
