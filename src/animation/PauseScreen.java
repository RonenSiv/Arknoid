package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import models.SpriteCollection;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    /**
     * The Game screen.
     */
    private SpriteCollection gameScreen;
    private boolean stop;

    /**
     * Instantiates a new Pause screen.
     *
     * @param k          the keyboard sensor
     * @param gameScreen the game screen
     */
    public PauseScreen(KeyboardSensor k, SpriteCollection gameScreen) {
        this.keyboard = k;
        this.stop = false;
        this.gameScreen = gameScreen;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.BLACK);
        d.drawText(350, d.getHeight() / 2, "Paused", 32);
        d.drawText(300, d.getHeight() / 2 + 50, "Press space to continue", 24);
        d.setColor(Color.WHITE);
        d.drawText(352, d.getHeight() / 2 + 2, "Paused", 32);
        d.drawText(302, d.getHeight() / 2 + 52, "Press space to continue", 24);
//        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
//            this.stop = true;
//        }
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
