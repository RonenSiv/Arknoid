// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Models.
 */
public abstract class Models {
    /**
     * Draw on.
     *
     * @param d the d
     */
    public abstract void drawOn(DrawSurface d);

    /**
     * Random color color.
     *
     * @return the color
     */
    protected Color randomColor() {
        Random rand = new Random();
        return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    /**
     * The type Tuple.
     *
     * @param <Point>   the type parameter
     * @param <Integer> the type parameter
     */
    public static class Tuple<Point, Integer> {
        /**
         * The Point.
         */
        private List<Point> points = new ArrayList<>();
        /**
         * The Integer.
         */
        private List<Integer> integers = new ArrayList<>();

        /**
         * Gets points.
         *
         * @return the points
         */
        public List<Point> getPoints() {
            return points;
        }

        /**
         * Gets integers.
         *
         * @return the integers
         */
        public List<Integer> getIntegers() {
            return integers;
        }
    }

    /**
     * Random int int.
     *
     * @param x the x
     * @return the int
     */
    public int randomInt(int x) {
        Random rand = new Random();
        return rand.nextInt(x);
    }
}
