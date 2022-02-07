package com.example.covidblast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    final String MAIN_COVER_FRAGMENT_TAG = "main_cover_fragment";

    MainCoverFragment mcf = null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    long coinsCount;
    TextView coinsTV;
    SharedPreferences sp;
    MusicPlayer player = MusicPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        player.initialize(this);
        player.setMusicOnOff(true);

        sp = getSharedPreferences("progress", MODE_PRIVATE);

        coinsTV = findViewById(R.id.coins_count_tv);

        handleCoinsTV(coinsTV);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        mcf = new MainCoverFragment();
        if(!mcf.isAdded())
            transaction.add(R.id.root_container, mcf, MAIN_COVER_FRAGMENT_TAG);
        transaction.commit();
    }

    @SuppressLint("SetTextI18n")
    public void handleCoinsTV(TextView coinsTV){
        coinsCount = sp.getLong("coins", 0);

        if(coinsCount >= 100000)
            coinsTV.setText((coinsCount/1000) + "K ");
        else
            coinsTV.setText(coinsCount + " ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(player.getIsMusicOn()) {
            player.setMusicOnOff(false);
            player.setLastMusicMode(true); //if music was on when user quit the app
        }

        String coins = coinsTV.getText().toString();
        if(coins.contains("K"))
            coins.substring(0, coins.indexOf("K"));

        coinsCount = Integer.parseInt(coins);

        sp.edit().putLong("coins", coinsCount).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setMusicOnOff(player.getLastMusicMode());
    }
}