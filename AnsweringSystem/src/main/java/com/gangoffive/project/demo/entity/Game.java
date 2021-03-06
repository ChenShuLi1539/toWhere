package com.gangoffive.project.demo.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    private List<Role> roles;

    public Game () {
        this.roles=new ArrayList<>();
        List<Skill> skills=new ArrayList<>();
        skills.add(new Skill("幕后黑手","在你使用【计策牌】后，你的心情值+0.5"));
        skills.add(new Skill("无谋之乐","在其他人弃牌阶段弃牌时，如果丢弃的牌中存在【计策牌】，那么你的心情值+0.3"));
        this.roles.add(new Role("张天舒",1,1,2,0,3,2,3,
                "园林大师","自然-园艺熟练度达到A",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("勇壮","在游戏开始时，你获得2点勇敢和1点坚韧"));
        skills.add(new Skill("重整旗鼓","出牌阶段，你可以丢弃3张牌，清除场上一名玩家的所有负面效果，并使其下回合抽牌数+1"));
        this.roles.add(new Role("兰文俊",1,3,3,3,0,3,2,
                "武术大师","体育-武术熟练度达到A",5.0,skills));
    }
}
