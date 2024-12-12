package com.oskar;

import org.junit.Test;
import static org.junit.Assert.*;

public class PanelTest 
{
	@Test
	public void test_maximum_tile_index_x()
	{
		GamePanel panel = new GamePanel();
		int maximum_tile_index_x = panel.remaining_possible_tiles();
		assertEquals( ( maximum_tile_index_x + 1 ) * panel.width_in_dots );
	}
	@Test
	public void  test_maximum_tile_index_y()
	{
		GamePanel panel = new GamePanel();
		int maximum_tile_index_y = panel.remaining_possible_tiles();
		assertEquals( ( maximum_tile_index_y + 1 ) * panel.height_in_dots );
	}
	
    @Test
    public void test_constructor() {
        GamePanel panel = new GamePanel();
        assertNotNull( panel );
    }
}