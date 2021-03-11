package com.gangoffive.project.demo.entity;

import lombok.Data;

@Data
public class Card {
    private String name;
    private int type;//0学习牌，1剧情牌，2名品牌，3计策牌，4机缘牌
    private String description;

    public Card (String name,int type,String description) {
        this.name=name;
        this.type=type;
        this.description=description;
    }
}
