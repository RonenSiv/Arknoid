// 318211463 Ronen Sivak
package mechanics;

import physics.Collidable;
import physics.CollisionInfo;
import physics.Line;
import physics.Point;

/**
 * The type Game environment.
 *
 * @author Ronen Sivak
 */
public class GameEnvironment {
    private java.util.List<Collidable> collidables;

    /**
     * Instantiates a new Game environment.
     */
    public GameEnvironment() {
        this.collidables = new java.util.ArrayList<>();
    }

    /**
     * Add collidable to lis.
     *
     * @param c the c
     */
// add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Gets closest collision.
     *
     * @param trajectory the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double minDistance = Double.MAX_VALUE;
        Collidable closestCollidable = null;
        Point closestPoint = null;
        for (Collidable c : this.collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p == null) {
                continue;
            }
            if (closestPoint == null) {
                closestPoint = p;
            }
            if (trajectory.start().distance(p) < minDistance) {
                minDistance = trajectory.start().distance(p);
                closestCollidable = c;
                closestPoint = p;
            }
        }
        if (closestCollidable == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, closestCollidable);
    }

    /**
     * Gets collidables.
     *
     * @return the collidables
     */
    public java.util.List<Collidable> getCollidables() {
        return this.collidables;
    }
}
