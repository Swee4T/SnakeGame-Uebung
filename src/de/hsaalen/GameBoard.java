package de.hsaalen;

public class GameBoard {
    private final int width_in_tiles;
    private final int height_in_tiles;
    
    public GameBoard(int width, int height) {
        this.width_in_tiles = width;
        this.height_in_tiles = height;
    }
    
    public boolean isOutOfBounds(IntPair position) {
        return position.x < 0 || position.x >= width_in_tiles ||
               position.y < 0 || position.y >= height_in_tiles;
    }
    
    public int getWidth() {
        return width_in_tiles;
    }
    
    public int getHeight() {
        return height_in_tiles;
    }
}