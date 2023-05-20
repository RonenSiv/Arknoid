// 318211463 Ronen Sivak
package objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import level.LevelInformation;
import listeners.HitListener;
import listeners.HitNotifier;
import level.GameLevel;
import models.PaddleModel;
import models.Sprite;
import mechanics.Timer;
import physics.Collidable;
import physics.Line;
import physics.Point;
import physics.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Paddle.
 *
 * @author Ronen Sivak
 */
public class Paddle implements Collidable, Sprite, HitNotifier {
    private biuoop.KeyboardSensor keyboard;
    //    private GameLevel game;
    private Rectangle rect;
    private int speed = 5;
    private int leftBorder;
    private int rightBorder;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean isEnlarged = false;
    private Timer timer;
    private LevelInformation levelInformation;
    private double progressTime = 0;

    /**
     * Instantiates a new Paddle.
     *
     * @param levelInformation the level information
     * @param ks               the ks
     * @param rect             the rect
     * @param leftBorder       the left border
     * @param rightBorder      the right border
     */
    public Paddle(LevelInformation levelInformation, KeyboardSensor ks,
                  Rectangle rect, int leftBorder, int rightBorder) {
        this.keyboard = ks;
        this.rect = rect;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.levelInformation = levelInformation;
    }

    /**
     * Move paddle to the left.
     */
    public void moveLeft() {
        if (this.rect.getUpperLeft().getX() > leftBorder) {
            int moveSpeed = this.speed;
            this.rect.move(-moveSpeed, 0);
            if (this.rect.getUpperLeft().getX() < leftBorder) {
                this.rect.move((int) (leftBorder - this.rect.getUpperLeft().getX()), 0);
                this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
            }
        }
    }

    /**
     * Move paddle to the right.
     */
    public void moveRight() {
        if (this.rect.getUpperLeft().getX() < rightBorder - this.rect.getWidth()) {
            int moveSpeed = this.speed;
            this.rect.move(moveSpeed, 0);
            if (this.rect.getLowerRight().getX() > rightBorder) {
                this.rect.move((int) (rightBorder - this.rect.getLowerRight().getX()), 0);
                this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
            }
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        PaddleModel paddleModel = new PaddleModel(levelInformation.getLevel(), this);
        paddleModel.drawOn(d);
        if (isEnlarged) {
            double time = (Math.abs(timer.getEndTime() - System.currentTimeMillis()));
            d.setColor(Color.BLACK);
            d.fillRectangle((int) this.rect.getUpperLeft().getX(),
                    (int) (this.rect.getLowerLeft().getY() + 1),
                    (int) ((this.rect.getWidth() / this.progressTime) * time), 5);
        }
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY) || this.keyboard.isPressed("a")
                || this.keyboard.isPressed("A")) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY) || this.keyboard.isPressed("d")
                || this.keyboard.isPressed("D")) {
            this.moveRight();
        }
        if (isEnlarged) {
            if (!timer.isTimePassed()) {
                timer.timePassed();
                if (timer.toSecondsLong() <= 1 && timer.lastTimePassed(100)) {
                    if (this.rect.getWidth() <= this.levelInformation.paddleWidth()) {
                        this.rect = new Rectangle(this.rect.getUpperLeft().getX() - this.levelInformation.paddleWidth(),
                                this.rect.getUpperLeft().getY(),
                                this.levelInformation.paddleWidth() * 3, this.rect.getHeight());
                    } else {
                        this.rect = new Rectangle(this.rect.getUpperLeft().getX() + this.levelInformation.paddleWidth(),
                                this.rect.getUpperLeft().getY(),
                                this.levelInformation.paddleWidth(), this.levelInformation.paddleHeight());
                    }
                    if (this.rect.getUpperLeft().getX() < leftBorder) {
                        this.rect.move((int) (leftBorder - this.rect.getUpperLeft().getX()), 0);
                        this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
                    }
                    if (this.rect.getUpperRight().getX() > rightBorder) {
                        this.rect.move((int) (rightBorder - this.rect.getUpperRight().getX()), 0);
                        this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
                    }
                }
            } else {
                isEnlarged = false;
                timer.reset();
                if (this.rect.getWidth() > this.levelInformation.paddleWidth()) {
                    this.rect = new Rectangle(this.rect.getUpperLeft().getX() + this.levelInformation.paddleWidth(),
                            this.rect.getUpperLeft().getY(),
                            this.levelInformation.paddleWidth(), this.levelInformation.paddleHeight());
                }
            }
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Add paddle to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
//        balls = g.getBalls();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
//        balls = this.game.getBalls();
        notifyHit(hitter);
        Line[] lines = this.rect.getSides();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // change the velocity based on the collision point (5 regions)
        if (lines[1].pointOnLine(collisionPoint)) {
            double fullLength = this.rect.getWidth();
            double collisionLength = collisionPoint.getX() - this.rect.getUpperLeft().getX();
            int scale = (int) (collisionLength * 5 / fullLength);
            switch (scale) {
                case 0:
                    return Velocity.fromAngleAndSpeed(120, currentVelocity.getSpeed());
                case 1:
                    return Velocity.fromAngleAndSpeed(150, currentVelocity.getSpeed());
                case 2:
                    return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                case 3:
                    return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
                case 4:
                    return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
                default:
                    return currentVelocity;
            }
        } else if (lines[0].pointOnLine(collisionPoint) || lines[2].pointOnLine(collisionPoint)) {
            dx = -dx;
            hitter.setCenter(new Point(hitter.getX(), this.rect.getLowerLeft().getY() + 1));
        } else if (lines[3].pointOnLine(collisionPoint)) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Sets color.
     *
     * @param c the c
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Sets is enlarged.
     *
     * @param isEnlarged the is enlarged
     */
    public void setIsEnlarged(boolean isEnlarged) {
        if (isEnlarged) {
            timer = new Timer(System.currentTimeMillis(), System.currentTimeMillis() + 8000);
            if (this.rect.getWidth() == this.levelInformation.paddleWidth()) {
                this.rect = new Rectangle(this.rect.getUpperLeft().getX() - this.rect.getWidth(),
                        this.rect.getUpperLeft().getY(),
                        this.rect.getWidth() * 3, this.rect.getHeight());
                if (this.rect.getUpperLeft().getX() < leftBorder) {
                    this.rect.move((int) (leftBorder - this.rect.getUpperLeft().getX()), 0);
                    this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
                }
                if (this.rect.getUpperRight().getX() > rightBorder) {
                    this.rect.move((int) (rightBorder - this.rect.getUpperRight().getX()), 0);
                    this.rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getLowerRight());
                }
            }
        }
        this.isEnlarged = isEnlarged;
        this.progressTime = Math.abs(timer.getEndTime() - System.currentTimeMillis());
    }
}
