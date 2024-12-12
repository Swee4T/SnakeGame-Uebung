package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreTest {
    @Test
    public void testScoreIncrement() {
        Score score = new Score();
        score.addPoints(10);
        assertEquals(10, score.getCurrentScore());
        score.addPoints(5);
        assertEquals(15, score.getCurrentScore());
    }
    
    @Test
    public void testScoreReset() {
        Score score = new Score();
        score.addPoints(10);
        score.reset();
        assertEquals(0, score.getCurrentScore());
    }
}