package com.example.covidblast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    final String MAIN_COVER_FRAGMENT_TAG = "main_cover_fragment";

    MainCoverFragment mainCoverFragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    int coinsCount;
    TextView coinsTV;
    SharedPreferences sp;
    MusicPlayer player = MusicPlayer.getInstance();
    boolean doubleBackToExitPressedOnce = false;
    private Toast backToast;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("progress", Context.MODE_PRIVATE);

        RelativeLayout rootLayout = findViewById(R.id.root_container);
        int backgroundDrawable = sp.getInt("background", R.drawable.river_boat);
        rootLayout.setBackgroundResource(backgroundDrawable);

        player.initialize(this);
        player.setMusicOnOff(true);

        coinsTV = findViewById(R.id.coins_count_tv);

        handleCoinsTV(coinsTV);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        mainCoverFragment = new MainCoverFragment();
        if(!mainCoverFragment.isAdded())
            transaction.add(R.id.root_container, mainCoverFragment, MAIN_COVER_FRAGMENT_TAG);
        transaction.commit();
    }

    @SuppressLint("SetTextI18n")
    public void handleCoinsTV(TextView coinsTV){
        int def = 0;
        coinsCount = sp.getInt("coins", def);

        if(coinsCount >= 1000000)
            coinsTV.setText(new DecimalFormat("###.#").format(coinsCount/1000000.0) + "M ");
        else if(coinsCount >= 100000)
            coinsTV.setText(new DecimalFormat("###.#").format(coinsCount/1000.0) + "K ");
        else
            coinsTV.setText(coinsCount + "");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(player.getIsMusicOn()) {
            player.setMusicOnOff(false);
            player.setLastMusicMode(true); //if music was on when user quit the app
        }

        String coins = coinsTV.getText().toString();
        if(coins.contains("M")) {
            coins = coins.substring(0, coins.indexOf("M"));
            try {
                coinsCount = Integer.parseInt(coins) * 1000000;
            }catch (Exception e) {
                coinsCount = (int)(Double.parseDouble(coins) * 1000000);
            }
        }
        else if(coins.contains("K")) {
            coins = coins.substring(0, coins.indexOf("K"));
            try {
                coinsCount = Integer.parseInt(coins) * 1000;
            }catch (Exception e) {
                coinsCount = (int)(Double.parseDouble(coins) * 1000);
            }
        }
        sp.edit().putInt("coins", coinsCount).commit();

        save_background();

    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setMusicOnOff(player.getLastMusicMode());
    }

    private void save_background() {
        ArrayList<Integer> drawables = new ArrayList<>(
                Arrays.asList(R.drawable.river_boat, R.drawable.dark_sky, R.drawable.night_snow,
                        R.drawable.red_sunset, R.drawable.retro, R.drawable.river,
                        R.drawable.landscape, R.drawable.space, R.drawable.island,
                        R.drawable.cartoon_sky, R.drawable.deep_sea, R.drawable.desert,
                        R.drawable.pyramid, R.drawable.hospital)
        );

        // backgrounds comparison
        // Source - https://gist.github.com/rodrigoborgesdeoliveira/e3e3045ebba54a7579277f58d0ff3705
        for (Integer drawable: drawables) {
            if (Objects.equals(
                    findViewById(R.id.root_container).getBackground().getConstantState(),
                    getResources().getDrawable(drawable).getConstantState())) {

                sp.edit().putInt("background", drawable).commit();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
        backToast.show();

        new Handler(Looper.getMainLooper())
                .postDelayed(() -> doubleBackToExitPressedOnce=false, 1500);
    }


}