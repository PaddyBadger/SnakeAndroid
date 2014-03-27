package com.example.myapplication2.snaketakeone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InterruptedIOException;
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
    private int unit = 50;


    public GameSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();

        backGroundPaint = new Paint();
        backGroundPaint.setColor(Color.WHITE);

        game = new GameState();
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

    @Override
    public void run() {
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();

                int w = canvas.getWidth();
                int h = canvas.getHeight();

                canvas.drawRect(0,0,w,h, backGroundPaint);

                Bait bait = game.getBait();
                Paint paintBait = new Paint();
                paintBait.setColor(Color.BLUE);

                Rect baitRect = new Rect(bait.x, bait.y, bait.x + unit, bait.y + unit);
                canvas.drawRect(baitRect, paintBait);

                List<Snake> snakeShapes = game.getSnake();

                for (int i = 0; i < snakeShapes.size(); i++) {
                    Snake snake = snakeShapes.get(i);

                    Paint paint = new Paint();
                    paint.setColor(Color.RED);

                    game.leadSnake.move();

                    Rect rect = new Rect(snakeShapes.get(i).x, snakeShapes.get(i).y, snakeShapes.get(i).x + unit, snakeShapes.get(i).y + unit);
                    canvas.drawRect(rect, paint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
