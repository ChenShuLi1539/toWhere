package com.gangoffive.project.demo.biz;

public interface OperateVideoBiz {
    String changeNameByVideoId(int VideoId,String VideoName);
    String deleteByVideoId(int VideoId);

    String changeVideoStatue(int noticeId);
}
