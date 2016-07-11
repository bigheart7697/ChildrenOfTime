package GraphicalUserInterface.CustomGame.NewMap;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rezab on 09/07/2016.
 */
public class Scenario extends JPanel {
    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private JTextArea scenarioName = new JTextArea(1, 89);
    private JTextArea scenario = new JTextArea(10, 89);

    public Scenario(SimpleMenuListener sListener, NewMap NM) {
        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            scenario.setFont(sFont.deriveFont(20f));
            scenarioName.setFont(sFont.deriveFont(20f));
            scenarioName.setText("Enter the scenario name.");
            scenario.setText("Enter the scenario.");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        JScrollPane scroller1 = new JScrollPane(scenarioName);
        JScrollPane scroller2 = new JScrollPane(scenario);
        scenarioName.setLineWrap(true);
        scenario.setLineWrap(true);
        scenarioName.setBackground(new Color(60, 60, 60));
        scenario.setBackground(new Color(60, 60, 60));
        scenarioName.setForeground(Color.white);
        scenario.setForeground(Color.white);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroller1);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroller2);
        add(OK);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NM.setScenarioName(scenarioName.getText());
                NM.setStory(scenario.getText());
                sListener.switchTo("new map");
            }
        });
//        scenario.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                scenario.setText("");
//            }
//        });
//
//        scenarioName.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                scenarioName.setText("");
//            }
//        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
