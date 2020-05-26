package com.wat128.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;
    private TextView highScoreLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        highScoreLabel = findViewById(R.id.highScoreLabel);
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText("High Score : " + highScore);

        soundPlayer = new SoundPlayer(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        soundPlayer.playTitleBgm();
    }

    public void startGame(View view){
        soundPlayer.stopTitleBgm();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
