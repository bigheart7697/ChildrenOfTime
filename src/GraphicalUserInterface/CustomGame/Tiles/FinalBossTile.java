package GraphicalUserInterface.CustomGame.Tiles;

import units.Enemy;

import java.util.ArrayList;

/**
 * Created by rezab on 11/07/2016.
 */
public class FinalBossTile {
    private String story, imageDirectory;
    private ArrayList<Enemy> enemies = new ArrayList<>();


    public FinalBossTile(String story, String imageDirectory) {
        this.story = story;
        this.imageDirectory = imageDirectory;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
}
