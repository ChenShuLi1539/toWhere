package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Nature {
    //角色的天性
    private String name;
    private int level;

    public Nature (String name,int level){
        this.name=name;
        this.level=level;
    }
}
