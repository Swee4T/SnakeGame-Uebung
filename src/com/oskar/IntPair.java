package com.oskar;

public class IntPair {

    public int x;
    public int y;

    public IntPair (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move (Direction direction, int dot_size_in_pixels) {
        switch(direction) {
            case Direction.left:
                x -= dot_size_in_pixels;
                break;

            case Direction.right:
                x += dot_size_in_pixels;
                break;

            case Direction.up:
                y -= dot_size_in_pixels;
                break;

            case Direction.down:
                y += dot_size_in_pixels;
        }
    }

    public IntPair clone() {
        return new IntPair( this.x, this.y);
    }

    public String toString() {
        String result = "(" + x + "/" + y + ")";
        return result;
    }
}
