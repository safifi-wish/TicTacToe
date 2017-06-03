package com.afifi.said.tictactoe.controller;

import com.afifi.said.tictactoe.utility.Constants;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeEngine {
    private Tile[][] board;

    public TicTacToeEngine() {
        board = new Tile[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        for( int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                board[row][col] = Tile.NONE;
            }
        }
    }
    public void updateBoard(Tile tile, int row, int column){
        board[row][column] = tile;
    }

    public Result getCurrentResult(){

        Tile winner = checkRows();
        if( winner.equals(Tile.NONE)){
            winner = checkColumns();
        }
        if( winner.equals(Tile.NONE) ){
            winner = checkDiagonals();
        }
        if( winner.equals(Tile.NONE) ){
            if( checkForDraw() ){
                return new Result(Result.State.DRAW);
            }else{
                return new Result(Result.State.INCOMPLETE);
            }
        }else{
            return new Result(Result.State.WINNER , winner);
        }

    }

    private Tile checkDiagonals() {
        Map<Tile,Integer> counters = new HashMap<>();
        for(int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (counters.containsKey(board[i][i])) {
                counters.put(board[i][i], counters.get(board[i][i]) + 1);
            } else {
                counters.put(board[i][i], 1);
            }
        }
        for (Tile tile : counters.keySet()) {
            if (counters.get(tile) == Constants.BOARD_SIZE) {
                return tile;
            }
        }
        counters.clear();
        for(int i = 0; i < Constants.BOARD_SIZE; i++) {
            int col = ( Constants.BOARD_SIZE - 1 ) - i;
            if (counters.containsKey(board[i][col])) {
                counters.put(board[i][col], counters.get(board[i][col]) + 1);
            } else {
                counters.put(board[i][col], 1);
            }
        }
        for (Tile tile : counters.keySet()) {
            if (counters.get(tile) == Constants.BOARD_SIZE) {
                return tile;
            }
        }
        return Tile.NONE;
    }

    private Tile checkColumns() {
        Map<Tile,Integer> counters = new HashMap<>();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            for( int row = 0; row < Constants.BOARD_SIZE; row++) {
                if (counters.containsKey(board[row][col])) {
                    counters.put(board[row][col], counters.get(board[row][col]) + 1);
                } else {
                    counters.put(board[row][col], 1);
                }
            }
            for (Tile tile : counters.keySet()) {
                if (counters.get(tile) == Constants.BOARD_SIZE) {
                    return tile;
                }
            }
            counters.clear();
        }
        return Tile.NONE;
    }

    private Tile checkRows() {
        Map<Tile,Integer> counters = new HashMap<>();
        for( int row = 0; row < Constants.BOARD_SIZE; row++) {
            for (int col = 0; col < Constants.BOARD_SIZE; col++) {
                if (counters.containsKey(board[row][col])) {
                    counters.put(board[row][col], counters.get(board[row][col]) + 1);
                } else {
                    counters.put(board[row][col], 1);
                }
            }
            for (Tile tile : counters.keySet()) {
                if (counters.get(tile) == Constants.BOARD_SIZE) {
                    return tile;
                }
            }
            counters.clear();
        }
        return Tile.NONE;
    }

    private boolean checkForDraw() {
        for (Tile[] rows : board) {
            for (Tile cell : rows) {
                if(cell == Tile.NONE) return false;
            }
        }
        return true;
    }

    public Tile[][] getBoard() {
        return board;
    }

}
