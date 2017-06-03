package com.afifi.said.tictactoe;

import com.afifi.said.tictactoe.controller.Result;
import com.afifi.said.tictactoe.controller.TicTacToeEngine;
import com.afifi.said.tictactoe.controller.Tile;
import com.afifi.said.tictactoe.utility.Constants;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TicTacToeEngineUnitTest {
    @Test
    public void board_creation() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        assertNotNull(game.getBoard());
    }

    @Test
    public void check_empty_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        Tile[][] board = game.getBoard();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            for (int row = 0; row < Constants.BOARD_SIZE; row++) {
                assertEquals(board[row][col], Tile.NONE);
            }
        }
    }

    @Test
    public void updating_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        Tile[][] board = game.getBoard();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            for (int row = 0; row < Constants.BOARD_SIZE; row++) {
                assertEquals(board[row][col], Tile.NONE);
            }
        }
        game.updateBoard(Tile.O,0,0);
        assertEquals(game.getBoard()[0][0], Tile.O);
        game.updateBoard(Tile.X,0,0);
        assertEquals(game.getBoard()[0][0], Tile.X);
        game.updateBoard(Tile.X,Constants.BOARD_SIZE-1,Constants.BOARD_SIZE-1);
        assertEquals(game.getBoard()[Constants.BOARD_SIZE-1][Constants.BOARD_SIZE-1], Tile.X);
    }

    @Test
    public void check_incomplete_empty_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        assertEquals(game.getCurrentResult().getState(), Result.State.INCOMPLETE);
    }

    @Test
    public void check_winner_row_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        for (int col = 0; col < Constants.BOARD_SIZE; col++) {
            game.updateBoard(Tile.values()[0] , 0, col);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinnerTile(), Tile.values()[0]);
    }

    @Test
    public void check_winner_col_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        for (int row = 0; row < Constants.BOARD_SIZE; row++) {
            game.updateBoard(Tile.values()[0] , row, 0);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinnerTile(), Tile.values()[0]);
    }

    @Test
    public void check_winner_diagonal_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            game.updateBoard(Tile.values()[0] , i, i);
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinnerTile(), Tile.values()[0]);

        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            game.updateBoard(Tile.values()[0] , i, ( Constants.BOARD_SIZE - 1 ) - i );
        }
        assertEquals(game.getCurrentResult().getState(), Result.State.WINNER);
        assertEquals(game.getCurrentResult().getWinnerTile(), Tile.values()[0]);
    }

    @Test
    public void check_draw_board() throws Exception {
        TicTacToeEngine game = new TicTacToeEngine();
        assertEquals(Constants.BOARD_SIZE, 3);
        /*
            X|O|X
            -----
            O|O|X
            -----
            X|X|O
         */
        game.updateBoard(Tile.X,0, 0);game.updateBoard(Tile.O,0, 1);game.updateBoard(Tile.X,0, 2);
        game.updateBoard(Tile.O,1, 0);game.updateBoard(Tile.O,1, 1);game.updateBoard(Tile.X,1, 2);
        game.updateBoard(Tile.X,2, 0);game.updateBoard(Tile.X,2, 1);game.updateBoard(Tile.O,2, 2);

        assertEquals(game.getCurrentResult().getState(), Result.State.DRAW);
    }

}