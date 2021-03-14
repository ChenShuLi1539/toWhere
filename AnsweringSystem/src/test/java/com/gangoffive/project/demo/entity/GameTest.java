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


        List<String> list = new ArrayList<>();
        list.add("male");
        list.add("female");
        list.add("female");
        list.add("male");

        //遍历删除,除去男生
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String a = iterator.next();
            if ("female".equals(a)) {
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
        System.out.println(list);
//        Player player=new Player("?",1);
//        Player temp=player;
//        temp.setName("123");
//        System.out.println(temp);
//        System.out.println(player);
    }

    @Test
    void testToString() {
//        List<Integer> list=new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        List<String> list2=new ArrayList<>();
//        list2.add("1");
//        list2.add("2");
//        game.gameInit(2,list2);
//        org.json.JSONObject jsonObject = new org.json.JSONObject(game.gameChooseRole());
//        System.out.println(game.gameChooseRole());
//        System.out.println(jsonObject);
    }
}