package com.gale_matany.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.PictureDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private static final int GET_READY = 1;
    private static final int PLAYING = 2;
    private static final int GAME_OVER = 3;

    private int gameState;
    private Paint textStartGamePaint, textPaint, circleLifePaint, textLivesPaint, circleDeathPaint, numPointPaint, linePaint;
    private float xTextScore, yTextScore, xLifeDeath, yLifeDeath, radiusLifeDeath;
    private int score = 0;

    Thread tickThread;

    private BrickCollection bricks;
    private Paddle paddle;
    private Ball ball;



    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        textStartGamePaint = new Paint();
        textStartGamePaint.setColor(Color.WHITE);
        textStartGamePaint.setTextSize(100);
        textStartGamePaint.setTextAlign(Paint.Align.CENTER);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        textLivesPaint = new Paint();
        textLivesPaint.setColor(Color.WHITE);
        textLivesPaint.setTextSize(50);
        textLivesPaint.setTextAlign(Paint.Align.RIGHT);

        circleLifePaint = new Paint();
        circleLifePaint.setColor(Color.WHITE);
        circleLifePaint.setStyle(Paint.Style.FILL);
//        circlePaint.setTextAlign(Paint.Align.RIGHT);

        circleDeathPaint = new Paint();
        circleDeathPaint.setColor(Color.rgb(68,68,68));
        circleDeathPaint.setStrokeWidth(5);
        circleDeathPaint.setStyle(Paint.Style.FILL);
//        circleDeathPaint.setTextAlign(Paint.Align.RIGHT);


        numPointPaint = new Paint();
        numPointPaint.setColor(Color.RED);
        numPointPaint.setStrokeWidth(5);
        numPointPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.FILL);

        gameState = GET_READY;

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);




        canvas.drawText("Score: " + score, xTextScore, yTextScore, textPaint);

        canvas.drawCircle(xLifeDeath, yLifeDeath, radiusLifeDeath, circleLifePaint);
        canvas.drawCircle(xLifeDeath-70, yLifeDeath, radiusLifeDeath, circleLifePaint);
        canvas.drawCircle(xLifeDeath-140, yLifeDeath, radiusLifeDeath, circleLifePaint);
        canvas.drawCircle(xLifeDeath-140, yLifeDeath, radiusLifeDeath-5, circleDeathPaint);
        canvas.drawText("Lives: ", xLifeDeath-175, yTextScore, textLivesPaint);

        bricks.draw(canvas);
        paddle.draw(canvas);
        ball.draw(canvas);

        if(gameState == GET_READY)
        {
            canvas.drawText("Click to PLAY!", (float) getWidth() / 2, (float) getHeight()/2, textStartGamePaint);
        }
        if(gameState == PLAYING){
            ball.move(getWidth(), getHeight());
            invalidate();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        bricks = new BrickCollection(getWidth(), getHeight());
        float brickW = bricks.getBrickW();
        float brickH = bricks.getBrickH();
        paddle = new Paddle((float) (getWidth()/2 - brickW/2), getHeight()-150, (float) (getWidth()/2 + brickW/2), getHeight()-150, brickH);

        ball = new Ball((float) getWidth()/2, (float) getHeight()-150-bricks.getBrickH(), bricks.getBrickH()/2);
        xTextScore = 20;
        yTextScore = 65;

        xLifeDeath = w-50;
        yLifeDeath = 50;
        radiusLifeDeath = 25;

        startClock();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(gameState == GET_READY)
                {
                    gameState = PLAYING;
                    invalidate();
                }
                if(gameState == PLAYING)
                {
                    paddle.setX(tx, bricks.getBrickW(), getWidth());
                    invalidate();
                }
//                else
//                {
//                    // check if red ball catch for dragging
//                    if(!isDraging && redBall.isInside(tx,ty))
//                        isDraging = true;
//                }
                break;
            case MotionEvent.ACTION_MOVE:
//                if(isDraging)
//                {
//                    redBall.setX(tx);
//                    redBall.setY(ty);
//                }
                break;

            case MotionEvent.ACTION_UP:
//                ///Log.d("mylog", "MotionEvent.ACTION_UP ");
//                isDraging = false;

                break;
        }
        return true;
    }

    public void startClock()
    {
//        tickThread = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                while(true)
//                {
//                    // update Hands
//                    score++;
//                    if(score == 10){
//
//                    }
//                    SystemClock.sleep(1000);
//
//                    // call to onDraw() from Thread
//                    postInvalidate();
//
//                    // sleep 1 second
//                }
//            }
//        });
//        tickThread.start();
    }






}
