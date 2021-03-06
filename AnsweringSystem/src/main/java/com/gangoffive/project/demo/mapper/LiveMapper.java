package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.AliveTime;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface LiveMapper {
    ArrayList<AliveTime> getTeacherTimesByteacherId(int Id);                //这是查询老师所有安排时间包括了历史时间
    ArrayList<AliveTime> getTeacherTodayTimesByteacherId(String now,int Id);               //这是查询老师从今天起之后时间，注意这是看未来时间安排的

    int addLiveTime(String courseId,Timestamp startTime, Timestamp endTime);

    ArrayList<AliveTime> getTodayTimesByCourseId(String now,int CourseId);              //通过课程号查询最新的直播时间

}
