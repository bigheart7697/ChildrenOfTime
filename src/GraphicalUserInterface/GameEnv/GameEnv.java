package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;
import GraphicalUserInterface.SimpleMenuListener;
import abilities.Ability;
import itemMGMT.Consumable;
import itemMGMT.ImmediateEffect;
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

    //Message Box Stuff
    private MessageBox messageBox;
    private boolean messageBoxFlag;

    //Shop Environment stuff
    private ShopEnv shop;
    private boolean shopFlag, buyFlag, sellFlag;
    private Hero targetHero;

    //Ability Upgrade Point stuff
    private AbilityUpEnv abilityUpEnv;
    private boolean chooseHeroFlag, abilityUpEnvFlag, upgradeFlag;
    //target hero used from shop section

    //Hero info panel stuff
    private HeroInfoEnv heroInfoEnv;
    private boolean heroInfoDisplayFlag;


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
        gameStarted = false;


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
        messageBox = new MessageBox();
        messageBox.setMessage(scenario.getBeginStory());
        messageBox.setImage(scenario.getBeginImage());
        messageBoxFlag = true;

        //Shop Environment stuff
        shop = new ShopEnv(scenario.getShop(), scenario.getPlayer().getHeroes());
        shopFlag = false;
        buyFlag = false;
        sellFlag = false;
        targetHero = null;

        //Ability Upgrade Point stuff
        abilityUpEnv = null;
        chooseHeroFlag = false;
        abilityUpEnvFlag = false;
        upgradeFlag = false;

        //Panel stuff
        fontColor = new Color(166, 143, 78);
        c1 = c2 = c3 = c4 = c5 = buttonColor = new Color(60, 60, 60);
        heroRect = new Rectangle2D.Double[scenario.getPlayer().getHeroes().size()];
        heroRectFlag = false;

        //Hero Info Panel Stuff
        heroInfoEnv = new HeroInfoEnv(targetHero);
        heroInfoDisplayFlag = false;

        //Movement handling
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (emgr.getCurrentCard().equalsIgnoreCase("game")) {

                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if (!speedFlag3) {
                            charImage = 0;
                            speedFlag1 = true;
                            ySpeed = -SPEED;
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (!speedFlag4) {
                            charImage = 1;
                            speedFlag2 = true;
                            xSpeed = -SPEED;
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (!speedFlag1) {
                            charImage = 2;
                            speedFlag3 = true;
                            ySpeed = SPEED;
                        }
                        break;
                    case KeyEvent.VK_D:
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
                    case KeyEvent.VK_W:
                        if (speedFlag1) {
                            if (speedFlag2) charImage = 1;
                            if (speedFlag4) charImage = 3;
                            speedFlag1 = false;
                            ySpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (speedFlag2) {
                            if (speedFlag1) charImage = 0;
                            if (speedFlag3) charImage = 2;
                            speedFlag2 = false;
                            xSpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (speedFlag3) {
                            if (speedFlag2) charImage = 1;
                            if (speedFlag4) charImage = 3;
                            speedFlag3 = false;
                            ySpeed = 0.0;
                        }
                        break;
                    case KeyEvent.VK_D:
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

                boolean clickFlag = false;

                if (dialogBoxFlag) {

                    if (buyFlag) {

                            if (dialogBox.getButtonYes().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                                if (targetHero.buyItem(shop.getSelectedBuy())) {

                                    shop.setRectFlagSell(false);
                                    //Decreasing the Gold number gradually
                                    int targetGold = scenario.getPlayer().getGold() - shop.getSelectedBuy().getCost();
                                    while (scenario.getPlayer().getGold() > targetGold) {
                                        scenario.getPlayer().setGold(scenario.getPlayer().getGold() - 1);
                                        try {
                                            Thread.sleep(20);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        paintComponent(getGraphics());
                                    }

                                    if (shop.getSelectedBuy() instanceof ImmediateEffect)
                                        ((ImmediateEffect) shop.getSelectedBuy()).increaseCost();
                                    dialogTypeFlag = false;
                                    dialogBoxMessage = "Successful!";
                                    buyFlag = false;
                                    targetHero = null;
                                } else {
                                    dialogBoxFlag = false;
                                    messageBoxFlag = true;
                                    messageBox.setMessage("Selected Hero's Inventory is full.");
                                    buyFlag = false;
                                    targetHero = null;
                                    clickFlag = true;
                                }
                            }
                            if (!clickFlag && dialogBox.getButtonNo().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                                buyFlag = false;
                                dialogBoxFlag = false;
                                targetHero = null;
                                clickFlag = true;
                            }

                    }

                    else if (sellFlag) {

                        if (dialogBox.getButtonYes().contains(e.getX(), e.getY()) && dialogTypeFlag) {


                            shop.getOwnerHero().sellItem(shop.getSelectedSell());
                            //Increasing the Gold number gradually
                            int targetGold = scenario.getPlayer().getGold() + shop.getSelectedSell().getCost() / 2;
                            while (scenario.getPlayer().getGold() < targetGold) {
                                scenario.getPlayer().setGold(scenario.getPlayer().getGold() + 1);
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                paintComponent(getGraphics());
                            }
                            dialogTypeFlag = false;
                            dialogBoxMessage = "Successful!";
                            shop.setRectFlagSell(false);
                            shop.setSelectedSell(null);
                            sellFlag = false;
                            targetHero = null;
                            clickFlag = true;
                        }
                        if (!clickFlag && dialogBox.getButtonNo().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                            sellFlag = false;
                            dialogBoxFlag = false;
                            targetHero = null;
                            clickFlag = true;
                        }

                    }

                    else if (chooseHeroFlag) {
                            if (dialogBox.getButtonYes().contains(e.getX(), e.getY())) {
                                abilityUpEnv = new AbilityUpEnv(targetHero.getName(), targetHero.getAbilities());
                                abilityUpEnvFlag = true;
                                chooseHeroFlag = false;
                                dialogBoxFlag = false;
                                dialogBoxMessage = null;
                                clickFlag = true;
                            }
                            if (!clickFlag && dialogBox.getButtonNo().contains(e.getX(), e.getY())) {
                                messageBoxFlag = true;
                                messageBox.setMessage("Choose a hero from the panel on your right");
                                dialogBoxFlag = false;
                                dialogBoxMessage = null;
                                targetHero = null;
                                clickFlag = true;
                            }
                    }

                    else if (upgradeFlag) {
                            if (dialogBox.getButtonYes().contains(e.getX(), e.getY())) {
                                abilityUpEnv.getSelected().setXP(scenario.getPlayer().getXP());
                                abilityUpEnv.getSelected().setUser(targetHero);

                                //Decreasing the XP number gradually
                                int targetXP = scenario.getPlayer().getXP() - abilityUpEnv.getSelected().getXPtoNextLevel();
                                while (scenario.getPlayer().getXP() > targetXP) {
                                    scenario.getPlayer().setXP(scenario.getPlayer().getXP() - 1);
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                    paintComponent(getGraphics());
                                }

                                abilityUpEnv.getSelected().upgrade();
                                dialogTypeFlag = false;
                                dialogBoxMessage = "Successful!";
                                upgradeFlag = false;
                                clickFlag = true;
                            }
                            if (!clickFlag && dialogBox.getButtonNo().contains(e.getX(), e.getY())) {
                                upgradeFlag = false;
                                dialogBoxFlag = false;
                                clickFlag = true;
                            }}

                    else {
                            if (dialogBox.getButtonGotIt().contains(e.getX(), e.getY()) && !dialogTypeFlag) {
                                dialogBoxFlag = false;
                                dialogTypeFlag = true;
                                clickFlag = true;
                            } else if (dialogBox.getButtonNo().contains(e.getX(), e.getY()) && dialogTypeFlag) {
                                eventToBeFired = null;
                                dialogBoxFlag = false;
                                clickFlag = true;
                            } else if (dialogBox.getButtonYes().contains(e.getX(), e.getY()) && dialogTypeFlag) {

                                switch (eventToBeFired.getType()) {
                                    case key:
                                        eventToBeFired.unlockDoor(scenario.getUnlockedDoorImg());
                                        dialogTypeFlag = false;
                                        dialogBoxMessage = "A door was unlocked.";
                                        eventDisappearFlag = true;
                                        break;
                                    case story:
                                        messageBoxFlag = true;
                                        messageBox.setMessage(eventToBeFired.getInfo());
                                        messageBox.setImage(eventToBeFired.getStoryImage());
                                        eventDisappearFlag = true;
                                        break;
                                    case shop:
                                        shopFlag = true;
                                        eventToBeFired = null;
                                        break;
                                    case ability:
                                        messageBoxFlag = true;
                                        messageBox.setMessage("Choose a hero from the panel on your right");
                                        chooseHeroFlag = true;
                                        eventToBeFired = null;
                                        break;
                                    case battle:
                                        eventDisappearFlag = true;
                                        break;
                                }
                                if (dialogTypeFlag) dialogBoxFlag = false;
                                clickFlag = true;
                            }
                    }
                }

                if (messageBoxFlag) {

                    if (!clickFlag && messageBox.getButtonGotIt().contains(e.getX(), e.getY())) {
                        messageBoxFlag = false;
                        if (buyFlag) buyFlag = false;
                        if (sellFlag) sellFlag = false;
                        if (chooseHeroFlag) chooseHeroFlag = false;
                        if (!gameStarted) gameStarted = true;
                        messageBox.setMessage(null);
                        messageBox.setImage(null);
                        if (!abilityUpEnvFlag && !heroInfoDisplayFlag) targetHero = null;
                        clickFlag = true;
                    }

                }

                if (shopFlag) {

                        if (!clickFlag && shop.getButtonInfo().contains(e.getX(), e.getY())) {
                            if (shop.getSelectedBuy() != null) {
                                messageBoxFlag = true;
                                messageBox.setMessage(shop.getSelectedBuy().getDescription());
                            } else if (shop.getSelectedSell() != null) {
                                messageBoxFlag = true;
                                messageBox.setMessage(shop.getSelectedSell().getDescription());
                            }
                            else {
                                dialogBoxFlag = true;
                                dialogTypeFlag = false;
                                dialogBoxMessage = "Please select an item.";
                            }
                            clickFlag = true;
                        }

                        if (!clickFlag && shop.getButtonExit().contains(e.getX(), e.getY())) {
                            shopFlag = false;
                            buyFlag = false;
                            sellFlag = false;
                            messageBoxFlag = false;
                            messageBox.setMessage(null);
                            dialogBoxFlag = false;
                            dialogBoxMessage = null;
                            shop.setSelectedBuy(null);
                            shop.setSelectedSell(null);
                            clickFlag = true;
                        }

                        if (!clickFlag && shop.getButtonBuy().contains(e.getX(), e.getY())) {
                            if (shop.getSelectedBuy() == null) {
                                dialogBoxFlag = true;
                                dialogTypeFlag = false;
                                dialogBoxMessage = "Please select an item.";
                            } else {
                                if (scenario.getPlayer().getGold() >= shop.getSelectedBuy().getCost()) {
                                    messageBoxFlag = true;
                                    messageBox.setMessage("Choose a hero from the panel on your right");
                                    buyFlag = true;
                                } else {
                                    dialogBoxFlag = true;
                                    dialogTypeFlag = false;
                                    dialogBoxMessage = "Not enough money.";
                                }
                            }
                            clickFlag = true;
                        }

                        if (!clickFlag && shop.getButtonSell().contains(e.getX(), e.getY())) {
                            if (shop.getRectSell().length == 0) {
                                dialogBoxFlag = true;
                                dialogTypeFlag = false;
                                dialogBoxMessage = "You have no items.";
                            }
                            else if (shop.getSelectedSell() == null) {
                                dialogBoxFlag = true;
                                dialogTypeFlag = false;
                                dialogBoxMessage = "Please select an item.";
                            } else {
                                sellFlag = true;
                                dialogBoxFlag = true;
                                dialogTypeFlag = true;
                                dialogBoxMessage = "Sell " + shop.getSelectedSell().getName() + "?";
                            }
                        }

                        if (shop.getRectFlagBuy() && !buyFlag) {
                            for (int i = 0; i < shop.getRectBuy().length; i++) {
                                if (shop.getRectBuy()[i].contains(e.getX(), e.getY())) {
                                    shop.setSelectedBuy(i);
                                    break;
                                } else shop.setSelectedBuy(null);
                            }
                        }

                        if (shop.getRectFlagSell() && !sellFlag) {
                            for (int i = 0; i < shop.getRectSell().length; i++) {
                                if (shop.getRectSell()[i].contains(e.getX(), e.getY())) {
                                    shop.setSelectedSell(i);
                                    shop.setOwnerHero(shop.getOwnerOfIndex(i));
                                    break;
                                } else {
                                    shop.setSelectedSell(null);
                                    shop.setOwnerHero(null);
                                }
                            }
                        }

                        if (buyFlag && heroRectFlag) {
                            for (int i = 0; i < heroRect.length; i++) {
                                if (heroRect[i].contains(e.getX(), e.getY())) {
                                    messageBoxFlag = false;
                                    dialogBoxFlag = true;
                                    dialogTypeFlag = true;
                                    targetHero = scenario.getPlayer().getHeroes().get(i);
                                    dialogBoxMessage = "Buy for " + scenario.getPlayer().getHeroes().get(i).getName() + "?";
                                    break;
                                }
                            }
                        }

                }

                //Ability Upgrade
                if (chooseHeroFlag && heroRectFlag && !abilityUpEnvFlag) {
                    for (int i = 0; i < heroRect.length; i++) {
                        if (heroRect[i].contains(e.getX(), e.getY())) {
                            messageBoxFlag = false;
                            dialogBoxFlag = true;
                            dialogTypeFlag = true;
                            dialogBoxMessage = "Proceed with " + scenario.getPlayer().getHeroes().get(i).getName() + "?";
                            targetHero = scenario.getPlayer().getHeroes().get(i);
                            break;
                        }
                    }
                }
                outer: if (abilityUpEnv != null && abilityUpEnvFlag) {if (!clickFlag && abilityUpEnv.getButtonInfo().contains(e.getX(), e.getY())) {
                        if (abilityUpEnv.getSelected() != null) {
                            messageBoxFlag = true;
                            messageBox.setMessage(abilityUpEnv.getSelected().getDescription());
                        } else {
                            dialogBoxFlag = true;
                            dialogTypeFlag = false;
                            dialogBoxMessage = "Please select an ability.";
                        }
                        clickFlag = true;
                    }

                    if (!clickFlag && abilityUpEnv.getButtonExit().contains(e.getX(), e.getY())) {
                        abilityUpEnvFlag = false;
                        messageBoxFlag = false;
                        dialogBoxFlag = false;
                        upgradeFlag = false;
                        targetHero = null;
                        abilityUpEnv.setSelected(null);
                        abilityUpEnv = null;
                        clickFlag = true;
                        break outer;
                    }

                    if (!clickFlag && abilityUpEnv.getButtonChange().contains(e.getX(), e.getY())) {
                        messageBoxFlag = true;
                        messageBox.setMessage("Choose a hero from the panel on your right");
                        chooseHeroFlag = true;
                        dialogBoxFlag = false;
                        abilityUpEnvFlag = false;
                        upgradeFlag = false;
                        targetHero = null;
                        abilityUpEnv.setSelected(null);
                        abilityUpEnv = null;
                        clickFlag = true;
                        break outer;
                    }


                    if (!clickFlag && abilityUpEnv.getButtonUpgrade().contains(e.getX(), e.getY())) {
                        if (abilityUpEnv.getSelected() == null) {
                            dialogBoxFlag = true;
                            dialogTypeFlag = false;
                            dialogBoxMessage = "Please select an ability.";
                        } else {
                            if (scenario.getPlayer().getXP() > abilityUpEnv.getSelected().getXPtoNextLevel()) {
                                if (abilityUpEnv.getSelected().getLevel() == 3) {
                                    dialogBoxFlag = true;
                                    dialogTypeFlag = false;
                                    dialogBoxMessage = "Upgraded to Maximum.";
                                } else if (abilityUpEnv.getSelected().hasRequiredAbility(targetHero)) {
                                    dialogBoxFlag = true;
                                    dialogTypeFlag = true;
                                    dialogBoxMessage = "Upgrade " + abilityUpEnv.getSelected().getName() + "?";
                                    upgradeFlag = true;
                                } else {
                                    dialogBoxFlag = true;
                                    dialogTypeFlag = false;
                                    dialogBoxMessage = "Upgrade Required abilities";
                                }
                            } else {
                                dialogBoxFlag = true;
                                dialogTypeFlag = false;
                                dialogBoxMessage = "Not enough XP.";
                            }
                        }
                        clickFlag = true;
                    }

                    if (abilityUpEnv.getRectFlag() && !upgradeFlag)
                        for (int i = 0; i < abilityUpEnv.getRect().length; i++) {
                            if (abilityUpEnv.getRect()[i].contains(e.getX(), e.getY())) {
                                abilityUpEnv.setSelected(i);
                                break;
                            } else abilityUpEnv.setSelected(null);
                        }
                }

                //Hero Info Display
                if (!chooseHeroFlag && !abilityUpEnvFlag && !shopFlag && !dialogBoxFlag && !messageBoxFlag && !clickFlag && heroRectFlag) {
                    for (int i = 0; i < heroRect.length; i++) {
                        if (heroRect[i].contains(e.getX(), e.getY())) {
                            targetHero = scenario.getPlayer().getHeroes().get(i);
                            heroInfoEnv.setHero(targetHero);
                            heroInfoDisplayFlag = true;
                            break;
                        }
                    }
                }
                if (heroInfoDisplayFlag) {
                    if (!clickFlag && heroInfoEnv.getButtonBack().contains(e.getX(), e.getY())) {
                        heroInfoDisplayFlag = false;
                        targetHero = null;
                    }
                    if (!clickFlag && heroInfoEnv.getButtonDescription().contains(e.getX(), e.getY())) {
                        messageBoxFlag = true;
                        messageBox.setMessage(targetHero.getDescription());
                    }
                }

                if (!clickFlag && menuButton != null && menuButton.contains(e.getX(), e.getY())) {
                    emgr.frame().setSize(new Dimension(1280, 800));
                        emgr.frame().setLocationRelativeTo(null);
                        geListener.switchTo("main");
                        clickFlag = true;
                }
                if (!clickFlag && settingsButton != null && settingsButton.contains(e.getX(), e.getY())) {
                    emgr.frame().setSize(new Dimension(1280, 800));
                        emgr.frame().setLocationRelativeTo(null);
                        geListener.switchTo("settings");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (dialogBox.getButtonYes().contains(e.getX(), e.getY())) { dialogBox.setColor(1, new Color(40, 40, 40)); }
                if (dialogBox.getButtonNo().contains(e.getX(), e.getY())) { dialogBox.setColor(2, new Color(40, 40, 40)); }
                if (dialogBox.getButtonGotIt().contains(e.getX(), e.getY())) { dialogBox.setColor(3, new Color(40, 40, 40)); }

                if (messageBox.getButtonGotIt().contains(e.getX(), e.getY())) { messageBox.setColor(new Color(40, 40, 40)); }

                if (shop.getButtonInfo().contains(e.getX(), e.getY())) { shop.setColor(1, new Color(120, 120, 120)); }
                if (shop.getButtonExit().contains(e.getX(), e.getY())) { shop.setColor(2, new Color(120, 120, 120)); }
                if (shop.getButtonBuy().contains(e.getX(), e.getY())) { shop.setColor(3, new Color(120, 120, 120)); }
                if (shop.getButtonSell().contains(e.getX(), e.getY())) { shop.setColor(4, new Color(120, 120, 120)); }

                if (abilityUpEnv != null) {
                    if (abilityUpEnv.getButtonUpgrade().contains(e.getX(), e.getY())) { abilityUpEnv.setColor(1, new Color(120, 120, 120)); }
                    if (abilityUpEnv.getButtonExit().contains(e.getX(), e.getY())) { abilityUpEnv.setColor(2, new Color(120, 120, 120)); }
                    if (abilityUpEnv.getButtonInfo().contains(e.getX(), e.getY())) { abilityUpEnv.setColor(3, new Color(120, 120, 120)); }                    if (abilityUpEnv.getButtonInfo().contains(e.getX(), e.getY())) { abilityUpEnv.setColor(3, new Color(120, 120, 120)); }
                    if (abilityUpEnv.getButtonChange().contains(e.getX(), e.getY())) { abilityUpEnv.setColor(4, new Color(120, 120, 120)); }
                }

                if (heroInfoEnv.getButtonBack().contains(e.getX(), e.getY())) { heroInfoEnv.setColor(2, new Color(120, 120, 120)); }
                if (heroInfoEnv.getButtonDescription().contains(e.getX(), e.getY())) { heroInfoEnv.setColor(1, new Color(120, 120, 120)); }

                if (settingsButton != null && settingsButton.contains(e.getX(), e.getY())) {c1 = new Color(50, 50, 50); }
                if (menuButton != null && menuButton.contains(e.getX(), e.getY())) { c2 = new Color(50, 50, 50); }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dialogBox.setColor(1, new Color(50, 50, 50));
                dialogBox.setColor(2, new Color(50, 50, 50));
                dialogBox.setColor(3, new Color(50, 50, 50));
                shop.setColor(1, new Color(130, 130, 130));
                shop.setColor(2, new Color(130, 130, 130));
                shop.setColor(3, new Color(130, 130, 130));
                shop.setColor(4, new Color(130, 130, 130));
                if (abilityUpEnv != null) {
                    abilityUpEnv.setColor(1, new Color(130, 130, 130));
                    abilityUpEnv.setColor(2, new Color(130, 130, 130));
                    abilityUpEnv.setColor(3, new Color(130, 130, 130));
                    abilityUpEnv.setColor(4, new Color(130, 130, 130));
                }
                heroInfoEnv.setColor(1, new Color(130, 130, 130));
                heroInfoEnv.setColor(2, new Color(130, 130, 130));
                messageBox.setColor(new Color(50, 50, 50));
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

            //Story Box
            if (messageBoxFlag) {
                messageBox.draw();
            }

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
                shop.draw();
            }

            //Ability Upgrade Environment
            if (abilityUpEnvFlag) {
                if (abilityUpEnv != null) abilityUpEnv.draw();
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

            g2.setFont(mmFont.deriveFont(25f));
            g2.setColor(new Color(240, 220, 98));
            g2.drawString(scenario.getPlayer().getName(), 950, 90);
            g2.setFont(mmFont.deriveFont(30f));
            g2.drawString("Heroes:", 920, 140);
            ArrayList<Hero> heroes = scenario.getPlayer().getHeroes();
            int yH = 190;
            for (Hero h: heroes) {
                g2.setColor(new Color(200, 200, 200));
                g2.setFont(geFont.deriveFont(22f));
                g2.drawString(h.getName(), 930, yH);
                if (!heroRectFlag) heroRect[heroes.indexOf(h)] = new Rectangle2D.Double(925, yH - 20, 100, 30);
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
            if (targetHero != null) {
                g2.setColor(new Color(120, 120, 120));
                g2.draw(heroRect[scenario.getPlayer().getHeroes().indexOf(targetHero)]);
            }

            yH += 10;
            g2.setColor(new Color(240, 220, 98));
            g2.setFont(mmFont.deriveFont(30f));
            g2.drawString("Gold:", 920, yH);
            g2.drawString(scenario.getPlayer().getGold() + "", 1080, yH);
            yH += 35;
            g2.drawString("XP:", 920, yH);
            g2.drawString(scenario.getPlayer().getXP() + "", 1080, yH);
            yH += 35;
            g2.drawString("IM Potions:", 920, yH);
            g2.drawString(scenario.getPlayer().getIMPotionRemaining() + "", 1080, yH);

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

            //Hero Info Panel
            if (targetHero != null && heroInfoDisplayFlag) {
                heroInfoEnv.draw();
            }

            //Dialog Box
            if (dialogBoxFlag) {
                dialogBox.draw(dialogBoxMessage, dialogTypeFlag);
            }

            //Story Box
            if (messageBoxFlag) {
                messageBox.draw();
            }
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

            if (!dialogBoxFlag && !messageBoxFlag && !shopFlag && !chooseHeroFlag && !abilityUpEnvFlag && !heroInfoDisplayFlag) {

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
        void draw(String msg, boolean yesNo) {
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
                g2.drawString("Ok", 435, 470);
            }

            dialogBoxFlag = true;
        }

        RoundRectangle2D.Double getButtonYes() { return buttonYes; }
        RoundRectangle2D.Double getButtonNo() { return buttonNo; }
        RoundRectangle2D.Double getButtonGotIt() { return buttonGotIt; }
        void setColor(int n, Color c) { switch (n) { case 1:c1 = c;break;    case 2:c2 = c;break;    case 3:c3 = c; break; } }
    }


    private class MessageBox {
        private RoundRectangle2D.Double BG;

        private Image image;

        private RoundRectangle2D.Double buttonGotIt;

        private Color BGColor, borderColor, c1;

        private ArrayList<String> Tokens;

        MessageBox() {
            c1 = new Color(50, 50, 50);
            BGColor = new Color(60, 60, 60);
            borderColor = new Color(150, 150, 150);
            BG = new RoundRectangle2D.Double(75, 75, 750, 600, 75, 75);
            buttonGotIt = new RoundRectangle2D.Double(400, 600, 100, 50, 30, 30);
            Tokens = new ArrayList<>();
            image = null;
        }

        void draw() {
            if (image != null) g2.drawImage(image, 0, 0, null);
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(30f));

            int yS = 150;
            for (String s: Tokens) {
                g2.drawString(s, 125, yS);
                yS += 30;
            }


            g2.setColor(c1);
            g2.fill(buttonGotIt);
            g2.setColor(borderColor);
            g2.draw(buttonGotIt);
            g2.setFont(mmFont.deriveFont(25f));
            if (!buyFlag && !sellFlag && !chooseHeroFlag) g2.drawString("Continue", 415, 630);
            else g2.drawString("Cancel", 422, 630);

        }

        RoundRectangle2D.Double getButtonGotIt() { return buttonGotIt; }

        void setMessage(String st) {
            Tokens.clear();
            int charMax = 0;
            if (st != null) {
                String temp = "";
                for (int i = 0; i < st.length(); i++) {
                    temp += st.charAt(i);
                    charMax ++;
                    if ((charMax > 56 && st.charAt(i) == ' ') || i == st.length() - 1) {
                        Tokens.add(temp);
                        temp = "";
                        charMax = 0;
                    }
                }
            }
        }

        void setImage(Image image) { this.image = image; }

        void setColor(Color c) { c1 = c; }
    }


    private class ShopEnv {

        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonBuy, buttonSell, buttonExit, buttonInfo;

        private Color BGColor, borderColor, c1, c2, c3, c4;

        private ArrayList<Item> shopItems, heroItems;
        private ArrayList<Hero> heroes, ownerShips;
        private Hero ownerHero;
        private Item selectedBuy, selectedSell;
        private Rectangle.Double[] shopItemRects, playerItemRects;
        private boolean rectFlagBuy, rectFlagSell;

        ShopEnv(ArrayList<Item> items, ArrayList<Hero> heroes) {
            c1 = c2 = c3 = c4 = new Color(130, 130, 130);
            BGColor = new Color(160, 160, 150);
            borderColor = new Color(60, 60, 60);
            BG = new RoundRectangle2D.Double(75, 75, 750, 750, 75, 75);
            buttonInfo = new RoundRectangle2D.Double(250, 750, 100, 50, 25, 25);
            buttonSell = new RoundRectangle2D.Double(400, 750, 100, 50, 25, 25);
            buttonBuy = new RoundRectangle2D.Double(550, 750, 100, 50, 25, 25);
            buttonExit = new RoundRectangle2D.Double(700, 750, 100, 50, 25, 25);
            this.shopItems = items;
            heroItems = new ArrayList<>();
            this.heroes = heroes;
            ownerShips = new ArrayList<>();
            ownerHero = null;
            shopItemRects = new Rectangle2D.Double[items.size()];
            int size = 0;
            for (Hero h : heroes) {
                size += h.getItems().size();
                heroItems.addAll(h.getItems());
            }
            playerItemRects = new Rectangle2D.Double[size];
            rectFlagBuy = false;
            rectFlagSell = false;
            selectedBuy = null;
            selectedSell = null;
        }

        void draw() {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(45f));
            g2.drawString("Shop", 700, 150);

            int xI = 300, yI = 120;
            boolean classFlag = false;
            for (Item i: shopItems) {
                if (!classFlag) {
                    g2.setFont(mmFont.deriveFont(25f));
                    g2.drawString(i.getClass().toString().substring(15, i.getClass().toString().length()) + "s:", xI - 170, yI + 35);
                    classFlag = true;
                }
                g2.drawImage(i.getImage(), xI, yI, 50, 50, null);
                if (!rectFlagBuy) shopItemRects[shopItems.indexOf(i)] = new Rectangle2D.Double(xI, yI, 100, 100);
                g2.setFont(mmFont.deriveFont(20f));
                if (i.getName().contains(" ")) {
                    String[] tokens = i.getName().split(" ");
                    g2.drawString(tokens[0], xI, yI + 70);
                    g2.drawString(tokens[1], xI, yI + 90);
                }
                else g2.drawString(i.getName(), xI, yI + 70);
                if (i.equals(selectedBuy)) g2.draw(new Rectangle2D.Double(xI, yI, 50, 50));
                xI += 100;
                if (xI >= 800 || (shopItems.indexOf(i) < shopItems.size() - 1 && !shopItems.get(shopItems.indexOf(i) + 1).getClass().equals(i.getClass()))) {
                    classFlag = false;
                    yI += 100;
                    xI = 300;
                }
            }
            rectFlagBuy = true;

            g2.setFont(mmFont.deriveFont(35f));
            g2.drawString("Your Items:", 130, yI + 150);
            xI = 130; yI = yI + 180;

            int k = 0;
            for (Hero h: heroes) {
                for (Item i : h.getItems()) {
                    g2.setFont(mmFont.deriveFont(20f));
                    g2.drawImage(i.getImage(), xI, yI, 50, 50, null);
                    if (!rectFlagSell) {
                        playerItemRects[k] = new Rectangle2D.Double(xI, yI, 50, 50);
                        ownerShips.add(h);
                    }
                    if (i.equals(selectedSell) && ownerShips.get(k).equals(ownerHero)) {
                        g2.draw(new Rectangle2D.Double(xI, yI, 50, 50));
                    }
                    g2.drawString(i.getCost() / 2 + " g", xI, yI + 70);
                    g2.drawString("Owner:", xI, yI + 90);
                    g2.drawString(h.getName(), xI, yI + 110);
                    if (i instanceof Consumable) {
                        g2.drawString("Remain. " + h.getRemainingUsages((Consumable) i), xI, yI + 130);
                    }
                    xI += 75;
                    if (xI >= 800) {
                        xI = 130;
                        yI += 150;

                    }
                    k ++;
                }
            }
            rectFlagSell = true;

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
            g2.setColor(c4);
            g2.fill(buttonSell);
            g2.setColor(borderColor);
            g2.draw(buttonSell);
            g2.setColor(borderColor);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Info", 285, 780);
            g2.drawString("Sell", 435, 780);
            g2.drawString("Buy", 585, 780);
            g2.drawString("Exit", 735, 780);
            g2.drawString("*Immediate Effect items are 4 dollars initially.", 100, 720);

        }

        Item getSelectedBuy() { return selectedBuy; }
        void setSelectedBuy(Item i) { selectedBuy = i; }
        void setSelectedBuy(int i) { selectedBuy = shopItems.get(i);}
        Rectangle2D.Double[] getRectBuy() { return shopItemRects; }
        boolean getRectFlagBuy() { return rectFlagBuy; }

        Item getSelectedSell() { return selectedSell; }
        void setSelectedSell(Item i) { selectedSell = i; }
        void setSelectedSell(int i) { selectedSell = heroItems.get(i);}
        Rectangle2D.Double[] getRectSell() { return playerItemRects; }
        boolean getRectFlagSell() { return rectFlagSell; }
        void setRectFlagSell(boolean b) {
            rectFlagSell = b;
            int size = 0;
            heroItems.clear();
            for (Hero h : heroes) {
                size += h.getItems().size();
                heroItems.addAll(h.getItems());
            }
            ownerShips.clear();
            playerItemRects = new Rectangle2D.Double[size];
        }
        Hero getOwnerHero() { return ownerHero; }
        void setOwnerHero(Hero h) { ownerHero = h; }
        Hero getOwnerOfIndex(int i) { return ownerShips.get(i); }

        RoundRectangle2D.Double getButtonBuy() { return buttonBuy; }
        RoundRectangle2D.Double getButtonSell() { return buttonSell; }
        RoundRectangle2D.Double getButtonExit() { return buttonExit; }
        RoundRectangle2D.Double getButtonInfo() { return buttonInfo; }
        void setColor(int n, Color c) { switch (n) { case 1:c3 = c;break;    case 2:c2 = c;break;    case 3:c1 = c; break;     case 4:c4 = c;break;} }


    }


    private class AbilityUpEnv {

        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonUpgrade, buttonExit, buttonInfo, changeButton;

        private Color BGColor, borderColor, c1, c2, c3, c4;

        private ArrayList<Ability> abilities;
        private String heroName;
        private Ability selected;
        private Rectangle.Double[] abilityRects;
        private boolean rectFlag;

        AbilityUpEnv(String name, ArrayList<Ability> abilities) {
            c1 = c2 = c3 = c4 = new Color(130, 130, 130);
            BGColor = new Color(170, 165, 170);
            borderColor = new Color(60, 60, 60);
            BG = new RoundRectangle2D.Double(75, 75, 750, 750, 75, 75);
            buttonInfo = new RoundRectangle2D.Double(400, 750, 100, 50, 25, 25);
            buttonUpgrade = new RoundRectangle2D.Double(550, 750, 100, 50, 25, 25);
            buttonExit = new RoundRectangle2D.Double(700, 750, 100, 50, 25, 25);
            changeButton = new RoundRectangle2D.Double(100, 750, 120, 50, 25, 25);
            heroName = name;
            this.abilities = abilities;
            abilityRects = new Rectangle2D.Double[abilities.size()];
            rectFlag = false;
            selected = null;
            upgradeFlag = false;
        }

        void draw() {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(48f));
            g2.drawString("Ability Upgrade Point", 150, 150);
            g2.setFont(mmFont.deriveFont(42f));
            g2.drawString(heroName + ":", 150, 270);
            g2.setFont(mmFont.deriveFont(25f));
            if (targetHero != null) {
                g2.drawImage(targetHero.getHeroImage(), 600, 140, null);
                g2.drawString("HP: " + targetHero.getHP(), 300, 240);
                g2.drawString("MP: " + targetHero.getMP(), 300, 270);
                g2.drawString("Attack Dmg: " + targetHero.getAttDmg(), 300, 300);
            }

            int xI = 200, yI = 380;
            for (Ability a: abilities) {
                g2.setFont(mmFont.deriveFont(30f));
                g2.drawString(a.getName(), xI - 20, yI + 10);
                g2.drawImage(a.getImage(), xI + 180, yI - 25, 50, 50, null);
                g2.drawString("Level " + a.getLevel(), xI + 260, yI + 10);
                g2.drawString("XP to next lvl: " + a.getXPtoNextLevel(), xI + 370, yI + 10);
                g2.draw(new Rectangle2D.Double(xI - 60, yI - 5, 20, 20));
                if (!rectFlag) abilityRects[abilities.indexOf(a)] = new Rectangle2D.Double(xI - 60, yI - 5, 20, 20);
                if (a.equals(selected)) g2.fill(new Rectangle2D.Double(xI - 60, yI - 5, 20, 20));
                yI += 70;
            }
            rectFlag = true;

            g2.setColor(c1);
            g2.fill(buttonUpgrade);
            g2.setColor(borderColor);
            g2.draw(buttonUpgrade);
            g2.setColor(c2);
            g2.fill(buttonExit);
            g2.setColor(borderColor);
            g2.draw(buttonExit);
            g2.setColor(c3);
            g2.fill(buttonInfo);
            g2.setColor(borderColor);
            g2.draw(buttonInfo);
            g2.setColor(c4);
            g2.fill(changeButton);
            g2.setColor(borderColor);
            g2.draw(changeButton);
            g2.setColor(borderColor);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Info", 435, 780);
            g2.drawString("Upgrade", 567, 780);
            g2.drawString("Exit", 735, 780);
            g2.drawString("Change Hero", 108, 780);
        }

        Ability getSelected() { return selected; }
        void setSelected(Ability a) { selected = a; }
        void setSelected(int i) { selected = abilities.get(i);}
        Rectangle2D.Double[] getRect() { return abilityRects; } // Pun intended :D
        boolean getRectFlag() { return rectFlag; }

        RoundRectangle2D.Double getButtonUpgrade() { return buttonUpgrade; }
        RoundRectangle2D.Double getButtonExit() { return buttonExit; }
        RoundRectangle2D.Double getButtonInfo() { return buttonInfo; }
        RoundRectangle2D.Double getButtonChange() { return changeButton; }
        void setColor(int n, Color c) { switch (n) { case 1:c1 = c;break;    case 2:c2 = c;break;    case 3:c3 = c; break;   case 4:c4 = c;break;} }


    }


    private class HeroInfoEnv {
        private RoundRectangle2D.Double BG;

        private RoundRectangle2D.Double buttonDescription, buttonBack;

        private Color BGColor, borderColor, c1, c2;

        private Hero hero;
        private ArrayList<Item> items;

        HeroInfoEnv(Hero hero) {
            c1 = c2 = new Color(130, 130, 130);
            BGColor = new Color(140, 148, 170);
            borderColor = new Color(60, 60, 60);
            BG = new RoundRectangle2D.Double(75, 75, 750, 750, 75, 75);
            buttonDescription = new RoundRectangle2D.Double(550, 750, 100, 50, 25, 25);
            buttonBack = new RoundRectangle2D.Double(700, 750, 100, 50, 25, 25);
            this.hero = hero;
            if (hero != null) items = hero.getItems();
        }

        void draw() {
            g2.setColor(BGColor);
            g2.fill(BG);
            g2.setColor(borderColor);
            g2.draw(BG);
            g2.setFont(mmFont.deriveFont(68f));
            g2.drawString(hero.getName(), 150, 215);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawImage(hero.getHeroImage(), 575, 130, null);
            g2.drawString("HP: " + hero.getHP(), 400, 200);
            g2.drawString("MP: " + hero.getMP(), 400, 230);
            g2.drawString("EP: " + hero.getEP(), 397, 260);
            g2.drawString("Attack Dmg: " + hero.getAttDmg(), 397, 290);

            g2.setFont(mmFont.deriveFont(35f));
            g2.drawString("Abilities:", 150, 340);
            int xI = 180, yI = 370;
            for (Ability a: hero.getAbilities()) {
                g2.drawImage(a.getImage(), xI, yI, 50, 50, null);
                g2.setFont(mmFont.deriveFont(20f));
                g2.drawString(a.getName(), xI, yI + 80);
                g2.drawString("Lvl " + a.getLevel(), xI + 60, yI + 35);
                xI += 150;
                if (xI >= 800) {
                    yI += 180;
                    xI = 200;
                }
            }

            xI = 180; yI = 540;
            g2.setFont(mmFont.deriveFont(35f));
            g2.drawString("Items:", 150, 525);
            for (Item i: items) {
                g2.drawImage(i.getImage(), xI, yI, 75, 75, null);
                g2.setFont(mmFont.deriveFont(22f).deriveFont(Font.BOLD));
                g2.drawString(i.getName(), xI, yI + 100);
                g2.setFont(mmFont.deriveFont(20f));
                g2.drawString("SellPrice: " + i.getCost() / 2, xI, yI + 130);
                if (i instanceof Consumable) {
                    g2.drawString("Remain. " + hero.getRemainingUsages((Consumable) i), xI, yI + 160);
                }
                xI += 150;
                if (xI >= 800) {
                    yI += 180;
                    xI = 200;
                }
            }

            g2.setColor(c1);
            g2.fill(buttonDescription);
            g2.setColor(borderColor);
            g2.draw(buttonDescription);
            g2.setColor(c2);
            g2.fill(buttonBack);
            g2.setColor(borderColor);
            g2.draw(buttonBack);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Info", 585, 780);
            g2.drawString("back", 735, 780);
        }

        Hero getHero() { return hero; }
        void setHero(Hero h) {
            hero = h;
            items = hero.getItems();
        }

        RoundRectangle2D.Double getButtonBack() { return buttonBack; }
        RoundRectangle2D.Double getButtonDescription() { return buttonDescription; }
        void setColor(int n, Color c) { switch (n) { case 1:c1 = c;break;    case 2:c2 = c;break;} }

    }

}

