package com.afifi.said.tictactoe.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.fragment.ResultFragment;
import com.afifi.said.tictactoe.fragment.ScoreFragment;
import com.afifi.said.tictactoe.model.GameData;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.utility.Constants;
import com.afifi.said.tictactoe.utility.GameEngine;
import com.afifi.said.tictactoe.view.BoardView;

/**
 * GameActivity displays the board view and game results.
 */
public class GameActivity extends FragmentActivity
        implements ResultFragment.OnPlayAgainClickListener {
    private GameData currentGame;
    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setupGame();
        setupBoardView();

        if (findViewById(R.id.game_fragment_container) != null) {
            if (savedInstanceState != null) return;
            Pair<Player, Player> playerPair = currentGame.getPlayerPair();
            Player playerTurn = currentGame.getPlayerTurn();
            ScoreFragment scoreFragment = ScoreFragment.newInstance(playerPair, playerTurn);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game_fragment_container, scoreFragment).commit();
        }
    }

    /**
     * Initialize a new game engine with a new board or retrieve the engine from a saved state
     */
    private void setupGame() {
        // Save game state on configuration changes
        final Object gameEngine = getLastCustomNonConfigurationInstance();
        if (gameEngine == null) {
            Bundle extras = getIntent().getExtras();
            Player player1 = (Player) extras.getSerializable(Constants.PLAYER1_KEY);
            Player player2 = (Player) extras.getSerializable(Constants.PLAYER2_KEY);
            currentGame = new GameData(player1, player2);
        } else {
            currentGame = (GameData) gameEngine;
        }
    }

    /**
     * Set the onClickListener on the board and the game engine reference for updating the board
     */
    private void setupBoardView() {
        boardView = (BoardView) findViewById(R.id.game_board_view);
        boardView.setEngineReference(currentGame);
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                processClick(event.getX(), event.getY());
                return false;
            }
        });
    }

    /**
     * Process a click on the board. If a round was completed display the result and disable
     * board clicking
     *
     * @param positionX x position relative to top-left corner of the board
     * @param positionY y position relative to top-left corner of the board
     */
    private void processClick(float positionX, float positionY) {
        int x = boardView.getBoardTranslation(positionX);
        int y = boardView.getBoardTranslation(positionY);
        final Result result = GameEngine.move(x, y, currentGame);
        boardView.invalidate();
        if (!result.getState().equals(Result.State.INCOMPLETE)) {
            boardView.setEnabled(false);
            replaceFragment(ResultFragment.newInstance(result));
        } else {
            Fragment fragment = getSupportFragmentManager().
                    findFragmentById(R.id.game_fragment_container);
            if (fragment instanceof ScoreFragment) {
                ((ScoreFragment) fragment).setTurnInfo(currentGame.getPlayerTurn());
            }
        }
    }

    /**
     * Replace the fragment in the container with the fragment passed.
     *
     * @param fragment fragment to be used in place of the current fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_fragment_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * This method is triggered when a game is over and the user presses on the "Play Again"
     * button. It resets the board and invalidates the view.
     */
    @Override
    public void onPlayAgain() {
        GameEngine.resetGame(currentGame);
        Pair<Player, Player> playerPair = currentGame.getPlayerPair();
        Player playerTurn = currentGame.getPlayerTurn();
        replaceFragment(ScoreFragment.newInstance(playerPair, playerTurn));
        boardView.invalidate();
        boardView.setEnabled(true);
    }

    /**
     * On state changes such as orientation changes, save the current board and the game
     * information.
     *
     * @return The game state details
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return currentGame;
    }

}
