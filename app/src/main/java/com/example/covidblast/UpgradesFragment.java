package com.example.covidblast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class UpgradesFragment extends Fragment implements View.OnClickListener{
    Button attackSpeedBtn, attackPowerBtn, coinEarningsBtn;
    int coins;
    ArrayList<Integer> prices = new ArrayList<>(
            Arrays.asList(100, 300, 750, 1250, 2500, 4000, 6000, 10000, 15000, 25000, 50000, 100000)
    );

    SharedPreferences sp;
    String speed_up = "speed_upgrade";
    String power_up = "power_upgrade";
    String coins_up = "coins_upgrade";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(getActivity());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_left));
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrades, container, false);

        sp = requireContext().getSharedPreferences("progress", Context.MODE_PRIVATE);
        attackSpeedBtn = view.findViewById(R.id.fs_btn);
        attackPowerBtn = view.findViewById(R.id.fp_btn);
        coinEarningsBtn = view.findViewById(R.id.cv_btn);

        attackSpeedBtn.setText(sp.getString(speed_up, "100"));
        attackPowerBtn.setText(sp.getString(power_up, "100"));
        coinEarningsBtn.setText(sp.getString(coins_up, "100"));

        attackSpeedBtn.setOnClickListener(this);
        attackPowerBtn.setOnClickListener(this);
        coinEarningsBtn.setOnClickListener(this);

        int def = 0;
        coins = sp.getInt("coins", def);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fs_btn:
                validateUpgrade(attackSpeedBtn, Integer.parseInt(attackSpeedBtn.getText().toString()));
                break;
            case R.id.fp_btn:
                validateUpgrade(attackPowerBtn, Integer.parseInt(attackPowerBtn.getText().toString()));
                break;
            case R.id.cv_btn:
                validateUpgrade(coinEarningsBtn, Integer.parseInt(coinEarningsBtn.getText().toString()));
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void validateUpgrade(Button btn, int cost){
        System.out.println(cost);
        if(coins < cost)
            Toast.makeText(getActivity(), R.string.not_enough_coins, Toast.LENGTH_SHORT).show();
        else{
            int index = prices.indexOf(cost);

            if (index < prices.size() - 1) {
                btn.setText(prices.get(index+1) + "");
            }
            else {
                btn.setText(prices.get(index) + "");
            }

            assert getActivity() != null;
            TextView tv = getActivity().findViewById(R.id.coins_count_tv);

            handleCoinsTV(tv, cost);
        }
    }

    @SuppressLint("SetTextI18n")
    public void handleCoinsTV(TextView coinsTV, int cost){
        coins-=cost;

        if(coins >= 1000000)
            coinsTV.setText(new DecimalFormat("###.#").format(coins/1000000.0) + "M ");
        else if(coins >= 100000)
            coinsTV.setText(new DecimalFormat("###.#").format(coins/1000.0) + "K ");
        else
            coinsTV.setText(coins + "");
    }

    @Override
    public void onPause() {
        super.onPause();

        sp.edit().putString(speed_up, attackSpeedBtn.getText().toString())
                .putString(power_up, attackPowerBtn.getText().toString())
                .putString(coins_up, coinEarningsBtn.getText().toString())
                .putInt("coins", coins).commit();
    }
}
