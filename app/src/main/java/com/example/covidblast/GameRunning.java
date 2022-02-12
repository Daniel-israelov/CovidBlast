package com.example.covidblast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class GameRunning extends Fragment implements Runnable {

    public static long start, end, curr, score = 0;

    Virus v1 = MainActivity.v1, v2 = MainActivity.v2,
            v3 = MainActivity.v3, v4 = MainActivity.v4;
    ImageView v1IV = MainActivity.v1IV, v2IV = MainActivity.v2IV,
            v3IV = MainActivity.v3IV, v4IV = MainActivity.v4IV;

    Context context = MainActivity.syringeIV.getContext();
    final MediaPlayer hitMP = MediaPlayer.create(context, R.raw.sound_disqualification);
    final MediaPlayer bounce1MP = MediaPlayer.create(context, R.raw.sound_bounce);
    final MediaPlayer bounce2MP = MediaPlayer.create(context, R.raw.sound_bounce);
    final MediaPlayer bounce3MP = MediaPlayer.create(context, R.raw.sound_bounce);
    final MediaPlayer bounce4MP = MediaPlayer.create(context, R.raw.sound_bounce);


    public GameRunning(Fragment mainCoverFragment, FragmentManager fragmentManager) {

        MainActivity.set_all_IV_visibilities(true);

        start = System.currentTimeMillis();

        Thread t1 = new Thread(this, "3");
        Thread t2 = new Thread(this, "4");
        Thread t3 = new Thread(this, "2");
        Thread t4 = new Thread(this, "1");

        if (Difficulty.getInstance().getCurrentDifficulty() == Difficulties.EASY) {
            t1.start();
        }
        else if (Difficulty.getInstance().getCurrentDifficulty() == Difficulties.MEDIUM) {
            t1.start();
            new Handler(Looper.getMainLooper()).postDelayed(t2::start, 2000);
        }
        else if (Difficulty.getInstance().getCurrentDifficulty() == Difficulties.HARD) {
            t1.start();
            new Handler(Looper.getMainLooper()).postDelayed(t2::start, 2000);
            new Handler(Looper.getMainLooper()).postDelayed(t3::start, 4000);
        }
        else if (Difficulty.getInstance().getCurrentDifficulty() == Difficulties.EXTREME) {
            t1.start();
            new Handler(Looper.getMainLooper()).postDelayed(t2::start, 2000);
            new Handler(Looper.getMainLooper()).postDelayed(t3::start, 4000);
            new Handler(Looper.getMainLooper()).postDelayed(t4::start, 6000);
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void run() {
        Virus v;
        ImageView iv;
        MediaPlayer bounceMP;

        switch (Thread.currentThread().getName()) {
            case "1":
                v = v1;
                iv = v1IV;
                bounceMP = bounce1MP;
                break;
            case "2":
                v = v2;
                iv = v2IV;
                bounceMP = bounce2MP;
                break;
            case "3":
                v = v3;
                iv = v3IV;
                bounceMP = bounce3MP;
                break;
            case "4":
                v = v4;
                iv = v4IV;
                bounceMP = bounce4MP;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Thread.currentThread().getName());
        }

        boolean falling = true, directionRight = true;
        float x, y;

        while (DifficultySelectionFragment.GAME_STARTED) {

            assert iv != null;
            x = iv.getX();
            y = iv.getY();

            // Change directions
            if (x < 0)
                directionRight = true;
            else if (x + iv.getWidth() >= MainActivity.SCREEN_WIDTH)
                directionRight = false;

            // Change gravity
            if (y < v.getMaxJump())
                falling = true;
            else if (y + iv.getHeight() >= MainActivity.SCREEN_HEIGHT - 150) {
                bounceMP.start();
                falling = false;
            }


            set_xy(v, iv, x, y, falling, directionRight);


            if (isHit(v)) {
                hitMP.start();
                end = System.currentTimeMillis();
                MainActivity.GAME_OVER = true;
                System.out.println("HIT!!!");
            }
            if (!DifficultySelectionFragment.GAME_STARTED) {
                break;
            }

            sleep();

        }

    }

    // Makes viruses bounce.
    public void set_xy(Virus virus, ImageView virusIV,  float x, float y,
                       boolean falling, boolean directionRight) {

        int newRotation;
        if (directionRight) { newRotation = (virus.getRotation() + 1) % 360; }
        else { newRotation = (virus.getRotation() - 1) % 360; }

        // Setting virus rotation same with it's direction.
        virus.setRotation(newRotation);
        virusIV.setRotation(newRotation);

        float tempXVelocity = (float)virus.getxVelocity();
        float tempYVelocity = (float)virus.getyVelocity();

        // Setting special velocity to slow down the peak of the jump.
        if (y <= 525) {
            float approachingToPeak = (float)(y / 1500); // 0.33 to 0.35
            if (falling) { tempYVelocity *= approachingToPeak * 0.5; } // 0.165 to 0.175
            else { tempYVelocity *= approachingToPeak * approachingToPeak; }
            tempXVelocity += 1;
        }
        else if (y <= 600) {
            float approachingToPeak = (float)(y / 1200); // 0.4375 to 0.55
            if (falling) { tempYVelocity *= approachingToPeak + 0.25;} // 0.6575 to 0.8
            else { tempYVelocity *= approachingToPeak; }
            tempXVelocity += 0.6;
        }
        else if (y <= 750) {
            float approachingToPeak = (float)(y / 750); // 0.8 to 1
            if (falling) { tempYVelocity *=  approachingToPeak; }
            else { tempYVelocity *= approachingToPeak; }
            tempXVelocity += 0.3;
        }

        if (falling && directionRight) { // down right x+ y+
            virus.setX(x + tempXVelocity);
            virus.setY(y + tempYVelocity);
            virusIV.setX(x + tempXVelocity);
            virusIV.setY(y + tempYVelocity);
        }
        else if (falling && !directionRight) { // down left x- y+
            virus.setX(x - tempXVelocity);
            virus.setY(y + tempYVelocity);
            virusIV.setX(x - tempXVelocity);
            virusIV.setY(y + tempYVelocity);
        }
        else if (!falling && directionRight) { // up right x+ y-
            virus.setX(x + tempXVelocity);
            virus.setY(y - tempYVelocity);
            virusIV.setX(x + tempXVelocity);
            virusIV.setY(y - tempYVelocity);
        }
        else if (!falling && !directionRight) { // up left x- y-
            virus.setX(x - tempXVelocity);
            virus.setY(y - tempYVelocity);
            virusIV.setX(x - tempXVelocity);
            virusIV.setY(y - tempYVelocity);
        }
    }

    // Checks if any ball has hit the syringe.
    public boolean isHit(Virus virus) {
        return Rect.intersects(MainActivity.syringe.getCollisionShape(), virus.getCollisionShape());
    }


    // Set timer to sleep after each loop to make the game around 60fps.
    private void sleep() {
        try{
            Thread.sleep(33);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private long calc_score_in_seconds(long start, long end) { return (end - start) / 1000; }

}
