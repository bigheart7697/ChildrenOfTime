package GraphicalUserInterface.CustomGame.NewItem;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rezab on 07/07/2016.
 */
public class NewItem extends JComponent {

    private BufferedImage BG;
    private Font nmFont;
    private Color fontColor;
    private SimpleMenuListener naListener;

    private RoundRectangle2D.Double immediateEffect, equipment, consumable, back;


    public NewItem(SimpleMenuListener nml) {

        naListener = nml;
        fontColor = Color.white;//new Color(166, 143, 78);

        try {
            nmFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(nmFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NI.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (immediateEffect.contains(e.getX(), e.getY())) {
                    naListener.switchTo("immediate effect");
                }
                if (consumable.contains(e.getX(), e.getY())) {
                    naListener.switchTo("consumable");
                }
                if (equipment.contains(e.getX(), e.getY())) {
                    naListener.switchTo("equipment");
                }
                if (back.contains(e.getX(), e.getY())) {
                    naListener.switchTo("custom");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

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
        immediateEffect = new RoundRectangle2D.Double(50, 100, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect(50, 100, 300, 80, 60, 60);

        equipment = new RoundRectangle2D.Double(900, 100, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect(900, 100, 300, 80, 60, 60);

        consumable = new RoundRectangle2D.Double(50, 300, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect(50, 300, 300, 80, 60, 60);

        back = new RoundRectangle2D.Double(900, 300, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect(900, 300, 300, 80, 60, 60);



        g2.setColor(fontColor);

        g2.setFont(nmFont.deriveFont(25f));
        g2.drawString("immediate effect", 130, 150);
        g2.drawString("equipment", 990, 150);
        g2.drawString("consumable", 150, 345);
        g2.drawString("back", 1060 - 40, 345);



        g.drawImage(buffer, 0, 0, null);

    }

    public void update() {
        repaint();
    }
}
