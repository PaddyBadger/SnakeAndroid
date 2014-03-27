package com.example.myapplication2.snaketakeone;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication2.app.R;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private CharSequence mTitle;
    FrameLayout game;
    GameSurfaceView gameSurfaceView;
    RelativeLayout buttonsHolder;
    LinearLayout gameButtons;
    Button left;
    Button right;
    Button up;
    Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurfaceView = new GameSurfaceView(this);
        game = new FrameLayout(this);
        buttonsHolder = new RelativeLayout(this);
        gameButtons = new LinearLayout(this);

        left = new Button(this);
        right = new Button(this);
        up = new Button(this);
        down = new Button(this);

        left.setText("<");
        right.setText(">");
        up.setText("^");
        down.setText("v");

        left.setId(1);
        right.setId(2);
        up.setId(3);
        down.setId(4);

        LinearLayout.LayoutParams buttons = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttons.gravity = Gravity.BOTTOM;

        gameButtons.setLayoutParams(buttons);
        gameButtons.addView(left);
        gameButtons.addView(right);
        gameButtons.addView(up);
        gameButtons.addView(down);

        setContentView(game);
        game.addView(gameSurfaceView);
        game.addView(gameButtons);


        View leftButtonListener = findViewById(1);
        leftButtonListener.setOnClickListener(this);

        View rightButtonListener = findViewById(2);
        rightButtonListener.setOnClickListener(this);

        View upButtonListener = findViewById(3);
        upButtonListener.setOnClickListener(this);

        View downButtonListener = findViewById(4);
        downButtonListener.setOnClickListener(this);

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
    public void onClick(View view) {
        if (view == left) {
            gameSurfaceView.game.userPressedLeft();
        } else if (view == right) {
            gameSurfaceView.game.userPressedRight();
        } else if (view == up) {
            gameSurfaceView.game.userPressedUp();
        } else if (view == down) {
            gameSurfaceView.game.userPressedDown();
        }
    }
}
