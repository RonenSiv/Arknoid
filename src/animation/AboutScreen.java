package animation;

import biuoop.DrawSurface;
import models.Sprite;

import java.awt.Color;

/**
 * The type About screen.
 */
public class AboutScreen implements Animation {
    private int width;
    private int height;
    private Sprite background;
    private String title;

    /**
     * Instantiates a new About screen.
     *
     * @param bg     the bg
     * @param width  the width
     * @param height the height
     * @param title  the title
     */
    public AboutScreen(Sprite bg, int width, int height, String title) {
        this.background = bg;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        background.drawOn(d);
        d.setColor(new Color(73, 116, 134));
        d.fillRectangle(width / 2 - 200, height / 2 - 150, 550, 400);
        d.setColor(new Color(68, 129, 67));
        d.drawRectangle(width / 2 - 200, height / 2 - 150, 550, 400);
        d.drawRectangle(width / 2 - 198, height / 2 - 148, 545, 395);
        title = "About";
        d.setColor(java.awt.Color.WHITE);
        d.drawText(d.getWidth() / 2 - 100, height / 2 - 120, this.title, 32);
        d.drawText(width / 2 - 150, height / 2 - 100 + (1) * 32,
                "Arkanoid is a game that is played on a rectangular board.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (2) * 32,
                "The goal of the game is to remove all the blocks from the board.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (3) * 32,
                "The player controls the paddle by moving it left and right.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (4) * 32,
                "The player can also move the paddle by pressing the a or d", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (5) * 32,
                "The player can also pause the game by pressing the p key.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (6) * 32,
                "The player can also quit the game by pressing the q key.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (7) * 32,
                "The player can also restart the game by pressing the r key.", 16);
        d.drawText(width / 2 - 150, height / 2 - 100 + (8) * 32,
                "The player can also go back to the main menu by pressing the b key.", 16);
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2 - 100 + 9 * 32, "Back", 16);
        d.setColor(Color.GREEN);
        d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2 - 100 + 10 * 32, "press space to choose", 16);
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
