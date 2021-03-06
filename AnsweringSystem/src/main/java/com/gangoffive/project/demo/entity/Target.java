package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Target {
    //人生目标
    private String name;
    private String description;
    private Double profit;

    public Target (String name,String description,Double profit) {
        this.name=name;
        this.description=description;
        this.profit=profit;
    }
}
