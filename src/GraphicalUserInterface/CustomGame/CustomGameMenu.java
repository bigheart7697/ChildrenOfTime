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

public class CustomGameMenu extends JComponent {

    private SimpleMenuListener cgmListener;

    private BufferedImage BG;
    private Font cgmFont;
    private Color fontColor, buttonColor, c1, c2, c3, c4, c5, c6, c7;

    private RoundRectangle2D.Double newMapButton, newAbilityButton, newItemButton, newHeroClassButton, newHeroButton, newEnemyButton;
    private Ellipse2D.Double backButton;

    public CustomGameMenu(SimpleMenuListener cgml) {

        cgmListener = cgml;
        fontColor = new Color(166, 143, 78);
        c1 = c2 = c3 = c4 = c5 = c6 = c7 = buttonColor = new Color(60, 60, 60);

        try {
            cgmFont = Font.createFont(Font.TRUETYPE_FONT, new File("MainMenuGraphics/mainMenuFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(cgmFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/CGM1.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (newMapButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new map");
                }
                if (newAbilityButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new ability");
                }
                if (newItemButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new item");
                }
                if (newHeroClassButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new hero class");
                }
                if (newHeroButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new hero");
                }
                if (newEnemyButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("new enemy");
                }
                if (backButton.contains(e.getX(), e.getY())) {
                    cgmListener.switchTo("main");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (newMapButton.contains(e.getX(), e.getY())) {
                    c1 = new Color(45,45,45);
                }
                if (newAbilityButton.contains(e.getX(), e.getY())) {
                    c2 = new Color(45,45,45);
                }
                if (newItemButton.contains(e.getX(), e.getY())) {
                    c3 = new Color(45,45,45);
                }
                if (newHeroClassButton.contains(e.getX(), e.getY())) {
                    c4 = new Color(45,45,45);
                }
                if (newHeroButton.contains(e.getX(), e.getY())) {
                    c5 = new Color(45,45,45);
                }
                if (newEnemyButton.contains(e.getX(), e.getY())) {
                    c6 = new Color(45,45,45);
                }
                if (backButton.contains(e.getX(), e.getY())) {
                    c7 = new Color(45,45,45);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                c1 = c2 = c3 = c4 = c5 = c6 = c7 = buttonColor;
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


        //Beautiful Stereo-type !
        g2.setColor(fontColor);
        g2.setFont(cgmFont.deriveFont(80f));
        g2.drawString("Make your", 570, 120);
        g2.setFont(cgmFont.deriveFont(200f));
        g2.drawString("Dreams", 670, 300);
        g2.setFont(cgmFont.deriveFont(120f));
        g2.drawString("come true", 900, 400);


        //Buttons
        g2.setColor(c1);
        newMapButton = new RoundRectangle2D.Double(130, 100, 300, 80, 60, 60);
        g2.fill(newMapButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 100, 300, 80, 60, 60);

        g2.setColor(c2);
        newAbilityButton = new RoundRectangle2D.Double(130, 200, 300, 80, 60, 60);
        g2.fill(newAbilityButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 200, 300, 80, 60, 60);

        g2.setColor(c3);
        newItemButton = new RoundRectangle2D.Double(130, 300, 300, 80, 60, 60);
        g2.fill(newItemButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 300, 300, 80, 60, 60);

        g2.setColor(c4);
        newHeroClassButton = new RoundRectangle2D.Double(130, 400, 300, 80, 60, 60);
        g2.fill(newHeroClassButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 400, 300, 80, 60, 60);

        g2.setColor(c5);
        newHeroButton = new RoundRectangle2D.Double(130, 500, 300, 80, 60, 60);
        g2.fill(newHeroButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 500, 300, 80, 60, 60);

        g2.setColor(c6);
        newEnemyButton = new RoundRectangle2D.Double(130, 600, 300, 80, 60, 60);
        g2.fill(newEnemyButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 600, 300, 80, 60, 60);

        g2.setColor(c7);
        backButton = new Ellipse2D.Double(1100, 600, 80, 80);
        g2.fill(backButton);
        g2.setColor(fontColor);
        g2.drawOval(1100, 600, 80, 80);

        g2.setFont(cgmFont.deriveFont(25f));
        g2.drawString("New Map", 235, 145);
        g2.drawString("New Ability", 230, 245);
        g2.drawString("New Item", 245, 345);
        g2.drawString("New Hero Class", 220, 445);
        g2.drawString("New Hero", 235, 545);
        g2.drawString("New Enemy", 230, 645);
        g2.setFont(cgmFont.deriveFont(20f));
        g2.drawString("Back", 1126, 645);


        g.drawImage(buffer, 0, 0, null);
    }

    public void update() {
        repaint();
    }
}
