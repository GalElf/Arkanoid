package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Paddle {

    private float xStart, yStart, xEnd, yEnd;
    private final Paint paddlePaint;
    private final Brick paddle;

    public Paddle(float xStart, float yStart, float xEnd, float yEnd)
    {
        paddle = new Brick(xStart, yStart, xEnd, yEnd);

        this.paddlePaint = new Paint();
        this.paddlePaint.setColor(Color.WHITE);
        this.paddlePaint.setStyle(Paint.Style.FILL);
    }

    public void resetPaddle(float xStart, float yStart, float xEnd, float yEnd){
        paddle.setXStart(xStart);
        paddle.setYStart(yStart);
        paddle.setXEnd(xEnd);
        paddle.setYEnd(yEnd);
    }

    public void draw(Canvas canvas)
    {
        paddle.draw(canvas, this.paddlePaint);
    }

    public void setX(float x, float brickW, float screenWidth)
    {
        int pixelMovement = 15;
        if(x < screenWidth/2) {
            if (paddle.getXStart() - pixelMovement < 0) {
                paddle.setXStart(0);
                paddle.setXEnd(brickW);
            } else {
                paddle.setXStart(paddle.getXStart() - pixelMovement);
                paddle.setXEnd(paddle.getXEnd() - pixelMovement);
            }
        }
        else {
            if (paddle.getXEnd() + pixelMovement > screenWidth) {
                paddle.setXStart(screenWidth - brickW);
                paddle.setXEnd(screenWidth);
            } else {
                paddle.setXStart(paddle.getXStart() + pixelMovement);
                paddle.setXEnd(paddle.getXEnd() + pixelMovement);
            }
        }
    }

    public Brick getBrick() {
        return paddle;
    }

    public float getXStart() {
        return paddle.getXStart();
    }

    public float getYStart() {
        return paddle.getYStart();
    }

    public float getXEnd() {
        return paddle.getXEnd();
    }

    public float getYEnd() {
        return paddle.getYEnd();
    }
}



//        if(x < paddle.getXStart() || (x < screenWidth/2 && x < paddle.getXEnd())) {
//            if (paddle.getXStart() - pixelMovement < 0) {
//                paddle.setXStart(0);
//                paddle.setXEnd(brickW);
//                Log.i("test", "enter 1: paddle.getXStart " + paddle.getXStart() + " | paddle.getXEnd: " + paddle.getXEnd() + " | x: " + x + " | screenWidth/2: " + (screenWidth/2));
//            } else {
//                paddle.setXStart(paddle.getXStart() - pixelMovement);
//                paddle.setXEnd(paddle.getXEnd() - pixelMovement);
//                Log.i("test", "enter 2: paddle.getXStart " + paddle.getXStart() + " | paddle.getXEnd: " + paddle.getXEnd() + " | x: " + x + " | screenWidth/2: " + (screenWidth/2));
//            }
//        }
//        if (x > paddle.getXEnd() || (x > screenWidth/2 && x > paddle.getXEnd())) {
//            if (paddle.getXEnd() + pixelMovement > screenWidth) {
//                paddle.setXStart(screenWidth - brickW);
//                paddle.setXEnd(screenWidth);
//                Log.i("test", "enter 3: paddle.getXStart " + paddle.getXStart() + " | paddle.getXEnd: " + paddle.getXEnd() + " | x: " + x + " | screenWidth/2: " + (screenWidth/2));
//            } else {
//                paddle.setXStart(paddle.getXStart() + pixelMovement);
//                paddle.setXEnd(paddle.getXEnd() + pixelMovement);
//                Log.i("test", "enter 4: paddle.getXStart " + paddle.getXStart() + " | paddle.getXEnd: " + paddle.getXEnd() + " | x: " + x + " | screenWidth/2: " + (screenWidth/2));
//            }
//        }
//        }
//        int pixelMovement = 150;
//        // press right
//        if(x > screenWidth/2) {
//            if(this.xEnd+50 > screenWidth){
//                this.xEnd = screenWidth;
//                this.xStart = screenWidth-brickW;
//            }else{
//                this.xEnd += pixelMovement;
//                this.xStart += pixelMovement;
//            }
//        }
//        // press left
//        else{
//            if(this.xStart-pixelMovement < 0) {
//                this.xStart = 0;
//                this.xEnd = brickW;
//            }
//            else{
//                this.xStart -= pixelMovement;
//                this.xEnd -= pixelMovement;
//            }
//        }
