package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private Animation animation;
    private String key;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param kb        the kb
     * @param animation the animation to stop.
     * @param key       the key to stop the animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor kb, Animation animation, String key) {
        this.keyboard = kb;
        this.animation = animation;
        this.key = key;
        this.stop = false;
        if (this.keyboard.isPressed(this.key)) {
            isAlreadyPressed = true;
        } else {
            isAlreadyPressed = false;
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.keyboard.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            isAlreadyPressed = false;
        }
    }

    @Override
    public void nextFrame() {

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
