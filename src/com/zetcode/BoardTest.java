package com.zetcode;

import static org.junit.Assert.*;
import org.junit.Test;
import javax.swing.Timer;

public class BoardTest {
    
    @Test
    public void testSnakeStartPosition() {
        Board board = new Board();
        board.set_start_pos_of_snake();
        
        // Test snake size
        assertEquals(board.initial_snake_size, board.getCurrentSnakeSize());
        
        // Test starting positions
        assertEquals(50, board.getSnakeX(0));  // Head X
        assertEquals(50, board.getSnakeY(0));  // Head Y
        assertEquals(40, board.getSnakeX(1));  // First body part X
        assertEquals(50, board.getSnakeY(1));  // First body part Y
        assertEquals(30, board.getSnakeX(2));  // Second body part X
        assertEquals(50, board.getSnakeY(2));  // Second body part Y
    }
    
    @Test
    public void testGameTimer() {
        Board board = new Board();
        board.start_game_loop_timer();
        
        Timer timer = board.getTimer();
        assertTrue(timer.isRunning());
        assertEquals(board.refreshrate_in_ms, timer.getDelay());
    }
    
    @Test
    public void testApplePosition() {
        Board board = new Board();
        board.set_apple_at_new_random_position();
        
        // Test apple position is within bounds
        assertTrue(board.getAppleX() >= 0);
        assertTrue(board.getAppleX() < board.board_width_in_pixels);
        assertTrue(board.getAppleY() >= 0);
        assertTrue(board.getAppleY() < board.board_height_in_pixels);
        
        // Test apple position aligns with grid
        assertEquals(0, board.getAppleX() % board.dot_size_in_pixels);
        assertEquals(0, board.getAppleY() % board.dot_size_in_pixels);
    }
}