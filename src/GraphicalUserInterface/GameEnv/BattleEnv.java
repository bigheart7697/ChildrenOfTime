package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;
import GraphicalUserInterface.MusicPlayer;
import GraphicalUserInterface.SimpleMenuListener;
import abilities.*;
import battleMechanics.Battlefield;
import itemMGMT.Consumable;
import player.op.Player;
import units.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BattleEnv extends JComponent {

    private EnvironmentMgr emgr;
    private SimpleMenuListener beListener;

    //Battle Stuff
    private Player player;
    private Battlefield battlefield;
    private int IMPotions;

    //Info stuff
    private Ellipse2D.Double settingsButton, menuButton, cancelButton;
    private Font mmFont, beFont;
    private Color fontColor, bgColor, c1, c2, c3;

    //Hero Stat Stuff
    private Rectangle2D.Double[] heroRect;
    private boolean heroRectFlag;

    //Enemy stat stuff
    private Rectangle2D.Double[] enemyRect;
    private boolean enemyRectFlag, enemyDisplayFlag;

    //Action Panel Stuff
    private RoundRectangle2D.Double attackButton, abilityButton, itemButton, nextTurnButton, infoButton;
    private Color buttonColor, c5, c6, c7, c8, c9, c10;
    private Hero doer, friendlyTarget;
    private Enemy target;
    private boolean attackFlag, abilityFlag, itemFlag, infoFlag;
    private Ellipse2D.Double deployButton;
    private boolean deployFlag, enemyTurnFlag;

    //Ability and Item panel
    private RoundRectangle2D.Double popUpPanel;
    private Rectangle2D.Double[] selectionRect;
    private Object selected;
    private ArrayList<Object> selections;
    private boolean popFlag, disappearFlag, selectionRectFlag;
    private double popX, popSpeed;

    //Info Panel Stuff
    private String displaying, dialogMessage;
    private RoundRectangle2D.Double dialogBox;
    private float size;
    private boolean dialogFlag, victory;

    //Animations
    private int[] swordX, swordY;
    private int startY;
    private int animationMover;
    private boolean attackAnimationFlag;

    private Image BG, arrow;

    private Graphics2D g2;


    BattleEnv(EnvironmentMgr emgr, SimpleMenuListener bel, Player player, Battlefield battlefield, int imp) {

        //Initiation
        this.emgr = emgr;
        beListener = bel;
        this.battlefield = battlefield;
        this.player = player;
        IMPotions = imp;

        //Image Loading
        try {
            BG = ImageIO.read(new File("BattleGraphics/BG.jpg"));
            arrow = ImageIO.read(new File("BattleGraphics/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Font loading
        try {
            mmFont = Font.createFont(Font.TRUETYPE_FONT, new File("MainMenuGraphics/mainMenuFont.ttf"));
            beFont = Font.createFont(Font.TRUETYPE_FONT, new File("GameEnvGraphics/gameEnvFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mmFont);
            ge.registerFont(beFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //Panel Stuff
        fontColor = new Color(210, 240, 210);
        c1 = c2 = c3 = c10 = bgColor = new Color(45, 60, 58);
        heroRect = new Rectangle2D.Double[battlefield.getHeroes().size()];
        heroRectFlag = false;
        enemyRectFlag = enemyDisplayFlag = false;

        //Action Panel Stuff
        c5 = c6 = c7 = c8 = c9 = buttonColor = new Color(100, 131, 137);
        attackFlag = abilityFlag = itemFlag = infoFlag = false;
        doer = friendlyTarget = null;
        target = null;
        deployButton = new Ellipse2D.Double(530, 200, 130, 130);
        deployFlag = enemyTurnFlag = false;

        //PopUp panel stuff
        popX = -650.0;
        popSpeed = 7.5;
        popUpPanel = new RoundRectangle2D.Double(popX, 370, 600, 80, 40, 50);
        popFlag = disappearFlag = selectionRectFlag = false;
        selectionRect = null;
        selections = new ArrayList<>();
        selected = null;


        //Info Panel Stuff
        displaying = dialogMessage = null;
        size = 25f;
        dialogBox = new RoundRectangle2D.Double(230, 200, 730, 330, 50, 50);
        dialogFlag = victory = false;


        //Animation Stuff
        swordX = new int[5];
        swordX[0] = 50;
        swordX[1] = 200;
        swordX[2] = 225;
        swordX[3] = 600;
        swordX[4] = 650;
        swordY = new int[3];
        swordY[0] = 15;
        swordY[1] = 50;
        swordY[2] = 35;
        animationMover = 0;
        startY = getHeight() / 2;
        attackAnimationFlag = false;



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (attackButton != null && attackButton.contains(e.getX(), e.getY())) {
                    panelDisappear();
                    if (doer != null) {
                        if (doer.getEP() < 2) {
                            displaying = "You don't have enough energy points";
                        }
                        else {
                            attackFlag = true;
                            displaying = "Choose a target";
                        }
                    }
                    else displaying = "Choose a hero first";
                    abilityFlag = itemFlag = infoFlag = deployFlag = false;
                    size = 25f;
                }
                if (abilityButton != null && !itemFlag && abilityButton.contains(e.getX(), e.getY())) {
                    if (doer != null) {
                        abilityFlag = true;
                        setUpPanel();
                        displaying = "Choose an ability";
                    }
                    else displaying = "Choose a hero first";
                    attackFlag = itemFlag = infoFlag = deployFlag = false;
                    size = 25f;
                }
                if (itemButton != null && !abilityFlag && itemButton.contains(e.getX(), e.getY())) {
                    if (doer != null) {
                        itemFlag = true;
                        setUpPanel();
                        displaying = "Choose an Item";
                    }
                    else displaying = "Choose a hero first";
                    abilityFlag = attackFlag = infoFlag = deployFlag = false;
                    size = 25f;
                }
                if (infoButton != null && infoButton.contains(e.getX(), e.getY())) {
                    if (doer != null) {
                        displaying = doer.getDescription();
                        size = 15f;
                    }
                    else {
                        displaying = "Choose a hero first";
                        size = 25f;
                    }
                    abilityFlag = itemFlag = attackFlag = deployFlag = false;
                }
                if (nextTurnButton != null && nextTurnButton.contains(e.getX(), e.getY())) {
                    enemyTurnFlag = true;
                    size = 25f;
                }

                if (deployFlag && deployButton.contains(e.getX(), e.getY())) {

                    if (attackFlag) {
                        attackAnimationFlag = true;
                        animationMover = -700;
                        boolean hasAttackModifier = false;
                        for (Ability ability : doer.getAbilities()) {
                            if (ability instanceof AttackModifier && ability.getLevel() > 0) {
                                ability.setTarget(target);
                                if (ability.getName().equalsIgnoreCase("Swirling attack") && ability.getLevel() > 0) {
                                    ability.cast();
                                    doer.setEP(doer.getEP() - 2);
                                    hasAttackModifier = true;
                                    displaying = doer.getName() + " has successfully attacked " + target.getName() +
                                            " and used the ability: Swirling Attack ";
                                    size = 15f;
                                }
                                else if (ability.getName().equalsIgnoreCase("Critical strike") && ability.getLevel() > 0) {
                                    hasAttackModifier = ability.cast();
                                    if (hasAttackModifier) {
                                        doer.setEP(doer.getEP() - 2);
                                        displaying = doer.getName() + " has successfully attacked " + target.getName() +
                                                " and used the ability: Critical Strike ";
                                        size = 15f;
                                    }
                                }
                                break;
                            }
                        }
                        if (!hasAttackModifier) {
                            doer.setEP(doer.getEP() - 2);
                            int tarHP = target.getHP() - doer.getAttDmg();
                            if (tarHP < 0) tarHP = 0;
                            //Decreasing HP gradually
                            while (target.getHP() > tarHP) {
                                target.setHP(target.getHP() - 1);
                                paintComponent(getGraphics());
                            }
                            displaying = doer.getName() + " has successfully attacked " + target.getName() + " with " + doer.getAttDmg() + " power. ";
                        }
                        if (target.getHP() <= 0) displaying += target.getName() + " has died. ";
                        attackFlag = false;
                        doer = null;
                        target = null;

                    }

                    if (abilityFlag) {

                        ActiveAbility aa = (ActiveAbility) selected;
                        if (aa.getMagicCost() > doer.getMP()) {
                            displaying = ("You don't have enough magic points");
                        }
                        else if (aa.getEPCost() > doer.getMP()) {
                            displaying = ("You don't have enough energy points");
                        }
                        else if (aa.getRemainingCD() > 0) {
                            displaying = ("Your desired ability is still in cooldown");
                        }
                        else {
                            if (target == null) {
                                aa.setTarget(friendlyTarget);
                                displaying = (doer.getName() + " has casted " + aa.getName() + " on " + friendlyTarget.getName() + " successfully!");
                                aa.cast();
                            }
                            else if (friendlyTarget == null) {
                                aa.setTarget(target);
                                displaying = (doer.getName() + " has casted " + aa.getName() + " on " + target.getName() + " successfully!");
                                aa.cast();
                            }

                        }
                        panelDisappear();
                        abilityFlag = false;
                        doer = null;
                        target = null;
                        friendlyTarget = null;
                        selected = null;
                    }

                    if (itemFlag) {
                        Consumable i = (Consumable) selected;
                        i.setTarget(friendlyTarget);
                        doer.useItem(i);
                        displaying = (doer.getName() + " " +  i.getSuccessMessage1() + " " + friendlyTarget.getName() + " " + i.getSuccessMessage2());
                        panelDisappear();
                        itemFlag = false;
                        doer = null;
                        target = null;
                        friendlyTarget = null;
                        selected = null;
                    }

                    deployFlag = false;
                    size = 25f;
                }

                //Choosing a hero
                if (heroRectFlag && !attackFlag && !abilityFlag && !itemFlag && !infoFlag) {
                    for (int i = 0; i < heroRect.length; i++) {
                        if (heroRect[i].contains(e.getX(), e.getY())) {
                            doer = battlefield.getHeroes().get(i);
                            break;
                        }
                        else doer = null;
                    }
                }

                //Choosing a Friend target
                if (enemyRectFlag && selected != null && (selected instanceof Restorer || selected instanceof  Consumable)) {
                    for (int i = 0; i < heroRect.length; i++) {
                        if (heroRect[i].contains(e.getX(), e.getY())) {
                            friendlyTarget = battlefield.getHeroes().get(i);
                            deployFlag = true;
                            break;
                        }
                        else {
                            friendlyTarget = null;
                            deployFlag = false;
                        }
                    }
                }

                //Choosing a target
                if (enemyRectFlag && (attackFlag || (selected != null && selected instanceof Attacker))) {
                    for (int i = 0; i < enemyRect.length; i++) {
                        if (enemyRect[i].contains(e.getX(), e.getY())) {
                            target = battlefield.getEnemies().get(i);
                            startY = (int) (enemyRect[i].getY() + enemyRect[i].getHeight() / 2);
                            deployFlag = true;
                            break;
                        }
                        else {
                            target = null;
                            deployFlag = false;
                        }
                    }
                }

                if (menuButton.contains(e.getX(), e.getY())) {
                    emgr.frame().setSize(new Dimension(1280, 800));
                    emgr.frame().setLocationRelativeTo(null);
                    beListener.switchTo("main");
                }
                if (cancelButton.contains(e.getX(), e.getY())) {
                    doer = null;
                    selected = null;
                    friendlyTarget = null;
                    target = null;
                    attackFlag = abilityFlag = itemFlag = infoFlag = false;
                    deployFlag = false;
                    panelDisappear();
                    displaying = "Canceled";
                }

                //Choosing ability/item
                if (selectionRectFlag) {
                    for (int i = 0; i < selectionRect.length; i++) {
                        if (selectionRect[i].contains(e.getX(), e.getY())) {
                            selected = selections.get(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (settingsButton != null && settingsButton.contains(e.getX(), e.getY())) c1 = new Color(56, 73, 71);
                if (menuButton != null && menuButton.contains(e.getX(), e.getY())) c2 = new Color(56, 73, 71);
                if (cancelButton != null && cancelButton.contains(e.getX(), e.getY())) c3 = new Color(56, 73, 71);
                if (attackButton != null && attackButton.contains(e.getX(), e.getY())) c5 = new Color(116, 150, 156);
                if (abilityButton != null && abilityButton.contains(e.getX(), e.getY())) c6 = new Color(116, 150, 156);
                if (itemButton != null && itemButton.contains(e.getX(), e.getY())) c7 = new Color(116, 150, 156);
                if (infoButton != null && infoButton.contains(e.getX(), e.getY())) c8 = new Color(116, 150, 156);
                if (nextTurnButton != null && nextTurnButton.contains(e.getX(), e.getY())) c9 = new Color(116, 150, 156);
                if (deployButton.contains(e.getX(), e.getY())) c10 = new Color(56, 73, 71);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                c1 = c2 = c3 = c10 = bgColor;
                c5 = c6 = c7 = c8 = c9 = buttonColor;
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
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawImage(BG, 0, -1000, getWidth(), getHeight() + 760, null);

        //Hero Postures
        int xH = 450, yH = getHeight() - 350, size = 70;
        for (Hero h: battlefield.getHeroes()) {
            if (h.getName().equalsIgnoreCase("meryl")) {
                g2.drawImage(h.getSecondHeroImage(), xH - 10, yH - 5, size, size, null);
                if (!heroRectFlag) heroRect[battlefield.getHeroes().indexOf(h)] = new Rectangle2D.Double(xH - 10, yH - 5, size, size);
            }
            else {
                g2.drawImage(h.getSecondHeroImage(), xH, yH, size, size, null);
                if (!heroRectFlag) heroRect[battlefield.getHeroes().indexOf(h)] = new Rectangle2D.Double(xH, yH, size, size);
            }
            xH -= 75;
            size += 10;
        }
        heroRectFlag = true;

        //Chosen Hero
        double xD , yD, sizeD;
        if (doer != null) {
            xD = heroRect[battlefield.getHeroes().indexOf(doer)].getX();
            yD = getHeight() - 390;
            sizeD = heroRect[battlefield.getHeroes().indexOf(doer)].getWidth();
            if (doer.getName().equalsIgnoreCase("meryl")) {
                xD += 13;
            }
            g2.drawImage(arrow, (int)(xD + sizeD / 3.5), (int)(yD), 35, 35, null);
        }

        //Hero Stats
        xH = 35; yH = 35;
        for (Hero h: battlefield.getHeroes()) {
            g2.setColor(buttonColor);
            g2.fill(new RoundRectangle2D.Double(xH, yH, 175, 100, 50, 50));
            g2.drawImage(h.getHeroHeadImage(), xH + 15, yH + 10, 50, 50, null);
            g2.setFont(beFont.deriveFont(20f));
            g2.setColor(fontColor);
            g2.drawString(h.getName(), xH + 10, yH + 80);
            g2.setFont(beFont.deriveFont(12f));
            g2.drawString("HP: " + h.getHP() + "/" + h.getMaxHP(), xH + 70, yH + 20);
            g2.drawString("MP: " + h.getMP() + "/" + h.getMaxMP(), xH + 70, yH + 33);
            g2.drawString("EP: " + h.getEP() + "/" + h.getMaxEP(), xH + 70, yH + 46);
            g2.drawString("AttDmg:  " + h.getAttDmg(), xH + 70, yH + 59);
            xH += 200;
            if (xH > 250) {
                xH = 35;
                yH += 125;
            }
        }

        //Chosen Hero
        if (doer != null) {
            g2.setColor(fontColor);
            g2.setFont(beFont.deriveFont(30f));
            g2.drawString("Selected: " + doer.getName(), 50, 335);
        }


        //PopUp panel for items and abilities
        if (popFlag) {
            g2.setColor(buttonColor);
            g2.fill(popUpPanel);
            g2.setColor(fontColor);
            g2.draw(popUpPanel);
            if (popX < 40) {
                popX += popSpeed;
                popUpPanel = new RoundRectangle2D.Double(popX, 370, 600, 80, 40, 50);
            }
            if (popX >= 40) {
                g2.setFont(mmFont.deriveFont(25f));

                if (abilityFlag) {
                    int cnt = 0;
                    for (ActiveAbility a : doer.getActAbs()) {
                        if (a.getLevel() > 0) cnt ++;
                    }
                    if (!selectionRectFlag) selectionRect = new Rectangle2D.Double[cnt];
                    g2.drawString("Abilities:", 65, 415);
                    boolean emptyFlag = true;
                    xD = 155; yD = 380;
                    for (ActiveAbility a : doer.getActAbs()) {
                        if (a.getLevel() == 0) continue;
                        g2.drawImage(a.getImage(), (int)xD, (int)yD, 40, 40, null);
                        if (!selectionRectFlag) selectionRect[doer.getActAbs().indexOf(a)] = new Rectangle2D.Double(xD, yD, 40, 40);
                        if (!selectionRectFlag) selections.add(a);
                        g2.setFont(mmFont.deriveFont(15f));
                        g2.drawString(a.getName(), (int)xD, (int)yD + 55);
                        g2.drawString(a.getEPCost() + "ep", (int)xD + 50, (int)yD + 15);
                        g2.drawString(a.getMagicCost() + "mp", (int)xD + 50, (int)yD + 30);
                        g2.drawString(a.getCD() + "cd", (int)xD + 50, (int)yD + 45);
                        g2.drawString(a.getRemainingCD() + "rcd", (int)xD + 50, (int)yD + 60);
                        emptyFlag = false;
                        xD += 105;
                    }
                    selectionRectFlag = true;
                    if (selected != null){
                        g2.draw(selectionRect[doer.getActAbs().indexOf(selected)]);
                        displaying = "Choose target";
                    }
                    if (emptyFlag) {
                        g2.setFont(mmFont.deriveFont(25f));
                        g2.drawString("You don't have any usable abilities!", 165, 415);
                    }
                }

                else if (itemFlag) {
                    g2.drawString("Items:", 65, 415);

                    ArrayList<Consumable> items = doer.getItems().stream().filter(i -> i instanceof Consumable).map(i -> (Consumable) i).collect(Collectors.toCollection(ArrayList::new));
                    if (items.size() == 0) g2.drawString("Selected hero doesn't have any usable items!", 165, 415);
                    else {
                        if (!selectionRectFlag) selectionRect = new Rectangle2D.Double[items.size()];
                        xD = 155; yD = 390;
                        for (Consumable i: items) {
                            g2.drawImage(i.getImage(), (int)xD, (int)yD, 40, 40, null);
                            if (!selectionRectFlag) selectionRect[items.indexOf(i)] = new Rectangle2D.Double(xD, yD, 40, 40);
                            if (!selectionRectFlag) selections.add(i);
                            g2.setFont(mmFont.deriveFont(15f));
                            g2.drawString(" " + i.getName(), (int)xD + 50, (int)yD + 15);
                            g2.drawString(doer.getRemainingUsages(i) + " uses remain.", (int)xD + 50, (int)yD + 30);
                            xD += 105;
                        }
                        selectionRectFlag = true;
                        if (selected != null){
                            g2.draw(selectionRect[items.indexOf(selected)]);
                            displaying = "Choose target";
                        }
                    }
                }

            }
        }
        else if (disappearFlag) {
            g2.setColor(buttonColor);
            g2.fill(popUpPanel);
            g2.setColor(fontColor);
            g2.draw(popUpPanel);
            if (popX > -650) {
                popX -= popSpeed;
                popUpPanel = new RoundRectangle2D.Double(popX, 370, 600, 80, 40, 50);
            }
        }


        //General Buttons
        g2.setColor(c1);
        if (settingsButton == null) settingsButton = new Ellipse2D.Double(460, 50, 80, 80);
        g2.fill(settingsButton);
        g2.setColor(fontColor);
        g2.drawOval(460, 50, 80, 80);

        g2.setColor(c2);
        if (menuButton == null) menuButton = new Ellipse2D.Double(560, 50, 80, 80);
        g2.fill(menuButton);
        g2.setColor(fontColor);
        g2.drawOval(560, 50, 80, 80);

        g2.setColor(c3);
        if (cancelButton == null) cancelButton = new Ellipse2D.Double(660, 50, 80, 80);
        g2.fill(cancelButton);
        g2.setColor(fontColor);
        g2.drawOval(660, 50, 80, 80);

        g2.setFont(mmFont.deriveFont(20f));
        g2.drawString("Settings", 473, 95);
        g2.drawString("Menu", 583, 95);
        g2.drawString("Cancel", 679, 95);


        //Action Panel
        g2.setColor(bgColor);
        g2.fill(new RoundRectangle2D.Double(10, getHeight() - 230, 400, 220, 50, 50));
        g2.setColor(fontColor);
        g2.draw(new RoundRectangle2D.Double(10, getHeight() - 230, 400, 220, 50, 50));

        if (attackButton == null) attackButton = new RoundRectangle2D.Double(30, getHeight() - 210, 150, 50, 25, 25);
        g2.setColor(c5);
        g2.fill(attackButton);
        g2.setColor(fontColor);
        g2.draw(attackButton);
        g2.setFont(beFont.deriveFont(30f));
        g2.drawString("Attack", 50, getHeight() - 180);

        if (abilityButton == null) abilityButton = new RoundRectangle2D.Double(30, getHeight() - 145, 150, 50, 25, 25);
        g2.setColor(c6);
        g2.fill(abilityButton);
        g2.setColor(fontColor);
        g2.draw(abilityButton);
        g2.setFont(beFont.deriveFont(30f));
        g2.drawString("Ability", 50, getHeight() - 115);

        if (itemButton == null) itemButton = new RoundRectangle2D.Double(30, getHeight() - 80, 150, 50, 25, 25);
        g2.setColor(c7);
        g2.fill(itemButton);
        g2.setColor(fontColor);
        g2.draw(itemButton);
        g2.setFont(beFont.deriveFont(30f));
        g2.drawString("Item", 70, getHeight() - 50);

        if (infoButton == null) infoButton = new RoundRectangle2D.Double(195, getHeight() - 80, 200, 50, 25, 25);
        g2.setColor(c8);
        g2.fill(infoButton);
        g2.setColor(fontColor);
        g2.draw(infoButton);
        g2.setFont(beFont.deriveFont(30f));
        g2.drawString("Info", 260, getHeight() - 50);

        if (nextTurnButton == null) nextTurnButton = new RoundRectangle2D.Double(195, getHeight() - 145, 200, 50, 25, 25);
        g2.setColor(c9);
        g2.fill(nextTurnButton);
        g2.setColor(fontColor);
        g2.draw(nextTurnButton);
        g2.setFont(beFont.deriveFont(30f));
        g2.drawString("Next Turn", 215, getHeight() - 115);
        g2.setFont(beFont.deriveFont(22f));
        g2.drawString("Current Turn: " + battlefield.getTurn(), 200, getHeight() - 170);
        //Immortality Potions remaining
        g2.setColor(fontColor);
        g2.drawString("IM Potions: " + IMPotions, 213, getHeight() - 190);

        if (deployFlag) {
            g2.setColor(c10);
            g2.fill(deployButton);
            g2.setColor(fontColor);
            g2.draw(deployButton);
            g2.setColor(fontColor);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Deploy!", 565, 270);
        }


        //Info Panel
        g2.setColor(bgColor);
        g2.fill(new RoundRectangle2D.Double(420, getHeight() - 230, getWidth() - 430, 220, 50, 50));
        g2.setColor(fontColor);
        g2.draw(new RoundRectangle2D.Double(420, getHeight() - 230, getWidth() - 430, 220, 50, 50));
        if (displaying != null) {
            drawDisplayingInfo();
        }


        //Enemy Postures
        if (enemyDisplayFlag) {
            xH = getWidth() - 550;
            yH = getHeight() - 350;
            size = 70;
            for (Unit u : battlefield.getEnemies()) {
                Enemy e = (Enemy) u;
                if (e.isDead()) continue;
                if (e instanceof FinalBoss) break;
                if (!(e instanceof Angel)) {
                    g2.drawImage(e.getImage(), xH, yH, size, size, null);
                    if (!enemyRectFlag) enemyRect[battlefield.getEnemies().indexOf(e)] = new Rectangle2D.Double(xH, yH, size, size);
                    xH += 75;
                    size += 10;
                }
            }
            xH = getWidth() - 450;
            yH = getHeight() - 470;
            size = 100;
            for (Unit u : battlefield.getEnemies()) {
                Enemy e = (Enemy) u;
                if (e.isDead()) continue;
                if (e instanceof FinalBoss) break;
                if (e instanceof Angel) {
                    g2.drawImage(e.getImage(), xH, yH, size, size, null);
                    if (!enemyRectFlag) enemyRect[battlefield.getEnemies().indexOf(e)] = new Rectangle2D.Double(xH, yH, size, size);
                    xH += 75;
                    size += 10;
                }
            }
            xH = getWidth() - 550;
            yH = getHeight() - 570;
            for (Unit u: battlefield.getEnemies()) {
                Enemy e = (Enemy) u;
                if (e.isDead()) continue;
                if (e instanceof FinalBoss) {
                    g2.drawImage(e.getImage(), xH, yH, 495, 254, null);
                    if (!enemyRectFlag) {
                        System.out.println(1);
                        enemyRect[battlefield.getEnemies().indexOf(e)] = new Rectangle2D.Double(xH, yH, 495, 254);
                    }
                }
            }
            enemyRectFlag = true;
        }

        //Enemy Stats
        xH = getWidth() - 220; yH = 35;
        for (Unit u: battlefield.getEnemies()) {
            Enemy e = (Enemy) u;
            if (e.isDead()) continue;
            g2.setColor(buttonColor);
            g2.fill(new RoundRectangle2D.Double(xH, yH, 175, 75, 50, 50));
            g2.setFont(beFont.deriveFont(20f));
            g2.setColor(fontColor);
            g2.drawString(e.getName(), xH + 10, yH + 65);
            g2.setFont(beFont.deriveFont(12f));
            g2.drawString("HP: " + e.getHP() + "/" + e.getMaxHP(), xH + 40, yH + 20);
            g2.drawString("AttDmg:  " + e.getAttDmg(), xH + 40, yH + 33);
            xH -= 200;
            if (xH < getWidth() - 420) {
                xH = getWidth() - 220;
                yH += 100;
            }
        }

        //Chosen Enemy
        if (target != null) {
            xD = enemyRect[battlefield.getEnemies().indexOf(target)].getX();
            yD = enemyRect[battlefield.getEnemies().indexOf(target)].getY();
            sizeD = enemyRect[battlefield.getEnemies().indexOf(target)].getWidth();
            g2.setColor(new Color(160, 255, 224));
            g2.drawOval((int)xD, (int)yD, (int)sizeD, (int)sizeD);
            g2.drawLine((int)(xD + sizeD / 2), (int)yD, (int)(xD + sizeD / 2), (int)(yD + sizeD));
            g2.drawLine((int)xD, (int)(yD + sizeD / 2), (int)(xD + sizeD), (int)(yD + sizeD / 2));
        }

        //Chosen Friendly Target
        if (friendlyTarget != null) {
            xD = heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getX();
            yD = heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getY();
            if (friendlyTarget.getName().equalsIgnoreCase("meryl")) {
                xD += 10; yD += 5;
            }
            sizeD = heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getWidth();
            g2.setColor(new Color(160, 255, 224));
            g2.drawOval((int)xD, (int)yD, (int)sizeD, (int)sizeD);
        }

        //Dialog Panel
        if (dialogFlag) {
            g2.setColor(bgColor);
            g2.fill(dialogBox);
            g2.setColor(fontColor);
            g2.draw(dialogBox);
            int charMax = 0;
            String temp = "";
            ArrayList<String> tokens = new ArrayList<>();
            for (int i = 0; i < dialogMessage.length(); i++) {
                temp += dialogMessage.charAt(i);
                charMax ++;
                if ((charMax > 75 && dialogMessage.charAt(i) == ' ') || i == dialogMessage.length() - 1) {
                    tokens.add(temp);
                    temp = "";
                    charMax = 0;
                }
            }
            int xT = (int)dialogBox.getX() + 30, yT = (int)dialogBox.getY() + 50;
            for (String s : tokens) {
                g2.setFont(mmFont.deriveFont(25f));
                g2.drawString(s, xT, yT);
                yT += 40;
            }

        }


        //Animations
        if (attackAnimationFlag) {
            if (animationMover > 0) {
                int[] xs = {swordX[0] + animationMover, swordX[0] + animationMover, swordX[1] + animationMover, swordX[1] + animationMover,
                        swordX[2] + animationMover, swordX[2] + animationMover, swordX[3] + animationMover, swordX[4] + animationMover};
                g2.setColor(new Color(200, 200, 200));
                int[] ys1 = {startY, startY - swordY[0], startY - swordY[0], startY - swordY[1],
                        startY - swordY[1], startY - swordY[2], startY - swordY[2], startY};
                int[] ys2 = {startY, startY + swordY[0], startY + swordY[0], startY + swordY[1],
                        startY + swordY[1], startY + swordY[2], startY + swordY[2], startY};
                g2.fill(new Polygon(xs, ys1, 8));
                g2.setColor(new Color(140, 140, 140));
                g2.fill(new Polygon(xs, ys2, 8));
            }
            else {
                int[] xs = {swordX[0] , swordX[0] , swordX[1] , swordX[1] ,
                        swordX[2] , swordX[2] , swordX[3] , swordX[4] };
                g2.setColor(new Color(Math.abs(animationMover) % 255, Math.abs(animationMover) % 255, Math.abs(animationMover) % 255));
                int[] ys1 = {startY, startY - swordY[0], startY - swordY[0], startY - swordY[1],
                        startY - swordY[1], startY - swordY[2], startY - swordY[2], startY};
                int[] ys2 = {startY, startY + swordY[0], startY + swordY[0], startY + swordY[1],
                        startY + swordY[1], startY + swordY[2], startY + swordY[2], startY};
                g2.fill(new Polygon(xs, ys1, 8));
                g2.setColor(new Color(255 - Math.abs(animationMover) % 255, 255 - Math.abs(animationMover) % 255, 255 - Math.abs(animationMover) % 255));
                g2.fill(new Polygon(xs, ys2, 8));
            }
            animationMover += 10;
            if (animationMover > getWidth() + 700) attackAnimationFlag = false;
        }


        if (victory && !attackAnimationFlag) {
            g2.setFont(mmFont.deriveFont(200f));
            g2.setColor(fontColor);
            g2.drawString("You Won!", 340, 400);
        }


        g.drawImage(buffer, 0, 0, null);
    }

    public void updateEnv() {
        if (enemyTurnFlag) {
            for (Enemy enemy : battlefield.getEnemies()) {
                enemy.setTarget();
                displaying = enemy.action();
                for (Hero h : battlefield.getHeroes()) {
                    if (h.getHP() <= 0) {
                        player.useIMPotion(h);
                        if (h.isDead()) {
                            MusicPlayer.stopMusic();
                            MusicPlayer.playMusic("audios/gameOver.wav");
                            dialogMessage = (h.getName() + " is dead and so is the spirit of this adventure, Game Over!");
                            dialogFlag = true;
                            paintComponent(getGraphics());
                            try {
                                Thread.sleep(7000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            dialogFlag = false;
                            emgr.frame().setSize(new Dimension(1280, 800));
                            emgr.frame().setLocationRelativeTo(null);
                            beListener.switchTo("main");
                            return;
                        }
                        else {
                            dialogMessage = (h.getName() + " is dying, immortality potion was used for reincarnation process, you now have "
                                    + player.getIMPotionRemaining() + " immortality potions left");
                            dialogFlag = true;
                            paintComponent(getGraphics());
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            dialogFlag = false;
                        }
                    }
                }
                paintComponent(getGraphics());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            battlefield.updateBattlefield();
            dialogMessage = "Your turn.";
            enemyTurnFlag = false;
        }
        if (battlefield.removeDeadUnit()) {
            enemyRectFlag = false;
            enemyRect = new Rectangle.Double[battlefield.getEnemies().size()];
        }
        if (battlefield.getEnemies().size() == 0) victory = true;
        if (victory && !attackAnimationFlag) {
            MusicPlayer.stopMusic();
            MusicPlayer.playMusic("audios/victory.wav");
            battlefield.getHeroes().forEach(Hero::renew);
            paintComponent(getGraphics());
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MusicPlayer.stopMusic();
            MusicPlayer.playMusic("audios/game");
            beListener.switchTo("game");
        }
        repaint();
    }

    void setEnemies(ArrayList<Unit> enemies) {
        battlefield.getEnemies().clear();
        battlefield.addUnits(enemies);
        enemyDisplayFlag = true;
        enemyRect = new Rectangle2D.Double[enemies.size()];
        enemyRectFlag = false;
    }

    void setDisplayInfo(String s) { displaying = s; }
    private void drawDisplayingInfo() {
        int charMax = 0;
        String temp = "";
        ArrayList<String> tokens = new ArrayList<>();
        for (int i = 0; i < displaying.length(); i++) {
            temp += displaying.charAt(i);
            charMax ++;
            if ((charMax > 140 && displaying.charAt(i) == ' ') || i == displaying.length() - 1) {
                tokens.add(temp);
                temp = "";
                charMax = 0;
            }
        }
        int xT = 440, yT = getHeight() - 200;
        for (String s : tokens) {
            g2.setFont(mmFont.deriveFont(size));
            g2.drawString(s, xT, yT);
            yT += 30;
        }
    }

    private void setUpPanel() {
        disappearFlag = false;
        popFlag = true;
        popX = -650.0;
    }

    private void panelDisappear() {
        disappearFlag = true;
        popFlag = false;
        selectionRectFlag = false;
        selections.clear();
    }

}

