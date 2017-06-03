package com.afifi.said.tictactoe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;
import com.afifi.said.tictactoe.views.BoardView;

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
                // This method will be executed once the timer is over
                // Display main menu
                Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private GameEngine getSampleEngine() {
        String player1 = getResources().getString(R.string.default_player1);
        String player2 = getResources().getString(R.string.default_player1);
        Player p1 = new Player(player1, Tile.X);
        Player p2 = new Player(player2, Tile.O);
        Pair<Player, Player> playerPair = new Pair<>(p1, p2);
        GameEngine gameEngine = new GameEngine(playerPair, 1);
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
