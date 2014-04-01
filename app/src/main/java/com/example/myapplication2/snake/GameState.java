package com.example.myapplication2.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by patriciaestridge on 3/25/14.
 */
public class GameState {
    public boolean isAlive = true;
    public List<SnakeBody> snakePieces = new ArrayList<SnakeBody>();
    public List<int[]> snakePositions = new ArrayList<int[]>();
    Snake leadSnake;
    Random random;
    Bait bait;

    public GameState(){
        Snake firstSnake = Snake.firstSnake();
        leadSnake = firstSnake;
        bait = new Bait(foodPosition(),foodPosition());
    }

    private int foodPosition() {
        random = new Random();
        int randomPosition = random.nextInt(10);
        int position = randomPosition*50;
        return position;
    }

    public Snake getLeadSnake() {
        if (isAlive) {
            return leadSnake;
        } else {
            //Game is over
        }
        return leadSnake;
    }

    public List<SnakeBody> getSnakePieces() {
        return snakePieces; }

    public Bait getBait() {
        isEaten();
        leadSnakePositions();
        moveSnakePieces();
        if (isEaten()) {
            bait.x = foodPosition();
            bait.y = foodPosition();
            addToSnakeBody();
        }
        return bait;
    }

    private boolean isEaten() {
        int[] baitPosition = {bait.x, bait.y};
        int[] leadSnakePosition = {leadSnake.x, leadSnake.y};

        return Arrays.equals(baitPosition, leadSnakePosition);
    }

    public List<SnakeBody> addToSnakeBody() {
        SnakeBody snakeBody = new SnakeBody(leadSnake.x, leadSnake.y);
        snakePieces.add(snakeBody);
        return snakePieces;
    }

    public List<int[]> leadSnakePositions() {
        int[] currentPosition = {leadSnake.x, leadSnake.y};
        snakePositions.add(0, currentPosition);
        return snakePositions;
    }

    public void moveSnakePieces() {
        for (int i = 0; i < snakePieces.size(); i++) {
            SnakeBody currentPiece = snakePieces.get(i);

            int x = snakePositions.get(i)[0];
            int y = snakePositions.get(i)[1];

            currentPiece.x = x;
            currentPiece.y = y;
        }
    }

    public void willSnakeEatItself() {

        if (snakePieces.size() > 0) {
            for (int i = 0; i < snakePieces.size(); i++) {
                int[] nextLeadSnakePosition = {leadSnake.x,leadSnake.y};
                if (leadSnake.down) {
                    nextLeadSnakePosition[1] = leadSnake.y+50;
                } else if (leadSnake.right) {
                    nextLeadSnakePosition[0] = leadSnake.x+50;
                } else if (leadSnake.left) {
                    nextLeadSnakePosition[0] = leadSnake.x-50;
                } else if (leadSnake.up) {
                    nextLeadSnakePosition[1] = leadSnake.y-50;
                }
                int[] thisSnakePiece = {snakePieces.get(i).x, snakePieces.get(i).y};

                if (nextLeadSnakePosition[0] == thisSnakePiece[0] && nextLeadSnakePosition[1] == thisSnakePiece[1]) {
                    isAlive = false;
                }
            }
        }
    }

    public void userPressedLeft() {
        if (snakePieces.size() > 0) {
            int[] nextPosition = {leadSnake.x - 50, leadSnake.y};
            int[] firstSnakePiece = {snakePieces.get(0).x, snakePieces.get(0).y};

            if (nextPosition[0] == firstSnakePiece[0] && nextPosition[1] == firstSnakePiece[1]) {
            } else {
                leadSnake.isLeft(true);
            }
        } else {
            leadSnake.isLeft(true);
        }
    }

    public void userPressedRight() {
        if (snakePieces.size() > 0) {
            int[] nextPosition = {leadSnake.x + 50, leadSnake.y};
            int[] firstSnakePiece = {snakePieces.get(0).x, snakePieces.get(0).y};

            if (nextPosition[0] == firstSnakePiece[0] && nextPosition[1] == firstSnakePiece[1]) {
            } else {
                leadSnake.isRight(true);
            }
        } else {
            leadSnake.isRight(true);
        }
    }

    public void userPressedUp() {
        if (snakePieces.size() > 0) {
            int[] nextPosition = {leadSnake.x, leadSnake.y - 50};
            int[] firstSnakePiece = {snakePieces.get(0).x, snakePieces.get(0).y};

            if (nextPosition[0] == firstSnakePiece[0] && nextPosition[1] == firstSnakePiece[1]) {
            } else {
                leadSnake.isUp(true);
            }
        } else {
            leadSnake.isUp(true);
        }
    }

    public void userPressedDown() {
        if (snakePieces.size() > 0) {
            int[] nextPosition = {leadSnake.x, leadSnake.y + 50};
            int[] firstSnakePiece = {snakePieces.get(0).x, snakePieces.get(0).y};

            if (nextPosition[0] == firstSnakePiece[0] && nextPosition[1] == firstSnakePiece[1]) {
            } else {
                leadSnake.isDown(true);
            }
        } else {
            leadSnake.isDown(true);
        }
    }
}
