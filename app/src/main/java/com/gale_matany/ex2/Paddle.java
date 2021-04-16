package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {

    private final Paint paddlePaint;
    private final Brick paddle;

    public Paddle(float xStart, float yStart, float xEnd, float yEnd)
    {
        this.paddle = new Brick(xStart, yStart, xEnd, yEnd);
        this.paddlePaint = new Paint();
        this.paddlePaint.setColor(Color.WHITE);
        this.paddlePaint.setStyle(Paint.Style.FILL);
    }

    public void resetPaddle(float xStart, float yStart, float xEnd, float yEnd){
        this.paddle.setXStart(xStart);
        this.paddle.setYStart(yStart);
        this.paddle.setXEnd(xEnd);
        this.paddle.setYEnd(yEnd);
    }

    public void draw(Canvas canvas)
    {
        this.paddle.draw(canvas, this.paddlePaint);
    }

    public void setX(float x, float brickW, float screenWidth)
    {
        int pixelMovement = 15;
        if(x < screenWidth/2) {
            if (this.paddle.getXStart() - pixelMovement < 0) {
                this.paddle.setXStart(0);
                this.paddle.setXEnd(brickW);
            } else {
                this.paddle.setXStart(this.paddle.getXStart() - pixelMovement);
                this.paddle.setXEnd(this.paddle.getXEnd() - pixelMovement);
            }
        }
        else {
            if (this.paddle.getXEnd() + pixelMovement > screenWidth) {
                this.paddle.setXStart(screenWidth - brickW);
                this.paddle.setXEnd(screenWidth);
            } else {
                this.paddle.setXStart(this.paddle.getXStart() + pixelMovement);
                this.paddle.setXEnd(this.paddle.getXEnd() + pixelMovement);
            }
        }
    }


    public float getXStart() {
        return this.paddle.getXStart();
    }

    public float getYStart() {
        return this.paddle.getYStart();
    }

    public float getXEnd() {
        return this.paddle.getXEnd();
    }

    public float getYEnd() {
        return this.paddle.getYEnd();
    }
}
