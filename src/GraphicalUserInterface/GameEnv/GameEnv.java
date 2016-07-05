package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEnv extends JComponent{

    private EnvironmentMgr emgr;

    private final static int BLOCK_SIZE = 50;
    private final static double SPEED = 1.1;

    private Image movChar[];
    int charImage;
    private double CharX, CharY;
    private double xSpeed, ySpeed;
    private boolean speedFlag1, speedFlag2, speedFlag3, speedFlag4;
    private Scenario scenario;

    public GameEnv(EnvironmentMgr emgr) {

        this.emgr = emgr;

        charImage = 0;
        xSpeed = 0.0;
        ySpeed = 0.0;

        CharX = 100;
        CharY = 730;

        speedFlag1 = speedFlag2 = speedFlag3 = speedFlag4 = false;

        scenario = new Scenario();
        movChar = new Image[4];
        try {
            movChar[0] = ImageIO.read(new File("GameEnvGraphics/MoveCharBack.png"));
            movChar[1] = ImageIO.read(new File("GameEnvGraphics/MoveCharLeft.png"));
            movChar[2] = ImageIO.read(new File("GameEnvGraphics/MoveCharFront.png"));
            movChar[3] = ImageIO.read(new File("GameEnvGraphics/MoveCharRight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (emgr.getCurrentCard().equalsIgnoreCase("game")) {

                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (!speedFlag3) {
                            charImage = 0;
                            speedFlag1 = true;
                            ySpeed = -SPEED;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!speedFlag4) {
                            charImage = 1;
                            speedFlag2 = true;
                            xSpeed = -SPEED;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!speedFlag1) {
                            charImage = 2;
                            speedFlag3 = true;
                            ySpeed = SPEED;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!speedFlag2) {
                            charImage = 3;
                            speedFlag4 = true;
                            xSpeed = SPEED;
                        }
                        break;
                    }
                }
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (speedFlag1) {
                            if (speedFlag2) charImage = 1;
                            if (speedFlag4) charImage = 3;
                            speedFlag1 = false;
                            ySpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (speedFlag2) {
                            if (speedFlag1) charImage = 0;
                            if (speedFlag3) charImage = 2;
                            speedFlag2 = false;
                            xSpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (speedFlag3) {
                            if (speedFlag2) charImage = 1;
                            if (speedFlag4) charImage = 3;
                            speedFlag3 = false;
                            ySpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (speedFlag4) {
                            if (speedFlag1) charImage = 0;
                            if (speedFlag3) charImage = 2;
                            speedFlag4 = false;
                            xSpeed = 0.0;
                        }
                        break;
                    }
                }
            }
            return false;
        });


    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0,0,getWidth(), getHeight());

        //Drawing the Scenario Map
        for (int i = 1; i <= 16; i++) {
            for (int j = 1; j <= 16; j++) {
                g2.drawImage(scenario.getMap().getTile(i, j).getImage(), 50 * i, 50 * j, BLOCK_SIZE, BLOCK_SIZE, null);
                if (scenario.getMap().getEvent(i, j) != null)
                    g2.drawImage(scenario.getMap().getEvent(i, j).getImage(), 50 * i, 50 * j, BLOCK_SIZE, BLOCK_SIZE, null);
            }
        }

        //The moving Character
        g2.drawImage(movChar[charImage], (int)CharX, (int)CharY, BLOCK_SIZE, BLOCK_SIZE, null);

        g.drawImage(buffer, 0, 0, null);
    }

    public void update() {

        CharX += xSpeed;
        CharY += ySpeed;

        Rectangle2D.Double charRect = new Rectangle2D.Double(CharX + 10, CharY + 10, BLOCK_SIZE - 20, BLOCK_SIZE - 5);
        for (int i = 0; i < scenario.getRows(); i++) {
            for (int j = 0; j < scenario.getColumns(); j++) {
                Rectangle2D.Double tileRect = new Rectangle2D.Double(scenario.getMap().getTile(
                        i + 1, j + 1).getX() * 50, scenario.getMap().getTile(i + 1, j + 1).getY() * 50, BLOCK_SIZE, BLOCK_SIZE);
                while (tileRect.intersects(charRect) && !scenario.getMap().getTile(i + 1, j + 1).isPassable()) {

                    CharX -= xSpeed;
                    CharY -= ySpeed;
                    charRect = new Rectangle2D.Double(CharX + 10, CharY + 10, BLOCK_SIZE - 20, BLOCK_SIZE - 5);
                }
            }
        }

        repaint();
    }


}
