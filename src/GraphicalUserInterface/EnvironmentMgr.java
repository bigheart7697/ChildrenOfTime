package GraphicalUserInterface;

import GraphicalUserInterface.CustomGame.*;
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

        EM.NA = new NewAbility();
        EM.NE = new NewEnemy();
        EM.NH = new NewHero();
        EM.NHC = new NewHeroClass();
        EM.NI = new NewItem();
        EM.NM = new NewMap(defaultListener);

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

        EM.cCard = "main";

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MM.update();
        if (GE != null) GE.update();
        if (CGM != null)CGM.update();
        if (SPM != null)SPM.update();
        NM.update();
    }

    public String getCurrentCard() { return cCard; }
    public JFrame frame() { return frame; }

}
