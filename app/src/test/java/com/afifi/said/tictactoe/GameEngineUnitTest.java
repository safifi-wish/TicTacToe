package com.afifi.said.tictactoe;

import android.support.v4.util.Pair;

import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameEngineUnitTest {

    private GameEngine game;
    private Pair<Player, Player> playerPair;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        Player p1 = new Player("test1", Tile.X);
        Player p2 = new Player("test2", Tile.O);
        playerPair = new Pair<>(p1, p2);
        game = new GameEngine(playerPair, 1);
    }

    @Test
    public void board_creation() throws Exception {
        assertNotNull(game.getBoard());
    }

    @Test
    public void check_empty_board() throws Exception {
        Tile[][] board = game.getBoard();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            for (int row = 0; row < Constants.BOARD_SIZE; row++) {
                assertEquals(board[row][col], Tile.NONE);
            }
        }
    }

    @Test
    public void updating_board() throws Exception {
        Tile[][] board = game.getBoard();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            for (int row = 0; row < Constants.BOARD_SIZE; row++) {
                assertEquals(board[row][col], Tile.NONE);
            }
        }
        game.updateBoard(Tile.O, 0, 0);
        assertEquals(game.getBoard()[0][0], Tile.O);
        game.updateBoard(Tile.X, 0, 0);
        assertEquals(game.getBoard()[0][0], Tile.X);
        game.updateBoard(Tile.X, Constants.BOARD_SIZE - 1, Constants.BOARD_SIZE - 1);
        assertEquals(game.getBoard()[Constants.BOARD_SIZE - 1][Constants.BOARD_SIZE - 1], Tile.X);
    }

    @Test
    public void check_incomplete_empty_board() throws Exception {
        assertEquals(game.getCurrentResult().getState(), Result.State.INCOMPLETE);
    }

    @Test
    public void check_winner_row_board() throws Exception {
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            game.updateBoard(playerPair.first.getTile(), 0, col);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinner(), playerPair.first);
    }

    @Test
    public void check_winner_col_board() throws Exception {
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            game.updateBoard(playerPair.first.getTile(), row, 0);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinner(), playerPair.first);
    }

    @Test
    public void check_winner_diagonal_board() throws Exception {
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            game.updateBoard(playerPair.first.getTile(), i, i);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinner(), playerPair.first);

        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            game.updateBoard(playerPair.first.getTile(), i, (Constants.BOARD_SIZE - 1) - i);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinner(), playerPair.first);
    }

    @Test
    public void check_draw_board() throws Exception {
        assertEquals(Constants.BOARD_SIZE, 3);
        /*
            X|O|X
            -----
            O|O|X
            -----
            X|X|O
         */
        game.updateBoard(Tile.X, 0, 0);
        game.updateBoard(Tile.O, 0, 1);
        game.updateBoard(Tile.X, 0, 2);
        game.updateBoard(Tile.O, 1, 0);
        game.updateBoard(Tile.O, 1, 1);
        game.updateBoard(Tile.X, 1, 2);
        game.updateBoard(Tile.X, 2, 0);
        game.updateBoard(Tile.X, 2, 1);
        game.updateBoard(Tile.O, 2, 2);

        assertEquals(game.getCurrentResult().getState(), Result.State.DRAW);
    }

}