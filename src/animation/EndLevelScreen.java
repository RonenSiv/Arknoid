package animation;

import biuoop.DrawSurface;
import models.Sprite;

import java.awt.Color;

/**
 * The type End level screen.
 */
public class EndLevelScreen implements Animation {
    private String level;
    private int score;
    private Sprite background;
    private boolean win;

    /**
     * Instantiates a new End level screen.
     *
     * @param win        the win value
     * @param background the background
     * @param level      the level
     * @param score      the score
     */
    public EndLevelScreen(boolean win, Sprite background, String level, int score) {
        this.background = background;
        this.level = level;
        this.score = score;
        this.win = win;
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();
        background.drawOn(d);
        if (win) {
            d.setColor(new Color(127, 255, 148));
        } else {
            d.setColor(new Color(220, 20, 60));
        }
        d.fillRectangle(width / 2 - 210, height / 2 - 100, width / 2, height / 3);
        d.setColor(Color.WHITE);
        d.drawText(width / 2 - 140, height / 2 - 60, "--" + this.level
                + (win ? " Completed" : " Failed") + "--", 23);
        d.drawText(width / 2 - 120, height / 2 + 30, "Level score is: " + this.score, 20);
        d.drawText(width / 2 - 120, height / 2 + 90, "Press space to continue", 20);
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
        return false;
    }
}
