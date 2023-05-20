// 318211463 Ronen Sivak
package objects;

import physics.Point;
import physics.Line;

/**
 * The type Rectangle.
 *
 * @author Ronen Sivak
 */
public class Rectangle {
    private Point upperLeft;
    private Point lowerRight;
    private double width;
    private double height;

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft  the upper left
     * @param lowerRight the lower right
     */
    public Rectangle(Point upperLeft, Point lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        this.width = lowerRight.getX() - upperLeft.getX();
        this.height = lowerRight.getY() - upperLeft.getY();
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param midPoint the mid point
     * @param width    the width
     * @param height   the height
     * @param scale    the scale
     */
    public Rectangle(Point midPoint, double width, double height, int scale) {
        this.upperLeft = new Point(midPoint.getX() - (width / 2) * scale, midPoint.getY() - (height / 2) * scale);
        this.lowerRight = new Point(midPoint.getX() + (width / 2) * scale, midPoint.getY() + (height / 2) * scale);
        this.width = width;
        this.height = height;
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Rectangle(double x, double y, double width, double height) {
        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;
        this.lowerRight = new Point(x + width, y + height);
    }

    /**
     * Intersection points of a rectangle with a given line.
     *
     * @param line the line we want to intersect with
     * @return a (possibly empty) List of intersection points with the specified line
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> points = new java.util.ArrayList<>();
        Line[] lines = this.getSides();
        for (Line l : lines) {
            Point intersection = l.intersectionWith(line);
            if (intersection != null) {
                points.add(intersection);
            }
        }
        return points;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets upper left point.
     *
     * @return the upper left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Gets lower right point.
     *
     * @return the lower right point
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }

    /**
     * Gets upper right point.
     *
     * @return the upper right point
     */
    public Point getUpperRight() {
        return new Point(this.lowerRight.getX(), this.upperLeft.getY());
    }

    /**
     * Gets lower left point.
     *
     * @return the lower left point
     */
    public Point getLowerLeft() {
        return new Point(this.upperLeft.getX(), this.lowerRight.getY());
    }

    /**
     * Gets center point.
     *
     * @return the center point
     */
    public Point getCenter() {
        return new Point(this.upperLeft.getX() + this.width / 2, this.upperLeft.getY() + this.height / 2);
    }

    /**
     * Get lines of the rectangle.
     *
     * @return the lines in a Line array
     */
    public Line[] getSides() {
        Line[] lines = new Line[4];
        lines[0] = new Line(this.upperLeft, new Point(this.upperLeft.getX(), this.lowerRight.getY())); // left
        lines[1] = new Line(this.upperLeft, new Point(this.lowerRight.getX(), this.upperLeft.getY())); // top
        lines[2] = new Line(this.lowerRight, new Point(this.lowerRight.getX(), this.upperLeft.getY())); // right
        lines[3] = new Line(this.lowerRight, new Point(this.upperLeft.getX(), this.lowerRight.getY())); // bottom
        return lines;
    }

    /**
     * chane the r.
     *
     * @param x the x movement
     * @param y the y movement
     */
    public void move(int x, int y) {
        this.upperLeft.move(x, y);
        this.lowerRight.move(x, y);
    }

    /**
     * Contains boolean.
     *
     * @param collisionPoint the collision point
     * @return the boolean
     */
    public boolean contains(Point collisionPoint) {
        return collisionPoint.getX() >= this.upperLeft.getX()
                && collisionPoint.getX() <= this.lowerRight.getX()
                && collisionPoint.getY() >= this.upperLeft.getY()
                && collisionPoint.getY() <= this.lowerRight.getY();
    }
}
