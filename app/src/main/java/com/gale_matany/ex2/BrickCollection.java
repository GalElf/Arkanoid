package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BrickCollection {

//    private final int MAX_COLS = 7;
//    private final int MIN_COLS = 3;
//    private final int MAX_ROWS = 6;
//    private final int MIN_ROWS = 2;

    private final int SPACE = 5;

    private final int cols, rows;
    private float brickW, brickH;

    private Brick[][] bricks;

    public BrickCollection(float width, float height, int cols, int rows)
    {
        this.rows = rows;
        this.cols = cols;
        this.brickW = (width-((this.cols-1)*this.SPACE))/this.cols;
        this.brickH = height/20;

        this.bricks = new Brick[this.rows][this.cols];
        crateBricks(width, height);
    }

    private void crateBricks(float width, float height){
        int startH = 220;
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                        this.bricks[i][j] = new Brick((float) j*this.brickW+j*this.SPACE,(float) startH+i*this.brickH+(i+1)*this.SPACE,
                        (float) (j+1)*this.brickW+j*this.SPACE, startH+this.brickH*(i+1)+(i+1)*this.SPACE);
            }
        }
    }

    public void resetBricks(float width, float height){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.bricks[i][j].setIsDead(false);
            }
        }
    }

    public boolean checkWin(){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                if(!this.bricks[i][j].getIsDead()){
                    return false;
                }
            }
        }
        return true;
    }

    public float getBrickW(){
        return this.brickW;
    }

    public float getBrickH(){
        return this.brickH;
    }

    public void draw(Canvas canvas){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.bricks[i][j].draw(canvas);
            }
        }
    }

    public void setIsDead(boolean isDead, int i, int j){
        this.bricks[i][j].setIsDead(isDead);
    }

    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public Brick getBrick(int i, int j) {
        return this.bricks[i][j];
    }
}
