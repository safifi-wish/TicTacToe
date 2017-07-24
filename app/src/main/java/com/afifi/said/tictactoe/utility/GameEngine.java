package com.afifi.said.tictactoe.utility;

import android.graphics.Point;
import android.support.v4.util.Pair;

import com.afifi.said.tictactoe.model.GameData;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.model.Tile;

import java.util.Random;

/**
 * This class only contains static methods that perform actions on a GameData object
 */
abstract public class GameEngine {

    /**
     * This method uses the game board and compares all valid directions that a line can be
     * created on the board
     *
     * @param tile     tile to be checked
     * @param gameData game containing board to be checked
     * @return result object which is either State.INCOMPLETE or State.WINNER if a tile sequence
     * was found
     */
    private static Result checkWinningTile(Tile tile, GameData gameData) {
        int rowCount, colCount;
        Pair<Player, Player> playerPair = gameData.getPlayerPair();
        boolean isPlayer1 = playerPair.first.getTile() == tile;
        Player winningPlayer = isPlayer1 ? playerPair.first : playerPair.second;

        // Check Rows and Columns
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            rowCount = 0;
            colCount = 0;
            for (int j = 0; j < Constants.BOARD_SIZE; j++) {
                // Check row
                if (gameData.getBoard()[j][i].equals(tile)) rowCount++;
                // Check column
                if (gameData.getBoard()[i][j].equals(tile)) colCount++;
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
            if (gameData.getBoard()[i][i].equals(tile)) diagonalCount++;
        }
        if (diagonalCount == Constants.BOARD_SIZE) {
            return new Result(
                    winningPlayer,
                    getCoordinates(0, 0, Constants.BOARD_SIZE - 1, Constants.BOARD_SIZE - 1));
        }
        diagonalCount = 0;
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (gameData.getBoard()[(Constants.BOARD_SIZE - 1) - i][i].equals(tile)) {
                diagonalCount++;
            }
        }
        if (diagonalCount == Constants.BOARD_SIZE) {
            return new Result(
                    winningPlayer,
                    getCoordinates(Constants.BOARD_SIZE - 1, 0, 0, Constants.BOARD_SIZE - 1));
        }
        return new Result(Result.State.INCOMPLETE);
    }

    /**
     * generate a pair of points object
     *
     * @param x1 point1.x
     * @param y1 point1.y
     * @param x2 point2.x
     * @param y2 point2.y
     * @return a pair of points
     */
    private static Pair<Point, Point> getCoordinates(int x1, int y1, int x2, int y2) {
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        return new Pair<>(point1, point2);
    }

    /**
     * check if current game is a complete
     *
     * @param gameData game board to check
     * @return true if board is full
     */
    private static boolean checkBoardFull(GameData gameData) {
        for (Tile[] rows : gameData.getBoard()) {
            for (Tile cell : rows) {
                if (cell == Tile.NONE) return false;
            }
        }
        return true;
    }


    /**
     * Given a game setup return a result object indicating if a player won, if a draw occurred or
     * the game is incomplete
     *
     * @param gameData current game data to be used for getting game result
     * @return result object containing current game result
     */
    public static Result getCurrentResult(GameData gameData) {
        Pair<Player, Player> playerPair = gameData.getPlayerPair();
        Result result = checkWinningTile(playerPair.first.getTile(), gameData);
        if (!result.getState().equals(Result.State.WINNER)) {
            result = checkWinningTile(playerPair.second.getTile(), gameData);
        }

        if (result.getState().equals(Result.State.INCOMPLETE) && checkBoardFull(gameData)) {
            result = new Result(Result.State.DRAW);
        }

        return result;
    }

    /**
     * Move method is used to perform an action on the game board, it returns the result of that
     * action.
     *
     * @param x        x coordinate from top-left must be less than Constansts.BOARD_SIZE
     * @param y        y coordinate from top-left must be less than Constansts.BOARD_SIZE
     * @param gameData game state information to manipulate
     * @return result containing outcome of a move
     */
    public static Result move(int x, int y, GameData gameData) {
        Result result = getCurrentResult(gameData);
        if (gameData.getBoard()[x][y] != Tile.NONE) {
            return result;
        } else if (!result.getState().equals(Result.State.INCOMPLETE)) {
            return result;
        }
        gameData.updateBoard(gameData.getPlayerTurn().getTile(), x, y);
        result = getCurrentResult(gameData);
        Pair<Player, Player> playerPair = gameData.getPlayerPair();
        switch (result.getState()) {
            case WINNER:
                result.getWinner().setWins(result.getWinner().getWins() + 1);
                break;
            case DRAW:
                playerPair.first.setDraws(playerPair.first.getDraws() + 1);
                playerPair.second.setDraws(playerPair.second.getDraws() + 1);
                break;
            case INCOMPLETE:
                boolean isFirstPlayerTurn = gameData.getPlayerTurn().equals(playerPair.second);
                gameData.setPlayerTurn(isFirstPlayerTurn ? playerPair.first : playerPair.second);
                break;
        }
        return result;
    }

    /**
     * Reset the game board and picks a new player randomly to start the next round
     *
     * @param gameData game state to reset
     */
    public static void resetGame(GameData gameData) {
        for (int x = 0; x < Constants.BOARD_SIZE; x++) {
            for (int y = 0; y < Constants.BOARD_SIZE; y++) {
                gameData.getBoard()[x][y] = Tile.NONE;
            }
        }

        // Pick a new Random player
        Random rand = new Random();
        Pair<Player, Player> playerPair = gameData.getPlayerPair();
        gameData.setPlayerTurn(rand.nextBoolean() ? playerPair.first : playerPair.second);
    }

}
