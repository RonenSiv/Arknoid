// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;
import objects.Paddle;
import objects.Rectangle;

import java.awt.Color;

/**
 * The type Paddle model.
 */
public class PaddleModel implements Sprite {
    private final Rectangle rect;
    private final int level;

    /**
     * Instantiates a new Paddle model.
     *
     * @param level  the level
     * @param paddle the paddle
     */
    public PaddleModel(int level, Paddle paddle) {
        this.level = level;
        this.rect = paddle.getCollisionRectangle();
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.level == 1) {
            paddleSkin1(d);
        } else if (this.level == 2) {
            paddleSkin2(d);
        } else if (this.level == 3) {
            paddleSkin3(d);
        } else if (this.level == 4) {
            paddleSkin4(d);
        }
    }

    @Override
    public void timePassed() {

    }

    private void paddleSkin1(DrawSurface d) {
        d.setColor(new Color(250, 85, 18));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        d.setColor(new Color(255, 117, 60));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (3 * this.rect.getWidth() / 4), (int) this.rect.getHeight());
        d.setColor(new Color(255, 187, 60));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (2 * this.rect.getWidth() / 4), (int) this.rect.getHeight());
        d.setColor(new Color(255, 239, 60));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (this.rect.getWidth() / 4), (int) this.rect.getHeight());
    }

    private void paddleSkin2(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rect.getUpperLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth() - (int) this.rect.getHeight(), (int) this.rect.getHeight());
        d.setColor(Color.WHITE);
        d.fillCircle((int) this.rect.getLowerLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.fillCircle((int) this.rect.getLowerRight().getX() - (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.setColor(Color.BLACK);
        d.drawCircle((int) this.rect.getLowerLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.drawCircle((int) this.rect.getLowerRight().getX() - (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.setColor(Color.WHITE);
        d.fillRectangle((int) this.rect.getUpperLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth() - (int) this.rect.getHeight(), (int) this.rect.getHeight());
    }

    private void paddleSkin3(DrawSurface d) {
        d.setColor(new Color(247, 233, 72));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        d.setColor(new Color(255, 128, 139));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (3 * this.rect.getWidth() / 4), (int) this.rect.getHeight());
        d.setColor(new Color(163, 211, 195));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (2 * this.rect.getWidth() / 4), (int) this.rect.getHeight());
        d.setColor(new Color(167, 33, 14));
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) (this.rect.getWidth() / 4), (int) this.rect.getHeight());
    }

    private void paddleSkin4(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rect.getUpperLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth() - (int) this.rect.getHeight(), (int) this.rect.getHeight());
        d.setColor(new Color(10, 171, 1));
        d.fillCircle((int) this.rect.getLowerLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.fillCircle((int) this.rect.getLowerRight().getX() - (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.setColor(Color.BLACK);
        d.drawCircle((int) this.rect.getLowerLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.drawCircle((int) this.rect.getLowerRight().getX() - (int) this.rect.getHeight() / 2,
                (int) this.rect.getCenter().getY(),
                (int) (this.rect.getHeight() / 2));
        d.setColor(new Color(10, 171, 1));
        d.fillRectangle((int) this.rect.getUpperLeft().getX() + (int) this.rect.getHeight() / 2,
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth() - (int) this.rect.getHeight(), (int) this.rect.getHeight());
    }
}
