package GraphicalUserInterface.CustomGame.Tiles;

/**
 * Created by rezab on 11/07/2016.
 */
public class StoryTile {
    private String story, imageDirectory;
     private int XOfTile, YOfTile;

    public StoryTile(String text, String imageDirectory, int XOfTile, int YOfTile) {
        this.story = text;
        this.imageDirectory = imageDirectory;
        this.YOfTile = YOfTile;
        this.XOfTile = XOfTile;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String text) {
        this.story = text;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public int getXOfTile() {
        return XOfTile;
    }

    public int getYOfTile() {
        return YOfTile;
    }
}
