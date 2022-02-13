package com.example.covidblast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DifficultySelectionFragment extends Fragment implements View.OnClickListener {
    Button easyBtn, mediumBtn, hardBtn, extremeBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficulty_selection, container, false);

        easyBtn = view.findViewById(R.id.btn_easy);
        mediumBtn = view.findViewById(R.id.btn_medium);
        hardBtn = view.findViewById(R.id.btn_hard);
        extremeBtn = view.findViewById(R.id.btn_extreme);

        easyBtn.setOnClickListener(this);
        mediumBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        extremeBtn.setOnClickListener(this);

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

        MainActivity.GAME_STARTED = true;
        MainActivity.GAME_OVER = false;

        Fragment mainCoverFragment = getParentFragmentManager().findFragmentByTag("main_cover_fragment");
        getParentFragmentManager().beginTransaction().hide(mainCoverFragment).commit();
        new GameRunning();
    }
}