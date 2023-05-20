// 318211463 Ronen Sivak
package level;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndLevelScreen;
import animation.KeyPressStoppableAnimation;
import animation.CountdownAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import mechanics.Counter;
import mechanics.GameEnvironment;
import mechanics.Timer;
import models.ScoreIndicator;
import models.Sprite;
import models.SpriteCollection;
import objects.Block;
import objects.Paddle;
import physics.Collidable;
import physics.Point;
import objects.Ball;
import objects.Rectangle;
import physics.Velocity;

import java.awt.Color;
import java.util.List;

import static java.lang.System.exit;

/**
 * The type Game.
 *
 * @author Ronen Sivak
 */
public class GameLevel implements Animation {
    private static final int BORDER_WIDTH = 1;
    private static int ballRad;
    private static final int MIN_BALL_RAD = 4;
    private static final int MAX_BALL_RAD = 50;
    private static final int MIN_BALL_SPEED = 3;
    private static final int MAX_BALL_SPEED = 6;
    private static int paddleWidth = 150;
    private static int paddleHeight = 20;
    private final int paddleSpeed;
    private final int ballSpeed;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final int numOfBalls;
    private final Point ballStartPoint;
    private final Point paddleStartPoint;
    private final SpriteCollection sprites = new SpriteCollection();
    private final GameEnvironment environment = new GameEnvironment();
    private Paddle paddle;
    private final java.util.List<Ball> balls;
    private final java.util.List<Block> blocks;
    private BlockRemover blockRemover;
    private BallRemover ballRemover;
    private final Counter blocksCounter;
    private final Counter ballsCounter;
    private final Counter scoreCounter;
    private ScoreTrackingListener scoreTrackingListener;
    private ScoreIndicator scoreIndicator;
    private final int bonusPercentage = 1;
    private final AnimationRunner runner;
    private boolean running = false;
    private final KeyboardSensor keyboard;
    private DrawLevelInfo drawLevelInfo;
    private final LevelInformation levelInformation;
    private final Sprite background;
    private final List<Velocity> velocities;
    private Timer timer;
    private boolean restart = false;
    private boolean back = false;
    private final int score;

    /**
     * Instantiates a new Game.
     *
     * @param levelInfo the level info
     * @param ks        the keyboard sensor
     * @param ar        the animation runner
     * @param score     the score
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor ks, AnimationRunner ar, int score) {
        levelInformation = levelInfo;
        balls = new java.util.ArrayList<>();
        blocks = new java.util.ArrayList<>();
        runner = ar;
        keyboard = ks;
        ballRad = levelInfo.ballRadius();
        background = this.levelInformation.getBackground();
        ballStartPoint = levelInfo.ballInitialPosition();
        paddleStartPoint = levelInfo.paddleInitialPosition();
        velocities = levelInfo.initialBallVelocities();
        numOfBalls = levelInfo.numberOfBalls();
        paddleSpeed = levelInfo.paddleSpeed();
        paddleHeight = levelInfo.paddleHeight();
        paddleWidth = levelInfo.paddleWidth();
        ballSpeed = Math.min(Math.max(100 / ballRad, MIN_BALL_SPEED), MAX_BALL_SPEED);
        blocksCounter = levelInfo.blocksCounter();
        ballsCounter = levelInfo.ballsCounter();
        scoreCounter = levelInfo.scoreCounter();
        this.score = score;
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        blockRemover = new BlockRemover(this, blocksCounter);
        ballRemover = new BallRemover(this, ballsCounter);
        scoreTrackingListener = new ScoreTrackingListener(scoreCounter);
        createGame();
    }

    /**
     * Create game.
     */
    public void createGame() {
        // set the level
        setGameLevel();
        // create the borders
        createBorders();
        // create the Blocks
        createBlocks();
        // create the Paddle
        createPaddle();
        // create the Balls
        createBalls();
        // create the ScoreIndicator
        createScoreIndicator();
        // create the LevelInfo
        createLevelInfo();
        // run the game
        run();
    }

