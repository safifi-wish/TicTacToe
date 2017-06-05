package com.afifi.said.tictactoe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.utility.Constants;

/**
 * Score fragment displays the running score of the players
 */
public class ScoreFragment extends Fragment {
    public static final String PLAYER_TURN_KEY = "PLAYER_TURN_KEY";
    TextView turnTitleTv;

    public static ScoreFragment newInstance(Pair<Player, Player> playerPair, Player playerTurn) {
        ScoreFragment scoreFragment = new ScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PLAYER1_KEY, playerPair.first);
        bundle.putSerializable(Constants.PLAYER2_KEY, playerPair.second);
        bundle.putSerializable(PLAYER_TURN_KEY, playerTurn);
        scoreFragment.setArguments(bundle);
        return scoreFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);
        View container1 = rootView.findViewById(R.id.score_player1_container_view);
        View container2 = rootView.findViewById(R.id.score_player2_container_view);
        turnTitleTv = (TextView) rootView.findViewById(R.id.score_turn_tv);

        Player player2 = (Player) getArguments().getSerializable(Constants.PLAYER2_KEY);
        Player player1 = (Player) getArguments().getSerializable(Constants.PLAYER1_KEY);
        Player playerTurn = (Player) getArguments().getSerializable(PLAYER_TURN_KEY);
        setPlayerInfo((ViewGroup) container1, player1);
        setPlayerInfo((ViewGroup) container2, player2);
        setTurnInfo(playerTurn);
        return rootView;
    }

    /**
     * Update the containers to custom score layout for each player
     *
     * @param view_container container to place custom item player score layout
     * @param player         player to display score data
     */
    private void setPlayerInfo(ViewGroup view_container, Player player) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        LinearLayout playerLayout =
                (LinearLayout) layoutInflater.inflate(R.layout.item_player_score, view_container);
        TextView nameTv = (TextView) playerLayout.findViewById(R.id.score_player_name_tv);
        TextView winsTitleTv = (TextView) playerLayout.findViewById(R.id.score_wins_title_tv);
        TextView winsValueTv = (TextView) playerLayout.findViewById(R.id.score_wins_value_tv);
        TextView drawTitleTv = (TextView) playerLayout.findViewById(R.id.score_draws_title_tv);
        TextView drawValueTv = (TextView) playerLayout.findViewById(R.id.score_draws_value_tv);
        nameTv.setText(player.getName());
        winsValueTv.setText(String.valueOf(player.getWins()));
        drawValueTv.setText(String.valueOf(player.getDraws()));

        int color = ResourcesCompat.getColor(getResources(), player.getTile().getColorId(), null);
        nameTv.setTextColor(color);
        winsTitleTv.setTextColor(color);
        winsValueTv.setTextColor(color);
        drawTitleTv.setTextColor(color);
        drawValueTv.setTextColor(color);
    }

    /**
     * Updates the UI with the player's turn info
     *
     * @param player player to be displayed
     */
    public void setTurnInfo(Player player) {
        getArguments().putSerializable(PLAYER_TURN_KEY, player);
        if (turnTitleTv == null) {
            return;
        }
        String title = player.getName() + "'s Turn";
        int color = ResourcesCompat.getColor(getResources(), player.getTile().getColorId(), null);
        turnTitleTv.setText(title);
        turnTitleTv.setTextColor(color);
    }
}
