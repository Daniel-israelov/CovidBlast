package com.example.covidblast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BloodDrop {
    Bitmap bloodDrop;
    Context context;
    int xPos, yPos;

    public BloodDrop(Context context, int x, int y) {
        this.context = context;
        bloodDrop = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.blood_drop);
        this.xPos = x;
        this.yPos = y;
    }
    public Bitmap getBloodDrop(){
        return bloodDrop;
    }
    public int getShotWidth() {
        return bloodDrop.getWidth();
    }
    public int getShotHeight() {
        return bloodDrop.getHeight();
    }
}
