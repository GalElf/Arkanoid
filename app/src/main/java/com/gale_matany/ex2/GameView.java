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

    private final Context context;

    private int gameState, score;
    private boolean isWon, paddleNeedToMove, runThreadGame;
    private final Paint textStartGamePaint, textPaint, textLivesPaint;
    private float width, height, xTextScore, yTextScore, xLifeDeath, yLifeDeath, brickW, brickH, xMove;

    private Thread gameThread;
    private BrickCollection bricks;
    private Paddle paddle;
    private Ball ball;
    private Lives lives;

    private Bitmap life, death;


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

        this.cols = (int) (Math.random() * (MAX_COLS+1 - MIN_COLS)) + MIN_COLS;
        this.rows = (int) (Math.random() * (MAX_ROWS+1 - MIN_ROWS)) + MIN_ROWS;

        this.score = 0;
        this.gameState = GET_READY;
        this.life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        this.life = Bitmap.createScaledBitmap(this.life, 85, 80, true);
        this.death = BitmapFactory.decodeResource(getResources(), R.drawable.death);
        this.death = Bitmap.createScaledBitmap(this.death, 85, 80, true);
        this.gameThread = null;

        this.paddleNeedToMove = false;
        this.isWon = false;
        this.runThreadGame = true;


    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

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


        if(this.lives == null)
            this.lives = new Lives(this.life,this.width-100, this.yLifeDeath);

        if(this.bricks == null)
            this.bricks = new BrickCollection(width, height, this.cols, this.rows);
        this.brickW = this.bricks.getBrickW();
        this.brickH = this.bricks.getBrickH();

        if(this.paddle == null)
            this.paddle = new Paddle(this.width/2 - this.brickW/2, this.height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);

        if(this.ball == null)
            this.ball = new Ball(this.width/2, this.height-150-this.brickH/2-this.brickH/2, this.brickH/2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.xMove = event.getX();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(this.gameState == GET_READY)
                    this.gameState = PLAYING;

                if(this.gameState == PLAYING)
                    this.paddleNeedToMove = true;

                if(this.gameState == GAME_OVER)
                {
                    this.gameState = GET_READY;
                    resetGame(1);
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
        if (this.gameState == PLAYING) {
            this.ball.move(this.width, this.height);
            this.ball.checkBallCollideBrick(this.paddle);
            for(int i=0; i<this.bricks.getRows(); i++) {
                boolean isCollide = false;
                for (int j = 0; j < this.bricks.getCols(); j++) {
                    isCollide = ball.checkBallCollideBricks(this.bricks.getBrick(i, j));
                    if (isCollide) {
                        setScore();
                        makeSoundAfterHit();
                        break;
                    }
                }
                if (isCollide) {
                    break;
                }
            }

            this.isWon = this.bricks.checkWin();
            if(this.isWon)
                this.gameState = GAME_OVER;
            boolean isStrike = this.ball.isStrike(this.height);
            if (isStrike) {
                this.lives.setDead(this.death);
                if (this.lives.getCurrLife() == -1)
                    this.gameState = GAME_OVER;
                else {
                    this.gameState = GET_READY;
                    resetGame(0);
                }
            }
        }
    }

    private void makeSoundAfterHit(){
        MediaPlayer mp = MediaPlayer.create(this.context, R.raw.sound_hit);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }

    private void resetGame(int resetType)
    {
        if(resetType == 1) {
            this.score = 0;
            this.lives.resetLive(this.life);
            this.bricks.resetBricks();
            this.brickW = bricks.getBrickW();
            this.brickH = bricks.getBrickH();
            this.gameThread = null;
            this.isWon = false;
        }
        this.ball.resetBall(this.width/2, this.height-150-this.brickH/2-this.brickH/2);
        this.paddle.resetPaddle(this.width/2 - brickW/2, height-150-this.brickH/2, this.width/2 + this.brickW/2, this.height-150);
    }

    private void setScore()
    {
        this.score += 5 * (this.lives.getCurrLife()+1);
    }

    public void setThread()
    {
        if(this.gameThread == null) {
            this.gameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (runThreadGame) {
                        postInvalidate();
                        SystemClock.sleep(5);
                    }
                    gameThread = null;
                }
            });
            this.gameThread.start();
        }
    }

    public void setRunThreadGame(boolean runThreadGame) {
        this.runThreadGame = runThreadGame;
    }

}
