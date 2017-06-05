package com.afifi.said.tictactoe.model;

import android.graphics.Point;
import android.support.v4.util.Pair;

/**
 * This model represents a result produced by the GameEngine indicating if a player won,
 * if a draw occurred or if the game is incomplete. Also if a winner exists it contains the
 * coordinates of the winning line
 */
public class Result {

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

    public enum State {WINNER, DRAW, INCOMPLETE}
}
