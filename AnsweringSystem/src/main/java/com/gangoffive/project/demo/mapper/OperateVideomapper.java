package com.gangoffive.project.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateVideomapper {
    int changeNameByVideoId(int VideoId, String VideoName);
    int deleteByVideoId(int VideoId);
    int changeVideoStatue(int noticeId,String status);
    int getVideoStatue(int noticeId);
}
