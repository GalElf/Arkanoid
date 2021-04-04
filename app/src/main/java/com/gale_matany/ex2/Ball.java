package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private float x, y, radius, dx, dy;
    private Paint ballPaint;

    public Ball(float x, float y, float radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = -10;
        this.dy = 10;

        ballPaint = new Paint();
        ballPaint.setColor(Color.WHITE);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public void move(int w, int h)
    {
        x = x + dx;
        y = y + dy;

        // check border left or right
        if(x-radius<0 || x+radius>w)
            dx = -dx;
        // bottom or top
        if(y+radius>h || y-radius<0)
            dy = -dy;
    }

    public float getX()
    {
        return x;
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
        canvas.drawCircle(x, y, radius, ballPaint);
    }


}