package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.entity.AliveTime;
import com.gangoffive.project.demo.entity.Notice;
import com.gangoffive.project.demo.mapper.LiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class OperateLiveTimeimpl implements OperateLiveTimeBiz {

    @Autowired
    LiveMapper liveMapper;
    @Override
    public int addLiveTime(String CourseId, String TeacherId, Timestamp startTime, Timestamp endTime) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ArrayList<AliveTime> teacherTime=liveMapper.getTeacherTodayTimesByteacherId(now,Integer.parseInt(TeacherId));
        System.out.println("size:"+teacherTime.size());
        for (int i = 0; i < teacherTime.size(); i++) {
            if (endTime.before(teacherTime.get(i).getStartTime())||startTime.after(teacherTime.get(i).getEndTime())){

            }else{
                return 2;
            }
        }

        return liveMapper.addLiveTime(CourseId,startTime,endTime);
    }

    @Override
    public ArrayList<AliveTime> getTeacherTimesByteacherId(int Id) {
        return liveMapper.getTeacherTimesByteacherId(Id);
    }

    @Override
    public ArrayList<AliveTime> getTeacherTodayTimesByteacherId(int Id) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return liveMapper.getTeacherTodayTimesByteacherId(now,Id);
    }

    @Override
    public ArrayList<AliveTime> getTodayTimesByCourseId(int CourseId) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return liveMapper.getTodayTimesByCourseId(now,CourseId);
    }

    @Override
    public Notice showAliveTimeByCourseId(int CouresId) {
        Notice notice=new Notice();
        ArrayList<AliveTime> time=this.getTodayTimesByCourseId(CouresId);
        if (time.size()>0){
            AliveTime neartime=time.get(0);
            for (AliveTime a:time) {
                if (a.getEndTime().before(neartime.getEndTime())){
                    neartime=a;
                }
            }
            notice.setLatestStartTime(neartime.getStartTime());
            notice.setLatestEndTime(neartime.getEndTime());
            notice.setStatus(neartime.getStatus());
            notice.setId(neartime.getId());
            notice.setCourseId(neartime.getCourseId());
        }
        return notice;
    }

}
