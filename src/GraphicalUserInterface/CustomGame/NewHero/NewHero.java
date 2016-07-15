package GraphicalUserInterface.CustomGame.NewHero;

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
public class NewHero extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JTextArea name = new JTextArea(1, 89), imageDirectory = new JTextArea(1, 89);
    private JTextArea description = new JTextArea(10, 89), firstNeededAbilities[] = new JTextArea[3], secondNeededAbilities[] = new JTextArea[3];
    private JTextArea firstNeededAbilitiesUpgrade[] = new JTextArea[3], secondNeededAbilitiesUpgrade[] = new JTextArea[3];
    private JLabel message = new JLabel("Choose two abilities"), classmessage = new JLabel("Choose hero class");
    private ButtonGroup g = new ButtonGroup();
    private ArrayList<JRadioButton> heroclass = new ArrayList<>();
    private ArrayList<JCheckBox> abilities = new ArrayList<>();
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    public NewHero(SimpleMenuListener sListener) {

        heroclass.add(new JRadioButton("fighter"));
        heroclass.add(new JRadioButton("supporter"));

        abilities.add(new JCheckBox("fight training"));
        abilities.add(new JCheckBox("work out"));
        abilities.add(new JCheckBox("quick as a bunny"));
        abilities.add(new JCheckBox("magic lessons"));
        abilities.add(new JCheckBox("overpowered attack"));
        abilities.add(new JCheckBox("swirling attack"));
        abilities.add(new JCheckBox("sacrifice"));
        abilities.add(new JCheckBox("critical strike"));
        abilities.add(new JCheckBox("elixir"));
        abilities.add(new JCheckBox("caretaker"));
        abilities.add(new JCheckBox("boost"));
        abilities.add(new JCheckBox("mana beam"));

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            message.setFont(sFont.deriveFont(20f));
            message.setForeground(Color.white);

            classmessage.setFont(sFont.deriveFont(20f));

            for (int cnt = 0; cnt < 3; cnt++) {

                description.setFont(sFont.deriveFont(20f));
                description.setForeground(Color.white);
                description.setBackground(new Color(60, 60, 60));
                description.setText("Enter the description");

                firstNeededAbilities[cnt] = new JTextArea(1, 89);
                firstNeededAbilities[cnt].setFont(sFont.deriveFont(20f));
                firstNeededAbilities[cnt].setForeground(Color.white);
                firstNeededAbilities[cnt].setBackground(new Color(60, 60, 60));
                firstNeededAbilities[cnt].setText("Enter the first needed ability for upgrade" + (cnt + 1));

                firstNeededAbilitiesUpgrade[cnt] = new JTextArea(1, 89);
                firstNeededAbilitiesUpgrade[cnt].setFont(sFont.deriveFont(20f));
                firstNeededAbilitiesUpgrade[cnt].setForeground(Color.white);
                firstNeededAbilitiesUpgrade[cnt].setBackground(new Color(60, 60, 60));
                firstNeededAbilitiesUpgrade[cnt].setText("Enter first needed ability's upgrade for upgrade" + (cnt + 1));

                secondNeededAbilities[cnt] = new JTextArea(1, 89);
                secondNeededAbilities[cnt].setFont(sFont.deriveFont(20f));
                secondNeededAbilities[cnt].setForeground(Color.white);
                secondNeededAbilities[cnt].setBackground(new Color(60, 60, 60));
                secondNeededAbilities[cnt].setText("Enter the second needed ability for upgrade" + (cnt + 1));

                secondNeededAbilitiesUpgrade[cnt] = new JTextArea(1, 89);
                secondNeededAbilitiesUpgrade[cnt].setFont(sFont.deriveFont(20f));
                secondNeededAbilitiesUpgrade[cnt].setForeground(Color.white);
                secondNeededAbilitiesUpgrade[cnt].setBackground(new Color(60, 60, 60));
                secondNeededAbilitiesUpgrade[cnt].setText("Enter second needed ability's upgrade for upgrade" + (cnt + 1));
            }

            name.setFont(sFont.deriveFont(20f));
            name.setForeground(Color.white);
            name.setBackground(new Color(60, 60, 60));
            name.setText("Enter the class name");

            imageDirectory.setFont(sFont.deriveFont(20f));
            imageDirectory.setForeground(Color.white);
            imageDirectory.setBackground(new Color(60, 60, 60));
            imageDirectory.setText("Enter the class imageDirectory");



        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NH.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(classmessage);
        for (JRadioButton tmp : heroclass ) {
            g.add(tmp);
            add(tmp);
        }

        add(name);
        add(description);
        add(imageDirectory);
        add(message);
        add(abilities.get(0));
        add(abilities.get(1));
        add(abilities.get(2));
        add(abilities.get(3));
        add(abilities.get(4));
        add(abilities.get(5));
        add(abilities.get(6));
        add(abilities.get(7));
        add(abilities.get(8));
        add(abilities.get(9));
        add(abilities.get(10));
        add(abilities.get(11));

        for (int cnt = 0; cnt < 3; cnt++) {
            add(firstNeededAbilities[cnt]);
            add(firstNeededAbilitiesUpgrade[cnt]);
        }

        for (int cnt = 0; cnt < 3; cnt++) {
            add(secondNeededAbilities[cnt]);
            add(secondNeededAbilitiesUpgrade[cnt]);
        }

        add(cancel);
        add(save);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sListener.switchTo("custom");
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int checkedNum = 0;
                for (int cnt = 0; cnt < abilities.size(); cnt++)
                    if (abilities.get(cnt).isSelected())
                        checkedNum++;
                if (firstNeededAbilitiesUpgrade[0].getText().matches("[0-9]+") && Integer.parseInt(firstNeededAbilitiesUpgrade[0].getText()) > 0 && Integer.parseInt(firstNeededAbilitiesUpgrade[0].getText()) < 4
                        && firstNeededAbilitiesUpgrade[1].getText().matches("[0-9]+") && Integer.parseInt(firstNeededAbilitiesUpgrade[1].getText()) > 0 && Integer.parseInt(firstNeededAbilitiesUpgrade[1].getText()) < 4
                        && firstNeededAbilitiesUpgrade[2].getText().matches("[0-9]+") && Integer.parseInt(firstNeededAbilitiesUpgrade[2].getText()) > 0 && Integer.parseInt(firstNeededAbilitiesUpgrade[2].getText()) < 4
                        && secondNeededAbilitiesUpgrade[0].getText().matches("[0-9]+") && Integer.parseInt(secondNeededAbilitiesUpgrade[0].getText()) > 0 && Integer.parseInt(secondNeededAbilitiesUpgrade[0].getText()) < 4
                        && secondNeededAbilitiesUpgrade[1].getText().matches("[0-9]+") && Integer.parseInt(secondNeededAbilitiesUpgrade[1].getText()) > 0 && Integer.parseInt(secondNeededAbilitiesUpgrade[1].getText()) < 4 && checkedNum == 2
                        && secondNeededAbilitiesUpgrade[2].getText().matches("[0-9]+") && Integer.parseInt(secondNeededAbilitiesUpgrade[2].getText()) > 0 && Integer.parseInt(secondNeededAbilitiesUpgrade[2].getText()) < 4) {

                    String s = description.getText() + "\n" + imageDirectory.getText() + "\n" + firstNeededAbilities[0].getText() + "\n" + firstNeededAbilitiesUpgrade[0].getText()
                            + "\n" + firstNeededAbilities[1].getText() + "\n" + firstNeededAbilitiesUpgrade[1].getText() +
                            "\n" + firstNeededAbilities[2].getText() + "\n" + firstNeededAbilitiesUpgrade[2].getText() + "\n" +
                            secondNeededAbilities[0].getText() + "\n" + secondNeededAbilitiesUpgrade[0].getText() + "\n" +
                            secondNeededAbilities[1].getText() + "\n" + secondNeededAbilitiesUpgrade[1].getText() + "\n" +
                            secondNeededAbilities[2].getText() + "\n" + secondNeededAbilitiesUpgrade[2].getText();
                    for (JCheckBox ability : abilities) {
                        if (ability.isSelected()) {
                            s += "\n" + ability.getText();
                        }
                    }

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Hero/" + name.getText() + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + name.getText()).getBytes();
                    p = Paths.get("Save/Hero/List.txt");

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
