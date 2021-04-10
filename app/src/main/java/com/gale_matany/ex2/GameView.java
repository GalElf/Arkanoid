package com.gale_matany.ex2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private final Paint textStartGamePaint, textPaint, textLivesPaint;
    private float xTextScore, yTextScore, xLifeDeath, yLifeDeath, brickW, brickH;
    private int score;
    private float xMove;

    Thread gameThread, paddleThread;
    private boolean paddleThreadNeedToMove;
    private BrickCollection bricks;
    private Paddle paddle;
    private Ball ball;
    private Lives lives;

    private Bitmap life;
    private Bitmap death;

    private float width, height;
    private Context context;



    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        this.textStartGamePaint = new Paint();
        this.textStartGamePaint.setColor(Color.WHITE);
        this.textStartGamePaint.setTextSize(100);
        this.textStartGamePaint.setTextAlign(Paint.Align.CENTER);

        this.textPaint = new Paint();
        this.textPaint.setColor(Color.WHITE);
        this.textPaint.setTextSize(70);

        this.textLivesPaint = new Paint();
        this.textLivesPaint.setColor(Color.WHITE);
        this.textLivesPaint.setTextSize(70);
        this.textLivesPaint.setTextAlign(Paint.Align.RIGHT);


        this.score = 0;
        this.gameState = GET_READY;
        this.paddleThreadNeedToMove = false;
        this.life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        this.life = Bitmap.createScaledBitmap(this.life, 85, 80, true);
        this.death = BitmapFactory.decodeResource(getResources(), R.drawable.death);
        this.death = Bitmap.createScaledBitmap(this.death, 85, 80, true);
        this.gameThread = null;
        this.paddleThread = null;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);


        this.ball.draw(canvas);
        this.bricks.draw(canvas);
        this.paddle.draw(canvas);
        this.lives.drawLife(canvas);
        canvas.drawText("Lives: ", this.xLifeDeath, this.yTextScore, this.textLivesPaint);
        canvas.drawText("Score: " + this.score, this.xTextScore, this.yTextScore, this.textPaint);


        if(gameState == GET_READY)
        {
            canvas.drawText("Click to PLAY!", (float) getWidth() / 2, (float) getHeight()/2, this.textStartGamePaint);
        }
        if(gameState == PLAYING){

        }
        if(gameState == GAME_OVER) {
            canvas.drawText("GAME OVER - You Lose!", (float) getWidth() / 2, (float) getHeight()/2, this.textStartGamePaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = getWidth();
        this.height = getHeight();

        this.xTextScore = 40;
        this.yTextScore = 75;

        this.xLifeDeath = getWidth()-320;
        this.yLifeDeath = 20;
        this.lives = new Lives(life,width-100, yLifeDeath);

        this.bricks = new BrickCollection(width, height);
        this.brickW = this.bricks.getBrickW();
        this.brickH = this.bricks.getBrickH();

        this.paddle = new Paddle(this.width/2 - brickW/2, height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);

        this.ball = new Ball(this.width/2, this.height-150-this.brickH/2-this.brickH/2, this.brickH/2);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();
        this.xMove = tx;
        setGameThread();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(this.gameState == GET_READY)
                {
                    this.gameState = PLAYING;
                    invalidate();
                }
                if(this.gameState == PLAYING)
                {
                    this.paddleThreadNeedToMove = true;
                    setPaddleThread();
                }
                if(this.gameState == GAME_OVER)
                {
                    this.gameState = PLAYING;
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(this.gameState == PLAYING)
                {
                    setPaddleThread();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(this.gameState == PLAYING)
                {
                    this.paddleThreadNeedToMove = false;
                }
                break;
        }
        return true;
    }

    public void setPaddleThread()
    {
        if(this.paddleThread == null) {
            this.paddleThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (paddleThreadNeedToMove) {
                        if (gameState == PLAYING) {
                            paddle.setX(xMove, brickW, width);
                            postInvalidate();
                        }
                        SystemClock.sleep(10);
                    }
                    paddleThread = null;
                }
            });
            this.paddleThread.start();
        }
    }



    public void setGameThread() {
        if (this.gameThread == null) {
            this.gameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isStrike;
                    while (true) {
                        if (gameState == PLAYING) {
                            ball.move(width, height, paddle);
//                            ball.checkBallCollideBrick(context, paddle);
                            isStrike = ball.isStrike(height);
                            if (isStrike) {
                                setScore();
                                lives.setDead(death);
                                if (lives.getCurrLife() == -1) {
                                    gameState = GAME_OVER;
                                    resetGame(1);
                                    postInvalidate();
                                    break;
                                } else {
                                    gameState = GET_READY;
                                    resetGame(0);
                                }
                            }
                            postInvalidate();
                        }
                        SystemClock.sleep(10);
                    }
                    gameThread = null;
                }
            });
            this.gameThread.start();
        }
    }



    public void resetGame(int resetType){
        this.ball.resetBall(this.width/2, this.height-150-this.brickH/2-this.brickH/2);
        this.paddle.resetPaddle(this.width/2 - brickW/2, height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);
        if(resetType == 1) {
            score = 0;
            this.lives.resetLive(this.life);
            this.gameThread = null;
            this.paddleThread = null;
            this.gameState = GET_READY;
        }
    }

    public void setScore(){
        this.score += 5 * (this.lives.getCurrLife()+1);
    }
}
