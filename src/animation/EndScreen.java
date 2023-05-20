package animation;

import biuoop.DrawSurface;
import level.LevelInformation;
import models.Sprite;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {
    private boolean stop;
    private int score;
    private boolean win;
    private Sprite background;
    private long time;

    /**
     * Instantiates a new End screen.
     *
     * @param win              the win value
     * @param timer            the timer
     * @param score            the score
     * @param levelInformation the level information
     */
    public EndScreen(boolean win, long timer, int score, LevelInformation levelInformation) {
        this.score = score;
        this.stop = false;
        this.background = levelInformation.getBackground();
        this.win = win;
        this.time = timer;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();
        String endTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Math.abs(time) % 60) % 60,
                TimeUnit.MILLISECONDS.toSeconds(Math.abs(time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))) % 60,
                (Math.abs(time - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))) % 1000)
        );
        background.drawOn(d);
        if (win) {
            d.setColor(new Color(127, 255, 148));
        } else {
            d.setColor(new Color(220, 20, 60));
        }
        d.fillRectangle(width / 2 - 210, height / 2 - 100, width / 2, height / 3);
        d.setColor(Color.WHITE);
        d.drawText(width / 2 - 120, height / 2 - 60, "--Game Over--", 32);
        d.drawText(width / 2 - 60, height / 2 - 30, "You " + (win ? "Won" : "Lose"), 20);
        d.drawText(width / 2 - 120, height / 2 + 30, "Your final score is: " + this.score, 20);
        d.drawText(width / 2 - 120, height / 2 + 60, "Your time is: " + endTime, 20);
        d.drawText(width / 2 - 120, height / 2 + 90, "Press x to close", 20);
        d.setColor(Color.BLACK);
        d.drawRectangle(width / 2 - 210, height / 2 - 100, width / 2, height / 3);
        d.setColor(Color.WHITE);
        d.drawRectangle(width / 2 - 212, height / 2 - 102, width / 2 + 4, height / 3 + 4);
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
