package com.example.myapplication2.snake;

/**
 * Created by patriciaestridge on 3/25/14.
 */
public class Snake {
    int x, y;
    public Boolean left, right, up;
    public Boolean down = true;
    private static int unit = 50;

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Snake firstSnake() {
        return new Snake(0, 0);
    }

    public void move() {
        if (down) {
            this.y += unit;
        } else if (up) {
            this.y -= unit;
        } else if (left) {
            this.x -= unit;
        } else if (right) {
            this.x += unit;
        }
    }

    public void isLeft(boolean b) {
        left = b;
        down = false;
        up = false;
        right = false;
    }

    public void isDown(boolean b) {
        down = b;
        left = false;
        right = false;
        up = false;
    }

    public void isRight(boolean b) {
        right = b;
        left = false;
        down = false;
        up = false;
    }

    public void isUp(boolean b) {
        up = b;
        down = false;
        right = false;
        left = false;
    }
}

