package GraphicalUserInterface;

import GraphicalUserInterface.CustomGame.*;
import GraphicalUserInterface.CustomGame.NewAbility.*;
import GraphicalUserInterface.CustomGame.NewItem.CreatingConsumable;
import GraphicalUserInterface.CustomGame.NewItem.CreatingEquipment;
import GraphicalUserInterface.CustomGame.NewItem.CreatingImmediateEffect;
import GraphicalUserInterface.CustomGame.NewItem.NewItem;
import GraphicalUserInterface.CustomGame.NewMap.*;
import GraphicalUserInterface.CustomGame.NewMap.CreatingTiles.*;
import GraphicalUserInterface.GameEnv.GameEnv;
import GraphicalUserInterface.GameEnv.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvironmentMgr implements ActionListener{

    private JFrame frame;
    private Timer timer;
    private CardLayout deck;
    private String cCard;

    private MainMenu MM;
    private GameEnv GE;
    private SinglePlayerMenu SPM;
    private CustomGameMenu CGM;
    private NewMap NM;
    private NewAbility NA;
    private NewItem NI;
    private NewHeroClass NHC;
    private NewHero NH;
    private NewEnemy NE;
    private PvPBattleMenu PvPBM;
    private SettingsMenu SM;
    private GraphicalUserInterface.CustomGame.NewMap.Scenario scenario;
    private GraphicalUserInterface.CustomGame.NewMap.DefeatMessage DM;
    private GraphicalUserInterface.CustomGame.NewMap.MapSize MS;
    private GraphicalUserInterface.CustomGame.NewMap.EarlyAmounts EA;
    private StartingPoint SP;
    private CreatingMap CM;
    private CreatingBattleTile CBT;
    private CreatingStoryTile CST;
    private CreatingDoorTile CDT;
    private CreatingKeyTile CKT;
    private CreatingShopTile CSHT;
    private CreatingFinalBossTile CFBT;
    private CreatingSelfBoost CSB;
    private CreatingAttackModifier CAM;
    private CreatingRestorer CR;
    private CreatingAttacker CA;
    private CreatingImmediateEffect CIE;
    private CreatingEquipment CE;
    private CreatingConsumable CC;


    public static void main(String[] args) {

        EnvironmentMgr EM = new EnvironmentMgr();
        EM.frame = new JFrame("Children Of Time");
        EM.timer = new Timer(1, EM);
        EM.deck = new CardLayout();

        EM.frame.setVisible(true);
        EM.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EM.frame.setSize(new Dimension(1280, 800));
        EM.frame.setLocationRelativeTo(null);
        EM.frame.setLayout(EM.deck);
        EM.frame.setResizable(false);

        //Normal Switch-Listener for several menus
        SimpleMenuListener defaultListener = target -> {
            EM.deck.show(EM.frame.getContentPane(), target);
            EM.cCard = target;
        };

        EM.MM = new MainMenu(defaultListener);


        EM.SPM = new SinglePlayerMenu(new SinglePlayerMenuListener() {
            @Override
            public void switchTo(int scenarioNum) {

                if (scenarioNum == 0) EM.GE = new GameEnv(EM, new Scenario(), defaultListener);
                else EM.GE = new GameEnv(EM, new Scenario(scenarioNum), defaultListener);
                EM.frame.add(EM.GE, "game");
                EM.deck.show(EM.frame.getContentPane(), "game");
                EM.cCard = "game";
                EM.frame.setSize(1206, 935);
                EM.frame.setLocationRelativeTo(null);
            }

            @Override
            public void switchTo() {
                EM.deck.show(EM.frame.getContentPane(), "main");
                EM.cCard = "main";
            }
        });

        EM.CGM = new CustomGameMenu(defaultListener);

        EM.NE = new NewEnemy();
        EM.NH = new NewHero();
        EM.NHC = new NewHeroClass();
        EM.NI = new NewItem(defaultListener);
        EM.NM = new NewMap(new SimpleMenuListener() {
            @Override
            public void switchTo(String target) {
                if (target.equals("starting point")) {
                    EM.SP = new StartingPoint(defaultListener, EM.NM);
                    EM.frame.add(EM.SP, "starting point");
                }
                if (target.equals("creating map")) {
                    EM.frame.setSize(1206, 935);
                    EM.frame.setLocationRelativeTo(null);
                    EM.CM = new CreatingMap(new SimpleMenuListener() {
                        @Override
                        public void switchTo(String target) {
                            EM.CBT = new CreatingBattleTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CBT, "battle tile");
                            EM.CST = new CreatingStoryTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CST, "story tile");
                            EM.CDT = new CreatingDoorTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CDT, "door tile");
                            EM.CKT = new CreatingKeyTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CKT, "key tile");
                            EM.CSHT = new CreatingShopTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CSHT, "shop tile");
                            EM.CFBT = new CreatingFinalBossTile(defaultListener, EM.CM);
                            EM.frame.add(EM.CFBT, "finalBoss tile");
                            if (target.equals("new map") || target.equals("custom")) {
                                EM.frame.setSize(new Dimension(1280, 800));
                                EM.frame.setLocationRelativeTo(null);
                            }
                            EM.deck.show(EM.frame.getContentPane(), target);
                            EM.cCard = target;
                        }
                    }, EM.NM);
                    EM.frame.add(EM.CM, "creating map");
                }
                EM.deck.show(EM.frame.getContentPane(), target);
                EM.cCard = target;
            }
        });
        EM.scenario = new GraphicalUserInterface.CustomGame.NewMap.Scenario(defaultListener, EM.NM);
        EM.DM = new GraphicalUserInterface.CustomGame.NewMap.DefeatMessage(defaultListener, EM.NM);
        EM.MS = new GraphicalUserInterface.CustomGame.NewMap.MapSize(defaultListener, EM.NM);
        EM.EA = new GraphicalUserInterface.CustomGame.NewMap.EarlyAmounts(defaultListener, EM.NM);
        EM.CSB = new CreatingSelfBoost(defaultListener);
        EM.CAM = new CreatingAttackModifier(defaultListener);
        EM.CR = new CreatingRestorer(defaultListener);
        EM.CA = new CreatingAttacker(defaultListener);
        EM.CIE = new CreatingImmediateEffect(defaultListener);
        EM.CE = new CreatingEquipment(defaultListener);
        EM.CC = new CreatingConsumable(defaultListener);


        EM.NA = new NewAbility(defaultListener);

        EM.PvPBM = new PvPBattleMenu();

        EM.SM = new SettingsMenu();

        EM.timer.start();

        EM.frame.add(EM.MM, "main");
        EM.frame.add(EM.SPM, "single");
        EM.frame.add(EM.CGM, "custom");
        EM.frame.add(EM.PvPBM, "pvp");
        EM.frame.add(EM.SM, "settings");

        EM.frame.add(EM.NM, "new map");
        EM.frame.add(EM.NA, "new ability");
        EM.frame.add(EM.NI, "new item");
        EM.frame.add(EM.NHC, "new hero class");
        EM.frame.add(EM.NH, "new hero");
        EM.frame.add(EM.NE, "new enemy");
        EM.frame.add(EM.scenario, "scenario");
        EM.frame.add(EM.DM, "defeat message");
        EM.frame.add(EM.MS, "map size");
        EM.frame.add(EM.EA, "early amounts");
        EM.frame.add(EM.CSB, "self boost");
        EM.frame.add(EM.CAM, "attack modifier");
        EM.frame.add(EM.CR, "restorer");
        EM.frame.add(EM.CA, "attacker");
        EM.frame.add(EM.CIE, "immediate effect");
        EM.frame.add(EM.CE, "equipment");
        EM.frame.add(EM.CC, "consumable");
        EM.cCard = "main";

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MM.update();
        if (GE != null) GE.update();
        if (CGM != null)CGM.update();
        if (SPM != null)SPM.update();
        if (NM != null)NM.update();
        if (CM != null) CM.update();
    }

    public String getCurrentCard() { return cCard; }
    public JFrame frame() { return frame; }

}
