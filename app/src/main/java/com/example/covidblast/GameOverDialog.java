package com.example.covidblast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GameOverDialog extends Fragment implements View.OnClickListener{
    EditText userNameET;
    Button finishBTN;
    String name, difficulty;
    int score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        userNameET = view.findViewById(R.id.user_name_et);
        finishBTN = view.findViewById(R.id.finish_btn);

        finishBTN.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        name = userNameET.getText().toString();


        if(name.equals("")){
            Toast.makeText(getActivity(), "text", Toast.LENGTH_SHORT).show(); // ??
        }
        else{
            name = userNameET.getText().toString();

            Score finalScore = new Score(name, difficulty, score);
        }


    }
}