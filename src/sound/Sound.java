// 318211463 Ronen Sivak
package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

/**
 * The type Sound.
 */
public class Sound {

    private long currentFrame;
    private Clip clip;
    private String status;
    private final String filePath;

    /**
     * Instantiates a new Sound.
     *
     * @param filePath the file path
     */
    public Sound(String filePath) {
        this.filePath = filePath;
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            currentFrame = 0L;
            status = "Stopped";
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play.
     */
    public void play() {
        if (checkFileExists(filePath)) {
            return;
        }
        if (status.equals("Stopped")) {
            clip.start();
            status = "Playing";
        } else if (status.equals("Playing")) {
            clip.setFramePosition((int) currentFrame);
        }
    }

    /**
     * Check file exists boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return !file.exists();
    }
}
