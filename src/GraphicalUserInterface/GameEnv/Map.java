package GraphicalUserInterface.GameEnv;

import units.Enemy;
import units.Unit;

import java.awt.*;
import java.util.ArrayList;

class Map {

    private Tile[][] Tiles;
    private GameEvent[][] GameEvents;

    Map(int rows, int columns) {
        Tiles = new Tile[rows][columns];
        GameEvents = new GameEvent[rows][columns];
    }

    Tile getTile(int row, int column) {
        return Tiles[row - 1][column - 1];
    }
    void setTile(Tile tile, int row, int column) {
        Tiles[row - 1][column - 1] = tile;
    }

    GameEvent getEvent(int row, int column) {
        return GameEvents[row - 1][column - 1];
    }
    void setEvent(GameEvent ge, int row, int column) {
        GameEvents[row - 1][column - 1] = ge;
    }


}

class Tile {

    private Image image;
    enum Type { tile, obstacle }
    private Type type;
    private int x, y;
    private boolean passable;
    private GameEvent gameEvent;

    Tile(Image img, int row, int column, boolean passable, Type t) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
    }


    Image getImage() {
        return image;
    }
    public Type getType() {
        return type;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    boolean isPassable() {
        if (gameEvent != null)
            return passable && gameEvent.isPassable();
        else
            return passable;
    }

    public GameEvent getGameEvent() { return gameEvent; }
    void setGameEvent(GameEvent gameEvent) { this.gameEvent = gameEvent; }
    public void removeGameEvent(GameEvent gameEvent) { this.gameEvent = null; }
}

class GameEvent {
    private Image image, storyImage;

    private Tile tile;

    enum Type {doorLockedLeft, doorUnlockedLeft,
        doorLockedRight, doorUnlockedRight,
        doorLockedDown, doorUnlockedDown,
        doorLockedUp, doorUnlockedUp,
        key, battle, story, shop, ability}

    private GameEvent relatedEvent;
    private Type type;
    private int x, y;
    private boolean passable;

    private String info;

    private ArrayList<Unit> enemies;
    private int[] rewards;


    GameEvent(Image img, int row, int column, boolean passable, Type t, Tile tile) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
        relatedEvent = null;
        this.tile = tile;
        tile.setGameEvent(this);
        info = null;
    }
    GameEvent(Image img, int row, int column, boolean passable, Type t, Tile tile, GameEvent relatedEvent) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
        this.relatedEvent = relatedEvent;
        relatedEvent.setRelatedEvent(this);
        this.tile = tile;
        tile.setGameEvent(this);
        info = null;
    }
    GameEvent(Image img, int row, int column, boolean passable, Type t, Tile tile, String info, Image storyImage) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
        relatedEvent = null;
        this.tile = tile;
        tile.setGameEvent(this);
        this.info = info;
        this.storyImage = storyImage;
    }
    GameEvent(Image img, int row, int column, boolean passable, Type t, Tile tile, String info, ArrayList<Unit> enemies, int gold, int xp) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
        relatedEvent = null;
        this.tile = tile;
        tile.setGameEvent(this);
        this.info = info;
        this.enemies = enemies;
        rewards = new int[2];
        rewards[0] = gold; rewards[1] = xp;
    }



    Image getImage() {
        return image;
    }
    private void setImage(Image img) { image = img; }
    Image getStoryImage() { return storyImage; }

    Type getType() {
        return type;
    }
    private void setType(Type t) { type = t; }

    int getX() {
        return x;
    }
    int getY() {
        return y;
    }

    boolean isPassable() {
        return passable;
    }

    private void setRelatedEvent(GameEvent relatedEvent) {
        this.relatedEvent = relatedEvent;
    }

    Tile getTile() { return tile; }

    String getInfo() { return info; }

    ArrayList<Unit> getEnemies() { return enemies; }
    int[] getRewards() { return rewards; }

    void unlockDoor(Image img) {
        switch (relatedEvent.getType()) {
            case doorLockedDown:
                relatedEvent.setType(Type.doorUnlockedDown);
                relatedEvent.setImage(img);
                break;
            case doorLockedUp:
                relatedEvent.setType(Type.doorUnlockedUp);
                relatedEvent.setImage(img);
                break;
            case doorLockedRight:
                relatedEvent.setType(Type.doorUnlockedRight);
                relatedEvent.setImage(img);
                break;
            case doorLockedLeft:
                relatedEvent.setType(Type.doorUnlockedLeft);
                relatedEvent.setImage(img);
                break;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}