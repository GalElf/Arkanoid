package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class Ball {

    private static final int BALL_SPEED_MAX = 13;
    private static final int BALL_SPEED_MIN = 8;

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

    // generate dx dy for the speed of the ball every start of GET READY STATE
    public void generateRandomBallSpeed(){
        int direction = new Random().nextBoolean() ? 1 : -1;
        this.dx = direction*((float)((Math.random() * (BALL_SPEED_MAX+1 - BALL_SPEED_MIN)) + BALL_SPEED_MIN));
        this.dy = -((float)((Math.random() * (BALL_SPEED_MAX+1 - BALL_SPEED_MIN)) + BALL_SPEED_MIN));
    }

    // move the ball against the wall, brick and paddle
    public void move(float width, float height)
    {
        this.x = this.x + this.dx;
        this.y = this.y + this.dy;
        // check border left or right
        if(x-radius<0 || x+radius>width)
            this.dx = -this.dx;
        // check border bottom or top
        if(this.y+this.radius>height || this.y-this.radius<0)
            this.dy = -this.dy;
    }

    // check for collide between bricks
    public boolean checkBallCollideBricks(Brick brick){
        if(brick.getIsDead()) {
            float top = brick.getYStart();
            float bottom = brick.getYEnd();
            float left = brick.getXStart();
            float right = brick.getXEnd();

            // left or right lower or upper corner
            if (((top <= this.y + this.dy && this.y + this.dy <= top+2) || (bottom-2 <= this.y + this.dy && this.y + this.dy <= bottom)) &&
                    ((left <= this.x + this.dx && this.x + this.dx <= left+2) || (right-2 <= this.x + this.dx && this.x + this.dx <= right))) {
                this.dx = -this.dx;
                this.dy = -this.dy;
                this.y = this.y + this.dy;
                brick.setIsDead(true);
                return true;
            }

            // left or right walls
            else if ((top < this.y && this.y <= bottom) &&
                    ((left - this.dx <= this.x + this.dx && this.x + this.dx <= left) || (right <= this.x + this.dx && this.x + this.dx <= right - this.dx))) {
                this.dx = -this.dx;
                brick.setIsDead(true);
                return true;
            }

            // top or bottom side
            else if ((left <= this.x && this.x <= right) &&
                    ((top <= this.y + this.dy && this.y + this.dy <= bottom) || (bottom <= this.y + this.dy && this.y + this.dy <= top))) {
                this.dy = -this.dy;
                this.y = this.y + this.dy;
                brick.setIsDead(true);
                return true;
            }
        }
        return false;
    }

    // check for collide between the paddle
    public void checkBallCollideBrick(Paddle paddle){
        float top = paddle.getYStart();
        float bottom = paddle.getYEnd();
        float left = paddle.getXStart();
        float right = paddle.getXEnd();
        // left or right edges
        if ((top <= this.y + this.dy && this.y + this.dy <= top + 3) &&
                ((left <= this.x + this.dx && left + 2 >= this.x + this.dx) || (right - 2 <= this.x + this.dx && this.x + this.dx <= right))) {
            this.dx = -this.dx;
            this.dy = -this.dy;
            this.y = this.y + this.dy;
        }
        // left or right walls
        else if ((top < this.y && this.y <= bottom) &&
                ((left - this.dx <= this.x + this.dx && this.x + this.dx <= left) || (right <= this.x + this.dx && this.x + this.dx <= right - this.dx))) {
            this.dx = -this.dx;
        }
        // top side
        else if ((left <= this.x && this.x <= right) && (top <= this.y + this.dy && this.y + this.dy <= bottom)) {
            this.dy = -this.dy;
            this.y = this.y + this.dy;
        }
    }

    // reset the ball location to current x and y that receive
    public void resetBall(float x, float y) {
        generateRandomBallSpeed();
        this.x = x;
        this.y = y;
    }

    // check if the ball miss the paddle location
    public boolean isStrike(float height) {
        return height <= this.y + this.radius;
    }

    // draw the ball on the canvas in the app
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(this.x, this.y, this.radius, this.ballPaint);
    }
}
