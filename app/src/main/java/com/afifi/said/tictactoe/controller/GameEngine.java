package com.afifi.said.tictactoe.controller;

import android.graphics.Point;
import android.support.v4.util.Pair;

import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;

import java.util.Random;

public class GameEngine {
    private Tile[][] board;
    private Pair<Player, Player> playerPair;
    private Player playerTurn;

    public GameEngine(Player player1, Player player2) {
        this.board = new Tile[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
        for (int x = 0; x < Constants.BOARD_SIZE; x++) {
            for (int y = 0; y < Constants.BOARD_SIZE; y++) {
                board[x][y] = Tile.NONE;
            }
        }
        this.playerPair = new Pair<>(player1, player2);
        Random rand = new Random();
        this.playerTurn = rand.nextBoolean() ? playerPair.first : playerPair.second;
    }

    public void updateBoard(Tile tile, int x, int y) {
        board[x][y] = tile;
    }

    public Result getCurrentResult() {
        Result result = checkWinningTile(playerPair.first.getTile());
        if (!result.getState().equals(Result.State.WINNER)) {
            result = checkWinningTile(playerPair.second.getTile());
        }
        if (result.getState().equals(Result.State.INCOMPLETE) && checkForDraw()) {
            return new Result(Result.State.DRAW);
        } else {
            return result;
        }
    }

    private Result checkWinningTile(Tile tile) {
        int rowCount, colCount;
        boolean isPlayer1 = playerPair.first.getTile() == tile;
        Player winningPlayer = isPlayer1 ? playerPair.first : playerPair.second;

        // check Rows and Columns
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            rowCount = 0;
            colCount = 0;
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                //check row
                if (board[j][i].equals(tile)) rowCount++;
                //check col
                if (board[i][j].equals(tile)) colCount++;
            }

            if (colCount == Constants.BOARD_SIZE) {
                return new Result(winningPlayer, getCoordinates(i, 0, i, Constants.BOARD_SIZE - 1));
            } else if (rowCount == Constants.BOARD_SIZE) {
                return new Result(winningPlayer, getCoordinates(0, i, Constants.BOARD_SIZE - 1, i));
            }
        }

        // Check Diagonals
        int diagonalCount = 0;
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (board[i][i].equals(tile)) diagonalCount++;
        }
        if (diagonalCount == Constants.BOARD_SIZE) {
            return new Result(winningPlayer,
                    getCoordinates(0, 0, Constants.BOARD_SIZE - 1, Constants.BOARD_SIZE - 1));
        }
        diagonalCount = 0;
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (board[(Constants.BOARD_SIZE - 1) - i][i].equals(tile)) diagonalCount++;
        }
        if (diagonalCount == Constants.BOARD_SIZE) {
            return new Result(winningPlayer,
                    getCoordinates(Constants.BOARD_SIZE - 1, 0, 0, Constants.BOARD_SIZE - 1));
        }
        return new Result(Result.State.INCOMPLETE);
    }

    private Pair<Point, Point> getCoordinates(int x1, int y1, int x2, int y2) {
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        return new Pair<>(point1, point2);
    }

    private boolean checkForDraw() {
        for (Tile[] rows : board) {
            for (Tile cell : rows) {
                if (cell == Tile.NONE) return false;
            }
        }
        return true;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Result move(int x, int y) {
        Result result = getCurrentResult();
        if (board[x][y] != Tile.NONE) {
            return result;
        } else if (!result.getState().equals(Result.State.INCOMPLETE)) {
            return result;
        }
        updateBoard(playerTurn.getTile(), x, y);
        result = getCurrentResult();
        switch (result.getState()) {
            case WINNER:
                result.getWinner().setWins(result.getWinner().getWins() + 1);
                break;
            case DRAW:
                playerPair.first.setDraws(playerPair.first.getDraws() + 1);
                playerPair.second.setDraws(playerPair.second.getDraws() + 1);
                break;
            case INCOMPLETE:
                playerTurn = playerTurn.equals(playerPair.second) ? playerPair.first : playerPair.second;
                break;
        }
        return result;
    }

    public void resetGame() {
        for (int x = 0; x < Constants.BOARD_SIZE; x++) {
            for (int y = 0; y < Constants.BOARD_SIZE; y++) {
                board[x][y] = Tile.NONE;
            }
        }
        Random rand = new Random();
        this.playerTurn = rand.nextBoolean() ? playerPair.first : playerPair.second;
    }

    public Pair<Player, Player> getPlayerPair() {
        return playerPair;
    }

}
