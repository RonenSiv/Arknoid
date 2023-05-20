// 318211463 Ronen Sivak
package mechanics;

import objects.Block;
import physics.Point;
import objects.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Block generator.
 */
public class BlockGenerator {
    /**
     * The Game.
     */
    private final int level;
    private final int width;
    private final int height;
    private final int borderWidth;
    private int blockWidth;
    private final int blockHeight;
    private final List<Block> blocks;

    /**
     * Instantiates a new Block generator.
     *
     * @param level       the level
     * @param width       the width
     * @param height      the height
     * @param borderWidth the border width
     * @param blockWidth  the block width
     * @param blockHeight the block height
     */
    public BlockGenerator(int level, int width, int height, int borderWidth, int blockWidth, int blockHeight) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        blocks = new ArrayList<>();
    }

    /**
     * Add blocks.
     *
     * @param level the level
     */
    private void addBlocks(int level) {
        if (level == 1) {
            level1();
        } else if (level == 2) {
            level2();
        } else if (level == 3) {
            level3();
        } else if (level == 4) {
            level4();
        }
    }

    /**
     * Level 1.
     */
    private void level1() {
        Rectangle rect = new Rectangle(new Point(width / 2 - blockWidth / 2, height / 4), blockWidth, blockHeight);
        Block block = new Block(rect, randomColor());
        blocks.add(block);
    }

    /**
     * Level 2.
     */
    private void level2() {
        Color color = randomColor();
        for (int i = 1, k = 0; i < width - 1; i += blockWidth, k++) {
            Rectangle rect = new Rectangle(new Point(i, 250), blockWidth, blockHeight);
            if (k >= 2) {
                color = randomColor();
                k = 0;
            }
            Block block = new Block(rect, color);
            blocks.add(block);
        }
    }

    /**
     * Level 3.
     */
    private void level3() {
        for (int i = 1; i < 6; i++) {
            Color color = randomColor();
            for (int j = width - borderWidth - blockWidth;
                 j >= blockWidth * 10 - (6 - i) * blockWidth; j -= blockWidth) {
                Rectangle rect = new Rectangle(new Point(j, 60 + i * blockHeight), blockWidth, blockHeight);
                Block block = new Block(rect, color);
                if (i == 1) {
                    block.setHitPoints(2);
                }
                blocks.add(block);
            }
        }
    }

    /**
     * Level 4.
     */
    private void level4() {
        blockWidth = 38;
        for (int i = 1; i < 8; i++) {
            Color color = randomColor();
            for (int j = 1; j <= width - borderWidth - blockWidth; j += blockWidth) {
                Rectangle rect = new Rectangle(new Point(j, 200 + i * blockHeight), blockWidth, blockHeight);
                Block block = new Block(rect, color);
                if (i == 1) {
                    block.setHitPoints(2);
                }
                blocks.add(block);
            }
        }
    }

    /**
     * Random color.
     *
     * @return the color
     */
    private Color randomColor() {
        Random rand = new Random();
        return new Color(50 + rand.nextInt(200), 50 + rand.nextInt(200), 50 + rand.nextInt(200));
    }

    /**
     * Gets blocks.
     *
     * @return the blocks
     */
    public List<Block> getBlocks() {
        addBlocks(level);
        return blocks;
    }
}
