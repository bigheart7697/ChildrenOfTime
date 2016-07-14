package GraphicalUserInterface;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {

    private static int cnt = 0;

    private static AudioStream BGM;

    public static void playMusic(String path) {
        if (path.contains("game")) {
            cnt ++;
            path += cnt + ".wav";
        }
        if (path.contains("main")) cnt = 0;
        try {
            InputStream test = new FileInputStream(path);
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
        } catch(IOException error) {
            System.out.print(error.toString());
        }
    }
    public static void stopMusic() {
        if (BGM != null) {
            AudioPlayer.player.stop(BGM);
        }
    }

}
