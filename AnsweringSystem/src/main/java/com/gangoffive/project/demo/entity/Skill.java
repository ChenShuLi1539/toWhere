package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Skill {
    //角色技能
    private String name;
    private String description;

    public Skill (String name,String description) {
        this.name=name;
        this.description=description;
    }
}
