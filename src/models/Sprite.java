// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;

/**
 * The interface Sprite.
 *
 * @author Ronen Sivak
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     *
     * @param d the d
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}
