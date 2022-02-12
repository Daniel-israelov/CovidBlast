package com.example.covidblast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverDialog extends Fragment implements View.OnClickListener{
    EditText userNameET;
    TextView finalScoreTv;
    Button finishBTN;
    String name, difficulty;
    public static int score;

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
        View view = inflater.inflate(R.layout.fragment_game_over_dialog, container, false);

        userNameET = view.findViewById(R.id.user_name_et);
        finishBTN = view.findViewById(R.id.finish_btn);

        finishBTN.setOnClickListener(this);

        finalScoreTv = view.findViewById(R.id.final_score_tv);
        long endTime = MainActivity.calc_score_in_seconds(GameRunning.start, System.currentTimeMillis());
        switch (Difficulty.getInstance().getCurrentDifficulty()) {
            case MEDIUM:
                score = (int)(endTime * 2);
                break;
            case HARD:
                score = (int)(endTime * 3);
                break;
            case EXTREME:
                score = (int)(endTime * 4);
                break;
        }
        finalScoreTv.setText("Your " + Difficulty.getInstance().getCurrentDifficulty() +
                " score is " + score);

        return view;
    }

    @Override
    public void onClick(View view) {
        name = userNameET.getText().toString();


        if(name.equals("")){
            Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
        }
        else{
            name = userNameET.getText().toString();
            Score finalScore = new Score(name, difficulty, score);
            MainActivity.REGISTERED = true;
            getParentFragmentManager().beginTransaction().hide(this).commit();

            // TODO: add finalScore to ScoreBoard.
        }

    }
}