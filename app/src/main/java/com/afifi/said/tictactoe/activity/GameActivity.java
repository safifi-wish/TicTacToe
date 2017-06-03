package com.afifi.said.tictactoe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;
import com.afifi.said.tictactoe.views.BoardView;

public class GameActivity extends AppCompatActivity {
    public static int ROUND_TIME_OUT = 3000;
    private GameEngine currentGameEngine;
    private BoardView boardView;
    private Pair<Player, Player> playerPair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        String player1Key = getResources().getString(R.string.default_player1);
        String player2Key = getResources().getString(R.string.default_player2);
        String player1Name = player1Key;
        String player2Name = player2Key;
        if (extras != null) {
            player1Name = extras.getString(player1Key);
            player2Name = extras.getString(player2Key);
        }
        Player p1 = new Player(player1Name, Tile.X);
        Player p2 = new Player(player2Name, Tile.O);
        playerPair = new Pair<>(p1, p2);
        currentGameEngine = new GameEngine(playerPair, 3);
        boardView = (BoardView) findViewById(R.id.game_board);
        boardView.setEngineReference(currentGameEngine);
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processClick(event.getX(), event.getY(), v.getMeasuredHeight());
                return false;
            }
        });
    }

    private void processClick(float eventX, float eventY, int dimen) {
        int x = (int) (Constants.BOARD_SIZE * (eventX / dimen));
        int y = (int) (Constants.BOARD_SIZE * (eventY / dimen));
        Result result = currentGameEngine.move(x, y);
        boardView.invalidate();

        if (result.getState().equals(Result.State.DRAW) ||
                result.getState().equals(Result.State.WINNER)) {
            boardView.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentGameEngine.resetBoard();
                    boardView.invalidate();
                    boardView.setEnabled(true);
                }
            }, ROUND_TIME_OUT);
        } else if (result.getState().equals(Result.State.GAME_OVER)){
            boardView.setEnabled(false);
            Toast.makeText(getApplicationContext(),result.getWinner().getName() + " won",
                    Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, ROUND_TIME_OUT);
        }

    }
}
