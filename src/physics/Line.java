// 318211463 Ronen Sivak
package physics;

import objects.Rectangle;
import objects.Ball;

/**
 * The type Line.
 *
 * @author Ronen Sivak
 */
public class Line {
    private Point start, end;
    private double slope;
    private static final double EPSILON = Math.pow(10, -4);

    /**
     * Instantiates for a new Line.
     *
     * @param start the start point
     * @param end   the end point
     */
    public Line(Point start, Point end) {
        if (start.equals(end)) {
            return;
        }
        this.start = start;
        this.end = end;
        this.slope = slope();
    }

    /**
     * Instantiates a new Line.
     *
     * @param x1 the x value of point 1
     * @param y1 the y value of point 1
     * @param x2 the x value of point 2
     * @param y2 the y value of point 2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Get line length.
     *
     * @return the length of the line in double
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Get line middle point.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2,
                (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * Get line starting point.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Get line end point.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Check if two lines are intersecting.
     *
     * @param other the other line
     * @return true if intersecting, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.equals(other)) {
            return true;
        }
        double m1 = this.slope;
        double m2 = other.slope();

        double b1 = this.start.getY() - (m1 * this.start.getX());
        double b2 = other.start().getY() - (m2 * other.start().getX());

        if (m1 == m2 && b1 == b2) {
            if (other.pointOnLine(this.start)
                    || other.pointOnLine(this.end)) {
                return true;
            }
            return false;
        }

        if (intersectionWith(other) != null) {
            return true;
        }
        return false;
    }

    /**
     * Get the intersection point.
     *
     * @param other the other line
     * @return the intersection point if the lines intersected and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (equals(other)) {
            return null;
        }
        // get the slope of this line
        double m1 = this.slope;
        // get the slop of the other line
        double m2 = other.slope();

        double b1 = this.start.getY() - (m1 * this.start.getX());
        double b2 = other.start().getY() - (m2 * other.start().getX());
        // If slopes are equal then (m1 == m2)
        if (m1 == m2) {
            Point maxPointThis = this.start.getMaxPoint(this.end);
            Point minPointThis = this.start.getMinPoint(this.end);
            Point maxPointOther = other.start().getMaxPoint(other.end());
            Point minPointOther = other.start().getMinPoint(other.end());
            if (maxPointThis.equals(minPointOther)) {
                return maxPointThis;
            } else if (maxPointOther.equals(minPointThis)) {
                return minPointThis;
            } else {
                return null;
            }
        }
        double x = (b2 - b1) / (m1 - m2);
        double y = (m1 * x) + b1;
        if (pointOnLine(new Point(x, y))
                && other.pointOnLine((new Point(x, y)))) {
            return new Point(x, y);
        }
        return null;
    }

    /**
     * Check if two lines are equal boolean.
     *
     * @param other the other line
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start()) && this.end.equals(other.end())
                || this.start.equals(other.end()) && this.end.equals(other.start()));
    }

    /**
     * Get the slope of the line.
     *
     * @return the slope value (MAX VALUE OF INTEGER IF LINE IS vertical)
     */
    public double slope() {
        if (this.end.getX() == this.start.getX()) {
            return Integer.MAX_VALUE;
        }
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * Check if a point is on the line.
     *
     * @param p the point to check
     * @return true if the point is on the line, false otherwise
     */
    public boolean pointOnLine(Point p) {
        if (p.distance(this.start) + p.distance(this.end) + EPSILON >= this.start.distance(this.end)
                && p.distance(this.start) + p.distance(this.end) - EPSILON <= this.start.distance(this.end)) {
            return true;
        }
        return false;
//        double x = p.getX();
//        double y = p.getY();
//        double maxX = Math.max(this.start.getX(), this.end.getX());
//        double minX = Math.min(this.start.getX(), this.end.getX());
//
//        double maxY = Math.max(this.start.getY(), this.end.getY());
//        double minY = Math.min(this.start.getY(), this.end.getY());
//
//        boolean xCon = x >= minX - EPSILON && x <= maxX + EPSILON;
//        boolean yCon = y >= minY - EPSILON && y <= maxY + EPSILON;
//
//        return xCon && yCon;
    }

    /**
     * Closest intersection of rectangle to start of line point.
     *
     * @param rect the rect
     * @return the point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        if (isHorizontal() && isVertical()) {
            for (Line side : rect.getSides()) {
                if (isIntersecting(side)) {
                    return this.end;
                }
            }
        }
        Point closest = null;
        java.util.List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.size() == 0) {
            return null;
        }
        for (Point p : intersectionPoints) {
            if (closest == null) {
                closest = p;
            }
            if (p.distance(this.start) < closest.distance(this.start)) {
                closest = p;
            }
        }
        return closest;
    }

    /**
     * Make trajectory line line.
     *
     * @param ball the ball
     * @return the line
     */
    public Line makeTrajectoryLine(Ball ball) {
        if (ball.getCenter().distance(this.start) > ball.getCenter().distance(this.end)) {
            return new Line(this.end, this.start);
        }
        return this;
    }

    /**
     * Is vertical boolean.
     *
     * @return the boolean
     */
    public boolean isVertical() {
        return this.start.getX() == this.end.getX();
    }

    /**
     * Is horizontal boolean.
     *
     * @return the boolean
     */
    public boolean isHorizontal() {
        return this.start.getY() == this.end.getY();
    }
}
