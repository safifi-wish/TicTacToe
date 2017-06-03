package com.afifi.said.tictactoe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.afifi.said.tictactoe.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                String player1Key = getResources().getString(R.string.default_player1);
                String player2Key = getResources().getString(R.string.default_player2);
                intent.putExtra(player1Key,"bob");
                intent.putExtra(player2Key,"alice");
                startActivity(intent);
            }
        });
    }
}
