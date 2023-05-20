// 318211463 Ronen Sivak
package objects;

import biuoop.DrawSurface;
import listeners.HitListener;
import listeners.HitNotifier;
import level.GameLevel;
import models.Sprite;
import physics.Collidable;
import physics.Line;
import physics.Point;
import physics.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Block.
 *
 * @author Ronen Sivak
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private Color color = null;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean isRemovable = true;
    private boolean isDeath = false;
    private int hitPoints = 1;

    /**
     * Instantiates a new Block.
     *
     * @param rect the rect
     */
    public Block(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Instantiates a new Block.
     *
     * @param rect  the rect
     * @param color the color
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
    }

    /**
     * Instantiates a new Block.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param color  the color
     */
    public Block(int x, int y, int width, int height, Color color) {
        this.rect = new Rectangle(new Point(x, y), width, height);
        this.color = color;
    }

    /**
     * Instantiates a new Block.
     *
     * @param upperLeft  the upper left
     * @param lowerRight the lower right
     * @param color      the color
     */
    public Block(Point upperLeft, Point lowerRight, Color color) {
        this.rect = new Rectangle(upperLeft, lowerRight);
        this.color = color;
    }

    /**
     * Gets block rectangle.
     *
     * @return the rect
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Sets rectangle.
     *
     * @param rect the rect
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Add block to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                        (int) this.rect.getWidth(), (int) this.rect.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
        if (color != null) {
            d.setColor(color);
        } else {
            d.setColor(Color.BLACK);
        }
        d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());

    }

    @Override
    public void timePassed() {

    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line[] lines = this.rect.getSides();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        if (lines[1].pointOnLine(collisionPoint) || lines[3].pointOnLine(collisionPoint)) {
            dy = -dy;
        }
        if (lines[0].pointOnLine(collisionPoint) || lines[2].pointOnLine(collisionPoint)) {
            dx = -dx;
        }
        if (hitter.getType() == Ball.BallBonusType.NORMAL) {
            notifyHit(hitter);
        } else if (hitter.getIsBonus() && this.isDeath) {
            notifyHit(hitter);
        }
        return new Velocity(dx, dy);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Sets removable.
     *
     * @param removable the removable
     */
    public void setRemovable(boolean removable) {
        this.isRemovable = removable;
    }

    /**
     * Sets death.
     *
     * @param death the death
     */
    public void setDeath(boolean death) {
        this.isDeath = death;
    }

    /**
     * Is removable boolean.
     *
     * @return the boolean
     */
    public boolean isRemovable() {
        return this.isRemovable;
    }

    /**
     * Is death boolean.
     *
     * @return the boolean
     */
    public boolean isDeath() {
        return this.isDeath;
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Sets hit points.
     *
     * @param hitPoints the hit points
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Remove from game.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }
}
