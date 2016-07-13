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
public class CreatingImmediateEffect extends JPanel {

    private BufferedImage BG;
    private Font sFont;

    private JLabel message = new JLabel("Choose the immediate effect type");
    private JComboBox whichState;
    private JTextArea amountGetter = new JTextArea(1, 89);
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
