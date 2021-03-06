package com.gangoffive.project.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class Video {
    private int Id;
    private String Name;
    private int studentId;
    private String studentName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;
    private String url;
    private int noticeId;
}
