package GraphicalUserInterface.GameEnv;

import GraphicalUserInterface.EnvironmentMgr;
import GraphicalUserInterface.MusicPlayer;
import GraphicalUserInterface.SimpleMenuListener;
import abilities.*;
import battleMechanics.Battlefield;
import itemMGMT.Consumable;
import itemMGMT.Item;
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

    //Info Dialog stuff
    private String info;
    private RoundRectangle2D.Double textHolder;
    private Rectangle2D.Double[] infoRects1, infoRects2;
    private Rectangle2D.Double selected1, selected2;
    private ArrayList<String> infoStrings;
    private boolean infoRectsFlag, isDisplaying;
    private boolean infoDialogFlag;

    //Animations
    private int[] swordX, swordY, axeX, axeY;
    private int swordStartY, swordRedColor, swordBlueColor, axeStartX, axeColor;
    private int animationMover;
    private boolean attackAnimationFlag, enemyAnimationFlag, healAnimationFlag, collectorAnimationFlag;
    private Image[] glows; //for default scenario only :D
    private Image greenShade;
    private int glowIndex, glowDuration;

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
            glows = new Image[4];
            for (int i = 0; i < 4; i++) {
                glows[i] = ImageIO.read(new File("BattleGraphics/glow" + (i + 1) + ".png"));
            }
            greenShade = ImageIO.read(new File("BattleGraphics/greenShade.png"));
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
        popX = 0.0;
        popSpeed = 1.25;
        popUpPanel = new RoundRectangle2D.Double(40, 370, popX, 80, 40, 50);
        popFlag = disappearFlag = selectionRectFlag = false;
        selectionRect = null;
        selections = new ArrayList<>();
        selected = null;


        //Info Panel Stuff
        displaying = dialogMessage = null;
        size = 25f;
        dialogBox = new RoundRectangle2D.Double(230, 200, 730, 330, 50, 50);
        dialogFlag = victory = false;

        //Info Dialog Stuff
        infoDialogFlag = false;
        infoRectsFlag = false;
        isDisplaying = false;
        textHolder = new RoundRectangle2D.Double(150, 200, 900, 400, 50, 50);
        infoRects1 = new Rectangle2D.Double[5];
        infoRects1[0] = new Rectangle2D.Double(160, 280, 80, 35); // Classes
        infoRects1[1] = new Rectangle2D.Double(160, 320, 80, 35); // Heroes
        infoRects1[2] = new Rectangle2D.Double(160, 360, 80, 35); // Abilities
        infoRects1[3] = new Rectangle2D.Double(160, 400, 80, 35); // Items
        infoRects1[4] = new Rectangle2D.Double(160, 440, 80, 35); // Enemies
        selected1 = null;
        selected2 = null;
        infoStrings = new ArrayList<>();
        info = null;


        //Animation Stuff
        swordX = new int[5];
        swordX[0] = 25;
        swordX[1] = 100;
        swordX[2] = 112;
        swordX[3] = 300;
        swordX[4] = 325;
        swordY = new int[3];
        swordY[0] = 7;
        swordY[1] = 25;
        swordY[2] = 17;
        swordStartY = getHeight() / 2;
        swordRedColor = 0;
        swordBlueColor = 0;
        axeX = new int[6];
        axeX[0] = 200;
        axeX[1] = 245;
        axeX[2] = 256;
        axeX[3] = 267;
        axeX[4] = 312;
        axeX[5] = 387;
        axeY = new int[3];
        axeY[0] = 7;
        axeY[1] = 50;
        axeY[2] = 80;
        axeStartX = -25;
        axeColor = 0;
        animationMover = 0;
        attackAnimationFlag = enemyAnimationFlag = healAnimationFlag = collectorAnimationFlag = false;
        glowIndex = 0;
        glowDuration = 0;



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!infoDialogFlag && attackButton != null && attackButton.contains(e.getX(), e.getY())) {
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
                if (!infoDialogFlag && abilityButton != null && !itemFlag && abilityButton.contains(e.getX(), e.getY())) {
                    if (doer != null) {
                        abilityFlag = true;
                        setUpPanel();
                        displaying = "Choose an ability";
                    }
                    else displaying = "Choose a hero first";
                    attackFlag = itemFlag = infoFlag = deployFlag = false;
                    size = 25f;
                }
                if (!infoDialogFlag && itemButton != null && !abilityFlag && itemButton.contains(e.getX(), e.getY())) {
                    if (doer != null) {
                        itemFlag = true;
                        setUpPanel();
                        displaying = "Choose an Item";
                    }
                    else displaying = "Choose a hero first";
                    abilityFlag = attackFlag = infoFlag = deployFlag = false;
                    size = 25f;
                }
                if (!infoDialogFlag && nextTurnButton != null && nextTurnButton.contains(e.getX(), e.getY())) {
                    enemyTurnFlag = true;
                    size = 25f;
                }
                if (infoButton != null && infoButton.contains(e.getX(), e.getY())) {
                    infoDialogFlag = !infoDialogFlag;
                    if (infoDialogFlag) size = 20f;
                    else size = 25f;
                    selected1 = null;
                    selected2 = null;
                    infoRectsFlag = false;
                }

                //Deploying
                if (!infoDialogFlag && deployFlag && deployButton.contains(e.getX(), e.getY())) {

                    //Decreasing enemy HPs gradually
                    int[] enemyHPsBefore = new int[battlefield.getEnemies().size()];
                    for (int i = 0; i < battlefield.getEnemies().size(); i++)
                        enemyHPsBefore[i] = battlefield.getEnemies().get(i).getHP();

                    if (attackFlag) {
                        attackAnimationFlag = true;
                        animationMover = -700;
                        boolean hasAttackModifier = false;
                        for (Ability ability : doer.getAbilities()) {
                            if (ability instanceof AttackModifier && ability.getLevel() > 0) {
                                ability.setTarget(target);
                                if (ability.getName().equalsIgnoreCase("Swirling attack") && ability.getLevel() > 0) {
                                    ability.cast();
                                    glowIndex = battlefield.getHeroes().indexOf(doer);
                                    glowDuration = 200;
                                    swordBlueColor = 55;
                                    doer.setEP(doer.getEP() - 2);
                                    hasAttackModifier = true;
                                    displaying = doer.getName() + " has successfully attacked " + target.getName() +
                                            " and used the ability: Swirling Attack ";
                                    size = 25f;
                                }
                                else if (ability.getName().equalsIgnoreCase("Critical strike") && ability.getLevel() > 0) {
                                    hasAttackModifier = ability.cast();
                                    if (hasAttackModifier) {
                                        glowIndex = battlefield.getHeroes().indexOf(doer);
                                        glowDuration = 200;
                                        swordBlueColor = 55;
                                        doer.setEP(doer.getEP() - 2);
                                        displaying = doer.getName() + " has successfully attacked " + target.getName() +
                                                " and used the ability: Critical Strike ";
                                        size = 25f;
                                    }
                                }
                                break;
                            }
                        }
                        if (!hasAttackModifier) {
                            doer.setEP(doer.getEP() - 2);
                            target.setHP(target.getHP() - doer.getAttDmg());
                            displaying = doer.getName() + " has successfully attacked " + target.getName() + " with " + doer.getAttDmg() + " power. ";
                        }
                        attackFlag = false;
                        doer = null;
                        target = null;

                    }

                    if (abilityFlag) {
                        glowIndex = battlefield.getHeroes().indexOf(doer);
                        glowDuration = 200;
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
                            panelDisappear();
                            if (aa instanceof Attacker) {
                                attackAnimationFlag = true;
                                animationMover = -700;
                                swordRedColor = 55;
                            }
                            if (target == null) {
                                healAnimationFlag = true;
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

                    //Decreasing Enemy HPs gradually
                    int[] enemyHPsAfter = new int[battlefield.getEnemies().size()];
                    for (int i = 0; i < battlefield.getEnemies().size(); i++) {
                        enemyHPsAfter[i] = battlefield.getEnemies().get(i).getHP();
                        if (enemyHPsAfter[i] < 0) enemyHPsAfter[i] = 0;
                    }
                    for (int i = 0; i < battlefield.getEnemies().size(); i++)
                        battlefield.getEnemies().get(i).setHP(enemyHPsBefore[i]);
                    boolean hpFlag = true;
                    while (hpFlag) {
                        hpFlag = false;
                        for (int i = 0; i < battlefield.getEnemies().size(); i++) {
                            if (battlefield.getEnemies().get(i).getHP() > enemyHPsAfter[i]) {
                                hpFlag = true;
                                battlefield.getEnemies().get(i).setHP(battlefield.getEnemies().get(i).getHP() - 1);
                            }
                        }
                        paintComponent(getGraphics());
                    }

                    //Dead enemies
                    battlefield.getEnemies().stream().filter(en -> en.getHP() <= 0).forEach(en -> displaying += en.getName() + " has died. ");

                    deployFlag = false;
                    size = 25f;
                }

                //Choosing a hero
                if (!infoDialogFlag && heroRectFlag && !attackFlag && !abilityFlag && !itemFlag && !infoFlag) {
                    for (int i = 0; i < heroRect.length; i++) {
                        if (heroRect[i].contains(e.getX(), e.getY())) {
                            doer = battlefield.getHeroes().get(i);
                            break;
                        }
                        else doer = null;
                    }
                }

                //Choosing a Friend target
                if (!infoDialogFlag && enemyRectFlag && selected != null && (selected instanceof Restorer || selected instanceof  Consumable)) {
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
                if (!infoDialogFlag && enemyRectFlag && (attackFlag || (selected != null && selected instanceof Attacker))) {
                    for (int i = 0; i < enemyRect.length; i++) {
                        if (enemyRect[i].contains(e.getX(), e.getY())) {
                            target = battlefield.getEnemies().get(i);
                            swordStartY = (int) (enemyRect[i].getY() + enemyRect[i].getHeight() / 2);
                            deployFlag = true;
                            break;
                        }
                        else {
                            target = null;
                            deployFlag = false;
                        }
                    }
                }

                //Choosing ability/item
                if (!infoDialogFlag && selectionRectFlag) {
                    for (int i = 0; i < selectionRect.length; i++) {
                        if (selectionRect[i].contains(e.getX(), e.getY())) {
                            selected = selections.get(i);
                            break;
                        }
                    }
                }

                //Almanac
                if (infoDialogFlag) {
                    if (infoRectsFlag) {
                        for (int i = 0; i < infoRects2.length; i++) {
                            if (infoRects2[i].contains(e.getX(), e.getY())) {
                                isDisplaying = true;
                                selected2 = infoRects2[i];
                                for (int j = 0; j < infoRects1.length; j++) {
                                    if (selected1.equals(infoRects1[j])) {
                                        switch (j) {
                                            case 0:
                                                if (i == 0) info = ("Fighter class:\n" +
                                                        "Maximum health: 200\n" +
                                                        "Health refill rate: 10 percent of maximum health\n" +
                                                        "Maximum magic: 120\n" +
                                                        "Magic refill rate: 5 percent of maximum magic\n" +
                                                        "Attack power: 120\n" +
                                                        "Energy points: 6\n" +
                                                        "Inventory size: 2\n" +
                                                        "Ability 1: Fight training\n" +
                                                        "Permanently increases attack power\n" +
                                                        "Upgrade1: +30 attack power for 2 xp points\n" +
                                                        "Upgrade2: +30 attack power for 3 xp points\n" +
                                                        "Upgrade3: +30 attack power for 4 xp points\n" +
                                                        "Ability 2: Work out\n" +
                                                        "Permanently increases maximum health\n" +
                                                        "Upgrade 1: +50 maximum health for 2 xp points\n" +
                                                        "Upgrade 2: +50 maximum health for 3 xp points\n" +
                                                        "Upgrade 3: +50 maximum health for 4 xp points\n");
                                                if (i == 1) info = ("Supporter class:\n" +
                                                        "Maximum health: 220\n" +
                                                        "Health refill rate: 5 percent of maximum health\n" +
                                                        "Maximum magic: 200\n" +
                                                        "Magic refill rate: 10 percent of maximum magic\n" +
                                                        "Attack power: 80\n" +
                                                        "Energy points: 5\n" +
                                                        "Inventory size: 3\n" +
                                                        "Ability 1: Quick as a bunny\n" +
                                                        "Permanently increases energy points\n" +
                                                        "Upgrade1: +1 energy point for 2 xp points\n" +
                                                        "Upgrade2: +1 energy point for 3 xp points\n" +
                                                        "Upgrade3: +1 energy point for 4 xp points\n" +
                                                        "Ability 2: Magic lessons\n" +
                                                        "Permanently increases maximum magic\n" +
                                                        "Upgrade 1: +50 maximum magic for 2 xp points\n" +
                                                        "Upgrade 2: +50 maximum magic for 3 xp points\n" +
                                                        "Upgrade 3: +50 maximum magic for 4 xp points\n");
                                                break;
                                            case 1:
                                                info = battlefield.getHeroes().get(i).getDescription();
                                                break;
                                            case 2:
                                                ArrayList<ActiveAbility> aas = new ArrayList<>();
                                                for (Hero h : battlefield.getHeroes()) {
                                                    aas.addAll(h.getActAbs());
                                                }
                                                info = aas.get(i).getDescription();
                                                break;
                                            case 3:
                                                ArrayList<Item> its = new ArrayList<>();
                                                for (Hero h : battlefield.getHeroes()) {
                                                    its.addAll(h.getItems());
                                                }
                                                info = its.get(i).getDescription();
                                                break;
                                            case 4:
                                                info = battlefield.getEnemies().get(i).getDescription();
                                                break;
                                        }
                                    }
                                }
                                break;
                            } else {
                                isDisplaying = false;
                                selected2 = null;
                            }
                        }
                    }
                    if (infoDialogFlag && !isDisplaying) {
                        if (!isDisplaying) for (int i = 0; i < infoRects1.length; i++) {
                            if (infoRects1[i].contains(e.getX(), e.getY())) {
                                infoRectsFlag = true;
                                selected1 = infoRects1[i];
                                switch (i) {
                                    case 0:
                                        infoStrings.clear();
                                        infoStrings.add("Fighter");
                                        infoStrings.add("Supporter");
                                        infoRects2 = new Rectangle2D.Double[2];
                                        infoRects2[0] = new Rectangle2D.Double(250, 270, 145, 30);
                                        infoRects2[1] = new Rectangle2D.Double(250, 310, 145, 30);
                                        break;
                                    case 1:
                                        infoStrings.clear();
                                        infoStrings.addAll(battlefield.getHeroes().stream().map(Unit::getName).collect(Collectors.toList()));
                                        int yi = 270;
                                        infoRects2 = new Rectangle2D.Double[infoStrings.size()];
                                        for (int j = 0; j < infoRects2.length; j++) {
                                            infoRects2[j] = new Rectangle2D.Double(250, yi, 145, 30);
                                            yi += 40;
                                        }
                                        break;
                                    case 2:
                                        infoStrings.clear();
                                        for (Hero h : battlefield.getHeroes()) {
                                            infoStrings.addAll(h.getActAbs().stream().map(Ability::getName).collect(Collectors.toList()));
                                        }
                                        yi = 270;
                                        infoRects2 = new Rectangle2D.Double[infoStrings.size()];
                                        for (int j = 0; j < infoRects2.length; j++) {
                                            infoRects2[j] = new Rectangle2D.Double(250, yi, 145, 30);
                                            yi += 40;
                                        }
                                        break;
                                    case 3:
                                        infoStrings.clear();
                                        for (Hero h : battlefield.getHeroes()) {
                                            infoStrings.addAll(h.getItems().stream().map(Item::getName).collect(Collectors.toList()));
                                        }
                                        yi = 270;
                                        infoRects2 = new Rectangle2D.Double[infoStrings.size()];
                                        for (int j = 0; j < infoRects2.length; j++) {
                                            infoRects2[j] = new Rectangle2D.Double(250, yi, 145, 30);
                                            yi += 40;
                                        }
                                        break;
                                    case 4:
                                        infoStrings.clear();
                                        infoStrings.addAll(battlefield.getEnemies().stream().map(Unit::getName).collect(Collectors.toList()));
                                        yi = 270;
                                        infoRects2 = new Rectangle2D.Double[infoStrings.size()];
                                        String repeatCheck = "";
                                        for (int j = 0; j < infoRects2.length; j++) {
                                            if ((repeatCheck.contains("thug") && infoStrings.get(j).contains("thug"))
                                                    || (repeatCheck.contains("angel") && infoStrings.get(j).contains("angel"))
                                                    || (repeatCheck.contains("tank") && infoStrings.get(j).contains("tank")))
                                            {
                                                yi -= 40;
                                            }
                                            infoRects2[j] = new Rectangle2D.Double(250, yi, 145, 30);
                                            repeatCheck = infoStrings.get(j);
                                            yi += 40;
                                        }
                                        break;
                                }
                                break;
                            } else {
                                infoRectsFlag = false;
                                selected1 = null;
                            }
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
                    attackFlag = abilityFlag = itemFlag = infoFlag = infoDialogFlag = false;
                    selected1 = null;
                    selected2 = null;
                    infoRectsFlag = false;
                    size = 25f;
                    deployFlag = false;
                    panelDisappear();
                    displaying = "Canceled";
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
            if (glowIndex == battlefield.getHeroes().indexOf(h) && glowDuration > 0) {
                if (h.getName().equalsIgnoreCase("meryl")) {
                    g2.drawImage(glows[glowIndex], xH - 20, yH - 15, size + 20, size + 20, null);
                }
                else {
                    g2.drawImage(glows[glowIndex], xH - 10, yH - 10, size + 20, size + 20, null);
                }
                glowDuration --;
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
        g2.setColor(fontColor);
        g2.setFont(mmFont.deriveFont(25f));
        g2.drawString(" Abilities", 60, 392);
        g2.drawString("   and", 60, 417);
        g2.drawString("  Items", 60, 442);
        if (popFlag) {
            g2.setColor(buttonColor);
            g2.fill(popUpPanel);
            g2.setColor(fontColor);
            g2.draw(popUpPanel);
            if (popX < 600) {
                popX += popSpeed + (600 - popX) / 40;
                popUpPanel = new RoundRectangle2D.Double(40, 370, popX, 80, 40, 50);
            }
            if (popX >= 600) {
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
                        if (!a.getName().contains(" ")) g2.drawString(a.getName(), (int)xD, (int)yD + 55);
                        else {
                            String t[] = a.getName().split(" ");
                            if (a.getName().contains("Over")) g2.drawString(t[0], (int)xD - 10, (int)yD + 55);
                            else g2.drawString(t[0], (int)xD - 10, (int)yD + 55);
                            g2.drawString(t[1], (int)xD, (int)yD + 65);
                        }
                        g2.drawString(a.getEPCost() + "ep", (int)xD + 60, (int)yD + 15);
                        g2.drawString(a.getMagicCost() + "mp", (int)xD + 60, (int)yD + 30);
                        g2.drawString(a.getCD() + "cd", (int)xD + 60, (int)yD + 45);
                        g2.drawString(a.getRemainingCD() + "rcd", (int)xD + 60, (int)yD + 60);
                        emptyFlag = false;
                        xD += 115;
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
            if (popX > 0) {
                popX -= popSpeed + (600 - popX) / 40;
                popUpPanel = new RoundRectangle2D.Double(40, 370, popX, 80, 40, 50);
            }
        }
        g2.setColor(buttonColor);
        g2.fill(new RoundRectangle2D.Double(30, 370, 20, 80, 20, 20));
        g2.setColor(fontColor);
        g2.draw(new RoundRectangle2D.Double(30, 370, 20, 80, 20, 20));


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
        g2.drawString("Almanac", 225, getHeight() - 50);

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
            drawDisplayingInfo(true);
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
        if (target != null && !healAnimationFlag) {
            xD = enemyRect[battlefield.getEnemies().indexOf(target)].getX();
            yD = enemyRect[battlefield.getEnemies().indexOf(target)].getY();
            sizeD = enemyRect[battlefield.getEnemies().indexOf(target)].getWidth();
            double sizeD2 = enemyRect[battlefield.getEnemies().indexOf(target)].getHeight();
            g2.setColor(new Color(160, 255, 224));
            g2.drawOval((int)xD, (int)yD, (int)sizeD, (int)sizeD2);
            g2.drawLine((int)(xD + sizeD / 2), (int)yD, (int)(xD + sizeD / 2), (int)(yD + sizeD2));
            g2.drawLine((int)xD, (int)(yD + sizeD2 / 2), (int)(xD + sizeD), (int)(yD + sizeD2 / 2));
        }

        //Chosen Friendly Target
        if (friendlyTarget != null && !healAnimationFlag) {
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
                g2.setColor(new Color(200 + swordRedColor, 200 + swordRedColor, 200 + swordBlueColor));
                int[] ys1 = {swordStartY, swordStartY - swordY[0], swordStartY - swordY[0], swordStartY - swordY[1],
                        swordStartY - swordY[1], swordStartY - swordY[2], swordStartY - swordY[2], swordStartY};
                int[] ys2 = {swordStartY, swordStartY + swordY[0], swordStartY + swordY[0], swordStartY + swordY[1],
                        swordStartY + swordY[1], swordStartY + swordY[2], swordStartY + swordY[2], swordStartY};
                g2.fill(new Polygon(xs, ys1, 8));
                g2.setColor(new Color(140 + swordRedColor, 140 + swordRedColor, 140 + swordBlueColor));
                g2.fill(new Polygon(xs, ys2, 8));
            }
            else {
                int[] xs = {swordX[0] , swordX[0] , swordX[1] , swordX[1] ,
                        swordX[2] , swordX[2] , swordX[3] , swordX[4] };
                g2.setColor(new Color(Math.abs(animationMover) % 255, Math.abs(animationMover) % 255, Math.abs(animationMover) % 255));
                int[] ys1 = {swordStartY, swordStartY - swordY[0], swordStartY - swordY[0], swordStartY - swordY[1],
                        swordStartY - swordY[1], swordStartY - swordY[2], swordStartY - swordY[2], swordStartY};
                int[] ys2 = {swordStartY, swordStartY + swordY[0], swordStartY + swordY[0], swordStartY + swordY[1],
                        swordStartY + swordY[1], swordStartY + swordY[2], swordStartY + swordY[2], swordStartY};
                g2.fill(new Polygon(xs, ys1, 8));
                g2.setColor(new Color(255 - Math.abs(animationMover) % 255, 255 - Math.abs(animationMover) % 255, 255 - Math.abs(animationMover) % 255));
                g2.fill(new Polygon(xs, ys2, 8));
            }
            animationMover += 7;
            if (animationMover > getWidth() + 700) {
                attackAnimationFlag = false;
                swordRedColor = 0;
                swordBlueColor = 0;
            }
        }
        if (enemyAnimationFlag) {
            if (animationMover > 0 && animationMover < getHeight() - 590) {
                int[] xs = {axeX[0] + axeStartX, axeX[0] + axeStartX, axeX[1] + axeStartX, axeX[0] + axeStartX,
                        axeX[2] + axeStartX, axeX[4] + axeStartX, axeX[3] + axeStartX, axeX[5] + axeStartX, axeX[5] + axeStartX};
                int[] ys1 = {200 + animationMover, 200 - axeY[0] + animationMover,
                        200 - axeY[0] + animationMover, 200 - axeY[1] + animationMover,
                        200 - axeY[2] + animationMover, 200 - axeY[1] + animationMover,
                        200 - axeY[0] + animationMover, 200 - axeY[0] + animationMover,
                        200 + animationMover};
                int[] ys2 = {200 + animationMover, 200 + axeY[0] + animationMover,
                        200 + axeY[0] + animationMover, 200 + axeY[1] + animationMover,
                        200 + axeY[2] + animationMover, 200 + axeY[1] + animationMover,
                        200 + axeY[0] + animationMover, 200 + axeY[0] + animationMover,
                        200 + animationMover};
                g2.setColor(new Color(111 + axeColor, 111, 111));
                g2.fill(new Polygon(xs, ys1, 9));
                g2.setColor(new Color(76 + axeColor, 76, 76));
                g2.draw(new Polygon(xs, ys1, 9));
                g2.fill(new Polygon(xs, ys2, 9));
            }
            else if (animationMover < 0  || animationMover > getHeight() - 590){
                int[] xs = {axeX[0] + axeStartX, axeX[0] + axeStartX, axeX[1] + axeStartX, axeX[0] + axeStartX,
                        axeX[2] + axeStartX, axeX[4] + axeStartX, axeX[3] + axeStartX, axeX[5] + axeStartX, axeX[5] + axeStartX};
                int place;
                if (animationMover < 0) place = 0;
                else place = getHeight() - 590;
                int[] ys1 = {200 + place, 200 - axeY[0] + place,
                        200 - axeY[0] + place, 200 - axeY[1] + place,
                        200 - axeY[2] + place, 200 - axeY[1] + place,
                        200 - axeY[0] + place, 200 - axeY[0] + place,
                        200 + place};
                int[] ys2 = {200 + place, 200 + axeY[0] + place,
                        200 + axeY[0] + place, 200 + axeY[1] + place,
                        200 + axeY[2] + place, 200 + axeY[1] + place,
                        200 + axeY[0] + place, 200 + axeY[0] + place,
                        200 + place};
                g2.setColor(new Color(111 + axeColor, 111, 111));
                g2.fill(new Polygon(xs, ys1, 9));
                g2.setColor(new Color(76 + axeColor, 76, 76));
                g2.draw(new Polygon(xs, ys1, 9));
                g2.fill(new Polygon(xs, ys2, 9));
            }
            animationMover += 7;
            if (animationMover > getHeight() + 400) {
                enemyAnimationFlag = false;
                axeColor = 0;
            }
        }

        //Info Dialog Panel
        if (infoDialogFlag) {
            g2.setColor(bgColor);
            g2.fill(textHolder);
            g2.setColor(fontColor);
            g2.draw(textHolder);
            g2.setFont(mmFont.deriveFont(25f));
            g2.drawString("Knowledge is Power", 170, 240);
            g2.setFont(mmFont.deriveFont(20f));
            g2.drawString("Classes", 170, 300);
            g2.drawString("Heroes", 170, 340);
            g2.drawString("Abilities", 170, 380);
            g2.drawString("Items", 170, 420);
            g2.drawString("Enemies", 170, 460);
            if (selected1 != null) g2.draw(selected1);
            if (selected2 != null) g2.draw(selected2);
            if (infoRectsFlag) {
                String repeatCheck = "";
                for (int i = 0; i < infoRects2.length; i++) {
                    if (selected1 == infoRects1[4]) {
                        String[] p = infoStrings.get(i).split(" ");
                        if (repeatCheck.equals(p[1])) continue;
                        String t = p[1].charAt(0) + "";
                        t = t.toUpperCase();
                        t += p[1].substring(1, p[1].length());
                        repeatCheck = p[1];
                        g2.drawString(t, (int)infoRects2[i].getX() + 10, (int)infoRects2[i].getY() + 20);
                    }
                    else g2.drawString(infoStrings.get(i), (int)infoRects2[i].getX() + 10, (int)infoRects2[i].getY() + 20);
                }
            }
            if (isDisplaying) {
                drawDisplayingInfo(false);
            }
        }

        //Victory
        if (victory && !attackAnimationFlag && glowDuration == 0) {
            g2.setFont(mmFont.deriveFont(180f));
            g2.setColor(fontColor);
            g2.drawString("You Won!", 315, 400);
        }

        if (healAnimationFlag) {
            if (target != null) {
                g2.drawImage(greenShade,
                        (int)enemyRect[battlefield.getEnemies().indexOf(target)].getX(),
                        (int)enemyRect[battlefield.getEnemies().indexOf(target)].getY(),
                        (int)enemyRect[battlefield.getEnemies().indexOf(target)].getWidth(),
                        (int)enemyRect[battlefield.getEnemies().indexOf(target)].getHeight(),
                        null);
            }
            if (friendlyTarget != null) {
                g2.drawImage(greenShade,
                        (int)heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getX(),
                        (int)heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getY(),
                        (int)heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getWidth(),
                        (int)heroRect[battlefield.getHeroes().indexOf(friendlyTarget)].getHeight(),
                        null);
            }
            g.drawImage(buffer, 0, 0, null);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            target = null;
            friendlyTarget = null;
            healAnimationFlag = false;
        }
        else if (collectorAnimationFlag) {
            for (int x = 0; x < buffer.getWidth(); x++) {
                for (int y = 0; y < buffer.getHeight(); y++) {
                    int rgba = buffer.getRGB(x, y);
                    Color col = new Color(rgba, true);
                    col = new Color(255 - col.getRed(),
                            255 - col.getGreen(),
                            255 - col.getBlue());
                    buffer.setRGB(x, y, col.getRGB());
                }
            }
            g.drawImage(buffer, 0, 0, null);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collectorAnimationFlag = false;
        }
        else g.drawImage(buffer, 0, 0, null);
    }

    public void updateEnv() {
        while (enemyTurnFlag || enemyAnimationFlag) {
            if (enemyTurnFlag) for (Enemy enemy : battlefield.getEnemies()) {
                enemy.setTarget();
                displaying = enemy.action();

                if (enemy.getTarget() != null && enemy.getTarget() instanceof Hero) {
                    enemyAnimationFlag = true;
                    animationMover = -700;
                    axeStartX = (int) heroRect[battlefield.getHeroes().indexOf(enemy.getTarget())].getX() - 210;
                    while (enemyAnimationFlag)
                        paintComponent(getGraphics());
                }
                if (enemy instanceof Angel) {
                    target = (Enemy) enemy.getTarget();
                    healAnimationFlag = true;
                }
                if (enemy instanceof Tank) {
                    enemyAnimationFlag = true;
                    animationMover = -700;
                    axeColor = 55;
                    axeStartX = 140;
                }
                if (enemy instanceof FinalBoss) {
                    collectorAnimationFlag = true;
                }

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
            }
            battlefield.updateBattlefield();
            if (!enemyAnimationFlag) displaying = "Your turn";
            enemyTurnFlag = false;
        }
        if (battlefield.removeDeadUnit()) {
            enemyRectFlag = false;
            enemyRect = new Rectangle.Double[battlefield.getEnemies().size()];
        }
        if (battlefield.getEnemies().size() == 0) victory = true;
        if (victory && !attackAnimationFlag && glowDuration == 0) {
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
    private void drawDisplayingInfo(boolean cond) {
        String st;
        if (cond) st = displaying;
        else st = info;
        int charMax = 0, limit;
        if (cond) limit = 75;
        else limit = 75;
        String temp = "";
        ArrayList<String> tokens = new ArrayList<>();
        for (int i = 0; i < st.length(); i++) {
            temp += st.charAt(i);
            charMax++;
            if ((charMax > limit && st.charAt(i) == ' ') || i == st.length() - 1) {
                tokens.add(temp);
                temp = "";
                charMax = 0;
            }
        }
        int xT, yT;
        if (cond) {
            xT = 440;
            yT = getHeight() - 200;
        }
        else {
            xT = 420;
            yT = 240;
        }
        for (String s : tokens) {
            g2.setFont(mmFont.deriveFont(size));
            g2.drawString(s, xT, yT);
            yT += 30;
        }

    }

    private void setUpPanel() {
        disappearFlag = false;
        popFlag = true;
        popX = 0.0;
    }

    private void panelDisappear() {
        disappearFlag = true;
        popFlag = false;
        selectionRectFlag = false;
        selections.clear();
    }

}

