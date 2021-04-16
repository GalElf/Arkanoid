package com.gale_matany.ex2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Lives {

    private static final int NUM_LIVES = 3;
    private final Bitmap[] lives;
    private final float xStart, yStart;
    private int currLife;
    private final Paint livesPaint;

    public Lives(Bitmap life, float xStart, float yStart)
    {
        livesPaint = new Paint();

        this.currLife = 2;
        this.xStart = xStart;
        this.yStart = yStart;
        lives = new Bitmap[NUM_LIVES];
        initLives(life);
    }

    // init all the live to state of true (is living)
    public void initLives(Bitmap life) {
        for (int i=0; i<NUM_LIVES; i++) {
            lives[i] = life;
        }
    }

    // change the next life to state of false (is dead)
    public void setDead(Bitmap death) {
        this.lives[this.currLife] = death;
        this.currLife--;
    }

    // reset all life to live
    public void resetLive(Bitmap life) {
        for (int i=0; i<NUM_LIVES; i++) {
            lives[i] = life;
        }
        this.currLife = 2;
    }

    // return how many life have left
    public int getCurrLife(){
        return this.currLife;
    }

    // draw 3 live on the canvas in the app
    public void drawLife(Canvas canvas){
        canvas.drawBitmap(lives[0], this.xStart, this.yStart, livesPaint);
        canvas.drawBitmap(lives[1], this.xStart-100, this.yStart, livesPaint);
        canvas.drawBitmap(lives[2], this.xStart-200, this.yStart, livesPaint);
    }
}
