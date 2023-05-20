// 318211463 Ronen Sivak
package listeners;

import objects.Block;
import objects.Paddle;
import objects.Ball;

/**
 * The interface Hit listener.
 */
public interface HitListener {
    /**
     * Hit event.
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the block that being hit
     * @param hitter   the hitter ball
     */
    void hitEvent(Block beingHit, Ball hitter);

    /**
     * Hit event.
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the paddle that being hit
     * @param hitter   the hitter ball
     */
    void hitEvent(Paddle beingHit, Ball hitter);
}
