package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Paddle {

    private float xStart, yStart, xEnd, yEnd;
    private Paint paddlePaint;

    public Paddle(float xStart, float yStart, float xEnd, float yEnd, float height)
    {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        paddlePaint = new Paint();
        paddlePaint.setColor(Color.WHITE);
        paddlePaint.setStrokeWidth(height/2);
        paddlePaint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawLine(xStart, yStart, xEnd, yEnd, paddlePaint);
    }

    public void setX(float x, float brickW, float screenWidth)
    {
        // press right
        if(x > screenWidth/2) {
            if(this.xEnd+50 > screenWidth){
                this.xEnd = screenWidth;
                this.xStart = screenWidth-brickW;
            }else{

            }            this.xStart += 50;
            this.xEnd += 50;
        }
        // press left
        else{
            if(this.xStart-50 < 0) {
                this.xStart = 0;
                this.xEnd = brickW;
            }
            else{
                this.xStart -= 50;
                this.xEnd -= 50;
            }
        }
    }

}
