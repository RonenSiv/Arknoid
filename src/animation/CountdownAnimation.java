package animation;

import biuoop.DrawSurface;
import mechanics.Timer;
import models.SpriteCollection;

import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Timer timer;
    private boolean stop;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.timer = new Timer(System.currentTimeMillis(), System.currentTimeMillis() + (countFrom * 1000));
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.white);
        d.drawText(d.getWidth() / 2 - 34, d.getHeight() / 2 - 2,
                countFrom != 0 ? Integer.toString(countFrom) : "GO", 50);
        d.setColor(Color.RED);
        d.drawText(d.getWidth() / 2 - 32, d.getHeight() / 2, countFrom != 0 ? Integer.toString(countFrom) : "GO", 50);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 33, d.getHeight() / 2 - 1,
                countFrom != 0 ? Integer.toString(countFrom) : "GO", 50);
        drawProgressBar(d, d.getWidth() / 2 - 100, d.getHeight() / 2 + 25,
                (int) (Math.abs(timer.getEndTime() - System.currentTimeMillis()) - 1000));

        if (this.timer.lastTimePassed(500)) {
            if (countFrom != 0) {
                countFrom--;
            }
            this.numOfSeconds--;
        }
        if (countFrom == 0) {
            if (numOfSeconds == 0) {
                this.stop = true;
            }
        }
    }

    private void drawProgressBar(DrawSurface d, int x, int y, int progress) {
        d.setColor(Color.BLACK);
        d.fillRectangle(x, y, (int) (progress / 10.0), 20);
        d.setColor(Color.RED);
        d.drawRectangle(x, y, (int) (progress / 10.0), 20);
        d.setColor(Color.WHITE);
        d.drawRectangle(x - 2, y - 2, (int) (progress / 10.0 + 4), 24);
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }
}
