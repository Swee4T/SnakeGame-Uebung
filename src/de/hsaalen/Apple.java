package de.hsaalen;

public class Apple {
    private IntPair position;
    private final int width_in_tiles;
    private final int height_in_tiles;
    private final int growthAmount;
    private final boolean isSuper;
    
    public Apple(int width, int height, boolean isSuper) {
        this.width_in_tiles = width;
        this.height_in_tiles = height;
        this.isSuper = isSuper;
        this.growthAmount = isSuper ? 3 : 1;
        placeAtRandom();
    }
    
    public void placeAtRandom() {
        int x = (int) (Math.random() * (width_in_tiles - 1));
        int y = (int) (Math.random() * (height_in_tiles - 1));
        position = new IntPair(x, y);
    }
    
    public IntPair getPosition() {
        return position;
    }
    
    public boolean isColliding(IntPair snakeHead) {
        return position.x == snakeHead.x && position.y == snakeHead.y;
    }
    
    public int getGrowthAmount() {
        return growthAmount;
    }
    
    public boolean isSuper() {
        return isSuper;
    }
}