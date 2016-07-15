package GraphicalUserInterface.CustomGame.NewEnemy;

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
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by rezab on 07/07/2016.
 */
public class NewEnemy extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JComboBox type;
    private JTextArea name = new JTextArea(1, 89), imageDirectory = new JTextArea(1, 89), description = new JTextArea(1, 89);
    private JTextArea amount[] = new JTextArea[3], maxHP[] = new JTextArea[3];
    private JLabel strengthMessage = new JLabel("Check the enemy power"), typeMessage = new JLabel("Choose the enemy type");
    private ArrayList<JCheckBox> strength = new ArrayList<>();
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    public NewEnemy(SimpleMenuListener sListener) {

        String[] types = {"attack one hero", "heal one alley", "attack all heroes"};
        type = new JComboBox(types);


        strength.add(new JCheckBox("weak"));
        strength.add(new JCheckBox("able"));
        strength.add(new JCheckBox("mighty"));


        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            strengthMessage.setFont(sFont.deriveFont(20f));
            strengthMessage.setForeground(Color.white);


            for (int cnt = 0; cnt < 3; cnt++) {
                amount[cnt] = new JTextArea(1, 89);
                amount[cnt].setFont(sFont.deriveFont(20f));
                amount[cnt].setForeground(Color.white);
                amount[cnt].setBackground(new Color(60, 60, 60));
                amount[cnt].setVisible(false);
                amount[cnt].setText("Enter the amount for strength" + (cnt + 1));

                maxHP[cnt] = new JTextArea(1, 89);
                maxHP[cnt].setFont(sFont.deriveFont(20f));
                maxHP[cnt].setForeground(Color.white);
                maxHP[cnt].setBackground(new Color(60, 60, 60));
                maxHP[cnt].setVisible(false);
                maxHP[cnt].setText("Enter the max HP for strength" + (cnt + 1));
            }

            name.setFont(sFont.deriveFont(20f));
            name.setForeground(Color.white);
            name.setBackground(new Color(60, 60, 60));
            name.setText("Enter the class name");

            description.setFont(sFont.deriveFont(20f));
            description.setForeground(Color.white);
            description.setBackground(new Color(60, 60, 60));
            description.setText("Enter the class description");

            imageDirectory.setFont(sFont.deriveFont(20f));
            imageDirectory.setForeground(Color.white);
            imageDirectory.setBackground(new Color(60, 60, 60));
            imageDirectory.setText("Enter the class imageDirectory");



        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NE.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(typeMessage);
        add(type);
        add(name);
        add(description);
        add(imageDirectory);
        add(strengthMessage);
        add(strength.get(0));
        add(strength.get(1));
        add(strength.get(2));

        for (int cnt = 0; cnt < 3; cnt++) {
            add(amount[cnt]);
            add(maxHP[cnt]);
        }

        add(cancel);
        add(save);

        for (int cnt = 0; cnt < 3; cnt++) {
            int finalCnt = cnt;
            strength.get(cnt).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (strength.get(finalCnt).isSelected()) {
                        amount[finalCnt].setVisible(true);
                        maxHP[finalCnt].setVisible(true);
                    }
                    else {
                        amount[finalCnt].setVisible(false);
                        maxHP[finalCnt].setVisible(false);
                    }
                }
            });
        }

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sListener.switchTo("custom");
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (strength.get(0).isSelected() && strength.get(1).isSelected() && strength.get(2).isSelected()
                && maxHP[0].getText().matches("[0-9]+") && Integer.parseInt(maxHP[0].getText()) > 0 && Integer.parseInt(maxHP[0].getText()) < 4
                        && maxHP[1].getText().matches("[0-9]+") && Integer.parseInt(maxHP[1].getText()) > 0 && Integer.parseInt(maxHP[1].getText()) < 4
                        && maxHP[2].getText().matches("[0-9]+") && Integer.parseInt(maxHP[2].getText()) > 0 && Integer.parseInt(maxHP[2].getText()) < 4
                        && amount[0].getText().matches("[0-9]+") && Integer.parseInt(amount[0].getText()) > 0 && Integer.parseInt(amount[0].getText()) < 4
                        && amount[1].getText().matches("[0-9]+") && Integer.parseInt(amount[1].getText()) > 0 && Integer.parseInt(amount[1].getText()) < 4
                        && amount[2].getText().matches("[0-9]+") && Integer.parseInt(amount[2].getText()) > 0 && Integer.parseInt(amount[2].getText()) < 4) {

                    String tmp = "";

                    for (JCheckBox str : strength) {
                        if (str.isSelected()) {
                            tmp = str.getText();
                        }
                    }

                    String s = tmp + "\n" + type.getSelectedItem() + "\n" + description.getText() + "\n" + imageDirectory.getText() + "\n"
                            + maxHP[0].getText() + "\n" + amount[0].getText()
                            + "\n" + maxHP[1].getText() + "\n" + amount[1].getText() +
                            "\n" + maxHP[2].getText() + "\n" + amount[2].getText();

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Enemy/" + name.getText() + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + name.getText()).getBytes();
                    p = Paths.get("Save/Enemy/List.txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE, APPEND))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    sListener.switchTo("custom");
                }

                else if (strength.get(0).isSelected() && strength.get(1).isSelected()
                        && maxHP[0].getText().matches("[0-9]+") && Integer.parseInt(maxHP[0].getText()) > 0 && Integer.parseInt(maxHP[0].getText()) < 4
                        && maxHP[1].getText().matches("[0-9]+") && Integer.parseInt(maxHP[1].getText()) > 0 && Integer.parseInt(maxHP[1].getText()) < 4
                        && amount[0].getText().matches("[0-9]+") && Integer.parseInt(amount[0].getText()) > 0 && Integer.parseInt(amount[0].getText()) < 4
                        && amount[1].getText().matches("[0-9]+") && Integer.parseInt(amount[1].getText()) > 0 && Integer.parseInt(amount[1].getText()) < 4) {

                    String tmp = "";

                    for (JCheckBox str : strength) {
                        if (str.isSelected()) {
                            tmp = str.getText();
                        }
                    }

                    String s = tmp + "\n" + type.getSelectedItem() + "\n" + description.getText() + "\n" + imageDirectory.getText() + "\n"
                            + maxHP[0].getText() + "\n" + amount[0].getText()
                            + "\n" + maxHP[1].getText() + "\n" + amount[1].getText();

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Enemy/" + name.getText() + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + name.getText()).getBytes();
                    p = Paths.get("Save/Enemy/List.txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE, APPEND))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    sListener.switchTo("custom");
                }

                    else if (strength.get(0).isSelected()
                        && maxHP[0].getText().matches("[0-9]+") && Integer.parseInt(maxHP[0].getText()) > 0 && Integer.parseInt(maxHP[0].getText()) < 4
                        && amount[0].getText().matches("[0-9]+") && Integer.parseInt(amount[0].getText()) > 0 && Integer.parseInt(amount[0].getText()) < 4) {

                    String tmp = "";

                    for (JCheckBox str : strength) {
                        if (str.isSelected()) {
                            tmp = str.getText();
                        }
                    }

                    String s = tmp + "\n" + type.getSelectedItem() + "\n" + description.getText() + "\n" + imageDirectory.getText() + "\n"
                            + maxHP[0].getText() + "\n" + amount[0].getText();

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Enemy/" + name.getText() + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + name.getText()).getBytes();
                    p = Paths.get("Save/Enemy/List.txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE, APPEND))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    sListener.switchTo("custom");
                }

                else {
                    JOptionPane.showMessageDialog(null, "Your information isn't correct");
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
