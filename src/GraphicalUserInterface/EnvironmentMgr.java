package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvironmentMgr implements ActionListener{

    private JFrame frame;
    private Timer timer;
    private CardLayout deck;

    private MainMenu MM;
    private GameEnv GE;
    private SinglePlayerMenu SPM;
    private CustomGameMenu CGM;
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

        EM.MM = new MainMenu(new MainMenuListener() {
            @Override
            public void switchTo(String target) {
                EM.deck.show(EM.frame.getContentPane(), target);
            }
        });
        EM.GE = new GameEnv();
        EM.SPM = new SinglePlayerMenu();
        EM.CGM = new CustomGameMenu();
        EM.PvPBM = new PvPBattleMenu();
        EM.SM = new SettingsMenu();

        EM.frame.add(EM.MM, "main");
        EM.frame.add(EM.GE, "game");
        EM.frame.add(EM.SPM, "single");
        EM.frame.add(EM.CGM, "custom");
        EM.frame.add(EM.PvPBM, "pvp");
        EM.frame.add(EM.SM, "settings");

        EM.deck.show(EM.frame.getContentPane(), "main");

        EM.timer.start();



    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        MM.update();
    }
}
