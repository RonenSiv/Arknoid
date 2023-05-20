// 318211463 Ronen Sivak
package physics;

import objects.Ball;
import objects.Rectangle;

/**
 * The interface Collidable.
 *
 * @author Ronen Sivak
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return Return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with.
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param hitter the ball that hit us.
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}
