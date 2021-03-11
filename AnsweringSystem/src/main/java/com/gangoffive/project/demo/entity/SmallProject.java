package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class SmallProject {
    private String name;
    private double mastery=0.0;
    private double expectation=0.0;
    private int eagerness=1;//对该项目的热爱程度：0不感兴趣，1普通，2感兴趣，3特别感兴趣

    public SmallProject (String name) {
        this.name=name;
    }
}
