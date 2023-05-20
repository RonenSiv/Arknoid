package mechanics;

import animation.MenuScreen;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import animation.ScoresScreen;
import animation.AboutScreen;
import animation.EndScreen;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import level.GameLevel;
import level.LevelInformation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

/**
 * The type Game flow.
 */
public class GameFlow {
    private final AnimationRunner animationRunner;
    private final KeyboardSensor keyboardSensor;
    private int score;
    private final GUI gui;
    private LevelInformation levelInformation;
    private final ScoreFile scoreFile;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar  the animation runner
     * @param ks  the keyboard sensor
     * @param gui the gui
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.scoreFile = new ScoreFile("src/score.txt");
        this.score = 0;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        startScreen(levels);
        boolean win = true;
        long time = 0;
        for (int i = 0; i < levels.size(); i++) {
            LevelInformation levelInfo = levels.get(i);
            levelInformation = levelInfo;
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, this.score);
            level.initialize();

            while (level.isRunning()) {
                level.run();
            }
            if (level.getRestart()) {
                i = -1;
                this.score = 0;
                time = 0;
                continue;
            }
            if (level.getBack()) {
                runLevels(levels);
                return;
            }
            Timer timer = level.getTimer();
            time += timer.toMillisLong();
            if (level.getBallsCounter().getValue() == 0) {
                this.score = level.getScoreCounter().getValue();
                win = false;
                break;
            }
            if (level.getBlocksCounter().getValue() == 0) {
                this.score = level.getScoreCounter().getValue();
            }
        }
        endScreen(win, time);
    }

    private void startScreen(List<LevelInformation> levels) {
        MenuScreen menuScreen = new MenuScreen(levels.get(0).getBackground(), this.keyboardSensor, 800, 600);
        this.animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, menuScreen, KeyboardSensor.SPACE_KEY)
        );
        while (menuScreen.getSelected() != 0) {
            if (menuScreen.getSelected() == 1) {
                ScoresScreen highScoresAnimation =
                        new ScoresScreen(levels.get(0).getBackground(),
                                800, 600, scoreFile, "Top 5 High Scores");
                this.animationRunner.run(new KeyPressStoppableAnimation(
                        this.keyboardSensor, highScoresAnimation, KeyboardSensor.SPACE_KEY));
            } else if (menuScreen.getSelected() == 2) {
                AboutScreen aboutScreen =
                        new AboutScreen(levels.get(0).getBackground(), 800, 600, "About");
                this.animationRunner.run(new KeyPressStoppableAnimation(
                        this.keyboardSensor, aboutScreen, KeyboardSensor.SPACE_KEY));
            } else if (menuScreen.getSelected() == 3) {
                System.out.println("Goodbye!");
                exit(0);
            }
            menuScreen.setSelected(0);
            this.animationRunner.run(new KeyPressStoppableAnimation(
                    this.keyboardSensor, menuScreen, KeyboardSensor.SPACE_KEY));
        }
    }

    private void endScreen(boolean win, long time) {
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                new EndScreen(win, time, this.score, this.levelInformation), "x"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String endTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Math.abs(time) % 60) % 60,
                TimeUnit.MILLISECONDS.toSeconds(Math.abs(time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))) % 60,
                (Math.abs(time
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))) % 1000)
        );
        scoreFile.write("Score: " + this.score
                + " [Complete Time: " + endTime + " (" + dtf.format(now) + ")]");
        gui.close();
    }
}
