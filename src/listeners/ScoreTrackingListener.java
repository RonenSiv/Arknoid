// 318211463 Ronen Sivak
package listeners;

import mechanics.Counter;
import objects.Block;
import objects.Paddle;
import objects.Ball;

/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;


    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }

    @Override
    public void hitEvent(Paddle beingHit, Ball hitter) {

    }

    /**
     * Gets current score.
     *
     * @return the current score
     */
    public Counter getCurrentScore() {
        return this.currentScore;
    }
}
