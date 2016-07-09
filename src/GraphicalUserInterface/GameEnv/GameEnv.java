package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;
import GraphicalUserInterface.SimpleMenuListener;
import itemMGMT.Item;
import units.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameEnv extends JComponent{

    //Environment Manager
    private EnvironmentMgr emgr;
    private SimpleMenuListener geListener;
    private Image BGImage;

    //Map stuff
    private final static int BLOCK_SIZE = 50;
    private final static double SPEED = 2.0;

    //Moving icon stuff
    private Image movChar[];
    private int charImage;
    private double CharX, CharY;
    private double xSpeed, ySpeed;
    private boolean speedFlag1, speedFlag2, speedFlag3, speedFlag4;

    //Scenario Stuff
    private Scenario scenario;

    //Dialog Box stuff
    private DialogBox dialogBox;
    private boolean dialogBoxFlag, dialogTypeFlag, eventDisappearFlag;
    private String dialogBoxMessage;
    private GameEvent eventToBeFired;

    //Story Box Stuff
    private StoryBox storyBox;
    private boolean storyBoxFlag;

    //Shop Environment stuff
    private ShopEnv shop;
    private boolean shopFlag, buyFlag;
    private Hero targetHero;

    //Panel stuff
    private Ellipse2D.Double settingsButton, menuButton;
    private Font mmFont, geFont;
    private Color fontColor, buttonColor, c1, c2, c3, c4, c5;
    private Rectangle2D.Double[] heroRect;
    private boolean heroRectFlag;

    //Graphics2D
    private Graphics2D g2;
    private boolean gameStarted;

    public GameEnv(EnvironmentMgr emgr, Scenario scenario, SimpleMenuListener gel) {

        //Environment Manager
        this.emgr = emgr;
        geListener = gel;


        //Start Flag
        gameStarted = true;


        //Moving Icon stuff
        charImage = 0;
        xSpeed = 0.0;
        ySpeed = 0.0;

        CharX = 100;
        CharY = 730;
        prevX = CharX;
        prevY = CharY;

        speedFlag1 = speedFlag2 = speedFlag3 = speedFlag4 = false;

        movChar = new Image[4];
        try {
            movChar[0] = ImageIO.read(new File("GameEnvGraphics/MoveCharBack.png"));
            movChar[1] = ImageIO.read(new File("GameEnvGraphics/MoveCharLeft.png"));
            movChar[2] = ImageIO.read(new File("GameEnvGraphics/MoveCharFront.png"));
            movChar[3] = ImageIO.read(new File("GameEnvGraphics/MoveCharRight.png"));
            BGImage = ImageIO.read(new File("GameEnvGraphics/MapBG.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mmFont = Font.createFont(Font.TRUETYPE_FONT, new File("MainMenuGraphics/mainMenuFont.ttf"));
            geFont = Font.createFont(Font.TRUETYPE_FONT, new File("GameEnvGraphics/gameEnvFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mmFont);
            ge.registerFont(geFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }


        //Scenario Stuff
        this.scenario = scenario;


        //Dialog Box stuff
        dialogBox = new DialogBox();
        dialogBoxFlag = dialogTypeFlag = eventDisappearFlag = false;
        eventToBeFired = null;

        //Story Box Stuff
        storyBox = new StoryBox();
        storyBoxFlag = false;

        //Shop Environment stuff
        shop = new ShopEnv(scenario.getShop());
        shopFlag = false;
        buyFlag = false;
        targetHero = null;

        //Panel stuff
        fontColor = new Color(166, 143, 78);
        c1 = c2 = c3 = c4 = c5 = buttonColor = new Color(60, 60, 60);
        heroRect = new Rectangle2D.Double[scenario.getPlayer().getHeroes().size()];
        heroRectFlag = false;


        //Interaction handling
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
                    if (buyFlag) {

                        if (dialogBox.getButtonYes().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                            if (targetHero.buyItem(shop.getSelected())) {
                                scenario.getPlayer().setGold(scenario.getPlayer().getGold() - shop.getSelected().getCost());
                                dialogBoxFlag = false;
                                storyBoxFlag = true;
                                storyBox.setStory("Successful!");
                                buyFlag = false;
                                targetHero = null;
                            }
                            else {
                                dialogBoxFlag = false;
                                storyBoxFlag = true;
                                storyBox.setStory("Selected Hero's Inventory is full.");
                                buyFlag = false;
                                targetHero = null;
                            }
                        }
                        if (dialogBox.getButtonNo().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                            buyFlag = false;
                            dialogBoxFlag = false;
                            targetHero = null;
                        }

                    }
                    else{
                        if (dialogBox.getButtonGotIt().contains(e.getX(), e.getY()) && !dialogTypeFlag) {
                            dialogBoxFlag = false;
                            dialogTypeFlag = true;
                        } else if (dialogBox.getButtonNo().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                            eventToBeFired = null;
                            dialogBoxFlag = false;
                        } else if (dialogBox.getButtonYes().contains(e.getX(), e.getY()) && dialogTypeFlag) {

                            switch (eventToBeFired.getType()) {
                                case key:
                                    eventToBeFired.unlockDoor(scenario.getUnlockedDoorImg());
                                    dialogTypeFlag = false;
                                    dialogBoxMessage = "A door was unlocked.";
                                    eventDisappearFlag = true;
                                    break;
                                case story:
                                    storyBoxFlag = true;
                                    storyBox.setStory(eventToBeFired.getStory());
                                    eventDisappearFlag = true;
                                    break;
                                case shop:
                                    shopFlag = true;
                                    eventToBeFired = null;
                                    break;
                                case battle:
                                    eventDisappearFlag = true;
                                    break;
                            }
                            if (dialogTypeFlag) dialogBoxFlag = false;

                        }
                    }
                }

                if (storyBoxFlag) {

                    if (!buyFlag && storyBox.getButtonGotIt().contains(e.getX(), e.getY())) {
                        storyBoxFlag = false;
                        storyBox.setStory(null);
                    }

                }

                if (shopFlag) {

                    if (shop.getButtonInfo().contains(e.getX(), e.getY())) {
                        storyBoxFlag = true;
                        if (shop.getSelected() != null) storyBox.setStory(shop.getSelected().getDescription());
                        else storyBox.setStory("Please select an item.");
                    }

                    if (shop.getButtonExit().contains(e.getX(), e.getY())) {
                        shopFlag = false;
                        buyFlag = false;
                        storyBoxFlag = false;
                        dialogBoxFlag = false;
                        shop.setSelected(null);
                    }

                    if (shop.getButtonBuy().contains(e.getX(), e.getY())) {
                        if (shop.getSelected() == null) {
                            storyBoxFlag = true;
                            storyBox.setStory("Please select an item.");
                        }
                        else {
                            if (scenario.getPlayer().getGold() > shop.getSelected().getCost()) {
                                storyBoxFlag = true;
                                storyBox.setStory("Choose a hero from the panel on your right");
                                buyFlag = true;
                            }
                            else {
                                storyBoxFlag = true;
                                storyBox.setStory("You don't have enough money.");
                            }
                        }
                    }


                    if (shop.getRectFlag() && !buyFlag)
                    for (int i = 0; i < shop.getRect().length; i++) {
                        if (shop.getRect()[i].contains(e.getX(), e.getY())) {
                            shop.setSelected(i);
                            break;
                        }
                        else shop.setSelected(null);
                    }


                    if (buyFlag && heroRectFlag) {
                        for (int i = 0; i < heroRect.length; i++) {
                            if (heroRect[i].contains(e.getX(), e.getY())) {
                                storyBoxFlag = false;
                                dialogBoxFlag = true;
                                dialogTypeFlag = true;
                                targetHero = scenario.getPlayer().getHeroes().get(i);
                                dialogBoxMessage = "Buy for " + scenario.getPlayer().getHeroes().get(i).getName() + "?";
                                break;
                            }
                        }
                    }

                }

                if (menuButton.contains(e.getX(), e.getY())) {
                    emgr.frame().setSize(new Dimension(1280, 800));
                    emgr.frame().setLocationRelativeTo(null);
                    geListener.switchTo("main");
                }
                if (settingsButton.contains(e.getX(), e.getY())) {
                    emgr.frame().setSize(new Dimension(1280, 800));
                    emgr.frame().setLocationRelativeTo(null);
                    geListener.switchTo("settings");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (dialogBox.getButtonYes().contains(e.getX(), e.getY())) {
                    dialogBox.setColor(1, new Color(40, 40, 40));
                }
                if (dialogBox.getButtonNo().contains(e.getX(), e.getY())) {
                    dialogBox.setColor(2, new Color(40, 40, 40));
                }
                if (dialogBox.getButtonGotIt().contains(e.getX(), e.getY())) {
                    dialogBox.setColor(3, new Color(40, 40, 40));
                }
                if (storyBox.getButtonGotIt().contains(e.getX(), e.getY())) {
                    storyBox.setColor(new Color(40, 40, 40));
                }
                if (shop.getButtonInfo().contains(e.getX(), e.getY())) {
                    shop.setColor(1, new Color(120, 120, 120));
                }
                if (shop.getButtonExit().contains(e.getX(), e.getY())) {
                    shop.setColor(2, new Color(120, 120, 120));
                }

                if (shop.getButtonBuy().contains(e.getX(), e.getY())) {
                    shop.setColor(3, new Color(120, 120, 120));
                }
                if (settingsButton.contains(e.getX(), e.getY())) {
                    c1 = new Color(50, 50, 50);
                }
                if (menuButton.contains(e.getX(), e.getY())) {
                    c2 = new Color(50, 50, 50);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dialogBox.setColor(1, new Color(50, 50, 50));
                dialogBox.setColor(2, new Color(50, 50, 50));
                dialogBox.setColor(3, new Color(50, 50, 50));
                shop.setColor(1, new Color(130, 130, 130));
                shop.setColor(2, new Color(130, 130, 130));
                shop.setColor(3, new Color(130, 130, 130));
                storyBox.setColor(new Color(50, 50, 50));
                c1 = c2 = buttonColor;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!gameStarted) {

        } else {
            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(0, 0, getWidth(), getHeight());

            //Drawing the Scenario Map
            for (int i = 1; i <= 16; i++) {
                for (int j = 1; j <= 16; j++) {
                    if (scenario.getMap().getTile(i, j).getImage() != null) {
                         g2.drawImage(scenario.getMap().getTile(i, j).getImage(), 50 * i, 50 * j, BLOCK_SIZE, BLOCK_SIZE, null);
                    }
                    if (scenario.getMap().getEvent(i, j) != null)
                        g2.drawImage(scenario.getMap().getEvent(i, j).getImage(), 50 * i, 50 * j, BLOCK_SIZE, BLOCK_SIZE, null);
                }
            }

            //The moving Character
            g2.drawImage(movChar[charImage], (int) CharX, (int) CharY, BLOCK_SIZE, BLOCK_SIZE, null);

            //Shop Environment
            if (shopFlag) {
                shop.drawShop();
            }

            //Dialog Box
            if (dialogBoxFlag) {
                dialogBox.drawDialogBox(dialogBoxMessage, dialogTypeFlag);
            }

            //Story Box
            if (storyBoxFlag) {
                storyBox.drawStoryBox();
            }

            //The Information panel
            Color borderColor = new Color(150, 150, 150);
            g2.setColor(buttonColor);
            g2.fill(new RoundRectangle2D.Double(900, 50, 250, 500, 60, 60));
            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Double(900, 50, 250, 500, 60, 60));
            g2.setColor(buttonColor);
            g2.fill(new RoundRectangle2D.Double(900, 575, 250, 170, 60, 60));
            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Double(900, 575, 250, 170, 60, 60));

            g2.setFont(geFont.deriveFont(30f).deriveFont(Font.ITALIC));
            g2.setColor(new Color(240, 220, 98));
            g2.drawString(scenario.getPlayer().getName(), 970, 90);
            g2.setFont(geFont.deriveFont(25f));
            g2.drawString("Heroes:", 920, 140);
            ArrayList<Hero> heroes = scenario.getPlayer().getHeroes();
            int yH = 190;
            for (Hero h: heroes) {
                g2.setColor(new Color(200, 200, 200));
                g2.setFont(geFont.deriveFont(22f));
                g2.drawString(h.getName(), 930, yH);
                if (!heroRectFlag) heroRect[heroes.indexOf(h)] = new Rectangle2D.Double(930, yH - 15, 100, 30);
                g2.setFont(geFont.deriveFont(14f).deriveFont(Font.BOLD));
                g2.drawString("HP: ", 1040, yH - 12);
                g2.drawString(h.getHP() + "", 1080, yH - 12);
                g2.drawString("EP: ", 1040, yH);
                g2.drawString(h.getEP() + "", 1080, yH);
                g2.drawString("MP: ", 1040, yH + 12);
                g2.drawString(h.getMP() + "", 1080, yH + 12);
                yH += 50;
            }
            heroRectFlag = true;

            yH += 10;
            g2.setColor(new Color(240, 220, 98));
            g2.setFont(geFont.deriveFont(25f));
            g2.drawString("Gold:", 920, yH);
            g2.drawString(scenario.getPlayer().getGold() + "", 1050, yH);
            yH += 25;
            g2.drawString("XP:", 920, yH);
            g2.drawString(scenario.getPlayer().getXP() + "", 1050, yH);

            g2.setColor(c1);
            if (settingsButton == null) settingsButton = new Ellipse2D.Double(930, 770, 80, 80);
            g2.fill(settingsButton);
            g2.setColor(fontColor);
            g2.drawOval(930, 770, 80, 80);

            g2.setColor(c2);
            if (menuButton == null) menuButton = new Ellipse2D.Double(1050, 770, 80, 80);
            g2.fill(menuButton);
            g2.setColor(fontColor);
            g2.drawOval(1050, 770, 80, 80);
            g2.setFont(mmFont.deriveFont(20f));
            g2.drawString("Settings", 942, 815);
            g2.drawString("Menu", 1073, 815);
        }


        g.drawImage(buffer, 0, 0, null);
    }


    //For future
//    private BufferedImage ApplyTransparency(BufferedImage image)
//    {
//        BufferedImage dest = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = dest.createGraphics();
//        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_OVER, 0.5F);
//        g.setComposite(ac);
//        g.drawImage(image, 0, 0, null);
//
//        return dest;
//    }
    
    

    private double prevX, prevY;
    public void update() {

            if (!dialogBoxFlag && !storyBoxFlag && !shopFlag) {

                prevX = CharX;
                prevY = CharY;

                CharX += xSpeed;
                CharY += ySpeed;

                //Intersection with blocks and events fired handled below
                Rectangle2D.Double charRect = new Rectangle2D.Double(CharX + 10, CharY + 15, BLOCK_SIZE - 20, BLOCK_SIZE - 10);
                for (int i = 0; i < scenario.getRows(); i++) {
                    for (int j = 0; j < scenario.getColumns(); j++) {

                        Tile tile = scenario.getMap().getTile(i + 1, j + 1);
                        Rectangle2D.Double tileRect = new Rectangle2D.Double(tile.getX() * 50, tile.getY() * 50, BLOCK_SIZE, BLOCK_SIZE);

                        GameEvent event = scenario.getMap().getEvent(i + 1, j + 1);

                        boolean doorFlag = false;
                        if (event != null) {
                            //Unlocked Doors
                            switch (event.getType()) {
                                case doorUnlockedLeft:
                                    if (CharX < event.getX() * 50 && tileRect.intersects(charRect)) {
                                        CharX = (event.getX() + 1) * 50;
                                        CharY = event.getY() * 50 - 15;
                                    }
                                    doorFlag = true;
                                    break;
                                case doorUnlockedRight:
                                    if (CharX > event.getX() * 50 && tileRect.intersects(charRect)) {
                                        CharX = (event.getX() - 1) * 50;
                                        CharY = event.getY() * 50 - 15;
                                    }
                                    doorFlag = true;
                                    break;
                                case doorUnlockedUp:
                                    if (CharY < event.getY() * 50 && tileRect.intersects(charRect)) {
                                        CharY = (event.getY() + 1) * 50;
                                        CharX = event.getX() * 50;
                                    }
                                    doorFlag = true;
                                    break;
                                case doorUnlockedDown:
                                    if (CharY > event.getY() * 50 && tileRect.intersects(charRect)) {
                                        CharY = (event.getY() - 1) * 50 - 15;
                                        CharX = event.getX() * 50;
                                    }
                                    doorFlag = true;
                                    break;
                            }


                            //Events other than Unlocked doors
                            if (!doorFlag && tileRect.intersects(charRect)) {

                                if (event.getType() == GameEvent.Type.key) {
                                    dialogBoxMessage = "Pick up the Key?";
                                    dialogTypeFlag = true;
                                }
                                else if(event.getType() == GameEvent.Type.doorLockedDown ||
                                        event.getType() == GameEvent.Type.doorLockedUp ||
                                        event.getType() == GameEvent.Type.doorLockedRight ||
                                        event.getType() == GameEvent.Type.doorLockedLeft) {
                                    dialogBoxMessage = "This door is locked.";
                                    dialogTypeFlag = false;
                                }
                                else {
                                    dialogBoxMessage = "Enter " + event.getType() + "?";
                                    dialogTypeFlag = true;
                                }

                                dialogBoxFlag = true;
                                eventToBeFired = event;
                                CharX = prevX;
                                CharY = prevY;
                            }
                        }

                        //Event removal for battles, stories, and keys
                        if (eventDisappearFlag) {
                            eventDisappearFlag = false;
                            scenario.getMap().setEvent(null, eventToBeFired.getX(), eventToBeFired.getY());
                            eventToBeFired.getTile().setGameEvent(null);
                            CharX = eventToBeFired.getX() * 50;
                            CharY = eventToBeFired.getY() * 50 - 15;
                            eventToBeFired = null;
                        }

                        //To stop the character from moving into obstacles
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
        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonYes;
        private RoundRectangle2D.Double buttonNo;

        private RoundRectangle2D.Double buttonGotIt;

        private Color BGColor, borderColor, c1, c2, c3;
        

        DialogBox() {
            c1 = c2 = c3 = new Color(50, 50, 50);
            BGColor = new Color(60, 60, 60);
            borderColor = new Color(150, 150, 150);
            BG = new RoundRectangle2D.Double(275, 325, 350, 200, 100, 100);
            buttonYes = new RoundRectangle2D.Double(340, 440, 100, 50, 30, 30);
            buttonNo = new RoundRectangle2D.Double(460, 440, 100, 50, 30, 30);
            buttonGotIt = new RoundRectangle2D.Double(400, 440, 100, 50, 30, 30);
        }
        void drawDialogBox(String msg, boolean yesNo) {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(30f));
            g2.drawString(msg, 325, 385);
            if (yesNo) {
                g2.setColor(c1);
                g2.fill(buttonYes);
                g2.setColor(borderColor);
                g2.draw(buttonYes);
                g2.setColor(c2);
                g2.fill(buttonNo);
                g2.setColor(borderColor);
                g2.draw(buttonNo);
                g2.setFont(mmFont.deriveFont(25f));
                g2.drawString("Yes", 375, 470);
                g2.drawString("No", 500, 470);
            }
            else {
                g2.setColor(c3);
                g2.fill(buttonGotIt);
                g2.setColor(borderColor);
                g2.draw(buttonGotIt);
                g2.setFont(mmFont.deriveFont(25f));
                g2.drawString("OK", 435, 470);
            }

            dialogBoxFlag = true;
        }

        RoundRectangle2D.Double getButtonYes() { return buttonYes; }
        RoundRectangle2D.Double getButtonNo() { return buttonNo; }
        RoundRectangle2D.Double getButtonGotIt() { return buttonGotIt; }
        void setColor(int n, Color c) { switch (n) { case 1:c1 = c;break;    case 2:c2 = c;break;    case 3:c3 = c; break; } }
    }


    private class StoryBox {
        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonGotIt;

        private Color BGColor, borderColor, c1;

        private ArrayList<String> Tokens;

        StoryBox() {
            c1 = new Color(50, 50, 50);
            BGColor = new Color(60, 60, 60);
            borderColor = new Color(150, 150, 150);
            BG = new RoundRectangle2D.Double(175, 225, 550, 350, 100, 100);
            buttonGotIt = new RoundRectangle2D.Double(400, 490, 100, 50, 30, 30);
            Tokens = new ArrayList<>();
        }

        void drawStoryBox() {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(30f));

            int yS = 300;
            for (String s: Tokens) {
                g2.drawString(s, 225, yS);
                yS += 30;
            }

            if (!buyFlag) {
                g2.setColor(c1);
                g2.fill(buttonGotIt);
                g2.setColor(borderColor);
                g2.draw(buttonGotIt);
                g2.setFont(mmFont.deriveFont(25f));
                g2.drawString("Continue", 415, 520);
            }

        }

        RoundRectangle2D.Double getButtonGotIt() { return buttonGotIt; }

        void setStory(String st) {
            Tokens.clear();
            if (st != null) {
                String temp = "";
                for (int i = 0; i < st.length(); i++) {
                    temp += st.charAt(i);
                    if (st.charAt(i) == '\n' || i == st.length() - 1) {
                        Tokens.add(temp);
                        temp = "";
                    }
                }
            }
        }

        void setColor(Color c) { c1 = c; }
    }


    private class ShopEnv {

        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonBuy, buttonExit, buttonInfo;

        private Color BGColor, borderColor, c1, c2;

        private ArrayList<Item> items;
        private Item selected;
        private Rectangle.Double[] itemRects;
        private boolean rectFlag;

        ShopEnv(ArrayList<Item> items) {
            c1 = c2 = c3 = new Color(150, 150, 150);
            BGColor = new Color(150, 150, 150);
            borderColor = new Color(60, 60, 60);
            BG = new RoundRectangle2D.Double(75, 75, 750, 750, 75, 75);
            buttonInfo = new RoundRectangle2D.Double(400, 750, 100, 50, 25, 25);
            buttonBuy = new RoundRectangle2D.Double(550, 750, 100, 50, 25, 25);
            buttonExit = new RoundRectangle2D.Double(700, 750, 100, 50, 25, 25);
            this.items = items;
            itemRects = new Rectangle2D.Double[items.size()];
            rectFlag = false;
            selected = null;
        }

        void drawShop() {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(48f));
            g2.drawString("Welcome to our shop!", 300, 150);

            int xI = 100, yI = 250;
            for (Item i: items) {
                g2.drawImage(i.getImage(), xI, yI, 100, 100, null);
                if (!rectFlag) itemRects[items.indexOf(i)] = new Rectangle2D.Double(xI, yI, 100, 100);
                g2.setFont(mmFont.deriveFont(22f));
                g2.drawString(i.getName(), xI + 10, yI + 120);
                if (i.equals(selected)) g2.draw(new Rectangle2D.Double(xI, yI, 100, 100));
                xI += 150;
                if (xI >= 800) {
                    yI += 150;
                    xI = 100;
                }
            }
            rectFlag = true;

            g2.setColor(c1);
            g2.fill(buttonBuy);
            g2.setColor(borderColor);
            g2.draw(buttonBuy);
            g2.setColor(c2);
            g2.fill(buttonExit);
            g2.setColor(borderColor);
            g2.draw(buttonExit);
            g2.setColor(c3);
            g2.fill(buttonInfo);
            g2.setColor(borderColor);
            g2.draw(buttonInfo);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Info", 435, 780);
            g2.drawString("Buy", 585, 780);
            g2.drawString("Exit", 735, 780);
            g2.drawString("*Items that doesn't display a cost on info are 4 dollars.", 100, 650);

        }

        Item getSelected() { return selected; }
        void setSelected(Item i) { selected = i; }
        void setSelected(int i) { selected = items.get(i);}
        Rectangle2D.Double[] getRect() { return itemRects; } // Pun intended :D
        boolean getRectFlag() { return rectFlag; }

        RoundRectangle2D.Double getButtonBuy() { return buttonBuy; }
        RoundRectangle2D.Double getButtonExit() { return buttonExit; }
        RoundRectangle2D.Double getButtonInfo() { return buttonInfo; }
        void setColor(int n, Color c) { switch (n) { case 1:c3 = c;break;    case 2:c2 = c;break;    case 3:c1 = c; break; } }


    }
}

