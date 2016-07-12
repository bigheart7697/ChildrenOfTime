package GraphicalUserInterface.CustomGame.NewMap.CreatingTiles;

import GraphicalUserInterface.CustomGame.NewMap.CreatingMap;
import GraphicalUserInterface.CustomGame.Tiles.BattleTile;
import GraphicalUserInterface.SimpleMenuListener;
import units.Angel;
import units.Tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rezab on 11/07/2016.
 */
public class CreatingBattleTile extends JPanel {
    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private ArrayList<JLabel> enemyLabel = new ArrayList<>();
    private ArrayList<ButtonGroup> enemyGroups = new ArrayList<>();
    private ArrayList<HashMap<String, JRadioButton>> versionRadioButton = new ArrayList<>();
    private JTextArea moneyPrize = new JTextArea(1, 84);
    private JTextArea experiencePrize = new JTextArea(1, 84);
    private ArrayList<JTextArea> enemies =new ArrayList<>();
    private ArrayList<Integer> version = new ArrayList<>();

    public CreatingBattleTile(SimpleMenuListener sListener, CreatingMap CM) {
        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            enemies.add(new JTextArea(1, 84));
            enemies.add(new JTextArea(1, 84));
            enemies.add(new JTextArea(1, 84));
            enemies.get(0).setText("Enter the number of tanks");
            enemies.get(0).setFont(sFont.deriveFont(20f));
            enemies.get(0).setBackground(new Color(60, 60, 60));
            enemies.get(0).setForeground(Color.white);
            enemies.get(1).setText("Enter the number of angels");
            enemies.get(1).setFont(sFont.deriveFont(20f));
            enemies.get(1).setBackground(new Color(60, 60, 60));
            enemies.get(1).setForeground(Color.white);
            enemies.get(2).setText("Enter the number of thugs");
            enemies.get(2).setFont(sFont.deriveFont(20f));
            enemies.get(2).setBackground(new Color(60, 60, 60));
            enemies.get(2).setForeground(Color.white);
            enemyLabel.add(new JLabel("Tank"));
            enemyLabel.add(new JLabel("Angel"));
            enemyLabel.add(new JLabel("Thug"));
            enemyLabel.get(0).setFont(sFont.deriveFont(20f));
            enemyLabel.get(0).setForeground(Color.white);
            enemyLabel.get(1).setFont(sFont.deriveFont(20f));
            enemyLabel.get(1).setForeground(Color.white);
            enemyLabel.get(2).setFont(sFont.deriveFont(20f));
            enemyLabel.get(2).setForeground(Color.white);
            version.add(-1);
            version.add(-1);
            version.add(-1);
            versionRadioButton.add(new HashMap<>());
            versionRadioButton.add(new HashMap<>());
            versionRadioButton.add(new HashMap<>());
            versionRadioButton.get(0).put("Weak", new JRadioButton("Weak"));
            versionRadioButton.get(0).put("Able", new JRadioButton("Able"));
            versionRadioButton.get(1).put("Weak", new JRadioButton("Weak"));
            versionRadioButton.get(1).put("Able", new JRadioButton("Able"));
            versionRadioButton.get(2).put("Weak", new JRadioButton("Weak"));
            versionRadioButton.get(2).put("Able", new JRadioButton("Able"));
            versionRadioButton.get(2).put("Mighty", new JRadioButton("Mighty"));
            enemyGroups.add(new ButtonGroup());
            enemyGroups.add(new ButtonGroup());
            enemyGroups.add(new ButtonGroup());
            enemyGroups.get(0).add(versionRadioButton.get(0).get("Weak"));
            enemyGroups.get(0).add(versionRadioButton.get(0).get("Able"));
            enemyGroups.get(1).add(versionRadioButton.get(1).get("Weak"));
            enemyGroups.get(1).add(versionRadioButton.get(1).get("Able"));
            enemyGroups.get(2).add(versionRadioButton.get(2).get("Weak"));
            enemyGroups.get(2).add(versionRadioButton.get(2).get("Able"));
            enemyGroups.get(2).add(versionRadioButton.get(2).get("Mighty"));
            experiencePrize.setFont(sFont.deriveFont(20f));
            moneyPrize.setFont(sFont.deriveFont(20f));
            moneyPrize.setText("Enter the money prize.");
            experiencePrize.setText("Enter the experience Prize.");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        moneyPrize.setLineWrap(true);
        experiencePrize.setLineWrap(true);
        moneyPrize.setBackground(new Color(60, 60, 60));
        experiencePrize.setBackground(new Color(60, 60, 60));
        moneyPrize.setForeground(Color.white);
        experiencePrize.setForeground(Color.white);
        add(moneyPrize);
        add(experiencePrize);
        add(enemyLabel.get(0));
        add(versionRadioButton.get(0).get("Weak"));
        add(versionRadioButton.get(0).get("Able"));
        add(enemies.get(0));
        add(enemyLabel.get(1));
        add(versionRadioButton.get(1).get("Weak"));
        add(versionRadioButton.get(1).get("Able"));
        add(enemies.get(1));
        add(enemyLabel.get(2));
        add(versionRadioButton.get(2).get("Weak"));
        add(versionRadioButton.get(2).get("Able"));
        add(versionRadioButton.get(2).get("Mighty"));
        add(enemies.get(2));
        versionRadioButton.get(0).get("Weak").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(0, 0);
            }
        });
        versionRadioButton.get(0).get("Able").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(0, 1);
            }
        });
        versionRadioButton.get(1).get("Weak").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(1, 0);
            }
        });
        versionRadioButton.get(1).get("Able").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(1, 1);
            }
        });
        versionRadioButton.get(2).get("Weak").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(2, 0);
            }
        });
        versionRadioButton.get(2).get("Able").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(2, 1);
            }
        });
        versionRadioButton.get(2).get("Mighty").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                version.set(2, 2);
            }
        });

        add(OK);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isComplete()) {
                    BattleTile tmp = new BattleTile(Integer.parseInt(moneyPrize.getText()), Integer.parseInt(experiencePrize.getText()));
                    for (int cnt = 0; cnt < Integer.parseInt(enemies.get(0).getText()); cnt++) {
                        tmp.addEnemy(new Tank(version.get(0), cnt + 1));
                    }

                    for (int cnt = 0; cnt < Integer.parseInt(enemies.get(0).getText()); cnt++) {
                        tmp.addEnemy(new Angel(version.get(1), cnt + 1));
                    }

                    for (int cnt = 0; cnt < Integer.parseInt(enemies.get(0).getText()); cnt++) {
                        tmp.addEnemy(new Tank(version.get(2), cnt + 1));
                    }
                    CM.addBattleTile(tmp);
                    sListener.switchTo("creating map");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Your information is incomplete");
                }

            }
        });
//        experiencePrize.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                experiencePrize.setText("");
//            }
//        });
//
//        moneyPrize.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                moneyPrize.setText("");
//            }
//        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }

    public boolean isComplete() {
        if (moneyPrize.getText().matches("[0-9]+") && moneyPrize.getText().matches("[0-9]+")) {
            for (int cnt = 0; cnt < enemies.size(); cnt++) {
                if (!enemies.get(cnt).getText().matches("[0-9]+") || version.get(cnt).equals(-1))
                    return false;
            }
        }
        else
            return false;
        return true;
    }
}

