package com.afifi.said.tictactoe.controller;

public class Result {
    public enum State { WINNER, DRAW, INCOMPLETE }
    private State state;
    private Tile winnerTile;

    Result(State state) {
        this.state = state;
    }

    Result(State state, Tile winnerTile) {
        this.state = state;
        this.winnerTile = winnerTile;
    }

    public State getState() {
        return state;
    }

    public Tile getWinnerTile() {
        return winnerTile;
    }
}
