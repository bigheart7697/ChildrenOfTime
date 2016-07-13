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
 * Created by rezab on 12/07/2016.
 */
public class CreatingSelfBoost extends JPanel {
    private BufferedImage BG;
    private Font sFont;

    private JComboBox whichState;
    private JLabel message = new JLabel("Choose the self boost type:");
    private JTextArea neededExperience[] = new JTextArea[3], increaseAmountGetter = new JTextArea(1, 89), nameGetter = new JTextArea(1, 89), descriptionGetter = new JTextArea(10, 89);
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    private int increaseAmount, XPToNextLevel, XPPattern;
    private String selfBoostType, name, description;

    public CreatingSelfBoost(SimpleMenuListener sListener) {
        String[] selfBoostTypes = {"magic point", "health point", "attack power", "energy point"};
        whichState = new JComboBox(selfBoostTypes);
        neededExperience[0] = new JTextArea(1, 89);
        neededExperience[1] = new JTextArea(1, 89);
        neededExperience[2] = new JTextArea(1, 89);

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            whichState.setFont(sFont.deriveFont(20f));
            whichState.setForeground(Color.white);
            whichState.setBackground(new Color(60, 60, 60));

            increaseAmountGetter.setFont(sFont.deriveFont(20f));
            increaseAmountGetter.setForeground(Color.white);
            increaseAmountGetter.setBackground(new Color(60, 60, 60));
            increaseAmountGetter.setText("Enter the increase amount");
            descriptionGetter.setFont(sFont.deriveFont(20f));
            descriptionGetter.setForeground(Color.white);
            descriptionGetter.setBackground(new Color(60, 60, 60));
            descriptionGetter.setText("Enter the description of the ability");
            nameGetter.setFont(sFont.deriveFont(20f));
            nameGetter.setForeground(Color.white);
            nameGetter.setBackground(new Color(60, 60, 60));
            nameGetter.setText("Enter the ability name");
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
        add(increaseAmountGetter);
        add(neededExperience[0]);
        add(neededExperience[1]);
        add(neededExperience[2]);
        add(cancel);
        add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selfBoostType = (String)whichState.getSelectedItem();
                if (selfBoostType.equals("energy point") && increaseAmountGetter.getText().matches("[0-9]+") && neededExperience[0].getText().matches("[0-9]+")
                        && neededExperience[1].getText().matches("[0-9]+") && neededExperience[2].getText().matches("[0-9]+")
                        &&  Integer.parseInt(increaseAmountGetter.getText()) > 0 && Integer.parseInt(increaseAmountGetter.getText()) < 10
                        && Integer.parseInt(neededExperience[0].getText()) > 0 && Integer.parseInt(neededExperience[0].getText()) < 10
                        && Integer.parseInt(neededExperience[1].getText()) > 0 && Integer.parseInt(neededExperience[1].getText()) < 10
                        && Integer.parseInt(neededExperience[2].getText()) > 0 && Integer.parseInt(neededExperience[2].getText()) < 10) {
                    description = descriptionGetter.getText();
                    increaseAmount = Integer.parseInt(increaseAmountGetter.getText());
                    XPToNextLevel = Integer.parseInt(neededExperience[0].getText());
                    XPPattern = Integer.parseInt(neededExperience[1].getText()) * 10 + Integer.parseInt(neededExperience[2].getText());
                    sListener.switchTo("new ability");
                }

                else if (!selfBoostType.equals("energy point") && increaseAmountGetter.getText().matches("[0-9]+") && neededExperience[0].getText().matches("[0-9]+")
                        && neededExperience[1].getText().matches("[0-9]+") && neededExperience[2].getText().matches("[0-9]+")
                        &&  Integer.parseInt(increaseAmountGetter.getText()) > 0 && Integer.parseInt(increaseAmountGetter.getText()) < 100
                        && Integer.parseInt(neededExperience[0].getText()) > 0 && Integer.parseInt(neededExperience[0].getText()) < 10
                        && Integer.parseInt(neededExperience[1].getText()) > 0 && Integer.parseInt(neededExperience[1].getText()) < 10
                        && Integer.parseInt(neededExperience[2].getText()) > 0 && Integer.parseInt(neededExperience[2].getText()) < 10) {
                    description = descriptionGetter.getText();
                    increaseAmount = Integer.parseInt(increaseAmountGetter.getText());
                    XPToNextLevel = Integer.parseInt(neededExperience[0].getText());
                    XPPattern = Integer.parseInt(neededExperience[1].getText()) * 10 + Integer.parseInt(neededExperience[2].getText());
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
