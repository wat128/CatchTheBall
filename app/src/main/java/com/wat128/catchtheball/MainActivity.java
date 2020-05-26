package com.wat128.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    // サイズ
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    // 位置
    private float boxY;
    private float orangeX;
    private float orangeY;
    private float pinkX;
    private float pinkY;
    private float blackX;
    private float blackY;

    // スコア
    private int score = 0;

    // Handler & Timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        // Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);

        scoreLabel.setText("score : 0");
    }

    public void changePos() {

        hitCheck();

        // Orange
        orangeX -= 12;
        if(orangeX < 0){
            orangeX = screenWidth + 20;
            orangeY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Black
        blackX -= 16;
        if(blackX < 0){
            blackX = screenWidth + 10;
            blackY = (float)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        // Pint
        pinkX -= 20;
        if(pinkX < 0){
            pinkX = screenWidth + 5000;
            pinkY = (float)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        // Box
        if(action_flg) {
            boxY -= 20;
        }
        else {
            boxY += 20;
        }

        if(boxY < 0)
            boxY = 0;

        if(boxY > frameHeight - boxSize)
            boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText(("score : " + score));
    }

    @Override
    public void onBackPressed() { }

    public void hitCheck() {
        // Orange
        float orangeCenterX = orangeX + orange.getHeight() / 2;
        float orangeCenterY = orangeY + orange.getHeight() / 2;

        if(hitStatus(orangeCenterX, orangeCenterY)) {
            orangeX = -10.0f;
            score += 10;
        }

        // Pink
        float pinkCenterX = pinkX + pink.getHeight() / 2;
        float pinkCenterY = pinkY + pink.getHeight() / 2;

        if(hitStatus(pinkCenterX, pinkCenterY)) {
            pinkX = -10.0f;
            score += 30;
        }

        // Black
        float blackCenterX = blackX + black.getHeight() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

       if(hitStatus(blackCenterX, blackCenterY)) {
            // Game Over!
            if(timer != null){
                timer.cancel();
                timer = null;
            }

            // リザルト画面
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    public boolean hitStatus(float centerX, float centerY)
    {
        if(0 <= centerX
            && centerX <= boxSize
            && boxY <= centerY
            && centerY <= boxY + boxSize)
        {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flg == false){
            start_flg = true;
            frameHeight = findViewById(R.id.frame).getHeight();
            boxY = box.getY();
            boxSize = box.getHeight();

            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
        else {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            }
            else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }
}