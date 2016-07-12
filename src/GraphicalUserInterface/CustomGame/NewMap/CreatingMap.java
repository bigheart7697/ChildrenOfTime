package GraphicalUserInterface.CustomGame.NewMap;

import GraphicalUserInterface.CustomGame.Tiles.*;
import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rezab on 10/07/2016.
 */
public class CreatingMap extends JComponent {
    private BufferedImage BG, selectedBGTile, selectedFGTile, ability, battle, BGTile, door, lockedDoor, finalBoss, key, mapBG[][], mapFG[][], Obs1, Obs2, Obs3, Obs4, Obs5, Obs6, Obs7, Obs8, Obs9, Obs10, Obs11, Obs12, Obs13, Obs14, shop, story;
    private Font nmFont;
    private Color fontColor, sColor[] = new  Color[24];
    private SimpleMenuListener nmListener;
    private Boolean isFilled[][], isMapComplete = false;

    private Rectangle2D.Double  abilityTile, battleTile, backgroundTile, doorTile, lockedDoorTile, finalBossTile, keyTile, mapBGTile[][], Obs1Tile, Obs2Tile, Obs3Tile, Obs4Tile, Obs5Tile, Obs6Tile, Obs7Tile, Obs8Tile, Obs9Tile, Obs10Tile, Obs11Tile, Obs12Tile, Obs13Tile, Obs14Tile, shopTile, storyTile;
    private RoundRectangle2D.Double ok;
    private Ellipse2D.Double back, save;
    private NewMap NM;
    private ArrayList<BattleTile> BT = new ArrayList<>();
    private ArrayList<StoryTile> ST = new ArrayList<>();
    private ArrayList<DoorTile> DT = new ArrayList<>();
    private ArrayList<KeyTile> KT = new ArrayList<>();
    private ArrayList<ShopTile> SHT = new ArrayList<>();


