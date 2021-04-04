package com.gale_matany.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BrickCollection {

    private static final int MAX_COLS = 7;
    private static final int MIN_COLS = 3;
    private static final int MAX_ROWS = 6;
    private static final int MIN_ROWS = 2;

    private static final int SPACE = 5;

    private int cols, rows;
    private float brickW, brickH;

    private Brick bricks[][];

    public BrickCollection(float width, float height)
    {
        getRandomColsRows(width, height);
        bricks = new Brick[rows][cols];
        crateBricks(width, height);
    }

    private void getRandomColsRows(float width, float height){
        this.cols = (int) (Math.random() * (MAX_COLS+1 - MIN_COLS)) + MIN_COLS;
        this.rows = (int) (Math.random() * (MAX_ROWS+1 - MIN_ROWS)) + MIN_ROWS;
        brickW = (width-((cols-1)*SPACE))/cols;
        brickH = height/20;
    }

    private void crateBricks(float width, float height){
        int startH = 250;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                bricks[i][j] = new Brick((float) j*brickW+j*SPACE, (float) startH+i*brickH+(i+1)*SPACE, (float) (j+1)*brickW+j*SPACE, startH+brickH*(i+1)+(i+1)*SPACE);
            }
        }
    }

    public float getBrickW(){
        return brickW;
    }

    public float getBrickH(){
        return brickH;
    }

    public void draw(Canvas canvas){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                bricks[i][j].draw(canvas);
            }
        }
    }
}
