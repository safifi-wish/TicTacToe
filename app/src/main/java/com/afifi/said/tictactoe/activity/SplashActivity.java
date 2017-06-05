package com.afifi.said.tictactoe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.model.GameData;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;
import com.afifi.said.tictactoe.view.BoardView;

/**
 * Splash activity is used to display a short intro page
 */
public class SplashActivity extends Activity {

    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BoardView boardView = (BoardView) findViewById(R.id.splash_board);
        boardView.setEngineReference(getSampleGame());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Move to Next activity when timer finishes
                Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private GameData getSampleGame() {
        Player player1 = new Player(getResources().getString(R.string.default_player1), Tile.O);
        Player player2 = new Player(getResources().getString(R.string.default_player2), Tile.X);
        GameData game = new GameData(player1, player2);
        //Sample board
        for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
            if (i % 2 == 0) {
                game.updateBoard(Tile.X, i, i);
            } else {
                game.updateBoard(Tile.O, i, i);
            }
        }
        return game;
    }
}
