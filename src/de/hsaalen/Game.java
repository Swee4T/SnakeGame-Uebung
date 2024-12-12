package de.hsaalen;

public class Game 
{
    private final GameBoard board;
    private final Apple apple;
    private final Score score;
    private final int initial_snake_size = 3;
        
    public Snake snake;
    public Direction direction = Direction.right;
    public boolean inGame = true;
        
    public Game() 
    {
        this.board = new GameBoard(30, 30);
        this.apple = new Apple(board.getWidth(), board.getHeight());
        this.score = new Score();
        place_snake_at_initial_location();
    }
    
    public void place_snake_at_initial_location() 
    {
        snake = new Snake(initial_snake_size);
    }

    public void handle_round()
    {
        if (!inGame) 
            return;
        
        checkApple();
        checkCollision();
        move();
    }
    
    private void checkApple() 
    {
        if (apple.isColliding(snake.head_position())) 
        {
            snake.grow(direction);
            apple.placeAtRandom();
            score.addPoints(10);
        }
    }

    private void move() 
    {
        snake.move(direction);
    }

    private void checkCollision()
    {
        if (board.isOutOfBounds(snake.head_position()) || 
            snake.is_snake_colliding(board.getWidth(), board.getHeight()))
        {
            inGame = false;
        }
    }
    
    public IntPair getApplePosition() {
        return apple.getPosition();
    }
    
    public int getWidthInTiles() {
        return board.getWidth();
    }
    
    public int getHeightInTiles() {
        return board.getHeight();
    }
}