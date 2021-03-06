package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Player {
    private String name;
    private int id;
    private Role role;

    public Player (String name,int id) {
        this.name=name;
        this.id=id;
    }
}
