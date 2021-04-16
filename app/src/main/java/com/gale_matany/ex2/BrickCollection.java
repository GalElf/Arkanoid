package com.gale_matany.ex2;

import android.graphics.Canvas;

public class BrickCollection {

    private final int SPACE = 5;

    private final int cols, rows;
    private final float brickW, brickH;

    private final Brick[][] bricks;

    public BrickCollection(float width, float height, int cols, int rows)
    {
        this.rows = rows;
        this.cols = cols;
        this.brickW = (width-((this.cols-1)*this.SPACE))/this.cols;
        this.brickH = height/20;

        this.bricks = new Brick[this.rows][this.cols];
        crateBricks();
    }

    // init the all the bricks
    private void crateBricks(){
        int startH = 220;
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.bricks[i][j] = new Brick((float) j*this.brickW+j*this.SPACE,(float) startH+i*this.brickH+(i+1)*this.SPACE,
                        (float) (j+1)*this.brickW+j*this.SPACE, startH+this.brickH*(i+1)+(i+1)*this.SPACE);
            }
        }
    }

    // reset the bricks to start new game - reset them from dead to live
    public void resetBricks(){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.bricks[i][j].setIsDead(false);
            }
        }
    }

    // check if all the brick are dead
    public boolean checkWin(){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                if(this.bricks[i][j].getIsDead()){
                    return false;
                }
            }
        }
        return true;
    }

    // return brick width
    public float getBrickW(){
        return this.brickW;
    }

    // return brick height
    public float getBrickH(){
        return this.brickH;
    }

    // draw all the brick on the canvas in the app
    public void draw(Canvas canvas){
        for(int i=0; i<this.rows; i++){
            for(int j=0; j<this.cols; j++){
                this.bricks[i][j].draw(canvas);
            }
        }
    }

    // return the number of columns
    public int getCols() {
        return this.cols;
    }

    // return the number of rows
    public int getRows() {
        return this.rows;
    }

    // return specific brick according to i j in the 2D array
    public Brick getBrick(int i, int j) {
        return this.bricks[i][j];
    }
}
