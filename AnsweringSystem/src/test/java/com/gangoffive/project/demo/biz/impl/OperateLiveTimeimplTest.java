package com.gangoffive.project.demo.biz.impl;

import com.gangoffive.project.demo.biz.OperateLiveTimeBiz;
import com.gangoffive.project.demo.entity.AliveTime;
import com.gangoffive.project.demo.entity.Notice;
import com.gangoffive.project.demo.tool.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OperateLiveTimeimplTest {

    @Autowired
    OperateLiveTimeBiz operateLiveTimeBiz;
    @Test
    void getTeacherTimesByteacherId() {
        ArrayList<AliveTime> list=operateLiveTimeBiz.getTeacherTimesByteacherId(222);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("now:"+now);
        now=now.replace(" ","").replace(":","").replace("-","");

        System.out.println("now2:"+now);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("allTime:"+list.get(i).getStartTime());
            cal.setTime(list.get(i).getStartTime());
            System.out.println("time:"+ft.format(cal.getTime()));
            System.out.println("YEAR:"+cal.get(Calendar.DATE));
            System.out.println("minute:"+cal.get(Calendar.MINUTE));
//            cal.get（Calendar.YEAR）；//年
//            cal.get（Calendar.MONTH） + 1;//月（必须要+1）
//            cal.get（Calendar.DATE）；//日
//            cal.get（Calendar.HOUR_OF_DAY）；//时
//            cal.get（Calendar.MINUTE）；//分
//            cal.get（Calendar.SECOND）；//秒
//            cal.get（Calendar.DAY_OF_WEEK）；//星期（Locale.ENGLISH情况下，周日是1,剩下自己推算）
//            System.out.println("time:"+list.get(i).getStartTime());
//            System.out.println("year:"+list.get(i).getStartTime().getYear());
//            System.out.println("month:"+list.get(i).getStartTime().getMonth());
//            System.out.println("day:"+list.get(i).getStartTime().getDate());
//            System.out.println("hourse:"+list.get(i).getStartTime().getHours());
        }

    }

    @Test
    void getTeacherTodayTimesByteacherId() {
        ArrayList<AliveTime> list=operateLiveTimeBiz.getTeacherTodayTimesByteacherId(222);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            cal.setTime(list.get(i).getStartTime());
            System.out.println("time:"+ft.format(cal.getTime()));
            System.out.println("YEAR:"+cal.get(Calendar.DATE));
            System.out.println("minute:"+cal.get(Calendar.MINUTE));
        }

    }
    @Test
    void test(){
        String date = DateUtil.timeCycle ("Sun Jan 05 2020 00:00:00 GMT 0800 (中国标准时间)");

        System.out.println (date);

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = date;
        try {
            ts = Timestamp.valueOf(tsStr);
            System.out.println(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //输出结果   2020-01-05 08:00:00
//        String tsStr = "2011-05-09 11:49:45";
//        String tsStr1 = "2011-05-09 11:50:45";
//
//        Date d = new Date("Thu May 12 2016 08:00:00 GMT+0800 (中国标准时间)");
//        System.out.println(d);
//        Timestamp ts = Timestamp.valueOf(tsStr);
//        Timestamp ts1 = Timestamp.valueOf(tsStr1);
//
//        System.out.println(ts.before(ts1));
    }
    @Test
    void getTodayTimesByCourseId(){
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ArrayList<AliveTime> time=operateLiveTimeBiz.getTodayTimesByCourseId(4);
        for (AliveTime a:time) {
            System.out.println(a);
        }

    }

}
