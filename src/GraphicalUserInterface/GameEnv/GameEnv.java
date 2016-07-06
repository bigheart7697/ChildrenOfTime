package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEnv extends JComponent{

    private EnvironmentMgr emgr;

    private final static int BLOCK_SIZE = 50;
    private final static double SPEED = 2.1;

    private Image movChar[];
    private int charImage;
    private double CharX, CharY;
    private double xSpeed, ySpeed;
    private boolean speedFlag1, speedFlag2, speedFlag3, speedFlag4;
    private Scenario scenario;

    private DialogBox dialogBox;
    private boolean dialogBoxFlag, dialogTypeFlag, eventOccurFlag;
    String message;
    
    private Graphics2D g2;

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
        
        dialogBox = new DialogBox();
        dialogBoxFlag = false;
        

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dialogBoxFlag) {
                    if (dialogBox.getButtonGotIt().contains(e.getX(), e.getY()) ||
                            dialogBox.getButtonNo().contains(e.getX(), e.getY())) {
                        dialogBoxFlag = false;
                    }
                    if (dialogBox.getButtonYes().contains(e.getX(), e.getY())) {
                        //run some stuff
                        dialogBoxFlag = false;
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) buffer.getGraphics();
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

        //Dialog Box
        if (dialogBoxFlag) dialogBox.drawDialogBox(message, dialogTypeFlag);


        g.drawImage(buffer, 0, 0, null);
    }


    public void update() {

        if (!dialogBoxFlag) {
            CharX += xSpeed;
            CharY += ySpeed;


            //Intersection with blocks and events fired handled below
            Rectangle2D.Double charRect = new Rectangle2D.Double(CharX + 10, CharY + 15, BLOCK_SIZE - 20, BLOCK_SIZE - 10);
            for (int i = 0; i < scenario.getRows(); i++) {
                for (int j = 0; j < scenario.getColumns(); j++) {

                    Tile tile = scenario.getMap().getTile(i + 1, j + 1);
                    Rectangle2D.Double tileRect = new Rectangle2D.Double(tile.getX() * 50, tile.getY() * 50, BLOCK_SIZE, BLOCK_SIZE);

                    GameEvent event = scenario.getMap().getEvent(i + 1, j + 1);

                    //Unlocked Doors
                    if (event != null && event.getType() == GameEvent.Type.doorUnlockedLeft) {
                        if (charRect.getX() > event.getX() * 50 && tileRect.intersects(charRect)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            CharX = (event.getX() + 1) * 50;
                        }
                    }
                    else if (event != null && event.getType() == GameEvent.Type.doorUnlockedRight) {
                        if (charRect.getX() < event.getX() * 50 && tileRect.intersects(charRect)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            CharX = (event.getX() - 1) * 50;
                        }
                    }
                    else if (event != null && event.getType() == GameEvent.Type.doorUnlockedUp) {
                        if (charRect.getX() < event.getY() * 50 && tileRect.intersects(charRect)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            CharY = (event.getY() + 1) * 50;
                        }
                    }
                    else if (event != null && event.getType() == GameEvent.Type.doorUnlockedDown) {
                        if (charRect.getX() > event.getY() * 50 && tileRect.intersects(charRect)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            CharY = (event.getY() - 1) * 50;
                        }
                    }

                    //Locked Doors
//                else if (event != null && event.getType() == GameEvent.Type.doorLockedLeft) {
//                    if (charRect.getX() > event.getX() * 50 && tileRect.intersects(charRect)) {
//                        CharX = (event.getX() + 1) * 50;
//                    }
//                }
//                else if (event != null && event.getType() == GameEvent.Type.doorLockedRight) {
//                    if (charRect.getX() < event.getX() * 50 && tileRect.intersects(charRect)) {
//                        CharX = (event.getX() - 1) * 50;
//                    }
//                }
//                else if (event != null && event.getType() == GameEvent.Type.doorLockedUp) {
//                    if (charRect.getX() < event.getY() * 50 && tileRect.intersects(charRect)) {
//                        CharY = (event.getY() + 1) * 50;
//                    }
//                }
//                else if (event != null && event.getType() == GameEvent.Type.doorLockedDown) {
//                    if (charRect.getX() > event.getY() * 50 && tileRect.intersects(charRect)) {
//                        CharY = (event.getY() - 1) * 50;
//                    }
//                }

                    else if (tileRect.intersects(charRect) && event != null) {

                        message = "Enter " + event.getType() + "?";
                        dialogTypeFlag = true;
                        dialogBoxFlag = true;

                        event.fireEvent();

                        if (tile.getGameEvent() != null &&!scenario.getMap().getEvent(i + 1, j + 1).isPassable()) {
                            CharX = charRect.getX() - 10; CharY = charRect.getY() - 15;
                        }

                        if (event.getType() == GameEvent.Type.battle || event.getType() == GameEvent.Type.key || event.getType() == GameEvent.Type.story) {
                            scenario.getMap().setEvent(null, i + 1, j + 1);
                            event.getTile().setGameEvent(null);
                        }

                    }


                    while (tileRect.intersects(charRect) && !scenario.getMap().getTile(i + 1, j + 1).isPassable()) {

                        CharX -= xSpeed;
                        CharY -= ySpeed;
                        charRect = new Rectangle2D.Double(CharX + 10, CharY + 15, BLOCK_SIZE - 20, BLOCK_SIZE - 10);
                    }


                }
            }

        }

        repaint();
    }


    private class DialogBox {
        private RoundRectangle2D.Double BG1;
        private RoundRectangle2D.Double BG2;

        private RoundRectangle2D.Double buttonYes;
        private RoundRectangle2D.Double buttonNo;

        private RoundRectangle2D.Double buttonGotIt;

        private Color BG1Color, BG2Color;
        

        DialogBox() {
            BG1Color = new Color(150, 150, 150);
            BG1 = new RoundRectangle2D.Double(275, 325, 350, 200, 100, 100);
            BG2Color = new Color(60, 60, 60);
            BG2 = new RoundRectangle2D.Double(280, 330, 340, 190, 100, 100);
            buttonYes = new RoundRectangle2D.Double(340, 440, 100, 50, 30, 30);
            buttonNo = new RoundRectangle2D.Double(460, 440, 100, 50, 30, 30);
            buttonGotIt = new RoundRectangle2D.Double(400, 440, 100, 50, 30, 30);
        }
        void drawDialogBox(String msg, boolean yesNo) {
            g2.setColor(BG1Color);
            g2.fill(BG1);
            g2.setColor(BG2Color);
            g2.fill(BG2);
            g2.setColor(BG1Color);
            g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
            g2.drawString(msg, 325, 385);
            g2.setColor(BG1Color);
            if (yesNo) {
                g2.fill(buttonYes);
                g2.fill(buttonNo);
                g2.setColor(BG2Color);
                g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
                g2.drawString("Yes", 370, 470);
                g2.drawString("No", 495, 470);
            }
            else {
                g2.fill(buttonGotIt);
                g2.setColor(BG2Color);
                g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
                g2.drawString("OK", 435, 470);
            }

            dialogBoxFlag = true;
        }

        RoundRectangle2D.Double getButtonYes() { return buttonYes; }
        RoundRectangle2D.Double getButtonNo() { return buttonNo; }
        RoundRectangle2D.Double getButtonGotIt() { return buttonGotIt; }
    }
    
}

