// 318211463 Ronen Sivak
package listeners;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    /**
     * Add hit listener.
     *
     * @param hl the hl
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hit listener.
     *
     * @param hl the hl
     */
    void removeHitListener(HitListener hl);
}
