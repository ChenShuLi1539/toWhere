package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.OperateVideoBiz;
import com.gangoffive.project.demo.mapper.OperateVideomapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateVideoimpl implements OperateVideoBiz {

    @Autowired
    OperateVideomapper operateVideomapper;
    @Override
    public String changeNameByVideoId(int VideoId, String VideoName) {
        if (VideoName==null||VideoName.equals(" ")||VideoName.equals("")){
            System.out.println("名字不为空或空格");
            return "名字不为空或空格";
        }else {
            if (operateVideomapper.changeNameByVideoId(VideoId,VideoName)==1){
                return "成功";
            }else {
                return  "失败";
            }

        }

    }

    @Override
    public String deleteByVideoId(int VideoId) {
        if (operateVideomapper.deleteByVideoId(VideoId)==1){
            return "成功";
        }else {
            return "失败";
        }

    }

    @Override
    public String changeVideoStatue(int noticeId) {
        int status=operateVideomapper.getVideoStatue(noticeId);
        if (status==1){
            operateVideomapper.changeVideoStatue(noticeId,"0");
            return "关闭成功";
        }else {
            operateVideomapper.changeVideoStatue(noticeId,"1");
            return "打开成功";
        }
    }
}

