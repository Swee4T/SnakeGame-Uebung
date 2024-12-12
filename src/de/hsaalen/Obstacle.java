package de.hsaalen;

public class Obstacle {
    private IntPair position;
    
    public Obstacle(int x, int y) {
        this.position = new IntPair(x, y);
    }
    
    public IntPair getPosition() {
        return position;
    }
    
    public boolean isColliding(IntPair snakeHead) {
        return position.x == snakeHead.x && position.y == snakeHead.y;
    }
}