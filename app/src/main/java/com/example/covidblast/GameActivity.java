//package com.example.covidblast;
//
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.View;
//import android.view.WindowManager;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.Objects;
//
//public class GameActivity extends AppCompatActivity {
//
//    private GameView gameView;
//    boolean pressPause = false;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar())).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        Point point = new Point();
//        getWindowManager().getDefaultDisplay().getSize(point);
//
//        // Get difficulty level for current game.
//        Difficulties currentDiff = Difficulty.getInstance().getCurrentDifficulty();
//        gameView = new GameView(this, point.x, point.y, currentDiff);
//
//        setContentView(gameView);
//    }
//
//}
