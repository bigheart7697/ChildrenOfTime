package GraphicalUserInterface.CustomGame.Tiles;

import units.Enemy;

import java.util.ArrayList;

/**
 * Created by rezab on 11/07/2016.
 */
public class FinalBossTile extends Tiles {

    private String story, imageDirectory;
    private ArrayList<String> enemies = new ArrayList<>();


    public FinalBossTile(String story, String imageDirectory) {
        this.story = story;
        this.imageDirectory = imageDirectory;
    }

    public void addEnemy(String enemy) {
        enemies.add(enemy);
    }
    public ArrayList<String> getEnemies() {
        return enemies;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
