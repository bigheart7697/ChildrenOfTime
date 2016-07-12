package GraphicalUserInterface.CustomGame.NewAbility;

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
public class NewAbility extends JComponent {
    private BufferedImage BG;
    private Font nmFont;
    private Color fontColor;
    private SimpleMenuListener naListener;

    private RoundRectangle2D.Double selfBoost, attackModifier, restorer, attacker, back;


    public NewAbility(SimpleMenuListener nml) {
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
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NA.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selfBoost.contains(e.getX(), e.getY())) {
                    naListener.switchTo("self boost");
                }
                if (restorer.contains(e.getX(), e.getY())) {
                    naListener.switchTo("restorer");
                }
                if (attackModifier.contains(e.getX(), e.getY())) {
                    naListener.switchTo("attack modifier");
                }
                if (attacker.contains(e.getX(), e.getY())) {
                    naListener.switchTo("attacker");
                }
                if (back.contains(e.getX(), e.getY())) {
                    naListener.switchTo("custom");
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
        selfBoost = new RoundRectangle2D.Double((getWidth() / 2) - 150, 100, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 100, 300, 80, 60, 60);

        restorer = new RoundRectangle2D.Double((getWidth() / 2) - 150, 200, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 200, 300, 80, 60, 60);

        attackModifier = new RoundRectangle2D.Double((getWidth() / 2) - 150, 300, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 300, 300, 80, 60, 60);

        attacker = new RoundRectangle2D.Double((getWidth() / 2) - 150, 400, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 400, 300, 80, 60, 60);

        back = new RoundRectangle2D.Double((getWidth() / 2) - 150, 500, 300, 80, 60, 60);
        g2.setColor(fontColor);
        g2.drawRoundRect((getWidth() / 2) - 150, 500, 300, 80, 60, 60);


        g2.setColor(fontColor);

        g2.setFont(nmFont.deriveFont(25f));
        g2.drawString("self boost", (getWidth() / 2) - 50, 150);
        g2.drawString("attack modifier", (getWidth() / 2) - 70, 245);
        g2.drawString("restorer", (getWidth() / 2) - 40, 345);
        g2.drawString("attacker", (getWidth() / 2) - 40, 445);
        g2.drawString("back", (getWidth() / 2) - 20, 545);


        g.drawImage(buffer, 0, 0, null);

    }

    public void update() {
        repaint();
    }

}
