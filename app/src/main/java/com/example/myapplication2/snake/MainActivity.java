package com.example.myapplication2.snake;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    private CharSequence mTitle;
    FrameLayout game;
    GameSurfaceView gameSurfaceView;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetectorCompat flingDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurfaceView = new GameSurfaceView(this);
        game = new FrameLayout(this);

        this.flingDetector = new GestureDetectorCompat(this, this);

        setContentView(game);
        game.addView(gameSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.onResumeGameSurfaceView();
    }

    @Override
    public void onPause() {
        super.onPause();
        gameSurfaceView.onPauseGameSurfaceView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (gameSurfaceView.isGameOver()) {
           return onSingleTapUp(event);
        }
        return flingDetector.onTouchEvent(event);

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                   if (diffY > 0) {
                       onSwipeDown();
                   } else {
                    onSwipeUp();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void onSwipeRight() {
        gameSurfaceView.game.userPressedRight();
    }
    public void onSwipeLeft() {
        gameSurfaceView.game.userPressedLeft();
    }
    public void onSwipeDown() {
        gameSurfaceView.game.userPressedDown();
    }
    public void onSwipeUp() {
        gameSurfaceView.game.userPressedUp();
    }

    public boolean onSingleTapUp(MotionEvent e) {
        boolean result = false;
        gameSurfaceView.onRestart();
        return result;
    }


    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }
    public void onShowPress(MotionEvent e) {
    }
    public void onLongPress(MotionEvent e) {
    }
    public boolean onDown(MotionEvent e) {
        return true;
    }


}
