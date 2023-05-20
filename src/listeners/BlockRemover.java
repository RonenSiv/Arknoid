// 318211463 Ronen Sivak
package listeners;

import mechanics.Counter;
import level.GameLevel;
import objects.Block;
import objects.Paddle;
import physics.Point;
import physics.Velocity;
import objects.Ball;

import java.awt.Color;
import java.util.Random;

/**
 * The type Block remover.
 */
public class BlockRemover implements HitListener {
    private final GameLevel game;
    private final Counter remainingBlocks;
    private Counter bonus;

    /**
     * Instantiates a new Block remover.
     *
     * @param game          the game
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
        this.bonus = new Counter();
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isRemovable()) {
            if (beingHit.getHitPoints() > 1) {
                beingHit.setHitPoints(beingHit.getHitPoints() - 1);
                beingHit.setColor(beingHit.getColor().darker());
            } else {
                Random rand = new Random();
                int percentage = game.getBonusPercentage();
                if (rand.nextInt(100) < percentage || this.bonus.getValue() == 100 - percentage) {
                    randomEffect(beingHit);
                } else {
                    this.bonus.increase(1);
                }
                beingHit.removeFromGame(this.game);
                beingHit.removeHitListener(this);
                this.remainingBlocks.decrease(1);
            }
        }
    }

    @Override
    public void hitEvent(Paddle beingHit, Ball hitter) {
        // do nothing
    }

    /**
     * Random effect.
     *
     * @param beingHit the block that being hit
     */
    public void randomEffect(Block beingHit) {
        this.bonus.reset();
        Random rand = new Random();
        Ball newBall = new Ball((int) beingHit.getRect().getCenter().getX(),
                (int) beingHit.getRect().getCenter().getY(), 15, Color.YELLOW);
        if (newBall.getX() + 2 * newBall.getSize() >= game.getWidth()) {
            newBall.setCenter(new Point(newBall.getX() - (2 * newBall.getSize()), newBall.getY()));
        } else if (newBall.getX() - 2 * newBall.getSize() <= 0) {
            newBall.setCenter(new Point(newBall.getX() + (2 * newBall.getSize()), newBall.getY()));
        }
        newBall.setVelocity(new Velocity(0, 5));
        newBall.setIsBonus(true);
        int random = rand.nextInt(100);
        if (random < 20) {
            newBall.setBallType(Ball.BallBonusType.PLUS);
            newBall.setColor(Color.GREEN);
        } else if (random <= 75) {
            newBall.setBallType(Ball.BallBonusType.DOUBLE);
        } else if (random <= 105) {
            newBall.setBallType(Ball.BallBonusType.PADDLE);
            newBall.setColor(Color.ORANGE);
//        } else {
//            newBall.setBallType(Ball.BallBonusType.DEATH);
//            newBall.setColor(Color.RED);
//        }
        }
        this.game.addBall(newBall, newBall.getVelocity());
    }
}