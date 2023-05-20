// 318211463 Ronen Sivak
package level;

import mechanics.BlockGenerator;
import mechanics.Counter;
import models.LevelModel;
import models.Sprite;
import objects.Block;
import physics.Point;
import physics.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Level.
 */
public class Level implements LevelInformation {
    private int level;
    private static final int NUM_OF_BALLS = 1;
    private static final int PADDLE_WIDTH = 150;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_SPEED = 15;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BORDER_WIDTH = 1;
    private static final int BLOCK_WIDTH = 40;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BALL_RADIUS = 7;
    private static final int MIN_BALL_SPEED = 3;
    private static final int MAX_BALL_SPEED = 6;
    private List<Block> blocks;

    /**
     * Instantiates a new Level.
     *
     * @param level the level
     */
    public Level(int level) {
        this.level = level;
    }

    @Override
    public int numberOfBalls() {
        if (this.level == 1) {
            return NUM_OF_BALLS;
        } else if (this.level == 2) {
            return NUM_OF_BALLS * 10;
        } else if (this.level == 3) {
            return NUM_OF_BALLS * 2;
        } else if (this.level == 4) {
            return NUM_OF_BALLS * 3;
        } else {
            return NUM_OF_BALLS;
        }
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        for (int i = 0; i < numberOfBalls(); i++) {
            if (level == 1) {
                velocities.add(Velocity.fromAngleAndSpeed(90,
                        Math.min(Math.max(100 / BALL_RADIUS, MIN_BALL_SPEED), MAX_BALL_SPEED)));
            } else {
                Random rand = new Random();
                velocities.add(Velocity.fromAngleAndSpeed(rand.nextInt(90) + 45,
                        Math.min(Math.max(100 / BALL_RADIUS, MIN_BALL_SPEED), MAX_BALL_SPEED)));
            }
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        if (this.level == 1) {
            return PADDLE_SPEED;
        } else if (this.level == 2) {
            return PADDLE_SPEED - 5;
        } else if (this.level == 3) {
            return PADDLE_SPEED + 2;
        } else if (this.level == 4) {
            return PADDLE_SPEED;
        } else {
            return PADDLE_SPEED;
        }
    }

    @Override
    public int paddleWidth() {
        if (this.level == 1) {
            return PADDLE_WIDTH;
        } else if (this.level == 2) {
            return PADDLE_WIDTH * 2;
        } else if (this.level == 3) {
            return PADDLE_WIDTH - 5;
        } else if (this.level == 4) {
            return PADDLE_WIDTH + 20;
        } else {
            return PADDLE_WIDTH;
        }
    }

    @Override
    public int paddleHeight() {
        return PADDLE_HEIGHT;
    }

    @Override
    public Point paddleInitialPosition() {
        return new Point(WIDTH / 2.0 - paddleWidth() / 2.0, HEIGHT - 2 * PADDLE_HEIGHT);
    }

    @Override
    public String levelName() {
        if (this.level == 1) {
            return "Blinding Lights";
        } else if (this.level == 2) {
            return "Its a Plane?";
        } else if (this.level == 3) {
            return "Bikini Bottom";
        } else if (this.level == 4) {
            return "The Strongest";
        } else {
            return "";
        }
    }

    @Override
    public Sprite getBackground() {
        return new LevelModel(level, WIDTH, HEIGHT);
    }

    @Override
    public List<Block> blocks() {
        blocks = new BlockGenerator(level, WIDTH, HEIGHT, BORDER_WIDTH, BLOCK_WIDTH, BLOCK_HEIGHT).getBlocks();
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        int numberOfBlocksToRemove = 0;
        for (Block block : blocks) {
            if (block.isRemovable()) {
                numberOfBlocksToRemove++;
            }
        }
        return numberOfBlocksToRemove;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int ballRadius() {
        return BALL_RADIUS;
    }

    @Override
    public Point ballInitialPosition() {
        return new Point(WIDTH / 2.0, HEIGHT - 2 * PADDLE_HEIGHT - 2 * BALL_RADIUS);
    }

    @Override
    public Counter blocksCounter() {
        return new Counter();
    }

    @Override
    public Counter ballsCounter() {
        return new Counter();
    }

    @Override
    public Counter scoreCounter() {
        return new Counter();
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
