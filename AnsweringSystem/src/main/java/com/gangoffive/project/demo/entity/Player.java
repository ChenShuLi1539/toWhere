package com.gangoffive.project.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Player {
    private String name;
    private int id;
    private Role role;
    private Double mood=8.0;
    private List<BigProject> bigProjects;

    public Player (String name,int id) {
        this.name=name;
        this.id=id;
        for(int i=0;i<6;i++) {
            bigProjects.add(new BigProject(i));
        }
    }
}
