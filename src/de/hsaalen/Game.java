package de.hsaalen;

import java.util.List;
import java.util.LinkedList;

public class Game 
{
	public final int width_in_tiles  = 30;
    public final int height_in_tiles = 30;
	public final int initial_snake_size = 3;
		
    public Snake snake;
    public IntPair apple_position; 
	public Direction direction = Direction.right;

    public boolean inGame = true;
		
	public Game( ) 
	{
		place_snake_at_initial_location();        
        place_apple_at_random_location();	
	}
	
	public void place_snake_at_initial_location() 
	{
		snake = new Snake( 3 );
	}
	
	private void place_apple_at_random_location() 
	{
        int apple_x = (int) (Math.random() * maximum_tile_index_x());
        int apple_y = (int) (Math.random() * maximum_tile_index_y());
		apple_position = new IntPair( apple_x, apple_y );
    }

	public void handle_round()
	{
		if ( !inGame ) 
			return;
		
		checkApple();
        checkCollision();
        move();
	}
	
	private void checkApple() 
	{
        if ((snake.head_position().x == apple_position.x) && (snake.head_position().y == apple_position.y)) 
		{
			snake.grow( direction );
            place_apple_at_random_location();
        }
    }

    private void move() 
	{
		snake.move( direction );
    }

    private void checkCollision()
	{
		if ( snake.is_snake_colliding( width_in_tiles, height_in_tiles ) )
			inGame = false;
   }
	
    public int maximum_tile_index_x()
	{
		return width_in_tiles - 1;
	}
	
    public int maximum_tile_index_y()
	{
		return height_in_tiles - 1;
	}

}