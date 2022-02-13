package com.example.covidblast;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * MusicPlayer is a Singleton class that manages the in app music
 * and in game sound effects.
 */
public class MusicPlayer {
    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private boolean isMusicOn = true;
    private boolean lastMusicMode = true; //handle music in main activity 'onPause' and 'onResume'

    public static MusicPlayer getInstance() {
        if (instance == null)
            instance = new MusicPlayer();
        return instance;
    }

    //initialize the music in MainActivity.
    public void initialize(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.bg_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
    }

    public boolean getIsMusicOn(){ return this.isMusicOn; }
    public boolean getLastMusicMode(){ return this.lastMusicMode; }

    public void setMusicOnOff(boolean mode){
        isMusicOn = mode;
        setLastMusicMode(mode);

        if(isMusicOn)
            mediaPlayer.start();
        else
            mediaPlayer.pause();
    }
    public void setLastMusicMode(boolean lastMode){ this.lastMusicMode = lastMode; }
}