    /**
     * Run the game.
     */
    public void run() {
        CountdownAnimation countDown = new CountdownAnimation(4, 3, this.sprites);
        this.runner.run(countDown);
        this.running = true;
        this.timer = new Timer(System.currentTimeMillis(), System.currentTimeMillis());
        this.drawLevelInfo.setTimer(timer);
        this.runner.run(this);
    }

    /**
     * Create balls.
     */
    public void createBalls() {
        for (int i = 0; i < numOfBalls; i++) {
            Ball ball = new Ball(ballStartPoint, Math.min(Math.max(ballRad, MIN_BALL_RAD), MAX_BALL_RAD),
                    Color.WHITE);
            Velocity ballVelocity = velocities.get(i);
            addBall(ball, ballVelocity);
        }
    }

    /**
     * Add ball.
     *
     * @param ball         the ball
     * @param ballVelocity the ball velocity
     */
    public void addBall(Ball ball, Velocity ballVelocity) {
        ball.setGameEnvironment(environment);
        if (ball.getType() == Ball.BallBonusType.NORMAL) {
            ball.setVelocity(ballVelocity);
            this.ballsCounter.increase(1);
        }
        balls.add(ball);
        ball.addToGame(this);
    }

    /**
     * Create borders.
     */
    public void createBorders() {
        // set borders
        Block upper = new Block(new Rectangle(new Point(0, 0), WIDTH, 20), Color.GRAY);
        Block lower = new Block(new Rectangle(new Point(0, HEIGHT - BORDER_WIDTH + ballRad / 2), WIDTH,
                BORDER_WIDTH), Color.GRAY);
        Block left = new Block(new Rectangle(new Point(0, 0), BORDER_WIDTH, HEIGHT), Color.GRAY);
        Block right = new Block(new Rectangle(new Point(WIDTH - BORDER_WIDTH, 0), BORDER_WIDTH, HEIGHT),
                Color.GRAY);
        upper.addToGame(this);
        upper.setRemovable(false);
        lower.addToGame(this);
        lower.addHitListener(ballRemover);
        lower.setRemovable(false);
        lower.setDeath(true);
        left.addToGame(this);
        left.setRemovable(false);
        right.addToGame(this);
        right.setRemovable(false);

        // set ball killers
        Block leftBallKiller = new Block(new Rectangle(new Point(-getBorderWidth() * 10, 0), getBorderWidth() * 2,
                getHeight()), Color.GRAY);
        Block rightBallKiller = new Block(new Rectangle(new Point(getWidth() + getBorderWidth() * 10, 0),
                getBorderWidth() * 2, getHeight()), Color.GRAY);
        Block upperBallKiller = new Block(new Rectangle(new Point(0, -getBorderWidth() * 10), getWidth(),
                getBorderWidth() * 2), Color.GRAY);
        Block lowerBallKiller = new Block(new Rectangle(new Point(0, getHeight() + getBorderWidth() * 10),
                getWidth(), getBorderWidth() * 2), Color.GRAY);
        leftBallKiller.addToGame(this);
        leftBallKiller.addHitListener(ballRemover);
        leftBallKiller.setRemovable(false);
        rightBallKiller.addToGame(this);
        rightBallKiller.addHitListener(ballRemover);
        rightBallKiller.setRemovable(false);
        upperBallKiller.addToGame(this);
        upperBallKiller.addHitListener(ballRemover);
        upperBallKiller.setRemovable(false);
        lowerBallKiller.addToGame(this);
        lowerBallKiller.addHitListener(ballRemover);
        lowerBallKiller.setRemovable(false);
    }

    /**
     * Create blocks.
     */
    public void createBlocks() {
        for (Block block : this.levelInformation.blocks()) {
            addBlocks(block);
        }
        this.blocksCounter.increase(levelInformation.numberOfBlocksToRemove());
    }

    /**
     * Add blocks.
     *
     * @param block the block
     */
    public void addBlocks(Block block) {
        block.addToGame(this);
        this.blocks.add(block);
        if (block.isRemovable()) {
            block.addHitListener(scoreTrackingListener);
            block.addHitListener(blockRemover);
        }
    }

