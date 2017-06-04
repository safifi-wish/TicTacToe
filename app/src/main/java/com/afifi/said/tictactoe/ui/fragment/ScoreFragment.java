package com.afifi.said.tictactoe.ui.fragment;

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

public class ScoreFragment extends Fragment {

    public static ScoreFragment newInstance(Pair<Player, Player> playerPair) {
        ScoreFragment scoreFragment = new ScoreFragment();
        Bundle bundle =  new Bundle();
        bundle.putSerializable(Constants.PLAYER1_KEY, playerPair.first);
        bundle.putSerializable(Constants.PLAYER2_KEY, playerPair.second);
        scoreFragment.setArguments(bundle);
        return scoreFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);
        View container1 = rootView.findViewById(R.id.score_player1_container_view);
        View container2 = rootView.findViewById(R.id.score_player2_container_view);
        Player player1 = (Player) getArguments().getSerializable(Constants.PLAYER1_KEY);
        Player player2 = (Player) getArguments().getSerializable(Constants.PLAYER2_KEY);
        setPlayerInfo((ViewGroup) container1, player1);
        setPlayerInfo((ViewGroup) container2, player2);
        return rootView;
    }

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

}
