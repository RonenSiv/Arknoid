// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;
import mechanics.Counter;
import level.GameLevel;
import mechanics.Timer;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter counter;
    /**
     * The Timer.
     */
    private Timer timer;

    /**
     * Instantiates a new Score indicator.
     *
     * @param counter the counter
     */
    public ScoreIndicator(Counter counter) {
        this.counter = counter;
        timer = new Timer(System.currentTimeMillis(), System.currentTimeMillis() + 3500);
    }


    @Override
    public void drawOn(DrawSurface d) {

    }

    @Override
    public void timePassed() {
        timer.timePassed();
    }

    /**
     * Add score.
     *
     * @param score the score
     */
    public void addScore(int score) {
        this.counter.increase(score);
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Add to game.
     *
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Remove from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return counter.getValue();
    }
}
