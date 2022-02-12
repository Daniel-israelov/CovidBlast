package com.example.covidblast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
//public class MainActivity extends AppCompatActivity {

    public static int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static MusicPlayer player = MusicPlayer.getInstance();
    @SuppressLint("StaticFieldLeak")
    public static ImageView syringeIV;
    @SuppressLint("StaticFieldLeak")
    public static ImageView v1IV, v2IV, v3IV, v4IV;
    public static Syringe syringe;
    public static Virus v1, v2, v3, v4;
    private final Random random = new Random();

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

        handleCoinsTV(coinsTV);

        syringe = new Syringe(getResources());
        syringe.setWidth(convertDpToPx(this, 20));
        syringe.setHeight(convertDpToPx(this, 150));
        syringe.setY(SCREEN_HEIGHT - syringe.getHeight()); // minus syringeIV.height
        syringeIV = findViewById(R.id.syringe_iv);
        syringeIV.setImageBitmap(syringe.getSyringe());

        handle_viruses();

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
        if (DifficultySelectionFragment.GAME_STARTED) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                case MotionEvent.ACTION_MOVE :
                    if (event.getRawX() <= 75) {
                        syringeIV.animate().x(50).setDuration(0).start();
                    }
                    else if (event.getRawX() >= SCREEN_WIDTH - 60) {
                        syringeIV.animate().x(SCREEN_WIDTH - 85).setDuration(0).start();
                    }
                    else { // between
                        syringeIV.animate().x(event.getRawX() - 25).setDuration(0).start();
                    }
                    syringe.setX(syringeIV.getX());
            }
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

        if (!DifficultySelectionFragment.GAME_STARTED &&
                Objects.requireNonNull(fragmentManager.findFragmentByTag("difficulty_selection_fragment")).isVisible()) {
            transaction = fragmentManager.beginTransaction();
            transaction.hide(Objects.requireNonNull(fragmentManager.findFragmentByTag("difficulty_selection_fragment")));
            mainCoverFragment.playBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.scoresBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.instructionsBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.settingsBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.upgradeBtn.setVisibility(View.VISIBLE);
            mainCoverFragment.backgroundBtn.setVisibility(View.VISIBLE);
            transaction.commit();
        } else {
            if (doubleBackToExitPressedOnce) {
                backToast.cancel();
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            backToast = Toast.makeText(this, R.string.back_toast, Toast.LENGTH_SHORT);
            backToast.show();

            new Handler(Looper.getMainLooper())
                    .postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }

    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public void handle_viruses() {
        v1 = new Virus(getResources(), 0);
        v1.setWidth(convertDpToPx(this, 50));
        v1.setHeight(convertDpToPx(this, 50));
        v1IV = findViewById(R.id.corona_virus_iv);
        v1IV.setImageBitmap(v1.getVirus());
        set_random_starting_xy(v1, v1IV);

        v2 = new Virus(getResources(), 1);
        v2.setWidth(convertDpToPx(this, 50));
        v2.setHeight(convertDpToPx(this, 50));
        v2IV = findViewById(R.id.red_virus_iv);
        v2IV.setImageBitmap(v2.getVirus());
        set_random_starting_xy(v2, v2IV);

        v3 = new Virus(getResources(), 2);
        v3.setWidth(convertDpToPx(this, 30));
        v3.setHeight(convertDpToPx(this, 30));
        v3IV = findViewById(R.id.green_virus_iv);
        v3IV.setImageBitmap(v3.getVirus());
        set_random_starting_xy(v3, v3IV);

        v4 = new Virus(getResources(), 3);
        v4.setWidth(convertDpToPx(this, 30));
        v4.setHeight(convertDpToPx(this, 30));
        v4IV = findViewById(R.id.blue_virus_iv);
        v4IV.setImageBitmap(v4.getVirus());
        set_random_starting_xy(v4, v4IV);
    }

    public void set_random_starting_xy(Virus virus, ImageView virusIV) {
        int rand = random.nextInt(200) + 450;

        if (rand % 2 == 0) {
            virus.setX(-virus.getWidth());
            virusIV.setX(-virus.getWidth());
        }
        else {
            virus.setX(SCREEN_WIDTH);
            virusIV.setX(SCREEN_WIDTH);
        }

        virus.setY(rand);
        virusIV.setY(rand);
    }

    public static void set_all_IV_visibilities(boolean bool) {
        if (bool) {
            syringeIV.setVisibility(View.VISIBLE);
            v1IV.setVisibility(View.VISIBLE);
            v2IV.setVisibility(View.VISIBLE);
            v3IV.setVisibility(View.VISIBLE);
            v4IV.setVisibility(View.VISIBLE);
        }
        else {
            syringeIV.setVisibility(View.INVISIBLE);
            v1IV.setVisibility(View.INVISIBLE);
            v2IV.setVisibility(View.INVISIBLE);
            v3IV.setVisibility(View.INVISIBLE);
            v4IV.setVisibility(View.INVISIBLE);
        }
    }

}