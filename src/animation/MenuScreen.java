package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import mechanics.Timer;
import models.Sprite;

import java.awt.Color;

/**
 * The type Menu screen.
 */
public class MenuScreen implements Animation {
    private int width;
    private int height;
    private String title;
    private String[] options;
    private int selected;
    private boolean stop;
    private KeyboardSensor keyboard;
    private Sprite background;
    private Timer actionTimer;

    /**
     * Constructor.
     *
     * @param bg     the background
     * @param ks     the keyboard sensor
     * @param width  the width of the screen
     * @param height the height of the screen
     */
    public MenuScreen(Sprite bg, KeyboardSensor ks, int width, int height) {
        this.keyboard = ks;
        this.background = bg;
        this.width = width;
        this.height = height;
        this.selected = 0;
        this.stop = false;
        this.actionTimer = new Timer(System.currentTimeMillis(), System.currentTimeMillis() + 100);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        drawMenu(d);
        keyPressed();
        actionTimer.timePassed();
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Draw menu.
     *
     * @param d the d
     */
    public void drawMenu(DrawSurface d) {
        background.drawOn(d);
        d.setColor(new Color(73, 116, 134));
        d.fillRectangle(width / 2 - 100, height / 2 - 150, 250, 280);
        d.setColor(new Color(68, 129, 67));
        d.drawRectangle(width / 2 - 100, height / 2 - 150, 250, 280);
        d.drawRectangle(width / 2 - 98, height / 2 - 148, 245, 275);
        title = "Arkanoid";
        options = new String[]{"Play", "High Scores", "About", "Quit"};
        d.setColor(java.awt.Color.WHITE);
        d.drawText(width / 2 - 50, height / 2 - 100, this.title, 32);
        for (int i = 0; i < this.options.length; i++) {
            if (i == this.selected) {
                d.setColor(java.awt.Color.RED);
            } else {
                d.setColor(java.awt.Color.WHITE);
            }
            d.drawText(width / 2 - 32, height / 2 - 100 + (i + 1) * 32, this.options[i], 16);
        }
        d.setColor(Color.GREEN);
        d.drawText(width / 2 - 60, height / 2 - 100 + 5 * 32, "press space to choose", 16);
    }

    /**
     * Key pressed.
     */
    public void keyPressed() {
        if (this.actionTimer.isTimePassed()) {
            if (this.keyboard.isPressed(KeyboardSensor.DOWN_KEY)) {
                this.selected = (this.selected + 1) % this.options.length;
            }
            if (this.keyboard.isPressed(KeyboardSensor.UP_KEY)) {
                this.selected = (this.selected - 1 + this.options.length) % this.options.length;
            }
            this.actionTimer.reset(100);
        }
    }

    /**
     * Sets the selected option.
     *
     * @param selected the selected option
     */
    public void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * Gets the selected option.
     *
     * @return the selected option
     */
    public int getSelected() {
        return this.selected;
    }
}
