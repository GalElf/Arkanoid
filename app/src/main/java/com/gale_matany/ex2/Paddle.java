package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Paddle {

    private float xStart, yStart, xEnd, yEnd;
    private final Paint paddlePaint;

    public Paddle(float xStart, float yStart, float xEnd, float yEnd, float height)
    {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        this.paddlePaint = new Paint();
        this.paddlePaint.setColor(Color.WHITE);
        this.paddlePaint.setStrokeWidth(height/2);
        this.paddlePaint.setStyle(Paint.Style.FILL);
    }

    public void resetPaddle(float xStart, float yStart, float xEnd, float yEnd){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawLine(this.xStart, this.yStart, this.xEnd, this.yEnd, this.paddlePaint);
    }

    public void setX(float x, float brickW, float screenWidth)
    {
        int pixelMovement = 50;
        // press right
        if(x > screenWidth/2) {
            if(this.xEnd+50 > screenWidth){
                this.xEnd = screenWidth;
                this.xStart = screenWidth-brickW;
            }else{
                this.xEnd += pixelMovement;
                this.xStart += pixelMovement;
            }
        }
        // press left
        else{
            if(this.xStart-pixelMovement < 0) {
                this.xStart = 0;
                this.xEnd = brickW;
            }
            else{
                this.xStart -= pixelMovement;
                this.xEnd -= pixelMovement;
            }
        }
    }

}
