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

class SinglePlayerMenu extends JComponent{

    private SinglePlayerMenuListener spmListener;

    private BufferedImage BG;
    private Font spmFont;
    private Color fontColor, buttonColor, c1, c2, c3;

    private RoundRectangle2D.Double defaultScenarioButton, customButton;
    private Ellipse2D.Double backButton;

    SinglePlayerMenu(SinglePlayerMenuListener spml) {

        spmListener = spml;

        fontColor = new Color(166, 143, 78);
        c1 = c2 = c3 = buttonColor = new Color(60, 60, 60);

        try {
            spmFont = Font.createFont(Font.TRUETYPE_FONT, new File("MainMenuGraphics/mainMenuFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(spmFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("SinglePlayerMenuGraphics/BG.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (defaultScenarioButton.contains(e.getX(), e.getY())) {
                    spmListener.switchTo(0);
                }
                if (customButton.contains(e.getX(), e.getY())) {
                    spmListener.switchTo(); // should be given an int as argument. Custom game stuff
                }
                if (backButton.contains(e.getX(), e.getY())) {
                    spmListener.switchTo();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (defaultScenarioButton.contains(e.getX(), e.getY())) {
                    c1 = new Color(45,45,45);
                    if (defaultScenarioButton == null) System.out.println(1);
                }
                if (customButton.contains(e.getX(), e.getY())) {
                    if (customButton == null) System.out.println(2);
                    c2 = new Color(45,45,45);
                }
                if (backButton.contains(e.getX(), e.getY())) {
                    if (backButton == null) System.out.println(3);
                    c3 = new Color(45,45,45);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) { c1 = c2 = c3 = buttonColor; }
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
        g2.setFont(spmFont.deriveFont(120f));
        g2.drawString("Choose", 80, 170);
        g2.setFont(spmFont.deriveFont(80f));
        g2.drawString("your", 215, 100);
        g2.setFont(spmFont.deriveFont(200f));
        g2.drawString("Fate", 335, 170);

        //Buttons
        g2.setColor(c1);
        if (defaultScenarioButton == null) defaultScenarioButton = new RoundRectangle2D.Double(130, 500, 300, 80, 60, 60);
        g2.fill(defaultScenarioButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 500, 300, 80, 60, 60);

        g2.setColor(c2);
        if (customButton == null) customButton = new RoundRectangle2D.Double(130, 600, 300, 80, 60, 60);
        g2.fill(customButton);
        g2.setColor(fontColor);
        g2.drawRoundRect(130, 600, 300, 80, 60, 60);

        g2.setColor(c3);
        if (backButton == null) backButton = new Ellipse2D.Double(1100, 600, 80, 80);
        g2.fill(backButton);
        g2.setColor(fontColor);
        g2.drawOval(1100, 600, 80, 80);

        g2.setFont(spmFont.deriveFont(25f));
        g2.drawString("Default Scenario", 215, 545);
        g2.drawString("Custom Scenario", 215, 645);
        g2.setFont(spmFont.deriveFont(20f));
        g2.drawString("Back", 1126, 645);


        g.drawImage(buffer, 0, 0, null);
    }

    void updateEnv() { repaint(); }

}
