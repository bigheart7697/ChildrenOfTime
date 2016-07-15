package GraphicalUserInterface.CustomGame.NewItem;

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
 * Created by rezab on 13/07/2016.
 */
public class CreatingImmediateEffect extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JLabel message = new JLabel("Choose the immediate effect type");
    private JComboBox whichState;
    private JTextArea description = new JTextArea(1, 89), nameGetter = new JTextArea(1, 89), imageDirectory = new JTextArea(1 ,89), amountGetter = new JTextArea(1, 89);
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    public CreatingImmediateEffect(SimpleMenuListener sListener) {

        String[] immediateEffectTypes = {"max HP", "max MP", "attack power"};
        whichState = new JComboBox(immediateEffectTypes);

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            whichState.setFont(sFont.deriveFont(20f));
            whichState.setForeground(Color.white);
            whichState.setBackground(new Color(60, 60, 60));

            imageDirectory.setFont(sFont.deriveFont(20f));
            imageDirectory.setForeground(Color.white);
            imageDirectory.setBackground(new Color(60, 60, 60));
            imageDirectory.setText("Enter the image directory");

            description.setFont(sFont.deriveFont(20f));
            description.setForeground(Color.white);
            description.setBackground(new Color(60, 60, 60));
            description.setText("Enter the description");

            nameGetter.setFont(sFont.deriveFont(20f));
            nameGetter.setForeground(Color.white);
            nameGetter.setBackground(new Color(60, 60, 60));
            nameGetter.setText("Enter the item name");

            amountGetter.setFont(sFont.deriveFont(20f));
            amountGetter.setForeground(Color.white);
            amountGetter.setBackground(new Color(60, 60, 60));
            amountGetter.setText("Enter the amount");


            message.setFont(sFont.deriveFont(20f));
            message.setForeground(Color.white);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NI.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(message);
        add(whichState);
        add(nameGetter);
        add(description);
        add(imageDirectory);
        add(amountGetter);
        add(cancel);
        add(save);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sListener.switchTo("new item");
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (amountGetter.getText().matches("[0-9]+") && Integer.parseInt(amountGetter.getText()) > 0 && Integer.parseInt(amountGetter.getText()) < 100) {

                    String s = "immediate effect" + "\n" + whichState.getSelectedItem() + "\n" + description.getText() + "\n" + imageDirectory.getText() + "\n" + amountGetter.getText();

                    byte data[] = s.getBytes();
                    Path p = Paths.get("Save/Item/" + nameGetter.getText() + ".txt");

                    try (OutputStream out = new BufferedOutputStream(
                            Files.newOutputStream(p, CREATE))) {
                        out.write(data, 0, data.length);
                    } catch (IOException x) {
                        System.err.println(x);
                    }

                    data = ("\n" + nameGetter.getText()).getBytes();
                    p = Paths.get("Save/Item/List.txt");

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
