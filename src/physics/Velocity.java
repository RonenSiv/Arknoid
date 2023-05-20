// 318211463 Ronen Sivak
package physics;

/**
 * The type Velocity.
 *
 * @author Ronen Sivak
 */
public class Velocity {
    private double dx, dy;
    private Line trajectory;

    /**
     * Instantiates a new Velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Instantiates a new Velocity From angle and speed.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = -Math.sin(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Gets dx.
     *
     * @return the dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets dy.
     *
     * @return the dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the p
     * @return the new point
     */
    public Point applyToPoint(Point p) {
        Point point = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return point;
    }

    /**
     * Gets trajectory.
     *
     * @return the trajectory
     */
    public Line getTrajectory() {
        return this.trajectory;
    }

    /**
     * Sets trajectory.
     *
     * @param trajectory the trajectory
     */
    public void setTrajectory(Line trajectory) {
        this.trajectory = trajectory;
    }

    /**
     * Gets angle.
     *
     * @return the angle
     */
    public double getAngle() {
        return Math.atan2(this.dy, this.dx);
    }

    /**
     * Sets velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Sets velocity.
     *
     * @param v the v
     */
    public void setVelocity(Velocity v) {
        this.dx = v.dx;
        this.dy = v.dy;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(double speed) {
        double angle = getAngle();
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }

    /**
     * Sets angle.
     *
     * @param angle the angle
     */
    public void setAngle(double angle) {
        double speed = getSpeed();
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = -Math.sin(Math.toRadians(angle)) * speed;
    }
}