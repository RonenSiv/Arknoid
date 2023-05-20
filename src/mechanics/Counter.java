// 318211463 Ronen Sivak
package mechanics;

/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Instantiates a new Counter.
     */
    public Counter() {
        count = 0;
    }

    /**
     * Increase.
     * add number to current count.
     *
     * @param number the number
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Decrease.
     * subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * Gets value.
     * get current count.
     *
     * @return the value
     */
    public int getValue() {
        return count;
    }

    /**
     * Reset.
     */
    public void reset() {
        count = 0;
    }
}