package GraphicalUserInterface.CustomGame.NewAbility;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by rezab on 12/07/2016.
 */
public class CreatingAttackModifier extends JPanel {
    private BufferedImage BG;
    private Font sFont;

    private JComboBox whichState;
    private JLabel message = new JLabel("Choose the attack modifier type:");
    private JTextArea imageDirectory = new JTextArea(1, 89), neededExperience[] = new JTextArea[3], percentage[] = new JTextArea[3], n = new JTextArea(1, 89), nameGetter = new JTextArea(1, 89), descriptionGetter = new JTextArea(10, 89);
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    private int N = 0, XPToNextLevel, XPPattern, pPatteern;
    private String attackModifierType, name, description;

    public CreatingAttackModifier(SimpleMenuListener sListener) {

        String[] attackModifierTypes = {"swirling attack", "critical strike"};
        whichState = new JComboBox(attackModifierTypes);

        neededExperience[0] = new JTextArea(1, 89);
        neededExperience[1] = new JTextArea(1, 89);
        neededExperience[2] = new JTextArea(1, 89);

        percentage[0] = new JTextArea(1, 89);
        percentage[1] = new JTextArea(1, 89);
        percentage[2] = new JTextArea(1, 89);

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            whichState.setFont(sFont.deriveFont(20f));
            whichState.setForeground(Color.white);
            whichState.setBackground(new Color(60, 60, 60));

            n.setFont(sFont.deriveFont(20f));
            n.setForeground(Color.white);
            n.setBackground(new Color(60, 60, 60));
            n.setText("Enter the N");

            nameGetter.setFont(sFont.deriveFont(20f));
            nameGetter.setForeground(Color.white);
            nameGetter.setBackground(new Color(60, 60, 60));
            nameGetter.setText("Enter the ability name");

            imageDirectory.setFont(sFont.deriveFont(20f));
            imageDirectory.setForeground(Color.white);
            imageDirectory.setBackground(new Color(60, 60, 60));
            imageDirectory.setText("Enter the image directory");

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

            percentage[0].setFont(sFont.deriveFont(20f));
            percentage[0].setForeground(Color.white);
            percentage[0].setBackground(new Color(60, 60, 60));
            percentage[0].setText("Enter the percentage for first upgrade");

            percentage[1].setFont(sFont.deriveFont(20f));
            percentage[1].setForeground(Color.white);
            percentage[1].setBackground(new Color(60, 60, 60));
            percentage[1].setText("Enter the percentage for second upgrade");

            percentage[2].setFont(sFont.deriveFont(20f));
            percentage[2].setForeground(Color.white);
            percentage[2].setBackground(new Color(60, 60, 60));
            percentage[2].setText("Enter the percentage for third upgrade");

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

        n.setVisible(false);

        add(message);
        add(whichState);
        add(nameGetter);
        add(descriptionGetter);
        add(imageDirectory);
        add(n);
        add(neededExperience[0]);
        add(neededExperience[1]);
        add(neededExperience[2]);
        add(percentage[0]);
        add(percentage[1]);
        add(percentage[2]);
        add(cancel);
        add(save);

        whichState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (whichState.getSelectedItem().equals("critical strike")) {
                    n.setVisible(true);
                }
                else
                    n.setVisible(false);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attackModifierType = (String)whichState.getSelectedItem();
                if (attackModifierType.equals("critical strike") && n.getText().matches("[0-9]+") && neededExperience[0].getText().matches("[0-9]+")
                        && neededExperience[1].getText().matches("[0-9]+") && neededExperience[2].getText().matches("[0-9]+")
                        &&  Integer.parseInt(n.getText()) > 0 && Integer.parseInt(n.getText()) < 10 && percentage[0].getText().matches("[0-9]+")
                        && percentage[1].getText().matches("[0-9]+") && percentage[2].getText().matches("[0-9]+")
                        && Integer.parseInt(neededExperience[0].getText()) > 0 && Integer.parseInt(neededExperience[0].getText()) < 10
                        && Integer.parseInt(neededExperience[1].getText()) > 0 && Integer.parseInt(neededExperience[1].getText()) < 10
                        && Integer.parseInt(neededExperience[2].getText()) > 0 && Integer.parseInt(neededExperience[2].getText()) < 10
                        && Integer.parseInt(percentage[0].getText()) > 0 && Integer.parseInt(percentage[0].getText()) < 100
                        && Integer.parseInt(percentage[1].getText()) > 0 && Integer.parseInt(percentage[1].getText()) < 100
                        && Integer.parseInt(percentage[2].getText()) > 0 && Integer.parseInt(percentage[2].getText()) < 100) {
                    description = descriptionGetter.getText();
                    name = nameGetter.getText();
                    N = Integer.parseInt(n.getText());
                    XPToNextLevel = Integer.parseInt(neededExperience[0].getText());
                    XPPattern = Integer.parseInt(neededExperience[1].getText()) * 10 + Integer.parseInt(neededExperience[2].getText());
                    pPatteern = Integer.parseInt(percentage[0].getText()) * 100 + Integer.parseInt(percentage[1].getText()) * 10 + Integer.parseInt(percentage[2].getText());

                    String s = "attack modifier" + "\n" + attackModifierType + "\n" + description + "\n" + imageDirectory.getText() + "\n" + N + "\n" + XPToNextLevel + "\n"
                            + XPPattern + "\n" + pPatteern;

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Ability/" + name + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + name).getBytes();
                    p = Paths.get("Save/Ability/List.txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE, APPEND))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    sListener.switchTo("new ability");
                }

                else if (attackModifierType.equals("swirling attack") && neededExperience[0].getText().matches("[0-9]+")
                        && neededExperience[1].getText().matches("[0-9]+") && neededExperience[2].getText().matches("[0-9]+") && percentage[0].getText().matches("[0-9]+")
                        && percentage[1].getText().matches("[0-9]+") && percentage[2].getText().matches("[0-9]+")
                        && Integer.parseInt(neededExperience[0].getText()) > 0 && Integer.parseInt(neededExperience[0].getText()) < 10
                        && Integer.parseInt(neededExperience[1].getText()) > 0 && Integer.parseInt(neededExperience[1].getText()) < 10
                        && Integer.parseInt(neededExperience[2].getText()) > 0 && Integer.parseInt(neededExperience[2].getText()) < 10
                        && Integer.parseInt(percentage[0].getText()) > 0 && Integer.parseInt(percentage[0].getText()) < 100
                        && Integer.parseInt(percentage[1].getText()) > 0 && Integer.parseInt(percentage[1].getText()) < 100
                        && Integer.parseInt(percentage[2].getText()) > 0 && Integer.parseInt(percentage[2].getText()) < 100) {
                    description = descriptionGetter.getText();
                    name = nameGetter.getText();
                    XPToNextLevel = Integer.parseInt(neededExperience[0].getText());
                    XPPattern = Integer.parseInt(neededExperience[1].getText()) * 10 + Integer.parseInt(neededExperience[2].getText());
                    pPatteern = Integer.parseInt(percentage[0].getText()) * 100 + Integer.parseInt(percentage[1].getText()) * 10 + Integer.parseInt(percentage[2].getText());
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
