package com.example.covidblast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// TODO: add PlayMenu like Yaniv.

public class DifficultySelectionFragment extends Fragment implements View.OnClickListener{
    Button easyBtn, medBtn, hardBtn, xtrBtn;
    public static boolean GAME_STARTED = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficulty_selection, container, false);

        easyBtn = view.findViewById(R.id.btn_easy);
        medBtn = view.findViewById(R.id.btn_medium);
        hardBtn = view.findViewById(R.id.btn_hard);
        xtrBtn = view.findViewById(R.id.btn_extreme);

        easyBtn.setOnClickListener(this);
        medBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        xtrBtn.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Difficulty diff = Difficulty.getInstance();
        switch (view.getId()){
            case R.id.btn_easy:
                diff.setCurrentDifficulty(Difficulties.EASY);
                break;
            case R.id.btn_medium:
                diff.setCurrentDifficulty(Difficulties.MEDIUM);
                break;
            case R.id.btn_hard:
                diff.setCurrentDifficulty(Difficulties.HARD);
                break;
            case R.id.btn_extreme:
                diff.setCurrentDifficulty(Difficulties.EXTREME);
                break;
        }

        GAME_STARTED = true;
        getParentFragmentManager().beginTransaction()
                .hide(getParentFragmentManager().findFragmentByTag("main_cover_fragment")).commit();
        GameRunning gameRunning = new GameRunning();

    }

}