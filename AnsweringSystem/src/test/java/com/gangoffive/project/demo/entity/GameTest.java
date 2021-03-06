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
        List<Role> roles=game.getRoles();
        System.out.println(JSONObject.fromObject(roles.get(2)));
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