package GraphicalUserInterface.CustomGame.NewItem;

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
public class CreatingEquipment extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JLabel message = new JLabel("Choose the equipment type");
    private JComboBox whichState;
    private JTextArea nameGetter = new JTextArea(1, 89), imageDirectory = new JTextArea(1, 89), amountGetter = new JTextArea(1, 89), price = new JTextArea(1, 89);
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    public CreatingEquipment(SimpleMenuListener sListener) {

        String[] immediateEffectTypes = {"max HP", "max MP", "attack power", "EP"};
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

            nameGetter.setFont(sFont.deriveFont(20f));
            nameGetter.setForeground(Color.white);
            nameGetter.setBackground(new Color(60, 60, 60));
            nameGetter.setText("Enter the item name");

            amountGetter.setFont(sFont.deriveFont(20f));
            amountGetter.setForeground(Color.white);
            amountGetter.setBackground(new Color(60, 60, 60));
            amountGetter.setText("Enter the amount");

            price.setFont(sFont.deriveFont(20f));
            price.setForeground(Color.white);
            price.setBackground(new Color(60, 60, 60));
            price.setText("Enter the price");


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
        add(imageDirectory);
        add(amountGetter);
        add(price);
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
                if (amountGetter.getText().matches("[0-9]+") && Integer.parseInt(amountGetter.getText()) > 0
                        && Integer.parseInt(amountGetter.getText()) < 500 && whichState.getSelectedItem().equals("EP")
                        && price.getText().matches("[0-9]+") && Integer.parseInt(price.getText()) > 0 && Integer.parseInt(price.getText()) < 10) {
                    sListener.switchTo("custom");
                }
                else if (amountGetter.getText().matches("[0-9]+") && Integer.parseInt(amountGetter.getText()) > 0
                        && Integer.parseInt(amountGetter.getText()) < 500 && !whichState.getSelectedItem().equals("EP")
                        && price.getText().matches("[0-9]+") && Integer.parseInt(price.getText()) > 0 && Integer.parseInt(price.getText()) < 10) {
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
