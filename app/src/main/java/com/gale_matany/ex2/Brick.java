package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {

    private final float xStart, yStart, xEnd, yEnd;
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

    public void setIsDead(boolean isDead){
        this.isDead = isDead;
    }

    public void draw(Canvas canvas)
    {
        if(!this.isDead)
            canvas.drawRect(this.xStart, this.yStart, this.xEnd, this.yEnd, this.brickPaint);
    }

}
