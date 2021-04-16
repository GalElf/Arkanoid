package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {

    private float xStart, yStart, xEnd, yEnd;
    private final Paint brickPaint;
    private boolean isDead;

    public Brick(float xStart, float yStart, float xEnd, float yEnd)
    {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.isDead = false;

        this.brickPaint = new Paint();
        this.brickPaint.setColor(Color.RED);
        this.brickPaint.setStyle(Paint.Style.FILL);
    }

    // change the state of brick to dead
    public void setIsDead(boolean isDead){
        this.isDead = isDead;
    }

    // return if brick is dead or not
    public boolean getIsDead() {
        return !isDead;
    }

    // draw the brick on the canvas in the app
    public void draw(Canvas canvas)
    {
        if(!this.isDead)
            canvas.drawRect(this.xStart, this.yStart, this.xEnd, this.yEnd, this.brickPaint);
    }

    // draw for the paddle on the canvas in the app
    public void draw(Canvas canvas, Paint paint)
    {
            canvas.drawRect(this.xStart, this.yStart, this.xEnd, this.yEnd, paint);
    }

    // change the x y of all the point of the brick

    public void setXStart(float xStart) {
        this.xStart = xStart;
    }

    public void setYStart(float yStart) {
        this.yStart = yStart;
    }

    public void setXEnd(float xEnd) {
        this.xEnd = xEnd;
    }

    public void setYEnd(float yEnd) {
        this.yEnd = yEnd;
    }

    public float getXStart() {
        return this.xStart;
    }

    public float getYStart() {
        return this.yStart;
    }

    public float getXEnd() {
        return this.xEnd;
    }

    public float getYEnd() {
        return this.yEnd;
    }
}
