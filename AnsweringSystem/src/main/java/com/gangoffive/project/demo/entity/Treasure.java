package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Treasure {
    private String name;

    public Treasure(String name) {
        this.name=name;
    }
}
