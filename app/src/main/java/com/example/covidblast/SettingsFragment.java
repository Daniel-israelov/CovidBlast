package com.example.covidblast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    @SuppressLint("StaticFieldLeak")
    public static ImageButton musicBtn;
    @SuppressLint("StaticFieldLeak")
    public static ImageButton soundBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(getActivity());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_up));
        setExitTransition(inflater.inflateTransition(R.transition.slide_up));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        musicBtn = view.findViewById(R.id.btn_music);
        soundBtn = view.findViewById(R.id.btn_sound);

        musicBtn.setOnClickListener(this);
        soundBtn.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_sound: //in game sound effect
                soundBtn.setActivated(!soundBtn.isActivated());
                GameRunning.sound = !GameRunning.sound;
                break;
            case R.id.btn_music: //background music
                musicBtn.setActivated(!musicBtn.isActivated());
                MusicPlayer.getInstance().setMusicOnOff(!MusicPlayer.getInstance().getIsMusicOn());
                break;
        }
    }
}
