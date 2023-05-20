// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;
import objects.Ball;
import java.awt.Color;


/**
 * The type Ball model.
 */
public class BallModel extends Models {
    private Ball ball;
    private int level;

    /**
     * Instantiates a new Ball model.
     *
     * @param level the level
     * @param ball  the ball
     */
    public BallModel(int level, Ball ball) {
        this.level = level;
        this.ball = ball;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets ball.
     *
     * @return the ball
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Sets ball.
     *
     * @param ball the ball
     */
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (level == 1) {
            ballSkin1(d);
        } else if (level == 2) {
            ballSkin2(d);
        } else if (level == 3) {
            ballSkin3(d);
        } else if (level == 4) {
            ballSkin4(d);
        } else {
            drawBall(d, ball.getColor());
        }
    }

    private void ballSkin1(DrawSurface d) {
        if (ball.getIsBonus()) {
            drawOval(d, ball.getColor());
            d.setColor(Color.BLACK);
            return;
        }
        d.setColor(new Color(220, 71, 231));
        d.fillCircle(ball.getX(), ball.getY(), ball.getSize());
        d.setColor(new Color(252, 176, 62));
        d.fillCircle(ball.getX(), ball.getY(), ball.getSize() / 2);
        d.setColor(new Color(245, 228, 41));
        d.fillCircle(ball.getX(), ball.getY(), ball.getSize() / 4);
        d.setColor(Color.BLACK);
        d.drawCircle(ball.getX(), ball.getY(), ball.getSize());
    }

    private void ballSkin2(DrawSurface d) {
        if (ball.getIsBonus()) {
            drawOval(d, ball.getColor());
            d.setColor(Color.BLACK);
            return;
        }
        d.setColor(new Color(252, 192, 62));
        d.drawCircle(ball.getX(), ball.getY(), ball.getSize());
        d.setColor(new Color(252, 220, 62));
        d.drawCircle(ball.getX(), ball.getY(), ball.getSize() - 1);

    }

    private void ballSkin3(DrawSurface d) {
        if (ball.getIsBonus()) {
            drawOval(d, ball.getColor());
            d.setColor(Color.BLACK);
            return;
        }
        // legs
        d.setColor(new Color(227, 54, 226));
        d.fillRectangle(ball.getX(), ball.getY() + ball.getSize() / 3, 1, ball.getSize());
        d.fillRectangle(ball.getX() + ball.getSize() / 2, ball.getY() + ball.getSize() / 3 + 1, 1, ball.getSize());
        d.fillRectangle(ball.getX() + ball.getSize(), ball.getY() + ball.getSize() / 3 + 2, 1, ball.getSize());
        d.fillRectangle((int) (ball.getX() + 1.5 * ball.getSize()),
                ball.getY() + ball.getSize() / 3 + 1, 1, ball.getSize());
        d.fillRectangle(ball.getX() + 2 * ball.getSize(), ball.getY() + ball.getSize() / 3, 1, ball.getSize());
        // body
        d.fillOval(ball.getX(), (int) (ball.getY() - ball.getSize() / 1.5), ball.getSize() * 2,
                (int) (ball.getSize() * 1.5));
        d.setColor(Color.BLACK);
        d.drawOval(ball.getX(), (int) (ball.getY() - ball.getSize() / 1.5), ball.getSize() * 2,
                (int) (ball.getSize() * 1.5));

        d.setColor(new Color(255, 255, 255));
        d.fillCircle((int) (ball.getX() + ball.getSize() * 1.2), ball.getY() - ball.getSize() / 3, ball.getSize() / 3);
        d.setColor(new Color(253, 152, 252));
        d.fillRectangle((int) (ball.getX() + ball.getSize() * 1.2),
                ball.getY() - ball.getSize() / 3, ball.getSize() / 3, ball.getSize() / 3);
    }

    private void ballSkin4(DrawSurface d) {
        if (ball.getIsBonus()) {
            drawOval(d, ball.getColor());
            d.setColor(Color.BLACK);
            return;
        }
        drawBall(d, new Color(255, 153, 34));
        d.setColor(new Color(196, 29, 29));
        d.fillCircle(ball.getX(), ball.getY(), ball.getSize() / 3);
    }

    /**
     * Draws the ball.
     *
     * @param surface the draw surface
     * @param color   the color of the ball
     */
    private void drawBall(DrawSurface surface, Color color) {
        surface.setColor(color);
        surface.fillCircle((int) ball.getX(), (int) ball.getY(), ball.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) ball.getX(), (int) ball.getY(), ball.getSize());
    }

    /**
     * Draw oval.
     *
     * @param surface the surface
     * @param color   the color
     */
    public void drawOval(DrawSurface surface, Color color) {
        surface.setColor(color);
        surface.fillOval(ball.getX(), ball.getY(), ball.getSize() * 2, ball.getSize());
        surface.setColor(Color.BLACK);
        surface.drawOval(ball.getX(), ball.getY(), ball.getSize() * 2, ball.getSize());
        surface.setColor(Color.BLACK);
        if (ball.getType() == Ball.BallBonusType.DOUBLE) {
            surface.drawText(ball.getX() + ball.getSize() / 2 - ball.getSize() / 4, ball.getY() + ball.getSize(),
                    "2X", ball.getSize());
        } else if (ball.getType() == Ball.BallBonusType.PADDLE) {
            surface.drawText(ball.getX() + ball.getSize() / 2 - ball.getSize() / 4, ball.getY() + ball.getSize(),
                    "<->", ball.getSize());
        } else if (ball.getType() == Ball.BallBonusType.DEATH) {
            surface.drawText(ball.getX() + ball.getSize() / 2, ball.getY() + ball.getSize(), "X", ball.getSize());
        } else if (ball.getType() == Ball.BallBonusType.PLUS) {
            surface.drawText(ball.getX() + ball.getSize() / 2 - ball.getSize() / 4, ball.getY() + ball.getSize(),
                    "+3", ball.getSize());
        }
    }
}
