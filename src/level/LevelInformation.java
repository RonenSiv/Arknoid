package level;

import mechanics.Counter;
import models.Sprite;
import objects.Block;
import physics.Point;
import physics.Velocity;

import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Number of balls.
     *
     * @return the balls number
     */
    int numberOfBalls();

    /**
     * Initial ball velocities list.
     *
     * @return the list
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed.
     *
     * @return the int
     */
    int paddleSpeed();

    /**
     * Paddle width int.
     *
     * @return the speed
     */
    int paddleWidth();

    /**
     * Paddle height.
     *
     * @return the height
     */
    int paddleHeight();

    /**
     * Paddle initial position point.
     *
     * @return the point
     */
    Point paddleInitialPosition();

    /**
     * Level name string.
     *
     * @return the level name
     */
    String levelName();

    /**
     * Gets background.
     *
     * @return the background sprite
     */
    Sprite getBackground();

    /**
     * Blocks list.
     * The Blocks that make up this level, each block contains
     * @return the list of blocks
     */
    List<Block> blocks();

    /**
     * Number of blocks to remove.
     *
     * @return the number of blocks to remove
     */
    int numberOfBlocksToRemove();

    /**
     * Gets level.
     *
     * @return the level
     */
    int getLevel();

    /**
     * Ball radius.
     *
     * @return the ball radius
     */
    int ballRadius();

    /**
     * Ball initial position point.
     *
     * @return the point of the ball
     */
    Point ballInitialPosition();

    /**
     * Blocks counter.
     *
     * @return the counter of the blocks
     */
    Counter blocksCounter();

    /**
     * Balls counter.
     *
     * @return the counter of the balls
     */
    Counter ballsCounter();

    /**
     * Score counter.
     *
     * @return the counter of the score
     */
    Counter scoreCounter();
}
