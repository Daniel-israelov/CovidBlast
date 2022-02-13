package com.example.covidblast;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Syringe{
    private float x = (float)(MainActivity.SCREEN_WIDTH/2), y;
    private float width, height;
    private Bitmap syringe;

    Syringe(Resources resources){
        syringe = BitmapFactory.decodeResource(resources, R.drawable.syringe1);
        width = syringe.getWidth();
        height = syringe.getHeight();
        syringe = Bitmap.createScaledBitmap(syringe, (int)width, (int)height, false);
    }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Bitmap getSyringe() { return syringe; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }

    //return the frame/shape of the object for collision.
    public Rect getCollisionShape () {
        return new Rect((int)x, (int)y, (int)(x + width), (int)(y + height));
    }
}
