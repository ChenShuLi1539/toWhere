package com.gangoffive.project.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();
    @Test
    void getRoles() {
        System.out.println(game.getRoles());
    }

    @Test
    void testToString() {
        System.out.println(game.toString());
    }
}