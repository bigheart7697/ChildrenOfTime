package GraphicalUserInterface.CustomGame.Tiles;

/**
 * Created by rezab on 11/07/2016.
 */
public class StoryTile extends Tiles {

    private String story, imageDirectory;

    public StoryTile(String text, String imageDirectory) {
        this.story = text;
        this.imageDirectory = imageDirectory;
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

}
