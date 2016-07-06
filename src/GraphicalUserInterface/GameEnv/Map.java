package GraphicalUserInterface.GameEnv;

import javax.swing.*;
import java.awt.*;

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


    public Image getImage() {
        return image;
    }
    public Type getType() {
        return type;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isPassable() {
        if (gameEvent != null)
            return passable && gameEvent.isPassable();
        else
            return passable;
    }

    public GameEvent getGameEvent() { return gameEvent; }
    public void setGameEvent(GameEvent gameEvent) { this.gameEvent = gameEvent; }
    public void removeGameEvent(GameEvent gameEvent) { this.gameEvent = null; }
}

class GameEvent {
    private Image image;

    private Tile tile;

    enum Type {doorLockedLeft, doorUnlockedLeft,
        doorLockedRight, doorUnlockedRight,
        doorLockedDown, doorUnlockedDown,
        doorLockedUp, doorUnlockedUp,
        key, battle, story, shop, ability, boss}

    private GameEvent relatedEvent;
    private Type type;
    private int x, y;
    private boolean passable;

    GameEvent(Image img, int row, int column, boolean passable, Type t, Tile tile) {
        this.image = img;
        this.x = row;
        this.y = column;
        this.passable = passable;
        this.type = t;
        relatedEvent = null;
        this.tile = tile;
        tile.setGameEvent(this);
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
    }

    public Image getImage() {
        return image;
    }
    public Type getType() {
        return type;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isPassable() {
        return passable;
    }

    public GameEvent getRelatedEvent() {
        return relatedEvent;
    }
    public void setRelatedEvent(GameEvent relatedEvent) {
        this.relatedEvent = relatedEvent;
    }

    public Tile getTile() { return tile; }

    public void fireEvent() {
        System.out.println(type);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}