package com.wat128.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
