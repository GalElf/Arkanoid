package com.gale_matany.ex2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private final int GET_READY = 1;
    private final int PLAYING = 2;
    private final int GAME_OVER = 3;

    private final int MAX_COLS = 7;
    private final int MIN_COLS = 3;
    private final int MAX_ROWS = 6;
    private final int MIN_ROWS = 2;

    private final int cols, rows;


    private int gameState;
    boolean isWon;
    private final Paint textStartGamePaint, textPaint, textLivesPaint;
    private float xTextScore, yTextScore, xLifeDeath, yLifeDeath, brickW, brickH;
    private int score;
    private float xMove;

    private Thread gameThread;
    private boolean paddleNeedToMove, threadGame;
    private BrickCollection bricks;
    private Paddle paddle;
    private Ball ball;
    private Lives lives;

    private Bitmap life;
    private Bitmap death;

    private float width, height;
    private final Context context;



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

        this.cols = (int) (Math.random() * (this.MAX_COLS+1 - this.MIN_COLS)) + this.MIN_COLS;
        this.rows = (int) (Math.random() * (this.MAX_ROWS+1 - this.MIN_ROWS)) + this.MIN_ROWS;

        this.score = 0;
        this.gameState = GET_READY;
        this.life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        this.life = Bitmap.createScaledBitmap(this.life, 85, 80, true);
        this.death = BitmapFactory.decodeResource(getResources(), R.drawable.death);
        this.death = Bitmap.createScaledBitmap(this.death, 85, 80, true);
        this.gameThread = null;

        this.paddleNeedToMove = false;
        this.isWon = false;

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Log.i("test", "game state: " + this.gameState);

        gamePlay();
        this.ball.draw(canvas);

        this.bricks.draw(canvas);
        this.paddle.draw(canvas);
        this.lives.drawLife(canvas);
        canvas.drawText("Lives: ", this.xLifeDeath, this.yTextScore, this.textLivesPaint);
        canvas.drawText("Score: " + this.score, this.xTextScore, this.yTextScore, this.textPaint);


        if(this.gameState == GET_READY)
        {
            canvas.drawText("Click to PLAY!", this.width/2, this.height/2, this.textStartGamePaint);
        }
        if(this.gameState == PLAYING){
            if(this.paddleNeedToMove){
                this.paddle.setX(this.xMove, this.brickW, this.width);
            }
        }
        if(this.gameState == GAME_OVER) {
            String gameWinLoseTxt = "";
            if(this.isWon)
                gameWinLoseTxt = "You Win!";
            else
                gameWinLoseTxt = "You Lose!";
            canvas.drawText("GAME OVER - " + gameWinLoseTxt, this.width/2, this.height/2, this.textStartGamePaint);
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
        this.lives = new Lives(this.life,this.width-100, this.yLifeDeath);

        this.bricks = new BrickCollection(width, height, this.cols, this.rows);
        this.brickW = this.bricks.getBrickW();
        this.brickH = this.bricks.getBrickH();

        this.paddle = new Paddle(this.width/2 - this.brickW/2, this.height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);

        this.ball = new Ball(this.width/2, this.height-150-this.brickH/2-this.brickH/2, this.brickH/2);
//        int dis = 100;
//        this.ball1 = new Ball(this.width/2, this.height-150-this.brickH/2-this.brickH/2-dis, this.brickH/2);
//        this.ball2 = new Ball(this.width/2-this.brickW/2-dis-this.brickH/2, (float) (this.height-150-this.brickH*0.25), this.brickH/2);
//        this.ball3 = new Ball(this.width/2+this.brickW/2+dis+this.brickH/2, (float) (this.height-150-this.brickH*0.25), this.brickH/2);
//        this.ball4 = new Ball(this.width/2 - brickW/2-dis, height-150-this.brickH/2-dis, this.brickH/2);
//        this.ball5 = new Ball(this.width/2 + brickW/2+dis, height-150-this.brickH/2-dis, this.brickH/2);
//        this.ball = new Ball((float) (677.6667-50), 397+50, this.brickH/2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.xMove = event.getX();

        setThread();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(this.gameState == GET_READY)
                {
                    this.gameState = PLAYING;
                    invalidate();
                }

                if(this.gameState == PLAYING)
                    this.paddleNeedToMove = true;

                if(this.gameState == GAME_OVER)
                {
                    this.gameState = GET_READY;
                    resetGame(1);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if(this.gameState == PLAYING)
                    this.paddleNeedToMove = false;
                break;
        }
        return true;
    }

    private void gamePlay(){
        if (gameState == PLAYING) {
            this.ball.move(this.width, this.height);
            this.ball.checkBallCollideBrick(this.paddle);
            for(int i=0; i<bricks.getRows(); i++){
                for(int j=0; j<bricks.getCols(); j++){
                    boolean isCollide = ball.checkBallCollideBricks(this.bricks.getBrick(i, j));
                    if(isCollide){
                        MediaPlayer mp = MediaPlayer.create(this.context, R.raw.brick_broken3);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.reset();
                                mp.release();
                            }
                        });
                        setScore();
                    }
                }
            }
            this.isWon = this.bricks.checkWin();
            if(this.isWon)
                this.gameState = GAME_OVER;
            boolean isStrike = ball.isStrike(height);
            if (isStrike) {
                this.lives.setDead(death);
                if (lives.getCurrLife() == -1)
                    this.gameState = GAME_OVER;
                else {
                    this.gameState = GET_READY;
                    resetGame(0);
                }
            }
        }
    }

    public void resetGame(int resetType){
        if(resetType == 1) {
            this.score = 0;
            this.lives.resetLive(this.life);
            this.bricks.resetBricks(this.width, this.height);
            this.brickW = bricks.getBrickW();
            this.brickH = bricks.getBrickH();
            this.gameThread = null;
            this.isWon = false;
        }
        this.ball.resetBall(this.width/2, this.height-150-this.brickH/2-this.brickH/2);
        this.paddle.resetPaddle(this.width/2 - brickW/2, height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);
    }

    public void setScore(){
        this.score += 5 * (this.lives.getCurrLife()+1);
    }

    public void setThread()
    {
        if(this.gameThread == null) {
            this.gameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (gameState == PLAYING) {
                        postInvalidate();
                        SystemClock.sleep(3);
                    }
                    gameThread = null;
                }
            });
            this.gameThread.start();
        }
    }

}
