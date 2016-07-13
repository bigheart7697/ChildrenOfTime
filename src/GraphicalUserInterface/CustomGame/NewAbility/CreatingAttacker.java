package GraphicalUserInterface.CustomGame.NewAbility;

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
 * Created by rezab on 13/07/2016.
 */
public class CreatingAttacker extends JPanel {
    private BufferedImage BG;
    private Font sFont;

    private JComboBox whichState;
    private JLabel message = new JLabel("Choose the attack modifier type:");
    private JTextArea neededExperience[] = new JTextArea[3], amount[] = new JTextArea[3], coolDown[] = new JTextArea[3];
    private JTextArea nameGetter = new JTextArea(1, 89), descriptionGetter = new JTextArea(10, 89), EPGetter = new JTextArea(1, 89), MPGetter = new JTextArea(1, 89);
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    private int XPToNextLevel, XPPattern, amountPatteern, CDPattern, EP, MP;
    private String attackModifierType, name, description;

    public CreatingAttacker(SimpleMenuListener sListener) {
        String[] selfBoostTypes = {"sacrifice", "overpowered attack"};
        whichState = new JComboBox(selfBoostTypes);
        neededExperience[0] = new JTextArea(1, 89);
        neededExperience[1] = new JTextArea(1, 89);
        neededExperience[2] = new JTextArea(1, 89);
        amount[0] = new JTextArea(1, 89);
        amount[1] = new JTextArea(1, 89);
        amount[2] = new JTextArea(1, 89);
        coolDown[0] = new JTextArea(1, 89);
        coolDown[1] = new JTextArea(1, 89);
        coolDown[2] = new JTextArea(1, 89);

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            whichState.setFont(sFont.deriveFont(20f));
            whichState.setForeground(Color.white);
            whichState.setBackground(new Color(60, 60, 60));

            coolDown[0].setFont(sFont.deriveFont(20f));
            coolDown[0].setForeground(Color.white);
            coolDown[0].setBackground(new Color(60, 60, 60));
            coolDown[0].setText("Enter the cool down for first upgrade");
            coolDown[1].setFont(sFont.deriveFont(20f));
            coolDown[1].setForeground(Color.white);
            coolDown[1].setBackground(new Color(60, 60, 60));
            coolDown[1].setText("Enter the cool down for second upgrade");
            coolDown[2].setFont(sFont.deriveFont(20f));
            coolDown[2].setForeground(Color.white);
            coolDown[2].setBackground(new Color(60, 60, 60));
            coolDown[2].setText("Enter the cool down for third upgrade");
            EPGetter.setFont(sFont.deriveFont(20f));
            EPGetter.setForeground(Color.white);
            EPGetter.setBackground(new Color(60, 60, 60));
            EPGetter.setText("Enter the EP cost");
            MPGetter.setFont(sFont.deriveFont(20f));
            MPGetter.setForeground(Color.white);
            MPGetter.setBackground(new Color(60, 60, 60));
            MPGetter.setText("Enter the MP cost");
            nameGetter.setFont(sFont.deriveFont(20f));
            nameGetter.setForeground(Color.white);
            nameGetter.setBackground(new Color(60, 60, 60));
            nameGetter.setText("Enter the ability name");
            descriptionGetter.setFont(sFont.deriveFont(20f));
            descriptionGetter.setForeground(Color.white);
            descriptionGetter.setBackground(new Color(60, 60, 60));
            descriptionGetter.setText("Enter the description of the ability");
            neededExperience[0].setFont(sFont.deriveFont(20f));
            neededExperience[0].setForeground(Color.white);
            neededExperience[0].setBackground(new Color(60, 60, 60));
            neededExperience[0].setText("Enter the needed experience for first upgrade");
            neededExperience[1].setFont(sFont.deriveFont(20f));
            neededExperience[1].setForeground(Color.white);
            neededExperience[1].setBackground(new Color(60, 60, 60));
            neededExperience[1].setText("Enter the needed experience for second upgrade");
            neededExperience[2].setFont(sFont.deriveFont(20f));
            neededExperience[2].setForeground(Color.white);
            neededExperience[2].setBackground(new Color(60, 60, 60));
            neededExperience[2].setText("Enter the needed experience for third upgrade");
            amount[0].setFont(sFont.deriveFont(20f));
            amount[0].setForeground(Color.white);
            amount[0].setBackground(new Color(60, 60, 60));
            amount[1].setFont(sFont.deriveFont(20f));
            amount[1].setForeground(Color.white);
            amount[1].setBackground(new Color(60, 60, 60));
            amount[2].setFont(sFont.deriveFont(20f));
            amount[2].setForeground(Color.white);
            amount[2].setBackground(new Color(60, 60, 60));
            amount[0].setText("Enter the H for first upgrade");
            amount[1].setText("Enter the H for second upgrade");
            amount[2].setText("Enter the H for third upgrade");

            message.setFont(sFont.deriveFont(20f));
            message.setForeground(Color.white);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NA.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sListener.switchTo("new ability");
            }
        });

        add(message);
        add(whichState);
        add(nameGetter);
        add(descriptionGetter);
        add(EPGetter);
        add(MPGetter);
        add(coolDown[0]);
        add(coolDown[1]);
        add(coolDown[2]);
        add(neededExperience[0]);
        add(neededExperience[1]);
        add(neededExperience[2]);
        add(amount[0]);
        add(amount[1]);
        add(amount[2]);
        add(cancel);
        add(save);

        whichState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (whichState.getSelectedItem().equals("sacrifice")) {
                    amount[0].setText("Enter the H for first upgrade");
                    amount[1].setText("Enter the H for second upgrade");
                    amount[2].setText("Enter the H for third upgrade");
                }
                else {
                    amount[0].setText("Enter the the number after decimal point in N for first upgrade");
                    amount[1].setText("Enter the number after decimal point in N for second upgrade");
                    amount[2].setText("Enter the number after decimal point in N for third upgrade");
                }
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attackModifierType = (String)whichState.getSelectedItem();
                if (coolDown[0].getText().matches("[0-9]+") && coolDown[1].getText().matches("[0-9]+")
                        && coolDown[2].getText().matches("[0-9]+") && neededExperience[0].getText().matches("[0-9]+") && EPGetter.getText().matches("[0-9]+")
                        && neededExperience[1].getText().matches("[0-9]+") && neededExperience[2].getText().matches("[0-9]+") && MPGetter.getText().matches("[0-9]+")
                        &&  Integer.parseInt(coolDown[0].getText()) >= 0 && Integer.parseInt(coolDown[0].getText()) < 5
                        &&  Integer.parseInt(coolDown[1].getText()) >= 0 && Integer.parseInt(coolDown[1].getText()) < 5
                        &&  Integer.parseInt(coolDown[2].getText()) >= 0 && Integer.parseInt(coolDown[2].getText()) < 5 && amount[0].getText().matches("[0-9]+")
                        && amount[1].getText().matches("[0-9]+") && amount[2].getText().matches("[0-9]+")
                        && Integer.parseInt(EPGetter.getText()) > 0 && Integer.parseInt(EPGetter.getText()) < 10
                        && Integer.parseInt(MPGetter.getText()) > 0 && Integer.parseInt(MPGetter.getText()) < 100
                        && Integer.parseInt(neededExperience[0].getText()) > 0 && Integer.parseInt(neededExperience[0].getText()) < 10
                        && Integer.parseInt(neededExperience[1].getText()) > 0 && Integer.parseInt(neededExperience[1].getText()) < 10
                        && Integer.parseInt(neededExperience[2].getText()) > 0 && Integer.parseInt(neededExperience[2].getText()) < 10
                        && Integer.parseInt(amount[0].getText()) > 0 && Integer.parseInt(amount[0].getText()) < 100
                        && Integer.parseInt(amount[1].getText()) > 0 && Integer.parseInt(amount[1].getText()) < 100
                        && Integer.parseInt(amount[2].getText()) > 0 && Integer.parseInt(amount[2].getText()) < 100) {
                    description = descriptionGetter.getText();
                    name = nameGetter.getText();
                    EP = Integer.parseInt(EPGetter.getText());
                    MP = Integer.parseInt(MPGetter.getText());
                    CDPattern = Integer.parseInt(coolDown[0].getText()) * 100 + Integer.parseInt(coolDown[1].getText()) * 10 + Integer.parseInt(coolDown[2].getText());
                    XPToNextLevel = Integer.parseInt(neededExperience[0].getText());
                    XPPattern = Integer.parseInt(neededExperience[1].getText()) * 10 + Integer.parseInt(neededExperience[2].getText());
                    amountPatteern = Integer.parseInt(amount[0].getText()) * 10 + Integer.parseInt(amount[1].getText()) + Integer.parseInt(amount[2].getText()) / 10;
                    sListener.switchTo("new ability");
                }

                else {
                    JOptionPane.showMessageDialog(null, "Your information isn't correct!");
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
