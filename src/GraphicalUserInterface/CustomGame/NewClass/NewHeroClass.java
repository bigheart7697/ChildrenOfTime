package GraphicalUserInterface.CustomGame.NewClass;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rezab on 07/07/2016.
 */
public class NewHeroClass extends JPanel {
    private BufferedImage BG;
    private Font sFont;

    private JTextArea name = new JTextArea(1, 89), description = new JTextArea(10, 89);
    private JTextArea maxHP = new JTextArea(1, 89), HPRefill = new JTextArea(1, 89);
    private JTextArea maxMP = new JTextArea(1, 89), MPRefill = new JTextArea(1, 89);
    private JTextArea attDamage = new JTextArea(1, 89), EP = new JTextArea(1, 89);
    private JTextArea invSize = new JTextArea(1, 89);
    private JLabel message = new JLabel("Choose two abilities");
    private ArrayList<JCheckBox> abilities = new ArrayList<>();
    private JButton save = new JButton("save"), cancel = new JButton("cancel");

    public NewHeroClass(SimpleMenuListener sListener) {

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

            name.setFont(sFont.deriveFont(20f));
            name.setForeground(Color.white);
            name.setBackground(new Color(60, 60, 60));
            name.setText("Enter the class name");

            description.setFont(sFont.deriveFont(20f));
            description.setForeground(Color.white);
            description.setBackground(new Color(60, 60, 60));
            description.setText("Enter the class description");

            maxHP.setFont(sFont.deriveFont(20f));
            maxHP.setForeground(Color.white);
            maxHP.setBackground(new Color(60, 60, 60));
            maxHP.setText("Enter the max HP");

            HPRefill.setFont(sFont.deriveFont(20f));
            HPRefill.setForeground(Color.white);
            HPRefill.setBackground(new Color(60, 60, 60));
            HPRefill.setText("Enter the HP refill rate");

            maxMP.setFont(sFont.deriveFont(20f));
            maxMP.setForeground(Color.white);
            maxMP.setBackground(new Color(60, 60, 60));
            maxMP.setText("Enter the max MP");

            MPRefill.setFont(sFont.deriveFont(20f));
            MPRefill.setForeground(Color.white);
            MPRefill.setBackground(new Color(60, 60, 60));
            MPRefill.setText("Enter the MP refill rate");

            EP.setFont(sFont.deriveFont(20f));
            EP.setForeground(Color.white);
            EP.setBackground(new Color(60, 60, 60));
            EP.setText("Enter the EP");

            attDamage.setFont(sFont.deriveFont(20f));
            attDamage.setForeground(Color.white);
            attDamage.setBackground(new Color(60, 60, 60));
            attDamage.setText("Enter the attack power");

            invSize.setFont(sFont.deriveFont(20f));
            invSize.setForeground(Color.white);
            invSize.setBackground(new Color(60, 60, 60));
            invSize.setText("Enter the inventory size");


        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NI.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(name);
        add(description);
        add(maxHP);
        add(HPRefill);
        add(maxMP);
        add(MPRefill);
        add(attDamage);
        add(EP);
        add(invSize);
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
                if (maxMP.getText().matches("[0-9]+") && Integer.parseInt(maxMP.getText()) > 0 && Integer.parseInt(maxMP.getText()) < 500
                        && maxHP.getText().matches("[0-9]+") && Integer.parseInt(maxHP.getText()) > 0 && Integer.parseInt(maxHP.getText()) < 500
                        && HPRefill.getText().matches("[0-9]+") && Integer.parseInt(HPRefill.getText()) > 0 && Integer.parseInt(HPRefill.getText()) < 100
                        && MPRefill.getText().matches("[0-9]+") && Integer.parseInt(MPRefill.getText()) > 0 && Integer.parseInt(MPRefill.getText()) < 100
                        && EP.getText().matches("[0-9]+") && Integer.parseInt(EP.getText()) > 0 && Integer.parseInt(EP.getText()) < 10 && checkedNum == 2
                        && attDamage.getText().matches("[0-9]+") && Integer.parseInt(attDamage.getText()) > 0 && Integer.parseInt(attDamage.getText()) < 500
                        && invSize.getText().matches("[0-9]+") && Integer.parseInt(invSize.getText()) > 0 && Integer.parseInt(invSize.getText()) < 10) {
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
