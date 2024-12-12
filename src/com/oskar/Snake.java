package com.oskar;

import java.util.List;
import java.util.LinkedList;

public class Snake extends JFrame {

    public class Snake() {
        List<IntPair> positions;
        int dot_size_in_pixels;

        public Snake( int initial_snake_size, int dot_size_in_pixels) {
            positions = new LinkedList<IntPair>();
        }

        public void set_apple_at_new_random_position( int initial_snake_size) {
            for (int i = 0; i < initial_snake_size; i++) {
                int x = 50 - i * 10;
                int y = 50;

                IntPair new_position = new IntPair(x,y);
                positions.add( new_position);
            }
        }

	public void move( Direction direction )
	{
		for ( int i = length()-1; i > 0; i-- )
		{
			position( i ).x = position( i-1 ).x;
			position( i ).y = position( i-1 ).y;
        }
		head_position().move( direction, dot_size_in_pixels );
 	}
	
	public void grow( Direction direction )
	{
		IntPair new_head_position = head_position().clone();
		new_head_position.move( direction, dot_size_in_pixels );
		positions.add( 0, new_head_position );
 	}
	
	public boolean is_snake_colliding( int board_width_in_pixels, int board_height_in_pixels )
	{
		if ( is_colliding_with_itself() )
			return true;		
		if ( is_outside_board( board_width_in_pixels, board_height_in_pixels ) )
			return true;			
		return false;
	}
	public boolean is_colliding_with_itself()
	{
        for ( int i = length()-1; i > 1; i-- )
		{
			if ( position( i ).x == head_position().x &&
                 position( i ).y == head_position().y )
				return true;
		}
		return false;
 	}
	public boolean is_outside_board( int board_width_in_pixels, int board_height_in_pixels )
	{
		if ( head_position().x < 0 )
			return true;
		if ( head_position().x >= board_width_in_pixels )
			return true;
		if ( head_position().y < 0 )
			return true;
		if ( head_position().y >= board_height_in_pixels )
			return true;
		return false;
	}
 	
	public int length() 
	{
		return positions.size();
	}
	
	public IntPair position( int index )
	{
		return positions.get( index );
	}
	
	public IntPair head_position()
	{
		return position( 0 );
	}
	
	public String toString() 
	{
		String result = "Snake" + positions.toString();
		return result;
	}
}
