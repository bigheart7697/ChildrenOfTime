package GraphicalUserInterface;

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

class MainMenu extends JComponent{

    private MainMenuListener mmListener;

    private BufferedImage BG;
    private Font mmFont;
    private Color fontColor, buttonColor, c1, c2, c3, c4, c5;

    private RoundRectangle2D.Double singlePlayButton, customGameButton, multiPlayButton;
    private Ellipse2D.Double settingsButton, exitButton;

    MainMenu(MainMenuListener mml) {

        mmListener = mml;

        fontColor = new Color(166, 143, 78);
        c1 = c2 = c3 = c4 = c5 = buttonColor = new Color(60, 60, 60);

        try {
            mmFont = Font.createFont(Font.TRUETYPE_FONT, new File("MainMenuGraphics/mainMenuFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mmFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("MainMenuGraphics/BG.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (singlePlayButton.contains(e.getX(), e.getY())) {
                    mmListener.switchTo("single");
                }
                if (customGameButton.contains(e.getX(), e.getY())) {
                    mmListener.switchTo("custom");
                }
                if (multiPlayButton.contains(e.getX(), e.getY())) {
                    mmListener.switchTo("pvp");
                }
                if (settingsButton.contains(e.getX(), e.getY())) {
                    mmListener.switchTo("settings");
                }
                if (exitButton.contains(e.getX(), e.getY())) {
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (singlePlayButton.contains(e.getX(), e.getY())) {
                    c1 = new Color(45,45,45);
                }
                if (customGameButton.contains(e.getX(), e.getY())) {
                    c2 = new Color(45,45,45);
                }
                if (multiPlayButton.contains(e.getX(), e.getY())) {
                    c3 = new Color(45,45,45);
                }
                if (settingsButton.contains(e.getX(), e.getY())) {
                    c4 = new Color(45,45,45);
                }
                if (exitButton.contains(e.getX(), e.getY())) {
                    c5 = new Color(45,45,45);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                c1 = c2 = c3 = c4 = c5 = buttonColor;
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


        //Children Of Time
        g2.setColor(fontColor);
        g2.setFont(mmFont.deriveFont(120f));
        g2.drawString("Children", 770, 120);
        g2.setFont(mmFont.deriveFont(80f));
        g2.drawString("Of", 920, 200);
        g2.setFont(mmFont.deriveFont(200f));
        g2.drawString("Time", 860, 360);


        //Buttons
        g2.setColor(c1);
        if (singlePlayButton == null) singlePlayButton = new RoundRectangle2D.Double(130, 600, 160, 80, 60, 60);
        g2.fill(singlePlayButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 600, 160, 80, 60, 60);

        g2.setColor(c2);
        if (customGameButton == null) customGameButton = new RoundRectangle2D.Double(330, 600, 160, 80, 60, 60);
        g2.fill(customGameButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(330, 600, 160, 80, 60, 60);

        g2.setColor(c3);
        if (multiPlayButton == null) multiPlayButton = new RoundRectangle2D.Double(530, 600, 160, 80, 60, 60);
        g2.fill(multiPlayButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(530, 600, 160, 80, 60, 60);

        g2.setColor(c4);
        if (settingsButton == null) settingsButton = new Ellipse2D.Double(1000, 600, 80, 80);
        g2.fill(settingsButton);
        g2.setColor(fontColor);
        g2.drawOval(1000, 600, 80, 80);

        g2.setColor(c5);
        if (exitButton == null) exitButton = new Ellipse2D.Double(1100, 600, 80, 80);
        g2.fill(exitButton);
        g2.setColor(fontColor);
        g2.drawOval(1100, 600, 80, 80);

        g2.setFont(mmFont.deriveFont(25f));
        g2.drawString("Single Player", 150, 645);
        g2.drawString("Custom Game", 355, 645);
        g2.drawString("Multi Player", 555, 645);
        g2.setFont(mmFont.deriveFont(20f));
        g2.drawString("Settings", 1012, 645);
        g2.drawString("Exit", 1126, 645);


        g.drawImage(buffer, 0, 0, null);
    }

    void update() {
        repaint();
    }
}