    /**
     * Create paddle.
     */
    public void createPaddle() {
        this.paddle = new Paddle(this.levelInformation, this.keyboard,
                new Rectangle(paddleStartPoint, paddleWidth, paddleHeight),
                BORDER_WIDTH, WIDTH - BORDER_WIDTH);

        paddle.setSpeed(paddleSpeed);
        paddle.addHitListener(ballRemover);
        paddle.addToGame(this);
    }

    private void createLevelInfo() {
        drawLevelInfo = new DrawLevelInfo(this.levelInformation, this.scoreIndicator, this.timer);
        drawLevelInfo.addToGame(this);
    }

    private void createScoreIndicator() {
        scoreIndicator = new ScoreIndicator(scoreCounter);
        scoreIndicator.addScore(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Level finished.
     */
    public void levelFinished() {
        int blocksLeft = blocksCounter.getValue();
        Animation levelEnd = new EndLevelScreen(blocksLeft == 0, this.levelInformation.getBackground(),
                this.levelInformation.levelName(), this.scoreCounter.getValue());
        this.sprites.removeSprites(this.sprites.getSprites());
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, levelEnd, KeyboardSensor.SPACE_KEY));
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                    new PauseScreen(this.keyboard, this.sprites), KeyboardSensor.SPACE_KEY));
        }

        if (this.keyboard.isPressed("q")) {
            exit(0);
        }

        if (this.keyboard.isPressed("r") || this.keyboard.isPressed("b")) {
            this.running = false;
            if (this.keyboard.isPressed("r")) {
                this.restart = true;
            } else {
                this.back = true;
            }
            this.running = false;
            ballsCounter.reset();
            scoreCounter.reset();
            blocksCounter.reset();
            return;
        }

        if (blocksCounter.getValue() == 0 || ballsCounter.getValue() == 0) {
            if (blocksCounter.getValue() == 0) {
                scoreCounter.increase(100);
            }
            this.running = false;
            levelFinished();
        }
    }

    @Override
    public void nextFrame() {
        this.sprites.notifyAllTimePassed();
    }

    /**
     * Remove collidable.
     *
     * @param collidable the collidable
     */
    public void removeCollidable(Collidable collidable) {
        this.environment.getCollidables().remove(collidable);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    // setters

    /**
     * Sets game level.
     */
    public void setGameLevel() {
        this.sprites.addSprite(background);
    }

    // getters

    /**
     * Gets ball rad.
     *
     * @return the ball rad
     */
    public int getBallRad() {
        return ballRad;
    }

    /**
     * Gets game width.
     *
     * @return the width
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Gets game height.
     *
     * @return the height
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Gets border width.
     *
     * @return the border width
     */
    public int getBorderWidth() {
        return BORDER_WIDTH;
    }

    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * Gets balls.
     *
     * @return the balls
     */
    public java.util.List<Ball> getBalls() {
        return balls;
    }

    /**
     * Gets sprite.
     *
     * @return the sprite
     */
    public SpriteCollection getSprite() {
        return this.sprites;
    }

    /**
     * Gets blocks counter.
     *
     * @return the blocks counter
     */
    public Counter getBlocksCounter() {
        return this.blocksCounter;
    }

    /**
     * Gets balls counter.
     *
     * @return the balls counter
     */
    public Counter getBallsCounter() {
        return this.ballsCounter;
    }

    /**
     * Gets score counter.
     *
     * @return the score counter
     */
    public Counter getScoreCounter() {
        return this.scoreCounter;
    }

    /**
     * Gets ball speed.
     *
     * @return the ball speed
     */
    public int getBallSpeed() {
        return this.ballSpeed;
    }

    /**
     * Gets bonus percentage.
     *
     * @return the bonus percentage
     */
    public int getBonusPercentage() {
        return this.bonusPercentage;
    }

    /**
     * Gets restart.
     *
     * @return the restart
     */
    public boolean getRestart() {
        return this.restart;
    }

    /**
     * Gets back.
     *
     * @return the back
     */
    public boolean getBack() {
        return this.back;
    }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Gets level information.
     *
     * @return the level information
     */
    public LevelInformation getLevelInformation() {
        return this.levelInformation;
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return this.timer;
    }
}
