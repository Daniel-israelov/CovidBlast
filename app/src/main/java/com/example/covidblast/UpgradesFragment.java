package com.example.covidblast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradesFragment extends Fragment implements View.OnClickListener{
    Button fsBtn, fpBtn, cvBtn;
    long coins;

    SharedPreferences sp;
    String speed_up = "speed_upgrade";
    String power_up = "power_upgrade";
    String coins_up = "coins_upgrade";

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrades, container, false);

        sp = requireContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
        fsBtn = view.findViewById(R.id.fs_btn);
        fpBtn = view.findViewById(R.id.fp_btn);
        cvBtn = view.findViewById(R.id.cv_btn);

        fsBtn.setText(sp.getString(speed_up, "100") + "");
        fpBtn.setText(sp.getString(power_up, "100") + "");
        cvBtn.setText(sp.getString(coins_up, "100") + "");

        fsBtn.setOnClickListener(this);
        fpBtn.setOnClickListener(this);
        cvBtn.setOnClickListener(this);

        coins = sp.getLong("coins", 0);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fs_btn:
                validateUpgrade(fsBtn, Integer.parseInt(fsBtn.getText().toString()));
                break;
            case R.id.fp_btn:
                validateUpgrade(fpBtn, Integer.parseInt(fpBtn.getText().toString()));
                break;
            case R.id.cv_btn:
                validateUpgrade(cvBtn, Integer.parseInt(cvBtn.getText().toString()));
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateUpgrade(Button btn, int cost){
        if(coins < cost)
            Toast.makeText(getActivity(), R.string.not_enough_coins, Toast.LENGTH_SHORT).show();
        else{
            btn.setText((int)(cost * 1.20) + "");

            TextView tv = getActivity().findViewById(R.id.coins_count_tv);

            coins-=cost;
            tv.setText(coins + "");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        sp.edit().putString(speed_up, fsBtn.getText().toString()).putString(power_up, fpBtn.getText().toString())
                .putString(coins_up, cvBtn.getText().toString()).commit();
    }
}