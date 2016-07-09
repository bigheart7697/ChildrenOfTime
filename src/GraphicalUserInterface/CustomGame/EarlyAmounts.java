package GraphicalUserInterface.CustomGame;

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
public class EarlyAmounts extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private int EE, EM;

    private JPanel upPanel = new JPanel(), downPanel = new JPanel(), centerPanel = new JPanel(), upCenterPanel = new JPanel();
    private JButton OK = new JButton("OK");
    private JLabel earlyExperience = new JLabel("Enter the early experience"), earlyMoney = new JLabel("Enter the early money");
    private JTextField EEText = new JTextField(20), EMText = new JTextField(20);

    public EarlyAmounts(SimpleMenuListener sListener, NewMap NM) {

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            earlyExperience.setFont(sFont.deriveFont(20f));
            earlyExperience.setForeground(Color.white);
            earlyMoney.setForeground(Color.white);
            earlyMoney.setFont(sFont.deriveFont(20f));
            EEText.setFont(sFont.deriveFont(20f));
            EMText.setFont(sFont.deriveFont(20f));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        centerPanel.setLayout(new BorderLayout());

        class ImagePanel extends JPanel {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
            }
        }

        ImagePanel downCenterPanel =  new ImagePanel();

        upPanel.setBackground(new Color(60, 60, 60));
        upCenterPanel.setBackground(new Color(60, 60, 60));
        upPanel.add(earlyExperience);
        upPanel.add(EEText);
        upCenterPanel.add(earlyMoney);
        upCenterPanel.add(EMText);
        centerPanel.add(upCenterPanel, BorderLayout.NORTH);
        centerPanel.add(downCenterPanel, BorderLayout.CENTER);
        downPanel.setBackground(new Color(60, 60, 60));
        downPanel.add(OK);
        add(upPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(downPanel, BorderLayout.SOUTH);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NM.setExperience(EE);
                NM.setMoney(EM);
                sListener.switchTo("new map");
            }
        });



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
