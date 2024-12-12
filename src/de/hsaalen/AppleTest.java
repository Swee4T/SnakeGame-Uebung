package de.hsaalen;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppleTest {
    @Test
    public void testAppleCollision() {
        Apple apple = new Apple(30, 30, false);  // normaler Apfel
        assertTrue(apple.isColliding(apple.getPosition()));
        assertFalse(apple.isColliding(new IntPair(
            apple.getPosition().x + 1, 
            apple.getPosition().y + 1
        )));
    }
}