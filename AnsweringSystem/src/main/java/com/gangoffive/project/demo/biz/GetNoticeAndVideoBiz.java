package com.gangoffive.project.demo.biz;

import com.gangoffive.project.demo.entity.Notice;

import java.util.ArrayList;

public interface GetNoticeAndVideoBiz {

    ArrayList<Notice> showAliveVideoByCourseId(int CouresId);
}
