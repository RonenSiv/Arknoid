package level;

import biuoop.DrawSurface;
import mechanics.Timer;
import models.ScoreIndicator;
import models.Sprite;

import java.awt.Color;

/**
 * The type Draw level info.
 */
public class DrawLevelInfo implements Sprite {
    private String level;
    private String score;
    private String time;
    private ScoreIndicator scoreIndicator;
    private Timer timer;

    /**
     * Instantiates a new Draw level info.
     *
     * @param level          the level
     * @param scoreIndicator the score indicator
     * @param timer          the timer
     */
    public DrawLevelInfo(LevelInformation level, ScoreIndicator scoreIndicator, Timer timer) {
        this.level = "" + level.levelName();
        this.score = "" + scoreIndicator.getScore();
        if (timer != null) {
            this.time = "" + timer.toString();
        } else {
            this.time = "00:00:00";
        }
        this.scoreIndicator = scoreIndicator;
        this.timer = timer;
    }


    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(Color.black);
        d.drawText(10, 15, "Level Name: ", 15);
        d.setColor(Color.RED);
        d.drawText(100, 15, this.level, 15);
        d.setColor(Color.black);
        d.drawText(290, 15, "Score: ", 15);
        d.setColor(new Color(69, 229, 33));
        d.drawText(340, 15, this.score, 15);
//        d.setColor(new Color(178, 24, 24));
        d.setColor(Color.black);
        d.drawText(600, 15, "Time: ", 15);
        d.setColor(Color.BLUE);
        d.drawText(650, 15, this.time, 15);
    }

    @Override
    public void timePassed() {
        if (this.timer != null) {
            this.timer.timePassed();
            this.time = "" + timer.toString();
        }
        this.score = "" + scoreIndicator.getScore();
    }

    /**
     * Add to game.
     *
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * Sets timer.
     *
     * @param timer the timer
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
