package com.afifi.said.tictactoe.model;

import android.graphics.Point;
import android.support.v4.util.Pair;

public class Result {

    public enum State {WINNER, DRAW, INCOMPLETE}

    private State state;
    private Player winningPlayer;
    private Pair<Point, Point> winningCoordinates;

    public Result(State state) {
        this.state = state;
    }

    public Result(Player winningPlayer, Pair<Point, Point> winningCoordinates) {
        this.state = State.WINNER;
        this.winningPlayer = winningPlayer;
        this.winningCoordinates = winningCoordinates;
    }

    public State getState() {
        return state;
    }


    public Player getWinner() {
        return winningPlayer;
    }

    public Pair<Point, Point> getWinningCoordinates() {
        return winningCoordinates;
    }
}
