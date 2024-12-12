package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameBoardTest {
    @Test
    public void testBoundaryChecks() {
        GameBoard board = new GameBoard(30, 30);
        assertTrue(board.isOutOfBounds(new IntPair(-1, 15)));
        assertTrue(board.isOutOfBounds(new IntPair(30, 15)));
        assertFalse(board.isOutOfBounds(new IntPair(15, 15)));
    }
}