    public CreatingMap(SimpleMenuListener nml, NewMap NM) {
        nmListener = nml;
        this.NM = NM;
        mapBG = new BufferedImage[NM.getSizeOfMap()][NM.getSizeOfMap()];
        mapFG = new BufferedImage[NM.getSizeOfMap()][NM.getSizeOfMap()];
        mapBGTile = new Rectangle2D.Double[NM.getSizeOfMap()][NM.getSizeOfMap()];
        isFilled = new Boolean[NM.getSizeOfMap()][NM.getSizeOfMap()];
        Arrays.fill(sColor, new Color(45,45,45));
        for (int cnt1 = 0; cnt1 < NM.getSizeOfMap(); cnt1++) {
            for (int cnt2 = 0; cnt2 < NM.getSizeOfMap(); cnt2++) {
                isFilled[cnt1][cnt2] = false;
            }
        }
        sColor[0] = Color.white;
        fontColor = Color.white;//new Color(166, 143, 78);

        try {
            nmFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(nmFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
            ability = ImageIO.read(new File("GameEnvGraphics/ability.png"));
            battle = ImageIO.read(new File("GameEnvGraphics/battle.png"));
            BGTile = ImageIO.read(new File("GameEnvGraphics/BGTile.jpg"));
            selectedFGTile = BGTile;
            selectedBGTile = BGTile;
            door = ImageIO.read(new File("GameEnvGraphics/Door1.png"));
            lockedDoor = ImageIO.read(new File("GameEnvGraphics/Door0.png"));
            finalBoss = ImageIO.read(new File("GameEnvGraphics/finalBoss.png"));
            key = ImageIO.read(new File("GameEnvGraphics/key.png"));
            for (int cnt1 = 0; cnt1 < NM.getSizeOfMap(); cnt1++) {
                for (int cnt2 = 0; cnt2 < NM.getSizeOfMap(); cnt2++) {
                    mapBG[cnt1][cnt2] = ImageIO.read(new File("GameEnvGraphics/MapBG.jpg"));
                    mapFG[cnt1][cnt2] = ImageIO.read(new File("GameEnvGraphics/MapBG.jpg"));
                }
            }
            Obs1 = ImageIO.read(new File("GameEnvGraphics/Obs1.png"));
            Obs2 = ImageIO.read(new File("GameEnvGraphics/Obs2.png"));
            Obs3 = ImageIO.read(new File("GameEnvGraphics/Obs3.png"));
            Obs4 = ImageIO.read(new File("GameEnvGraphics/Obs4.png"));
            Obs5 = ImageIO.read(new File("GameEnvGraphics/Obs5.png"));
            Obs6 = ImageIO.read(new File("GameEnvGraphics/Obs6.png"));
            Obs7 = ImageIO.read(new File("GameEnvGraphics/Obs7.png"));
            Obs8 = ImageIO.read(new File("GameEnvGraphics/Obs8.png"));
            Obs9 = ImageIO.read(new File("GameEnvGraphics/Obs9.png"));
            Obs10 = ImageIO.read(new File("GameEnvGraphics/Obs10.png"));
            Obs11 = ImageIO.read(new File("GameEnvGraphics/Obs11.png"));
            Obs12 = ImageIO.read(new File("GameEnvGraphics/Obs12.png"));
            Obs13 = ImageIO.read(new File("GameEnvGraphics/Obs13.png"));
            Obs14 = ImageIO.read(new File("GameEnvGraphics/Obs14.png"));
            shop = ImageIO.read(new File("GameEnvGraphics/shop.png"));
            story = ImageIO.read(new File("GameEnvGraphics/story.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (abilityTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[2] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = ability;
                }
                if (backgroundTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[0] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = BGTile;
                }
                if (battleTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[1] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = battle;
                }
                if (doorTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[3] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = door;
                }

                if (lockedDoorTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[4] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = lockedDoor;
                }

                if (keyTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[5] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = key;
                }

                if (storyTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[6] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = story;
                }

                if (shopTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[7] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = shop;
                }

                if (Obs1Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[8] = Color.white;
                    selectedBGTile = Obs1;
                    selectedFGTile = Obs1;
                }

                if (Obs2Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[9] = Color.white;
                    selectedBGTile = Obs2;
                    selectedFGTile = Obs2;
                }

                if (Obs3Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[10] = Color.white;
                    selectedBGTile = Obs3;
                    selectedFGTile = Obs3;
                }

                if (Obs4Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[11] = Color.white;
                    selectedBGTile = Obs4;
                    selectedFGTile = Obs4;
                }

                if (Obs5Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[12] = Color.white;
                    selectedBGTile = Obs5;
                    selectedFGTile = Obs5;
                }

                if (Obs6Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[13] = Color.white;
                    selectedBGTile = Obs6;
                    selectedFGTile = Obs6;
                }

                if (Obs7Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[14] = Color.white;
                    selectedBGTile = Obs7;
                    selectedFGTile = Obs7;
                }

                if (Obs8Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[15] = Color.white;
                    selectedBGTile = Obs8;
                    selectedFGTile = Obs8;
                }

                if (Obs9Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[16] = Color.white;
                    selectedBGTile = Obs9;
                    selectedFGTile = Obs9;
                }

                if (Obs10Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[17] = Color.white;
                    selectedBGTile = Obs10;
                    selectedFGTile = Obs10;
                }

                if (Obs11Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[18] = Color.white;
                    selectedBGTile = Obs11;
                    selectedFGTile = Obs11;
                }

                if (Obs12Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[19] = Color.white;
                    selectedBGTile = Obs12;
                    selectedFGTile = Obs12;
                }

                if (Obs13Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[20] = Color.white;
                    selectedBGTile = Obs13;
                    selectedFGTile = Obs13;
                }

                if (Obs14Tile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[21] = Color.white;
                    selectedBGTile = Obs14;
                    selectedFGTile = Obs14;
                }

                if (finalBossTile.contains(e.getX(), e.getY())) {
                    Arrays.fill(sColor, new Color(45,45,45));
                    sColor[22] = Color.white;
                    selectedBGTile = BGTile;
                    selectedFGTile = finalBoss;
                }

                for (int cnt1 = 0; cnt1 < NM.getSizeOfMap(); cnt1++) {
                    for (int cnt2 = 0; cnt2 < NM.getSizeOfMap(); cnt2++) {
                        if (mapBGTile[cnt1][cnt2].contains(e.getX(), e.getY())) {
                            mapBG[cnt1][cnt2] = selectedBGTile;
                            mapFG[cnt1][cnt2] = selectedFGTile;
                            isFilled[cnt1][cnt2] = true;
                            enteringInputs(selectedBGTile, selectedFGTile);
                        }
                    }
                }

                if (save.contains(e.getX(), e.getY())) {
                    if (!isMapComplete()) {
                        isMapComplete = true;
                        repaint();
                    }
                    else
                        nmListener.switchTo("custom");
                }
                if (back.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("new map");
                }
                if (ok.contains(e.getX(), e.getY())) {
                    isMapComplete = false;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                if (newMapButton.contains(e.getX(), e.getY())) {
//                    c1 = new Color(45,45,45);
//                }
//                if (newAbilityButton.contains(e.getX(), e.getY())) {
//                    c2 = new Color(45,45,45);
//                }
//                if (newItemButton.contains(e.getX(), e.getY())) {
//                    c3 = new Color(45,45,45);
//                }
//                if (newHeroClassButton.contains(e.getX(), e.getY())) {
//                    c4 = new Color(45,45,45);
//                }
//                if (newHeroButton.contains(e.getX(), e.getY())) {
//                    c5 = new Color(45,45,45);
//                }
//                if (newEnemyButton.contains(e.getX(), e.getY())) {
//                    c6 = new Color(45,45,45);
//                }
//                if (backButton.contains(e.getX(), e.getY())) {
//                    c7 = new Color(45,45,45);
//                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //c1 = c2 = c3 = c4 = c5 = c6 = c7 = buttonColor;
            }
        });


    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Background
        g2.drawImage(BG, 0, 0, getWidth(), getHeight(), null);

        g2.drawImage(BGTile, 1100, 50, 50, 50, null);
        backgroundTile = new Rectangle2D.Double(1100, 50, 50, 50);
        g2.setColor(sColor[0]);
        g2.drawRect(1100, 50, 50, 50);

        g2.drawImage(BGTile, 1045, 50, 50, 50, null);
        g2.drawImage(battle, 1045, 50, 50, 50, null);
        battleTile = new Rectangle2D.Double(1045, 50, 50, 50);
        g2.setColor(sColor[1]);
        g2.drawRect(1045, 50, 50, 50);

        g2.drawImage(BGTile, 990, 50, 50, 50, null);
        g2.drawImage(ability, 990, 50, 50, 50, null);
        abilityTile = new Rectangle2D.Double(990, 50, 50, 50);
        g2.setColor(sColor[2]);
        g2.drawRect(990, 50, 50, 50);

        g2.drawImage(BGTile, 935, 50, 50, 50, null);
        g2.drawImage(door, 935, 50, 50, 50, null);
        doorTile = new Rectangle2D.Double(935, 50, 50, 50);
        g2.setColor(sColor[3]);
        g2.drawRect(935, 50, 50, 50);

        g2.drawImage(BGTile, 1100, 105, 50, 50, null);
        g2.drawImage(lockedDoor, 1100, 105, 50, 50, null);
        lockedDoorTile = new Rectangle2D.Double(1100, 105, 50, 50);
        g2.setColor(sColor[4]);
        g2.drawRect(1100, 105, 50, 50);

        g2.drawImage(BGTile, 1045, 105, 50, 50, null);
        g2.drawImage(key, 1045, 105, 50, 50, null);
        keyTile = new Rectangle2D.Double(1045, 105, 50, 50);
        g2.setColor(sColor[5]);
        g2.drawRect(1045, 105, 50, 50);

        g2.drawImage(BGTile, 990, 105, 50, 50, null);
        g2.drawImage(story, 990, 105, 50, 50, null);
        storyTile = new Rectangle2D.Double(990, 105, 50, 50);
        g2.setColor(sColor[6]);
        g2.drawRect(990, 105, 50, 50);

        g2.drawImage(BGTile, 935, 105, 50, 50, null);
        g2.drawImage(shop, 935, 105, 50, 50, null);
        shopTile = new Rectangle2D.Double(935, 105, 50, 50);
        g2.setColor(sColor[7]);
        g2.drawRect(935, 105, 50, 50);

        g2.drawImage(Obs1, 1100, 160, 50, 50, null);
        Obs1Tile = new Rectangle2D.Double(1100, 160, 50, 50);
        g2.setColor(sColor[8]);
        g2.drawRect(1100, 160, 50, 50);

        g2.drawImage(Obs3, 990, 160, 50, 50, null);
        Obs3Tile = new Rectangle2D.Double(990, 160, 50, 50);
        g2.setColor(sColor[10]);
        g2.drawRect(990, 160, 50, 50);

        g2.drawImage(Obs2, 1045, 160, 50, 50, null);
        Obs2Tile = new Rectangle2D.Double(1045, 160, 50, 50);
        g2.setColor(sColor[9]);
        g2.drawRect(1045, 160, 50, 50);

        g2.drawImage(Obs4, 935, 160, 50, 50, null);
        Obs4Tile = new Rectangle2D.Double(935, 160, 50, 50);
        g2.setColor(sColor[11]);
        g2.drawRect(935, 160, 50, 50);

        g2.drawImage(Obs5, 1100, 215, 50, 50, null);
        Obs5Tile = new Rectangle2D.Double(1100, 215, 50, 50);
        g2.setColor(sColor[12]);
        g2.drawRect(1100, 215, 50, 50);

        g2.drawImage(Obs6, 1045, 215, 50, 50, null);
        Obs6Tile = new Rectangle2D.Double(1045, 215, 50, 50);
        g2.setColor(sColor[13]);
        g2.drawRect(1045, 215, 50, 50);

        g2.drawImage(Obs7, 990, 215, 50, 50, null);
        Obs7Tile = new Rectangle2D.Double(990, 215, 50, 50);
        g2.setColor(sColor[14]);
        g2.drawRect(990, 215, 50, 50);

        g2.drawImage(Obs8, 935, 215, 50, 50, null);
        Obs8Tile = new Rectangle2D.Double(935, 215, 50, 50);
        g2.setColor(sColor[15]);
        g2.drawRect(935, 215, 50, 50);

        g2.drawImage(Obs9, 1100, 270, 50, 50, null);
        Obs9Tile = new Rectangle2D.Double(1100, 270, 50, 50);
        g2.setColor(sColor[16]);
        g2.drawRect(1100, 270, 50, 50);

        g2.drawImage(Obs10, 1045, 270, 50, 50, null);
        Obs10Tile = new Rectangle2D.Double(1045, 270, 50, 50);
        g2.setColor(sColor[17]);
        g2.drawRect(1045, 270, 50, 50);

        g2.drawImage(Obs11, 990, 270, 50, 50, null);
        Obs11Tile = new Rectangle2D.Double(990, 270, 50, 50);
        g2.setColor(sColor[18]);
        g2.drawRect(990, 270, 50, 50);

        g2.drawImage(Obs12, 935, 270, 50, 50, null);
        Obs12Tile = new Rectangle2D.Double(935, 270, 50, 50);
        g2.setColor(sColor[19]);
        g2.drawRect(935, 270, 50, 50);

        g2.drawImage(Obs13, 1100, 325, 50, 50, null);
        Obs13Tile = new Rectangle2D.Double(1100, 325, 50, 50);
        g2.setColor(sColor[20]);
        g2.drawRect(1100, 325, 50, 50);

        g2.drawImage(Obs14, 1045, 325, 50, 50, null);
        Obs14Tile = new Rectangle2D.Double(1045, 325, 50, 50);
        g2.setColor(sColor[21]);
        g2.drawRect(1045, 325, 50, 50);

        g2.drawImage(BGTile, 990, 325, 50, 50, null);
        g2.drawImage(finalBoss, 990, 325, 50, 50, null);
        finalBossTile = new Rectangle2D.Double(990, 325, 50, 50);
        g2.setColor(sColor[22]);
        g2.drawRect(990, 325, 50, 50);

        g2.setColor(fontColor);
        g2.setFont(nmFont.deriveFont(25f));
        g2.drawRoundRect(925, 40, 265, 495, 20, 20);
        g2.drawString("First choose the tile,", 925, 450);
        g2.drawString("Then choose a tile in map.", 925, 500);

        for (int cnt1 = 0; cnt1 < NM.getSizeOfMap(); cnt1++) {
            for (int cnt2 = 0; cnt2 < NM.getSizeOfMap(); cnt2++) {
                g2.drawImage(mapBG[cnt1][cnt2], 50 + cnt1 * 50, 50 + cnt2 * 50, 50, 50, null);
                g2.drawImage(mapFG[cnt1][cnt2], 50 + cnt1 * 50, 50 + cnt2 * 50, 50, 50, null);
                mapBGTile[cnt1][cnt2] = new Rectangle2D.Double(50 + cnt1 * 50, 50 + cnt2 * 50, 50, 50);
                g2.setColor(sColor[23]);
                g2.drawRect(50 + cnt1 * 50, 50 + cnt2 * 50, 50, 50);
            }
        }




        back = new Ellipse2D.Double(1000, 800, 80, 80);
        g2.setColor(fontColor);
        g2.drawOval(1000, 800, 80, 80);

        ok = new RoundRectangle2D.Double(1100, 720, 60, 40, 20, 20);
        if (isMapComplete) {
            g2.setFont(nmFont.deriveFont(15f));
            g2.drawRoundRect(1100, 720, 60, 40, 20, 20);
            g2.drawRoundRect(900, 700, 265, 80, 20, 20);
            g2.drawString("Your map isn't complete!", 900, 740);
            g2.setFont(nmFont.deriveFont(25f));
            g2.drawString("OK", 1115, 750);
        }

        save = new Ellipse2D.Double(1100, 800, 80, 80);
        g2.setColor(fontColor);
        g2.drawOval(1100, 800, 80, 80);

        g2.setFont(nmFont.deriveFont(25f));
        g2.drawString("save", 1122, 845);
        g2.drawString("back", 1022, 845);


        g.drawImage(buffer, 0, 0, null);

    }

    public void update() {
        repaint();
    }

    public boolean isMapComplete() {
        for (int cnt1 = 0; cnt1 < NM.getSizeOfMap(); cnt1++) {
            for (int cnt2 = 0; cnt2 < NM.getSizeOfMap(); cnt2++) {
                if (!isFilled[cnt1][cnt2]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void enteringInputs(BufferedImage selectedBGTile, BufferedImage selectedFGTile) {
        if (selectedBGTile.equals(BGTile) && selectedFGTile.equals(battle)) {
            nmListener.switchTo("battle tile");
        }
        else if (selectedBGTile.equals(BGTile) && selectedFGTile.equals(story)) {
            nmListener.switchTo("story tile");
        }

        else if (selectedBGTile.equals(BGTile) && (selectedFGTile.equals(door) || selectedFGTile.equals(lockedDoor))) {
            nmListener.switchTo("door tile");
        }

        else if (selectedBGTile.equals(BGTile) && (selectedFGTile.equals(key))) {
            nmListener.switchTo("key tile");
        }

        else if (selectedBGTile.equals(BGTile) && (selectedFGTile.equals(shop))) {
            nmListener.switchTo("shop tile");
        }
    }

    public void addBattleTile(BattleTile BT) {
        this.BT.add(BT);
    }

    public void addStoryTile(StoryTile ST) {
        this.ST.add(ST);
    }

    public void addDoorTile(DoorTile DT) {
        this.DT.add(DT);
    }

    public void addKeyTile(KeyTile KT) { this.KT.add(KT); }

    public void addShopTile(ShopTile SHT) { this.SHT.add(SHT); }

}
