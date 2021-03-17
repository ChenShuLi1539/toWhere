package com.gangoffive.project.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private String name;
    private int id;
    private Role role;
    private double mood=8.0;
    private List<BigProject> bigProjects=new ArrayList<>();
    private List<Card> cards=new ArrayList<>();
    private List<Buff> buffs=new ArrayList<>();
    private List<Treasure> treasures =new ArrayList<>();
    private int usedCardsNum=0;
    private int totalScore=0;
    private int projectScore=0;
    private int moodScore=0;
    private int cardScore=0;
    private int treasureScore=0;

    public Player (String name,int id) {
        this.name=name;
        this.id=id;
        for(int i=0;i<6;i++) {
            bigProjects.add(new BigProject(i));
        }
    }
}
