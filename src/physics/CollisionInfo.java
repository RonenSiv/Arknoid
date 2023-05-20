// 318211463 Ronen Sivak
package physics;

/**
 * The type Collision info.
 *
 * @author Ronen Sivak
 */
public class CollisionInfo {
    /**
     * The Collision point.
     */
    private Point collisionPoint;
    /**
     * The Collision object.
     */
    private Collidable collisionObject;

    /**
     * Instantiates a new Collision info.
     *
     * @param p the p
     * @param c the c
     */
    public CollisionInfo(Point p, Collidable c) {
        this.collisionPoint = p;
        this.collisionObject = c;
    }

    /**
     * Collision point.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Collision object.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
