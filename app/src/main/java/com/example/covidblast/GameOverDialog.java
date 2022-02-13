package com.example.covidblast;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_over_dialog, container, false);

        finalScoreTv = view.findViewById(R.id.final_score_tv);
        userNameET = view.findViewById(R.id.user_name_et);
        finishBTN = view.findViewById(R.id.finish_btn);

        finishBTN.setOnClickListener(this);

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
        String string_score = getResources().getString(R.string.score);
        finalScoreTv.setText(Difficulty.getInstance().getCurrentDifficulty() + " " + string_score + ": " + score);

        return view;
    }

    @Override
    public void onClick(View view) {
        name = userNameET.getText().toString();


        if(name.equals("")){
            Toast.makeText(getActivity(), R.string.insert_name_game_over, Toast.LENGTH_SHORT).show();
        }
        else{
            name = userNameET.getText().toString();
            difficulty = Difficulty.getInstance().getCurrentDifficulty() + "";

            Score finalScore = new Score(name, difficulty, score);
            Score.scores.add(finalScore);
            MainActivity.REGISTERED = true;
            getParentFragmentManager().beginTransaction().remove(this).commit();
        }

    }
}