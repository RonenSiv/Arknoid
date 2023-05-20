// 318211463 Ronen Sivak
package objects;

import biuoop.DrawSurface;
import level.LevelInformation;
import level.GameLevel;
import mechanics.GameEnvironment;
import models.BallModel;
import models.Sprite;
import physics.Velocity;
import physics.Point;
import physics.Line;
import physics.CollisionInfo;
import physics.Collidable;
import sound.Sound;

/**
 * The type Ball.
 *
 * @author Ronen Sivak
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment environment;
    private Line xAxisLine;
    private Line yAxisLine;

    private boolean isBonus = false;
    private LevelInformation levelInformation;

    /**
     * The enum Ball bonus type.
     */
    public enum BallBonusType {
        /**
         * Normal ball bonus type.
         */
        NORMAL,
        /**
         * Double ball bonus type.
         */
        DOUBLE,
        /**
         * Plus ball bonus type.
         */
        PLUS,
        /**
         * Paddle ball bonus type.
         */
        PADDLE,
        /**
         * Death ball bonus type.
         */
        DEATH;
    }

    private BallBonusType ballType = BallBonusType.NORMAL;

    /**
     * Instantiates a new Ball.
     *
     * @param center the center
     * @param r      the r
     * @param color  the color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        setRad(r);
        this.color = color;
    }

    /**
     * Instantiates a new Ball.
     *
     * @param x     the x
     * @param y     the y
     * @param r     the r
     * @param color the color
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        setRad(r);
        this.color = color;
    }

    /**
     * Gets x value.
     *
     * @return the x value
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Gets y value.
     *
     * @return the y value
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Gets ball radius.
     *
     * @return the radius
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Gets ball velocity.
     *
     * @return the ball velocity
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * Gets ball center.
     *
     * @return the ball center
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Gets ball color.
     *
     * @return the ball color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Sets ball color.
     *
     * @param color the chosen color
     */
    public void setColor(java.awt.Color color) {
        if (color == null) {
            System.out.println("invalid color");
            return;
        }
        this.color = color;
    }

    /**
     * Sets ball velocity.
     *
     * @param v the v
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
        setBallTrajectory();
    }

    /**
     * Sets ball velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
        setBallTrajectory();
    }

    /**
     * Sets valid radius.
     *
     * @param r the valid radius
     */
    public void setRad(int r) {
        this.r = Math.max(1, r);
    }

    @Override
    public void drawOn(DrawSurface surface) {
        int level = -1;
        if (levelInformation != null) {
            level = levelInformation.getLevel();
        }
        BallModel ballModel = new BallModel(level, this);
        ballModel.drawOn(surface);
    }

    /**
     * Check collision boolean.
     *
     * @param collisionInfo the collision info
     * @return true if there's a collision false otherwise
     */
    public boolean checkCollision(CollisionInfo collisionInfo) {
        if (collisionInfo == null) {
            return false;
        }
        Collidable block = collisionInfo.collisionObject();
        if (!isBonus) {
            this.center = changeCollisionCenter(collisionInfo);
            this.vel = block.hit(this, collisionInfo.collisionPoint(), this.vel);
            setBallTrajectory();
        } else {
            block.hit(this, collisionInfo.collisionPoint(), this.vel);
            this.center = this.vel.applyToPoint(this.center);
        }
        return true;
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    private void createAxis() {
        if (this.vel.getDx() <= 0 && this.vel.getDy() <= 0) {
            this.xAxisLine = new Line(this.center.getX() + this.r, this.center.getY(),
                    this.center.getX() - this.r, this.center.getY());
            this.yAxisLine = new Line(this.center.getX(), this.center.getY() + this.r,
                    this.center.getX(), this.center.getY() - this.r);
        } else if (this.vel.getDx() <= 0 && this.vel.getDy() >= 0) {
            this.xAxisLine = new Line(this.center.getX() + this.r, this.center.getY(),
                    this.center.getX() - this.r, this.center.getY());
            this.yAxisLine = new Line(this.center.getX(), this.center.getY() - this.r,
                    this.center.getX(), this.center.getY() + this.r);
        } else if (this.vel.getDx() >= 0 && this.vel.getDy() <= 0) {
            this.xAxisLine = new Line(this.center.getX() - this.r, this.center.getY(),
                    this.center.getX() + this.r, this.center.getY());
            this.yAxisLine = new Line(this.center.getX(), this.center.getY() + this.r,
                    this.center.getX(), this.center.getY() - this.r);
        } else {
            this.xAxisLine = new Line(this.center.getX() - this.r, this.center.getY(),
                    this.center.getX() + this.r, this.center.getY());
            this.yAxisLine = new Line(this.center.getX(), this.center.getY() - this.r,
                    this.center.getX(), this.center.getY() + this.r);
        }
    }

    /**
     * Add Ball to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        this.setLevelInformation(g.getLevelInformation());
    }

    /**
     * Move the ball one step.
     */
    public void moveOneStep() {
        if (this.vel != null) {
            checkAllCollisions();
            setBallTrajectory();
        }
    }

    /**
     * Check all collisions with given lines.
     */
    private void checkAllCollisions() {
        Line trajectory = this.vel.getTrajectory();
        setBallTrajectory();
        Line[] collisionlines = {trajectory, this.yAxisLine, this.xAxisLine};
        for (Line line : collisionlines) {
            CollisionInfo collisionInfo = this.environment.getClosestCollision(line);
            if (checkCollision(collisionInfo)) {
                Sound hitSound = new Sound("src/sound/hit.wav");
                hitSound.play();
                return;
            }
        }
        this.center = this.vel.applyToPoint(this.center);
    }

    /**
     * set the trajectory of the ball, create a vertical line for the trajectory and update the line Axes.
     */
    private void setBallTrajectory() {
        if (this.vel != null) {
            createAxis();
            Point newPoint = new Point(this.center.getX() + getVelocity().getDx(),
                    this.center.getY() + getVelocity().getDy());

            Line trajectory = new Line(getX(), getY(), newPoint.getX(), newPoint.getY());
            double length = trajectory.length();
            if (length <= this.r) {
                double angle = this.vel.getAngle();
                newPoint = new Point(this.center.getX() + this.r * Math.cos(angle),
                        this.center.getY() + this.r * Math.sin(angle));

                trajectory = new Line(getX(), getY(), newPoint.getX(), newPoint.getY());
            }
            trajectory = trajectory.makeTrajectoryLine(this);
            this.vel.setTrajectory(trajectory);
        }
    }

    /**
     * Sets game environment.
     *
     * @param environment the environment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Change the center of a ball according to the collision point.
     *
     * @param collisionInfo the collision info
     * @return the new center
     */
    private Point changeCollisionCenter(CollisionInfo collisionInfo) {
        Collidable block = collisionInfo.collisionObject();
        Point changePoint = collisionInfo.collisionPoint();
        if (block.getCollisionRectangle().getSides()[0].pointOnLine(collisionInfo.collisionPoint())) {
            changePoint = new Point(changePoint.getX() - this.r - 1, this.center.getY());
        } else if (block.getCollisionRectangle().getSides()[1].pointOnLine(collisionInfo.collisionPoint())) {
            changePoint = new Point(this.center.getX(), changePoint.getY() - this.r - 1);
        } else if (block.getCollisionRectangle().getSides()[2].pointOnLine(collisionInfo.collisionPoint())) {
            changePoint = new Point(changePoint.getX() + this.r + 1, this.center.getY());
        } else if (block.getCollisionRectangle().getSides()[3].pointOnLine(collisionInfo.collisionPoint())) {
            changePoint = new Point(this.center.getX(), changePoint.getY() + this.r + 1);
        }
        return changePoint;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(int size) {
        this.r = size;
    }

    /**
     * Sets is bonus.
     *
     * @param bonus the bonus
     */
    public void setIsBonus(boolean bonus) {
        this.isBonus = bonus;
    }


    /**
     * Sets center.
     *
     * @param center the center
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets ball type.
     *
     * @param ballType the ball type
     */
    public void setBallType(BallBonusType ballType) {
        this.ballType = ballType;
    }

    /**
     * Gets ball type.
     *
     * @return the ball type
     */
    public BallBonusType getType() {
        return this.ballType;
    }

    /**
     * Gets is bonus.
     *
     * @return the is bonus
     */
    public boolean getIsBonus() {
        return this.isBonus;
    }

    /**
     * Remove from game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Sets level information.
     *
     * @param levelInformation the level information
     */
    public void setLevelInformation(LevelInformation levelInformation) {
        this.levelInformation = levelInformation;
    }
}