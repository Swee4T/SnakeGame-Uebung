package de.hsaalen;

public class Game 
{
    private final GameBoard board;
    private Apple apple;  // nicht mehr final, da wir neue Äpfel erzeugen
    private final Score score;
    private final int initial_snake_size = 3;
        
    public Snake snake;
    public Direction direction = Direction.right;
    public boolean inGame = true;
        
    public Game() 
    {
        this.board = new GameBoard(30, 30);
        this.score = new Score();
        place_snake_at_initial_location();
        createNewRandomApple();
    }
    
    private void createNewRandomApple() {
        // 20% Chance für einen Super-Apfel
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
            // Mehrfaches Wachstum für Super-Apfel
            for(int i = 0; i < apple.getGrowthAmount(); i++) {
                snake.grow(direction);
            }
            // Mehr Punkte für Super-Apfel
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
            snake.is_snake_colliding(board.getWidth(), board.getHeight()))
        {
            inGame = false;
        }
    }
    
    public IntPair getApplePosition() {
        return apple.getPosition();
    }
    
    public boolean isCurrentAppleSuper() {
        return apple.isSuper();
    }
    
    public int getWidthInTiles() {
        return board.getWidth();
    }
    
    public int getHeightInTiles() {
        return board.getHeight();
    }
}