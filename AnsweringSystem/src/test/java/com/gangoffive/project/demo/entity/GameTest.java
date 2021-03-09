package com.gangoffive.project.demo.entity;

import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();
    @Test
    void getRoles() {
//        List<Integer> list=new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        List<String> list2=new ArrayList<>();
//        list2.add("1");
//        list2.add("2");
//        game.gameInit(2,list2,list);
//        List<Player> players=game.getPlayers();
//        game.randomEagerness(players.get(1),game.randomProject());
//        System.out.println(players.get(1).getBigProjects());
         double a=1.2;
         double b=a;
         b+=1.1;
         System.out.println(a);
         System.out.println(b);
    }

    @Test
    void testToString() {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        List<String> list2=new ArrayList<>();
        list2.add("1");
        list2.add("2");
        game.gameInit(2,list2,list);
        org.json.JSONObject jsonObject = new org.json.JSONObject(game.gameChooseRole());
        System.out.println(game.gameChooseRole());
        System.out.println(jsonObject);
    }
}