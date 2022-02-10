package com.example.covidblast;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Syringe{
    private int attack, attack_speed;
    private Bitmap syringe;

    Syringe(Resources resources){
        syringe = BitmapFactory.decodeResource(resources, R.drawable.syringe1);

        int width = syringe.getWidth();
        int height = syringe.getHeight();

        syringe = Bitmap.createScaledBitmap(syringe, width, height, false);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(int attack_speed) {
        this.attack_speed = attack_speed;
    }

    public Bitmap getSyringe() {
        return syringe;
    }
}
