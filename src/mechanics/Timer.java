// 318211463 Ronen Sivak
package mechanics;

import biuoop.DrawSurface;
import models.Sprite;

import java.util.concurrent.TimeUnit;

/**
 * The type Timer.
 */
public class Timer implements Sprite {
    /**
     * The Start time.
     */
    private double startTime;
    /**
     * The End time.
     */
    private double endTime;
    /**
     * The Last time.
     */
    private double lastTime;

    /**
     * Instantiates a new Timer.
     *
     * @param time      the time
     * @param deltaTime the delta time
     */
    public Timer(double time, double deltaTime) {
        this.startTime = time;
        this.endTime = deltaTime;
        this.lastTime = System.currentTimeMillis();
    }
    /**
     * Gets end time.
     *
     * @return the end time
     */
    public double getEndTime() {
        return this.endTime;
    }

    /**
     * Gets delta time.
     *
     * @return the delta time
     */
    public double getDeltaTime() {
        return this.endTime - this.startTime;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis();
        this.lastTime = System.currentTimeMillis();
    }

    /**
     * Reset.
     *
     * @param time the time
     */
    public void reset(double time) {
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis() + time;
    }

    @Override
    public void drawOn(DrawSurface d) {

    }

    @Override
    public void timePassed() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Math.abs((long) getDeltaTime())) % 60,
                TimeUnit.MILLISECONDS.toSeconds(Math.abs((long) getDeltaTime())
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) getDeltaTime()))) % 60,
                (Math.abs((long) getDeltaTime()
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) getDeltaTime()))) % 1000)
        );
    }

    /**
     * To string.
     *
     * @param time the time
     * @return the string
     */
    public String toString(double time) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Math.abs((long) time) % 60) % 60,
                TimeUnit.MILLISECONDS.toSeconds(Math.abs((long) time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))) % 60,
                (Math.abs((long) time
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))) % 1000)
        );
    }
    /**
     * To seconds long long.
     *
     * @return the long
     */
    public long toSecondsLong() {
        return TimeUnit.MILLISECONDS.toSeconds((long) getDeltaTime());
    }

    /**
     * To millis long long.
     *
     * @return the long
     */
    public long toMillisLong() {
        return (long) getDeltaTime();
    }

    /**
     * Is time passed boolean.
     *
     * @return the boolean
     */
    public boolean isTimePassed() {
        return this.startTime >= this.endTime;
    }

    /**
     * Last time passed boolean.
     *
     * @param time the time
     * @return the boolean
     */
    public boolean lastTimePassed(long time) {
        boolean val = System.currentTimeMillis() - this.lastTime >= time;
        if (val) {
            this.lastTime = System.currentTimeMillis();
        }
        return val;
    }
}

