package GraphicalUserInterface.GameEnv;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Scenario {

    private Map map;
    private int rows, columns;
    private Image BGTile, key, story, battle, shop, ability;
    private Image[] Obs, Doors;
    private String[] stories;

    private void imageLoader() {
        try {
            Obs = new Image[14];
            for (int i = 0; i < 14; i++) Obs[i] = ImageIO.read(new File("GameEnvGraphics/Obs" + (i + 1) + ".png"));

            Doors = new Image[2];
            for (int i = 0; i < 2; i++) Doors[i] = ImageIO.read(new File("GameEnvGraphics/Door" + i + ".png"));

            BGTile = ImageIO.read(new File("GameEnvGraphics/BGTile.jpg"));
            key = ImageIO.read(new File("GameEnvGraphics/key.png"));
            battle = ImageIO.read(new File("GameEnvGraphics/battle.png"));
            story = ImageIO.read(new File("GameEnvGraphics/story.png"));
            shop = ImageIO.read(new File("GameEnvGraphics/shop.png"));
            ability = ImageIO.read(new File("GameEnvGraphics/ability.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scenario() {

        imageLoader();

        //Default scenario

        map = new Map(rows = 16, columns = 16);

        //Tiles

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                map.setTile(new Tile(BGTile, i + 1, j + 1, true, Tile.Type.tile), i + 1, j + 1);
            }
        }

        //Around the Map
        map.setTile(new Tile(Obs[6], 1, 1, false, Tile.Type.obstacle), 1, 1);
        map.setTile(new Tile(Obs[7], 16, 1, false, Tile.Type.obstacle), 16, 1);
        map.setTile(new Tile(Obs[8], 16, 16, false, Tile.Type.obstacle), 16, 16);
        map.setTile(new Tile(Obs[9], 1, 16, false, Tile.Type.obstacle), 1, 16);

        for (int i = 2; i < 16; i++) {
            map.setTile(new Tile(Obs[0], 1, i, false, Tile.Type.obstacle), 1, i);
            map.setTile(new Tile(Obs[0], 16, i, false, Tile.Type.obstacle), 16, i);
            map.setTile(new Tile(Obs[1], i, 1, false, Tile.Type.obstacle), i, 1);
            map.setTile(new Tile(Obs[1], i, 16, false, Tile.Type.obstacle), i, 16);
        }

        //In Map Obstacles
        map.setTile(new Tile(Obs[11], 5, 16, false, Tile.Type.obstacle), 5, 16);
        map.setTile(new Tile(Obs[0], 5, 15, false, Tile.Type.obstacle), 5, 15);
        map.setTile(new Tile(Obs[0], 5, 14, false, Tile.Type.obstacle), 5, 14);
        map.setTile(new Tile(Obs[7], 5, 13, false, Tile.Type.obstacle), 5, 13);
        map.setTile(new Tile(Obs[1], 4, 13, false, Tile.Type.obstacle), 4, 13);
        map.setTile(new Tile(Obs[9], 3, 13, false, Tile.Type.obstacle), 3, 13);
        map.setTile(new Tile(Obs[0], 3, 12, false, Tile.Type.obstacle), 3, 12);
        map.setTile(new Tile(Obs[0], 3, 11, false, Tile.Type.obstacle), 3, 11);
        map.setTile(new Tile(Obs[2], 3, 10, false, Tile.Type.obstacle), 3, 10);

        map.setTile(new Tile(Obs[12], 1, 4, false, Tile.Type.obstacle), 1, 4);
        map.setTile(new Tile(Obs[3], 2, 4, false, Tile.Type.obstacle), 2, 4);
        map.setTile(new Tile(Obs[5], 4, 4, false, Tile.Type.obstacle), 4, 4);
        map.setTile(new Tile(Obs[1], 5, 4, false, Tile.Type.obstacle), 5, 4);
        map.setTile(new Tile(Obs[1], 6, 4, false, Tile.Type.obstacle), 6, 4);
        map.setTile(new Tile(Obs[11], 7, 4, false, Tile.Type.obstacle), 7, 4);
        map.setTile(new Tile(Obs[2], 7, 3, false, Tile.Type.obstacle), 7, 3);
        map.setTile(new Tile(Obs[1], 8, 4, false, Tile.Type.obstacle), 8, 4);
        map.setTile(new Tile(Obs[1], 9, 4, false, Tile.Type.obstacle), 9, 4);
        map.setTile(new Tile(Obs[3], 10, 4, false, Tile.Type.obstacle), 10, 4);

        map.setTile(new Tile(Obs[3], 7, 6, false, Tile.Type.obstacle), 7, 6);
        map.setTile(new Tile(Obs[1], 6, 6, false, Tile.Type.obstacle), 6, 6);
        map.setTile(new Tile(Obs[6], 5, 6, false, Tile.Type.obstacle), 5, 6);
        map.setTile(new Tile(Obs[0], 5, 7, false, Tile.Type.obstacle), 5, 7);
        map.setTile(new Tile(Obs[0], 5, 8, false, Tile.Type.obstacle), 5, 8);
        map.setTile(new Tile(Obs[9], 5, 9, false, Tile.Type.obstacle), 5, 9);
        map.setTile(new Tile(Obs[1], 6, 9, false, Tile.Type.obstacle), 6, 9);
        map.setTile(new Tile(Obs[1], 7, 9, false, Tile.Type.obstacle), 7, 9);
        map.setTile(new Tile(Obs[1], 8, 9, false, Tile.Type.obstacle), 8, 9);
        map.setTile(new Tile(Obs[1], 9, 9, false, Tile.Type.obstacle), 9, 9);
        map.setTile(new Tile(Obs[10], 10, 9, false, Tile.Type.obstacle), 10, 9);
        map.setTile(new Tile(Obs[0], 10, 8, false, Tile.Type.obstacle), 10, 8);
        map.setTile(new Tile(Obs[0], 10, 7, false, Tile.Type.obstacle), 10, 7);
        map.setTile(new Tile(Obs[2], 10, 6, false, Tile.Type.obstacle), 10, 6);
        map.setTile(new Tile(Obs[9], 10, 10, false, Tile.Type.obstacle), 10, 10);
        map.setTile(new Tile(Obs[1], 11, 10, false, Tile.Type.obstacle), 11, 10);
        map.setTile(new Tile(Obs[3], 12, 10, false, Tile.Type.obstacle), 12, 10);

        map.setTile(new Tile(Obs[11], 10, 16, false, Tile.Type.obstacle), 10, 16);
        map.setTile(new Tile(Obs[0], 10, 15, false, Tile.Type.obstacle), 10, 15);
        map.setTile(new Tile(Obs[0], 10, 14, false, Tile.Type.obstacle), 10, 14);
        map.setTile(new Tile(Obs[10], 10, 13, false, Tile.Type.obstacle), 10, 13);
        map.setTile(new Tile(Obs[6], 10, 12, false, Tile.Type.obstacle), 10, 12);
        map.setTile(new Tile(Obs[1], 9, 13, false, Tile.Type.obstacle), 9, 13);
        map.setTile(new Tile(Obs[5], 8, 13, false, Tile.Type.obstacle), 8, 13);
        map.setTile(new Tile(Obs[1], 11, 12, false, Tile.Type.obstacle), 11, 12);
        map.setTile(new Tile(Obs[3], 12, 12, false, Tile.Type.obstacle), 12, 12);

        map.setTile(new Tile(Obs[13], 14, 1, false, Tile.Type.obstacle), 14, 1);
        map.setTile(new Tile(Obs[0], 14, 2, false, Tile.Type.obstacle), 14, 2);
        map.setTile(new Tile(Obs[0], 14, 3, false, Tile.Type.obstacle), 14, 3);
        map.setTile(new Tile(Obs[0], 14, 4, false, Tile.Type.obstacle), 14, 4);
        map.setTile(new Tile(Obs[0], 14, 5, false, Tile.Type.obstacle), 14, 5);
        map.setTile(new Tile(Obs[4], 14, 6, false, Tile.Type.obstacle), 14, 6);
        map.setTile(new Tile(Obs[0], 16, 6, false, Tile.Type.obstacle), 16, 6);

        //Events

        //Doors
        GameEvent door2 = new GameEvent(Doors[0], 3, 4, false, GameEvent.Type.doorLockedUp, map.getTile(3, 4));
        GameEvent door1 = new GameEvent(Doors[0], 5, 5, false, GameEvent.Type.doorLockedLeft, map.getTile(5, 5));
        GameEvent door4 = new GameEvent(Doors[1], 10, 5, true, GameEvent.Type.doorUnlockedLeft, map.getTile(10, 5));
        GameEvent door3 = new GameEvent(Doors[0], 15, 6, false, GameEvent.Type.doorLockedDown, map.getTile(15, 6));
        GameEvent door5 = new GameEvent(Doors[1], 10, 11, true, GameEvent.Type.doorUnlockedLeft, map.getTile(10, 11));
        map.setEvent(door2, 3, 4);
        map.setEvent(door1, 5, 5);
        map.setEvent(door3, 15, 6);
        map.setEvent(door4, 10, 5);
        map.setEvent(door5, 10, 11);

        //Keys
        GameEvent key1 = new GameEvent(key, 12, 15, true, GameEvent.Type.key, map.getTile(12, 15), door1);
        GameEvent key2 = new GameEvent(key, 2, 2, true, GameEvent.Type.key, map.getTile(2, 2), door2);
        GameEvent key3 = new GameEvent(key, 6, 8, true, GameEvent.Type.key, map.getTile(6, 8), door3);
        map.setEvent(key1, 12, 15);
        map.setEvent(key2, 2, 2);
        map.setEvent(key3, 6, 8);

        //Shops
        GameEvent shop1 = new GameEvent(shop, 4, 14, false, GameEvent.Type.shop, map.getTile(4, 14));
        GameEvent shop2 = new GameEvent(shop, 4, 12, false, GameEvent.Type.shop, map.getTile(4, 12));
        GameEvent shop3 = new GameEvent(shop, 12, 9, false, GameEvent.Type.shop, map.getTile(12, 9));
        map.setEvent(shop1, 4, 14);
        map.setEvent(shop2, 4, 12);
        map.setEvent(shop3, 12, 9);

        //Story spots
        GameEvent story1 = new GameEvent(story, 2, 10, true, GameEvent.Type.story, map.getTile(2, 10));
        GameEvent story2 = new GameEvent(story, 15, 15, true, GameEvent.Type.story, map.getTile(15, 15));
        GameEvent story3 = new GameEvent(story, 9, 8, true, GameEvent.Type.story, map.getTile(9, 8));
        GameEvent story4 = new GameEvent(story, 15, 4, true, GameEvent.Type.story, map.getTile(15, 4));
        map.setEvent(story1, 2, 10);
        map.setEvent(story2, 15, 15);
        map.setEvent(story3, 9, 8);
        map.setEvent(story4, 15, 4);

        //Ability Upgrade points
        GameEvent abilityPoint1 = new GameEvent(ability, 4, 15, false, GameEvent.Type.ability, map.getTile(4, 15));
        GameEvent abilityPoint2 = new GameEvent(ability, 9, 10, false, GameEvent.Type.ability, map.getTile(9, 10));
        GameEvent abilityPoint3 = new GameEvent(ability, 13, 2, false, GameEvent.Type.ability, map.getTile(13, 2));
        map.setEvent(abilityPoint1, 4, 15);
        map.setEvent(abilityPoint2, 9, 10);
        map.setEvent(abilityPoint3, 13, 2);

        //Battles
        GameEvent battle1 = new GameEvent(battle, 2, 12, false, GameEvent.Type.battle, map.getTile(2, 12));
        GameEvent battle2 = new GameEvent(battle, 13, 11, false, GameEvent.Type.battle, map.getTile(13, 11));
        GameEvent battle3 = new GameEvent(battle, 7, 2, false, GameEvent.Type.battle, map.getTile(7, 2));
        GameEvent battle4 = new GameEvent(battle, 7, 5, false, GameEvent.Type.battle, map.getTile(7, 5));
        GameEvent battleOptional = new GameEvent(battle, 9, 14, false, GameEvent.Type.battle, map.getTile(9, 14));
        map.setEvent(battle1, 2, 12);
        map.setEvent(battle2, 13, 11);
        map.setEvent(battle3, 7, 2);
        map.setEvent(battle4, 7, 5);
        map.setEvent(battleOptional, 9, 14);


    }

    public Scenario(int num) {

        imageLoader();

    }

    Map getMap() {
        return map;
    }

    int getRows() { return rows; }
    int getColumns() { return columns; }

}
