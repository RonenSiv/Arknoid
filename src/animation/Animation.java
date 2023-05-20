package animation;

import biuoop.DrawSurface;

/**
 * The interface Animation.
 */
public interface Animation {
    /**
     * Do one frame.
     *
     * @param d the draw surface
     */
    void doOneFrame(DrawSurface d);

    /**
     * Next frame.
     */
    void nextFrame();

    /**
     * Stop animation boolean.
     *
     * @return the boolean
     */
    boolean shouldStop();
}
