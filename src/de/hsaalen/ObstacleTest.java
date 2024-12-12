package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObstacleTest {
    @Test
    public void testObstacleCollision() {
        Obstacle obstacle = new Obstacle(5, 5);
        assertTrue(obstacle.isColliding(new IntPair(5, 5)));
        assertFalse(obstacle.isColliding(new IntPair(6, 6)));
    }
}