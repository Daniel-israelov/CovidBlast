package com.example.covidblast;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Virus {
    public int hp, rotation = 0;
    private float x, y;
    private float width, height;
    private int xVelocity = 3, yVelocity = 15, maxJump;
    private Bitmap virus;

    Virus(Resources resources, int virusType) {

        switch(virusType) {
            case 0: // corona
                hp = 10;
                maxJump = 500;
                xVelocity = 3;
                yVelocity = 15;
                virus = BitmapFactory.decodeResource(resources, R.drawable.corona_virus);
                break;
            case 1: // red
                hp = 10;
                maxJump = 500;
                xVelocity = 3;
                yVelocity = 15;
                virus = BitmapFactory.decodeResource(resources, R.drawable.red_virus);
                break;
            case 2: // green
                hp = 3;
                maxJump = 750;
                xVelocity = 4;
                yVelocity = 20;
                virus = BitmapFactory.decodeResource(resources, R.drawable.green_virus);
                break;
            case 3: // blue
                hp = 3;
                maxJump = 750;
                xVelocity = 4;
                yVelocity = 20;
                virus = BitmapFactory.decodeResource(resources, R.drawable.blue_virus);
                break;
        }
        width = virus.getWidth();
        height = virus.getHeight();
        virus = Bitmap.createScaledBitmap(virus, (int)width, (int)height, false);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public int getxVelocity() { return xVelocity; }
    public int getyVelocity() { return yVelocity; }
    public int getMaxJump() { return maxJump; }
    public int getRotation() { return rotation; }

    public Bitmap getVirus() { return virus; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
    public void setRotation(int rotation) { this.rotation = rotation; }

    //return the frame/shape of the object for collision.
    public Rect getCollisionShape () {
        return new Rect((int)x, (int)y, (int)(x + width), (int)(y + height));
    }
}
