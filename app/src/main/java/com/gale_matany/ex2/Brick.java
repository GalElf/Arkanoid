package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {

    private float xStart, yStart, xEnd, yEnd;
    private Paint brickPaint;

    public Brick(float xStart, float yStart, float xEnd, float yEnd)
    {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        brickPaint = new Paint();
        brickPaint.setColor(Color.RED);
        brickPaint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawRect(xStart, yStart, xEnd, yEnd, brickPaint);
    }

}
