package com.example.covidblast;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Syringe {
    private int height, width, x;
    final private int y = 300;
    private int attack, attack_speed;
    Bitmap syringe;

    Syringe(int imgID, Resources resources){
        syringe = BitmapFactory.decodeResource(resources, imgID);

        width = syringe.getWidth();
        height = syringe.getHeight();

        width /=7;
        height/=7;

        syringe = Bitmap.createScaledBitmap(syringe, width, height, false);
    }

    public Bitmap getSyringe() {
        return syringe;
    }
}
