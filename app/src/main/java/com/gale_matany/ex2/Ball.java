package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Ball {

    private static final int BALL_SPEED_MAX = 20;
    private static final int BALL_SPEED_MIN = 10;

    private float x, y, dx, dy;
    private final float radius;
    private final Paint ballPaint;

    public Ball(float x, float y, float radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        generateRandomBallSpeed();

        this.ballPaint = new Paint();
        this.ballPaint.setColor(Color.WHITE);
        this.ballPaint.setStrokeWidth(10);
        this.ballPaint.setStyle(Paint.Style.FILL);
    }

    public void generateRandomBallSpeed(){
        int direction = new Random().nextBoolean() ? 1 : -1;
        this.dx = direction*((int) (Math.random() * (BALL_SPEED_MAX+1 - BALL_SPEED_MIN)) + BALL_SPEED_MIN);
        this.dy = -((int) (Math.random() * (BALL_SPEED_MAX+1 - BALL_SPEED_MIN)) + BALL_SPEED_MIN);
    }


    public void move(float w, float h)
    {
        this.x = this.x + this.dx;
        this.y = this.y + this.dy;
        // check border left or right
        if(x-radius<0 || x+radius>w)
            this.dx = -this.dx;
        // check border bottom or top
        if(this.y+this.radius>h || this.y-this.radius<0)
            this.dy = -this.dy;
    }

    public void resetBall(float x, float y) {
        generateRandomBallSpeed();
        this.x = x;
        this.y = y;
    }


    public boolean isStrike(float height) {
        return height <= this.y + this.radius;
    }

    public float getX()
    {
        return this.x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(this.x, this.y, this.radius, this.ballPaint);
    }


}
