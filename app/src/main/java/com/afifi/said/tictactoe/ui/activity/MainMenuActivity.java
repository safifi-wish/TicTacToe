package com.afifi.said.tictactoe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.model.Player;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final EditText player1Et = (EditText) findViewById(R.id.main_menu_player1_et);
        final EditText player2Et = (EditText) findViewById(R.id.main_menu_player2_et);

        Button playBtn = (Button) findViewById(R.id.main_menu_play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                String player1Name = getName(player1Et, R.string.default_player1);
                String player2Name = getName(player2Et, R.string.default_player2);
                Player player1 = new Player(player1Name, Tile.X);
                Player player2 = new Player(player2Name, Tile.O);
                intent.putExtra(Constants.PLAYER1_KEY, player1);
                intent.putExtra(Constants.PLAYER2_KEY, player2);
                startActivity(intent);
            }
        });
    }

    String getName(EditText playerEt, int defaultId) {
        if (playerEt == null || playerEt.getText().toString().equals("")) {
            return getResources().getString(defaultId);
        } else {
            return playerEt.getText().toString();
        }
    }
}
