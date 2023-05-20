// 318211463 Ronen Sivak

import mechanics.GameCreate;

/**
 * The type Ass5Game.
 */
public class Ass6Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int NUM_OF_LEVELS = 4;
    public static final String GAME_NAME = "Arkanoid";
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        GameCreate game = new GameCreate(SCREEN_WIDTH, SCREEN_HEIGHT, GAME_NAME, args, NUM_OF_LEVELS);
        game.runGame();
    }
}
