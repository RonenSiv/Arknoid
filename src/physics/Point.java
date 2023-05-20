// 318211463 Ronen Sivak
package physics;

/**
 * The type Point.
 *
 * @author Ronen Sivak
 */
public class Point {
    private double x;
    private double y;
    private static final double EPSILON = Math.pow(10, -6);

    /**
     * Instantiates for a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Distance between two points.
     *
     * @param other the other line
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2)
                + Math.pow(this.y - other.getY(), 2));
    }

    /**
     * Check equal points.
     *
     * @param other the other
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return (Math.abs(this.x - other.x) <= EPSILON && Math.abs(this.y - other.y) <= EPSILON);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets x.
     *
     * @param x the x we want to set to the point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y we want to set to the point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Move.
     *
     * @param x the number of x movement
     * @param y the number of y movement
     */
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Gets point from a slope, distance and a given starting point.
     *
     * @param slope    the slope
     * @param x        the x
     * @param y        the y
     * @param distance the distance
     * @return the point
     */
    public Point getPointFromSlopeGivenStart(double slope, double x, double y, double distance) {
        double newX = (distance * Math.cos(Math.atan(slope))) + x;
        double newY = (distance * Math.sin(Math.atan(slope))) + y;
        return new Point(newX, newY);
    }

    /**
     * Gets point from a slope, distance and a given ending point.
     *
     * @param slope    the slope
     * @param x        the x
     * @param y        the y
     * @param distance the distance
     * @return the point
     */
    public Point getPointFromSlopeGivenEnd(double slope, double x, double y, double distance) {
        double newX = x - (distance * Math.cos(Math.atan(slope)));
        double newY = y - (distance * Math.sin(Math.atan(slope)));
        return new Point(newX, newY);
    }

    /**
     * Sets new values.
     *
     * @param x the x
     * @param y the y
     */
    public void setNewValues(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets max point.
     *
     * @param other the other
     * @return the max point
     */
    public Point getMaxPoint(Point other) {
        if (this.x == other.x) {
            if (this.y > other.y) {
                return this;
            }
            return other;
        }
        if (this.x > other.x) {
            return this;
        }
        return other;
    }

    /**
     * Gets min point.
     *
     * @param other the other
     * @return the min point
     */
    public Point getMinPoint(Point other) {
        if (this.x == other.x) {
            if (this.y < other.y) {
                return this;
            }
            return other;
        }
        if (this.x < other.x) {
            return this;
        }
        return other;
    }
}
