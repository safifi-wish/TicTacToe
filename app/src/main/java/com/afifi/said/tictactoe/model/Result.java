package com.afifi.said.tictactoe.model;

import com.afifi.said.tictactoe.model.Player;

public class Result {

    public enum State {WINNER, DRAW, INCOMPLETE, GAME_OVER}

    private State state;
    private Player winningPlayer;

    public Result(State state) {
        this.state = state;
    }

    public Result(State state, Player winningPlayer) {
        this.state = state;
        this.winningPlayer = winningPlayer;
    }

    public State getState() {
        return state;
    }


    public Player getWinner() {
        return winningPlayer;
    }

    public void setState(State state) {
        this.state = state;
    }

}
