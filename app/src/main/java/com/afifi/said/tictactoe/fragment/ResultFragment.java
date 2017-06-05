package com.afifi.said.tictactoe.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Result;

/**
 * Result fragment displays of a game (winning player/Draw)
 */
public class ResultFragment extends Fragment {
    public static final String DRAW_KEY = "DRAW_VS_WIN_KEY";
    public static final String PLAYER_KEY = "PLAYER_KEY";

    OnPlayAgainClickListener playAgainCallback;

    public static ResultFragment newInstance(Result result) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DRAW_KEY, result.getState().equals(Result.State.DRAW));
        bundle.putSerializable(PLAYER_KEY, result.getWinner());
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        TextView playerNameTextView = (TextView) rootView.findViewById(R.id.result_player_tv);
        TextView InfoTextView = (TextView) rootView.findViewById(R.id.result_info_tv);

        if (getArguments().getBoolean(DRAW_KEY)) {
            InfoTextView.setText(getResources().getString(R.string.result_draw));
        } else if (getArguments().getSerializable(PLAYER_KEY) != null &&
                getArguments().getSerializable(PLAYER_KEY) instanceof Player) {
            Player player = (Player) getArguments().getSerializable(PLAYER_KEY);
            if (player != null) {
                int colorId = player.getTile().getColorId();
                int color = ResourcesCompat.getColor(getResources(), colorId, null);
                playerNameTextView.setText(player.getName());
                InfoTextView.setText(getResources().getString(R.string.result_win));
                playerNameTextView.setTextColor(color);
                InfoTextView.setTextColor(color);
            }
        }
        Button playAgainBtn = (Button) rootView.findViewById(R.id.result_again_btn);
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainCallback.onPlayAgain();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            playAgainCallback = (OnPlayAgainClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface OnPlayAgainClickListener {
        void onPlayAgain();
    }

}
