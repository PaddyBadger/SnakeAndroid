package com.example.myapplication2.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

/**
 * Created by patriciaestridge on 3/24/14.
 */
public class GameSurfaceView extends SurfaceView implements Runnable {
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = false;
    private Thread thread;
    private Paint backGroundPaint;
    GameState game;
    int w, h;
    private int unit = 48;
    Snake snakeShapes;
    int score;
    private Canvas canvas;
    private MainActivity main;


    public GameSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();

        backGroundPaint = new Paint();
        backGroundPaint.setColor(Color.WHITE);

        game = new GameState();
    }

    public boolean isGameOver() {
        return !game.isAlive;
    }

    public void onRestart() {
        if (!running) {
            game = new GameState();
            onResumeGameSurfaceView();
        }
    }

    public void onResumeGameSurfaceView() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseGameSurfaceView() {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void outOfBounds() {
        if (snakeShapes.x < 0 || snakeShapes.y < 0 || snakeShapes.x > w-unit|| snakeShapes.y > h ) {
            game.isAlive = false;
        }
    }

    public int getHighScore() {
        SharedPreferences prefs = getContext().getSharedPreferences("highScore", getContext().MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        return highScore;
    }


    public void setHighScore() {
        if (getHighScore() < score) {
            SharedPreferences prefs = getContext().getSharedPreferences("highScore", getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", score);
            editor.commit();
        }
    }

    @Override
    public void run() {

        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();

                w = canvas.getWidth();
                h = canvas.getHeight();

                canvas.drawRect(0,0,w,h, backGroundPaint);

                Bait bait = game.getBait();
                Paint paintBait = new Paint();
                paintBait.setColor(Color.BLUE);

                Rect baitRect = new Rect(bait.x, bait.y, bait.x + unit, bait.y + unit);
                canvas.drawRect(baitRect, paintBait);

                snakeShapes = game.getLeadSnake();
                Paint paint = new Paint();
                paint.setColor(Color.RED);

                game.leadSnake.move();

                Rect rect = new Rect(snakeShapes.x, snakeShapes.y, snakeShapes.x + unit, snakeShapes.y + unit);
                canvas.drawRect(rect, paint);

                outOfBounds();

                List<SnakeBody> snakeBody = game.getSnakePieces();

                score = snakeBody.size();
                Paint textPaint = new Paint();
                textPaint.setColor(Color.BLUE);
                textPaint.setTextSize(h/14);
                canvas.drawText(" "+score, w - w/5, h/12, textPaint);

                for (int i = 0; i < snakeBody.size(); i++) {
                    Rect bodyRect = new Rect(snakeBody.get(i).x, snakeBody.get(i).y, snakeBody.get(i).x + unit, snakeBody.get(i).y + unit);
                    canvas.drawRect(bodyRect, paint);
                }

                game.willSnakeEatItself();
                isGameOver();
                setHighScore();
                if (isGameOver()) {
                    running = false;
                    Paint textOverPaint = new Paint();
                    textOverPaint.setColor(Color.BLACK);
                    textOverPaint.setTextSize(h/12);
                    canvas.drawText("Game Over!", w/7, h/2, textOverPaint);
                    canvas.drawText("Your score " +score, w/9, h/2 + h/12, textOverPaint);
                    canvas.drawText("High score " +getHighScore(), w/9, h/2 + h/6, textOverPaint);
                    canvas.drawText("Replay?", w/4, h - h/10, textPaint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
