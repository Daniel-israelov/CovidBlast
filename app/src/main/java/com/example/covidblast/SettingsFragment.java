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

/**
 * Created By: Daniel Israelov
 * Date: 10/01/2022
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {
    ImageButton soundBtn;
    ImageButton musicBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        soundBtn = view.findViewById(R.id.btn_sound);
        musicBtn = view.findViewById(R.id.btn_music);

        soundBtn.setOnClickListener(this);
        musicBtn.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_sound:
                soundBtn.setActivated(!soundBtn.isActivated());
                break;
            case R.id.btn_music:
                musicBtn.setActivated(!musicBtn.isActivated());
                MusicPlayer.getInstance().setMusicOnOff(!MusicPlayer.getInstance().getIsMusicOn());
                break;
        }
    }
}
