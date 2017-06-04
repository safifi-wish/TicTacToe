package com.afifi.said.tictactoe.model;

import java.io.Serializable;

public class Player implements Serializable{
    private String name;
    private int wins;
    private int draws;
    private Tile tile;

    public Player(String name, Tile tile) {
        this.name = name;
        this.tile = tile;
    }

    public String getName() {
        return name;
    }

    public Tile getTile() {
        return tile;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
}
