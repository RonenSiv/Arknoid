package mechanics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The type Score file.
 */
public class ScoreFile {
    private final File file;

    /**
     * Instantiates a new Score file.
     *
     * @param fileName the file name
     */
    public ScoreFile(String fileName) {
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write to file.
     *
     * @param line the line
     */
    public void write(String line) {
        try {
            FileWriter myWriter = new FileWriter(file.getPath(), true);
            BufferedWriter writer = new BufferedWriter(myWriter);
            writer.write(line);
            writer.newLine();
            writer.close();
            System.out.println("Successfully saved score");
        } catch (IOException e) {
            System.out.println("An error occurred while trying to save score.");
            e.printStackTrace();
        }
    }

    /**
     * Read from file.
     *
     * @param scores the scores
     * @return the string
     */
    private String readToString(String[] scores) {
        String line = "";
        try {
            int i = 0;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                scores[i] = data;
                i++;
            }
            myReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * Sort score.
     *
     * @param scores the scores
     */
    private void sortScore(String[] scores) {
        int[] intScores = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            String[] parts = scores[i].split(" ");
            intScores[i] = Integer.parseInt(parts[1]);
        }
        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores.length; j++) {
                if (intScores[i] > intScores[j]) {
                    int temp = intScores[i];
                    intScores[i] = intScores[j];
                    intScores[j] = temp;
                    String temp2 = scores[i];
                    scores[i] = scores[j];
                    scores[j] = temp2;
                }
            }
        }
    }

    /**
     * Get high scores sorted to array.
     *
     * @return the string array
     */
    public String[] getHighScores() {
        long lines = 1;
        try {
            Path path = Paths.get(file.getPath());
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] scores = new String[(int) lines];
        readToString(scores);
        sortScore(scores);
        return scores;
    }
}
