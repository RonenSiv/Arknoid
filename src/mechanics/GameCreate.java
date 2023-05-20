package mechanics;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import level.Level;
import level.LevelInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Game create.
 */
public class GameCreate {
    private final int width;
    private final int height;
    private final String title;
    private final String[] levels;
    private final int numOfLevels;

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * Constructor.
     *
     * @param width       the width of the screen
     * @param height      the height of the screen
     * @param title       the title
     * @param levels      the levels
     * @param numOfLevels the num of levels
     */
    public GameCreate(int width, int height, String title, String[] levels, int numOfLevels) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.levels = levels;
        this.numOfLevels = numOfLevels;
    }

    /**
     * Run game.
     */
    public void runGame() {
        GUI gui = new GUI(this.title, this.width, this.height);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);
        java.util.List<Integer> levelArgs = new ArrayList<>();
        for (String level : this.levels) {
            if (checkValidLevel(level, this.numOfLevels)) {
                levelArgs.add(Integer.parseInt(level));
            } else {
                System.out.println(ANSI_RED + "* Invalid level number: " + ANSI_YELLOW + level + ANSI_RESET);
            }
        }
        List<LevelInformation> levels = new ArrayList<>();
        if (levelArgs.size() == 0) {
            levels.add(new Level(1));
            levels.add(new Level(2));
            levels.add(new Level(3));
            levels.add(new Level(4));
            System.out.println(ANSI_RED
                    + "No level number was given, so the default levels will be used." + ANSI_RESET);
        } else {
            for (int levelNum : levelArgs) {
                levels.add(new Level(levelNum));
            }
        }
        System.out.println(ANSI_GREEN + "___________________________________________" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Levels Order:" + ANSI_RESET);
        for (int i = 0; i < levels.size(); i++) {
            System.out.println(ANSI_RED + (i + 1) + ": " + ANSI_YELLOW + levels.get(i).levelName() + ANSI_RESET);
        }
        System.out.println(ANSI_GREEN + "The game will start now. " + ANSI_PURPLE + "Good Luck!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "___________________________________________" + ANSI_RESET);
        GameFlow game = new GameFlow(runner, keyboard, gui);
        game.runLevels(levels);
    }

    private boolean checkValidLevel(String level, int numOfLevels) {
        Pattern patt = Pattern.compile("\\d");
        Matcher matcher = patt.matcher(level);
        if (matcher.matches()) {
            if (Integer.parseInt(level) > 0 && Integer.parseInt(level) < numOfLevels + 1) {
                return true;
            }
        }
        return false;
    }
}
