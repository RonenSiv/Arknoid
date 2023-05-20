// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sprite collection.
 *
 * @author Ronen Sivak
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new java.util.ArrayList<Sprite>();
    }

    /**
     * Add sprite to list.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notify all time passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copySprites = new ArrayList<>(this.sprites);
        for (Sprite s : copySprites) {
            s.timePassed();
        }
    }

    /**
     * Draw all on.
     *
     * @param d the d
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public java.util.List<Sprite> getSprites() {
        return this.sprites;
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Remove sprites.
     *
     * @param sprites the sprites
     */
    public void removeSprites(List<Sprite> sprites) {
        this.sprites.removeAll(sprites);
    }
}
