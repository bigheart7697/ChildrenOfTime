package GraphicalUserInterface.CustomGame;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rezab on 07/07/2016.
 */
public class NewMap extends JComponent {

    private BufferedImage BG;
    private Font nmFont;
    private Color fontColor;
    private SimpleMenuListener nmListener;
    private String scenarioName = "", story = "", breakMessage = "";
    private int sizeOfMap, experience, money, XOfStartingPoint, YOfStartingPoint;
    private boolean isInformationComplete = false;

    private RoundRectangle2D.Double scenario, mapSize, defeatMessage, earlyAmountse, startingPoint, ok;
    private Ellipse2D.Double back, next;


    public NewMap(SimpleMenuListener nml) {
        nmListener = nml;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (scenario.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("scenario");
                }
                if (defeatMessage.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("defeat message");
                }
                if (mapSize.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("map size");
                }
                if (earlyAmountse.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("early amounts");
                }
                if (startingPoint.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("starting point");
                }
                if (back.contains(e.getX(), e.getY())) {
                    nmListener.switchTo("custom");
                }
                if (next.contains(e.getX(), e.getY())) {
                    if (scenarioName.equals("") || story.equals("") || defeatMessage.equals("") || sizeOfMap == 0 || experience == 0 || money == 0 || XOfStartingPoint == 0 || YOfStartingPoint == 0) {
                        isInformationComplete = true;
                        repaint();
                    }
                    else
                        nmListener.switchTo("creating map");
                }
                if (ok.contains(e.getX(), e.getY())) {
                    isInformationComplete = false;
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



        //Buttons
        scenario = new RoundRectangle2D.Double((getWidth() / 2) - 150, 100, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 100, 300, 80, 60, 60);

        defeatMessage = new RoundRectangle2D.Double((getWidth() / 2) - 150, 200, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 200, 300, 80, 60, 60);

        mapSize = new RoundRectangle2D.Double((getWidth() / 2) - 150, 300, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 300, 300, 80, 60, 60);

        earlyAmountse = new RoundRectangle2D.Double((getWidth() / 2) - 150, 400, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 400, 300, 80, 60, 60);

        startingPoint = new RoundRectangle2D.Double((getWidth() / 2) - 150, 500, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 500, 300, 80, 60, 60);

        back = new Ellipse2D.Double(50, 600, 80, 80);
        g2.setColor(fontColor);
        g2.drawOval(50, 600, 80, 80);

        ok = new RoundRectangle2D.Double(1000, 620, 60, 40, 20, 20);
        if (isInformationComplete) {
            g2.setFont(nmFont.deriveFont(15f));
            g2.drawRoundRect(1000, 620, 60, 40, 20, 20);
            g2.drawRoundRect(800, 600, 265, 80, 20, 20);
            g2.drawString("Your information isn't complete!", 800, 640);
            g2.setFont(nmFont.deriveFont(25f));
            g2.drawString("OK", 1015, 650);
        }

        next = new Ellipse2D.Double(1100, 600, 80, 80);
        g2.setColor(fontColor);
        g2.drawOval(1100, 600, 80, 80);

        g2.setFont(nmFont.deriveFont(25f));
        g2.drawString("Scenario", (getWidth() / 2) - 50, 150);
        g2.drawString("Defeat message", (getWidth() / 2) - 70, 245);
        g2.drawString("Map size", (getWidth() / 2) - 60, 345);
        g2.drawString("Early amounts", (getWidth() / 2) - 70, 445);
        g2.drawString("Starting point", (getWidth() / 2) - 70, 545);
        g2.drawString("next", 1126, 645);
        g2.drawString("back", 76, 645);


        g.drawImage(buffer, 0, 0, null);

    }

    public void update() {
        repaint();
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getBreakMessage() {
        return breakMessage;
    }

    public void setBreakMessage(String breakMessage) {
        this.breakMessage = breakMessage;
    }

    public int getSizeOfMap() {
        return sizeOfMap;
    }

    public void setSizeOfMap(int size) {
        this.sizeOfMap = size;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getYOfStartingPoint() {
        return YOfStartingPoint;
    }

    public void setYOfStartingPoint(int YOfStartingPoint) {
        this.YOfStartingPoint = YOfStartingPoint;
    }

    public int getXOfStartingPoint() {
        return XOfStartingPoint;
    }

    public void setXOfStartingPoint(int XOfStartingPoint) {
        this.XOfStartingPoint = XOfStartingPoint;
    }
}
