package animation;

import biuoop.DrawSurface;
import mechanics.ScoreFile;
import models.Sprite;

import java.awt.Color;

/**
 * The type High scores screen.
 */
public class ScoresScreen implements Animation {

    private int width;
    private int height;
    private Sprite background;
    private ScoreFile scoreFile;
    private String title;

    /**
     * Instantiates a new High scores screen.
     *
     * @param bg     the background
     * @param width  the screen width
     * @param height the screen height
     * @param sf     the score file
     * @param title  the title
     */
    public ScoresScreen(Sprite bg, int width, int height, ScoreFile sf, String title) {
        this.background = bg;
        this.width = width;
        this.height = height;
        this.scoreFile = sf;
        this.title = title;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        background.drawOn(d);
        d.setColor(new Color(73, 116, 134));
        d.fillRectangle(width / 2 - 200, height / 2 - 150, 550, 280);
        d.setColor(new Color(68, 129, 67));
        d.drawRectangle(width / 2 - 200, height / 2 - 150, 550, 280);
        d.drawRectangle(width / 2 - 198, height / 2 - 148, 545, 275);
        d.setColor(java.awt.Color.WHITE);
        d.drawText(width / 2 - 100, height / 2 - 100, this.title, 32);
        String[] scores = this.scoreFile.getHighScores();
        if (scores.length == 0) {
            d.drawText(width / 2 - 100, height / 2 - 100 + 32, "No scores yet", 16);
        } else {
            for (int i = 0; i < Math.min(5, scores.length); i++) {
                d.drawText(width / 2 - 150, height / 2 - 100 + (i + 1) * 32, i + 1 + ". " + scores[i], 16);
            }
        }
        d.setColor(Color.red);
        d.drawText(width / 2 - 60, height / 2 - 100 + 6 * 32, "Back", 16);
        d.setColor(Color.GREEN);
        d.drawText(width / 2 - 60, height / 2 - 100 + 7 * 32, "press space to choose", 16);
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
