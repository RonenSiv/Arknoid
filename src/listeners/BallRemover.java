// 318211463 Ronen Sivak
package listeners;

import mechanics.Counter;
import level.GameLevel;
import objects.Block;
import objects.Paddle;
import physics.Velocity;
import objects.Ball;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game           the game
     * @param remainingBalls the remaining balls
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        if (!hitter.getIsBonus()) {
            remainingBalls.decrease(1);
        }
        game.getBalls().remove(hitter);
    }

    @Override
    public void hitEvent(Paddle beingHit, Ball hitter) {
        if (hitter.getIsBonus()) {
            if (game.getBalls() != null) {
//                List<Ball> ballsCopy = new ArrayList<>(game.getBalls());
//                for (Ball ball : ballsCopy) {
//                    if(ball.getType() == Ball.BallType.DOUBLE) {
//                        continue;
//                    }
//                    Random rand = new Random();
//                    Ball newBall = new Ball(ball.getX(), ball.getY(), game.getBallRad(), ball.getColor());
//                    newBall.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(180), ball.getVelocity().getSpeed()));
//                    this.game.addBall(newBall);
//                }
                setBonus(hitter);
            }
            hitter.removeFromGame(game);
            game.getBalls().remove(hitter);
        }
    }

    /**
     * Sets bonus.
     *
     * @param hitter the hitter
     */
    public void setBonus(Ball hitter) {
        if (game.getBalls() != null) {
            List<Ball> ballsCopy = new ArrayList<>(game.getBalls());
            if (hitter.getType() == Ball.BallBonusType.DOUBLE && ballsCopy.size() < 500) {
                for (Ball ball : ballsCopy) {
                    if (ball.getIsBonus()) {
                        continue;
                    }
                    Random rand = new Random();
                    Ball newBall = new Ball(ball.getX(), ball.getY(), game.getBallRad(), ball.getColor());
                    newBall.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(180), ball.getVelocity().getSpeed()));
                    this.game.addBall(newBall, newBall.getVelocity());
                }
            } else if (hitter.getType() == Ball.BallBonusType.DEATH) {
                for (Ball ball : ballsCopy) {
                    ball.removeFromGame(game);
                    game.getBalls().remove(ball);
                    remainingBalls.decrease(1);
                    if (remainingBalls.getValue() == 0) {
                        break;
                    }
                }
            } else if (hitter.getType() == Ball.BallBonusType.PADDLE) {
                this.game.getPaddle().setIsEnlarged(true);
            } else if (hitter.getType() == Ball.BallBonusType.PLUS) {
                Random rand = new Random();
                for (int i = 0; i < 3; i++) {
                    Ball newBall = new Ball((int) game.getPaddle().getCollisionRectangle().getCenter().getX(),
                            (int) (game.getPaddle().getCollisionRectangle().getUpperLeft().getY()
                                    - 2 * game.getBallRad()),
                            game.getBallRad(), Color.WHITE);
                    newBall.setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(180), game.getBallSpeed()));
                    this.game.addBall(newBall, newBall.getVelocity());
                }
            }
        }
    }
}
