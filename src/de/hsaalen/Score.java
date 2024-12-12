package de.hsaalen;

public class Score {
    private int currentScore = 0;
    
    public void addPoints(int points) {
        currentScore += points;
    }
    
    public void reset() {
        currentScore = 0;
    }
    
    public int getCurrentScore() {
        return currentScore;
    }
}