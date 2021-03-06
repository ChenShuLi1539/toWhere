package com.gangoffive.project.demo.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();
    @Test
    void getRoles() {
        List<Role> roles=game.getRoles();
        System.out.println(roles.get(2).toString());
    }

    @Test
    void testToString() {
        System.out.println(game.toString());
    }
}