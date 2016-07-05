package GraphicalUserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class GameEnv extends JComponent{

    private Image BGTile;
    private Image[] Obs;
    private Image[] Doors;
    private final static int BLOCK_SIZE = 50;

    private Map map;

    GameEnv() {

        map = new Map(16, 16);

        try {
            BGTile = ImageIO.read(new File("GameEnvGraphics/BGTile.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Obs = new Image[14];
        for (int i = 0; i < 14; i++) {
            try {
                Obs[i] = ImageIO.read(new File("GameEnvGraphics/Obs" + (i + 1) + ".png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        Doors = new Image[2];
        for (int i = 0; i < 2; i++) {
            try {
                Doors[i] = ImageIO.read(new File("GameEnvGraphics/Door" + i + ".png"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0,0,getWidth(), getHeight());


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                map.setTile(new Tile(BGTile, i + 1, j + 1, true, Tile.Type.tile), i + 1, j + 1);
            }
        }


        //Default scenario

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

        //Tiles
        for (int i = 1; i <= 16; i++) {
            for (int j = 1; j <= 16; j++) {
                g2.drawImage(map.getTile(i, j).getImage(), 50 * i, 50 * j, BLOCK_SIZE, BLOCK_SIZE, null);
            }
        }


        //Events

        //Doors
        g2.drawImage(Doors[0], 150, 200, BLOCK_SIZE, BLOCK_SIZE, null);
        g2.drawImage(Doors[0], 250, 250, BLOCK_SIZE, BLOCK_SIZE, null);
        g2.drawImage(Doors[0], 500, 250, BLOCK_SIZE, BLOCK_SIZE, null);
        g2.drawImage(Doors[0], 750, 300, BLOCK_SIZE, BLOCK_SIZE, null);
        g2.drawImage(Doors[0], 500, 550, BLOCK_SIZE, BLOCK_SIZE, null);


        g.drawImage(buffer, 0, 0, null);
    }

}

class Map {

    private Tile[][] Tiles;

    Map(int rows, int columns) {
        Tiles = new Tile[rows][columns];
    }

    Tile getTile(int row, int colmun) {
        return Tiles[row - 1][colmun - 1];
    }

    void setTile(Tile tile, int row, int column) {
        Tiles[row - 1][column - 1] = tile;
    }

}

class Tile {

    private Image image;
    enum Type { tile, obstacle }
    private Type type;
    private int x, y;
    private boolean passable;

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
        return passable;
    }
}