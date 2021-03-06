package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.AliveTime;
import com.gangoffive.project.demo.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface VideoNoticeMapper {
    ArrayList<Notice> showAliveVideoByCourseId(int CouresId);

    int addVideoTime(String Name,int studentId ,Timestamp time,String url,int NoticeId);
}
