package com.wat128.catchtheball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static MediaPlayer mainBgm;
    private static MediaPlayer titleBgm;
    private static int hitSound;
    private static int overSound;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
        mainBgm = MediaPlayer.create(context, R.raw.wt);
        titleBgm = MediaPlayer.create(context, R.raw.csikos);
    }

    public void playMainBgm() {
        mainBgm.setLooping(true);
        mainBgm.start();
    }

    public void playTitleBgm() {
        titleBgm.setLooping(true);
        titleBgm.start();
    }

    public void stopMainBgm() {
        mainBgm.stop();
    }

    public void stopTitleBgm() {
        titleBgm.stop();
    }

    public void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playOverSound() {
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
