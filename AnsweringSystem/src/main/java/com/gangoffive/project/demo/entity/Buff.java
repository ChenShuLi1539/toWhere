package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Buff {
    private String name;
    private String description;
    private int lastTurns;//剩余持续的回合数
    private boolean isBeneficial;

    public Buff (String name,String description,int lastTurns,boolean isBeneficial) {
        this.name=name;
        this.description=description;
        this.lastTurns=lastTurns;
        this.isBeneficial=isBeneficial;
    }
}
