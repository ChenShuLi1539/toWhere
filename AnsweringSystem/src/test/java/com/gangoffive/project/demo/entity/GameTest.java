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
        String a="123";
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        //给list1赋值
        list1.add("测");
        list1.add("试");
        list1.add("一");
        list1.add("下");
        //给list2赋值
        list2.add("合");
        list2.add("并");
        list2.add("列");
        list2.add("表");
        //将list1.list2合并
        list1.addAll(list2);
        list2.clear();
        //循环输出list1 看看结果
        System.out.print(list2);
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