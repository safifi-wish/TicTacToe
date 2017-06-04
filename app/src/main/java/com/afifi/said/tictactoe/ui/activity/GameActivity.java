package com.afifi.said.tictactoe.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.ui.custom_view.BoardView;
import com.afifi.said.tictactoe.ui.fragment.ResultFragment;
import com.afifi.said.tictactoe.ui.fragment.ScoreFragment;
import com.afifi.said.tictactoe.utility.Constants;

public class GameActivity extends FragmentActivity
        implements ResultFragment.OnPlayAgainClickListener {
    private GameEngine currentGameEngine;
    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setupGameEngine();
        setupBoardView();

        if (findViewById(R.id.game_fragment_container) != null) {
            if (savedInstanceState != null) return;
            Pair<Player, Player> playerPair = currentGameEngine.getPlayerPair();
            ScoreFragment scoreFragment = ScoreFragment.newInstance(playerPair);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game_fragment_container, scoreFragment).commit();
        }
    }

    private void setupGameEngine() {
        // Save game state on configuration changes
        final Object gameEngine = getLastCustomNonConfigurationInstance();
        if (gameEngine == null) {
            Bundle extras = getIntent().getExtras();
            Player player1 = (Player) extras.getSerializable(Constants.PLAYER1_KEY);
            Player player2 = (Player) extras.getSerializable(Constants.PLAYER2_KEY);
            currentGameEngine = new GameEngine(player1, player2);
        } else {
            currentGameEngine = (GameEngine) gameEngine;
        }
    }

    private void setupBoardView() {
        boardView = (BoardView) findViewById(R.id.game_board_view);
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
        final Result result = currentGameEngine.move(x, y);
        boardView.invalidate();
        if (!result.getState().equals(Result.State.INCOMPLETE)) {
            boardView.setEnabled(false);
            replaceFragment(ResultFragment.newInstance(result));
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onPlayAgain() {
        replaceFragment(ScoreFragment.newInstance(currentGameEngine.getPlayerPair()));
        currentGameEngine.resetGame();
        boardView.resetGame();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return currentGameEngine;
    }

}
