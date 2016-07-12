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
public class EarlyAmounts extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JButton OK = new JButton("OK");
    private JLabel earlyExperience = new JLabel("Enter the early experience"), earlyMoney = new JLabel("Enter the early money");
    private JTextField EEText = new JTextField(70), EMText = new JTextField(75);

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
            EEText.setBackground(new Color(60, 60, 60));
            EEText.setForeground(Color.white);
            EMText.setFont(sFont.deriveFont(20f));
            EMText.setBackground(new Color(60, 60, 60));
            EMText.setForeground(Color.white);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(earlyExperience);
        add(EEText);
        add(earlyMoney);
        add(EMText);

        add(OK);


        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EEText.getText().matches("[0-9]+") && EMText.getText().matches("[0-9]+")) {
                    NM.setExperience(Integer.parseInt(EEText.getText()));
                    NM.setMoney(Integer.parseInt(EMText.getText()));
                    sListener.switchTo("new map");
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "The inputs are incorrect!");
                }
            }
        });



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
