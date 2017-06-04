package com.afifi.said.tictactoe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.ui.custom_view.BoardView;
import com.afifi.said.tictactoe.utility.Constants;

public class SplashActivity extends Activity {

    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BoardView boardView = (BoardView) findViewById(R.id.splash_board);
        GameEngine gameEngine = getSampleEngine();
        boardView.setEngineReference(gameEngine);

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

    private GameEngine getSampleEngine() {
        Player player1 = new Player(getResources().getString(R.string.default_player1), Tile.O);
        Player player2 = new Player(getResources().getString(R.string.default_player2), Tile.X);
        GameEngine gameEngine = new GameEngine(player1, player2);
        //Sample board
        for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
            if (i % 2 == 0) {
                gameEngine.updateBoard(Tile.X, i, i);
            } else {
                gameEngine.updateBoard(Tile.O, i, i);
            }
        }
        return gameEngine;
    }
}
