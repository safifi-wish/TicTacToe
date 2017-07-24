package com.afifi.said.tictactoe.model;

import android.support.v4.util.Pair;

import com.afifi.said.tictactoe.utility.Constants;
import com.afifi.said.tictactoe.utility.GameEngine;

/**
 * Holds information of current game, including board setup, players associated, and players turn
 */
public class GameData {
    private Tile[][] board;
    private Pair<Player, Player> playerPair;
    private Player playerTurn;

    public GameData(Player player1, Player player2) {
        this.playerPair = new Pair<>(player1, player2);
        this.board = new Tile[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        GameEngine.resetGame(this);
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Pair<Player, Player> getPlayerPair() {
        return playerPair;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void updateBoard(Tile tile, int x, int y) {
        board[x][y] = tile;
    }
}
