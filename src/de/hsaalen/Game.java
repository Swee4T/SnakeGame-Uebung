package de.hsaalen;

import java.util.List;
import java.util.ArrayList;

public class Game 
{
    private final GameBoard board;
    private Apple apple;
    private final Score score;
    private final int initial_snake_size = 3;
    private List<Obstacle> obstacles;
        
    public Snake snake;
    public Direction direction = Direction.right;
    public boolean inGame = true;
        
    public Game() 
    {
        this.board = new GameBoard(30, 30);
        this.score = new Score();
        place_snake_at_initial_location();
        createNewRandomApple();
        initObstacles();
    }
    
    private void initObstacles() {
        obstacles = new ArrayList<>();
        // Ecken
        obstacles.add(new Obstacle(2, 2));
        obstacles.add(new Obstacle(2, 3));
        obstacles.add(new Obstacle(board.getWidth()-3, 2));
        obstacles.add(new Obstacle(board.getWidth()-3, 3));
        obstacles.add(new Obstacle(2, board.getHeight()-3));
        obstacles.add(new Obstacle(2, board.getHeight()-4));
        obstacles.add(new Obstacle(board.getWidth()-3, board.getHeight()-3));
        obstacles.add(new Obstacle(board.getWidth()-3, board.getHeight()-4));
        // Mitte
        obstacles.add(new Obstacle(15, 15));
        obstacles.add(new Obstacle(15, 16));
    }
    
    private void createNewRandomApple() {
        boolean isSuper = Math.random() < 0.2;
        apple = new Apple(board.getWidth(), board.getHeight(), isSuper);
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
            for(int i = 0; i < apple.getGrowthAmount(); i++) {
                snake.grow(direction);
            }
            score.addPoints(apple.isSuper() ? 30 : 10);
            createNewRandomApple();
        }
    }

    private void move() 
    {
        snake.move(direction);
    }

    private void checkCollision()
    {
        if (board.isOutOfBounds(snake.head_position()) || 
            snake.is_snake_colliding(board.getWidth(), board.getHeight()) ||
            isCollidingWithObstacle())
        {
            inGame = false;
        }
    }
    
    private boolean isCollidingWithObstacle() {
        for(Obstacle obs : obstacles) {
            if(obs.isColliding(snake.head_position())) {
                return true;
            }
        }
        return false;
    }
    
    public IntPair getApplePosition() {
        return apple.getPosition();
    }
    
    public boolean isCurrentAppleSuper() {
        return apple.isSuper();
    }
    
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public int getWidthInTiles() {
        return board.getWidth();
    }
    
    public int getHeightInTiles() {
        return board.getHeight();
    }
}