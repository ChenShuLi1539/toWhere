package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Roommate {
    private int id;
    private String name;
    private boolean isOwner=false;

    public Roommate (int id,String name) {
        this.id=id;
        this.name=name;
    }
}
