package com.example.myapplication2.snaketakeone;

import android.app.Activity;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication2.app.R;


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
        return true;
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
