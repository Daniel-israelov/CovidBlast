package com.example.covidblast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public static int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static MusicPlayer player = MusicPlayer.getInstance();
    @SuppressLint("StaticFieldLeak")
    public static ImageView syringeIV;
    Syringe syringe;

    final String MAIN_COVER_FRAGMENT_TAG = "main_cover_fragment";
    Toast backToast;

    MainCoverFragment mainCoverFragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    TextView coinsTV;
    SharedPreferences sp;

    int coinsCount;
    boolean doubleBackToExitPressedOnce = false;


    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
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
        rootLayout.setOnTouchListener(this);

        player.initialize(this);
        player.setMusicOnOff(true);

        coinsTV = findViewById(R.id.coins_count_tv);
        syringe = new Syringe(getResources());
        syringeIV = findViewById(R.id.syringe_iv);
        syringeIV.setImageBitmap(syringe.getSyringe());

        handleCoinsTV(coinsTV);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        mainCoverFragment = new MainCoverFragment();
        if(!mainCoverFragment.isAdded())
            transaction.add(R.id.root_container, mainCoverFragment, MAIN_COVER_FRAGMENT_TAG);
        transaction.commit();
    }


    // Source - https://stackoverflow.com/questions/59021113/how-to-move-a-view-alongside-with-the-finger-movements-in-android
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (DifficultySelectionFragment.GAME_STARTED)
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                case MotionEvent.ACTION_MOVE :
                    if (event.getRawX() <= 75)  //left animation
                        syringeIV.animate().x(50).setDuration(0).start();
                    else if (event.getRawX() >= SCREEN_WIDTH - 60) //right animation
                        syringeIV.animate().x(SCREEN_WIDTH - 85).setDuration(0).start();
                    else  // between
                        syringeIV.animate().x(event.getRawX() - 25).setDuration(0).start();
            }
        return true;
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
        if (fragmentManager.findFragmentByTag("difficulty_selection_fragment").isVisible()) {
            transaction = fragmentManager.beginTransaction();
            transaction.hide(fragmentManager.findFragmentByTag("difficulty_selection_fragment"));
            mainCoverFragment.playBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.scoresBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.instructionsBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.settingsBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.upgradeBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.backgroundBtn.setVisibility(View.VISIBLE);
            transaction.commit();
        }
        else {
            if (doubleBackToExitPressedOnce) {
                backToast.cancel();
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            backToast = Toast.makeText(this, R.string.back_toast, Toast.LENGTH_SHORT);
            backToast.show();

            new Handler(Looper.getMainLooper())
                    .postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);
        }
    }

}