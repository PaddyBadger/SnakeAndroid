package com.example.myapplication2.snaketakeone;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by patriciaestridge on 3/25/14.
 */
public class GameState {
    public List<Snake> snakePieces = new ArrayList<Snake>();
    Snake leadSnake;
    Random random;
    Bait bait;

    public GameState(){
        Snake firstSnake = Snake.firstSnake();
        snakePieces.add(firstSnake);
        leadSnake = firstSnake;

        bait = new Bait(foodPosition(),foodPosition());

    }

    public int foodPosition() {
        random = new Random();
        int randomPosition = random.nextInt(10);
        int position = randomPosition*50;
        return position;
    }

    public List<Snake> getSnake() { return snakePieces;}

    public Bait getBait() {return bait; }

    public void isEaten() {

    }

    public void userPressedLeft() {
        leadSnake.isLeft(true);
    }

    public void userPressedRight() {
        leadSnake.isRight(true);
    }

    public void userPressedUp() {
        leadSnake.isUp(true);
    }

    public void userPressedDown() {
        leadSnake.isDown(true);
    }
}
