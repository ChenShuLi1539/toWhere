package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.GetNoticeAndVideoBiz;
import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.entity.AliveTime;
import com.gangoffive.project.demo.entity.Notice;
import com.gangoffive.project.demo.mapper.VideoNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetNoticeAndVideoimpl implements GetNoticeAndVideoBiz {

    @Autowired
    VideoNoticeMapper videoNoticeMapper;
    @Autowired
    OperateLiveTimeBiz operateLiveTimeBiz;


    @Override
    public ArrayList<Notice> showAliveVideoByCourseId(int CouresId) {
        return videoNoticeMapper.showAliveVideoByCourseId(CouresId);
    }
}
