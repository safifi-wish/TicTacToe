package com.afifi.said.tictactoe.model;

public class Player {
    private String name;
    private int score;
    private Tile tile;

    public Player(String name, Tile tile) {
        this.name = name;
        this.tile = tile;
        this.score = 0;
    }

    public void incrumentScore(){
        score++;
    }

    public String getName() {
        return name;
    }

    public Tile getTile() {
        return tile;
    }

    public int getScore() {
        return score;
    }
}
