package GraphicalUserInterface.CustomGame;

import GraphicalUserInterface.SimpleMenuListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by rezab on 09/07/2016.
 */
public class MapSize extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private int size;

    private JButton OK = new JButton("OK");
    private JLabel message = new JLabel("Choose the map size:");
    private ButtonGroup group = new ButtonGroup();
    private JRadioButton firstSize = new JRadioButton("8 * 8");
    private JRadioButton secondSize = new JRadioButton("10 * 10");
    private JRadioButton thirdSize = new JRadioButton("12 * 12");
    private JRadioButton fourthSize = new JRadioButton("14 * 14");
    private JRadioButton fifthSize = new JRadioButton("16 * 16");


    public MapSize(SimpleMenuListener sListener, NewMap NM) {

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            message.setFont(sFont.deriveFont(20f));
            firstSize.setFont(sFont.deriveFont(20f));
            secondSize.setFont(sFont.deriveFont(20f));
            fourthSize.setFont(sFont.deriveFont(20f));
            thirdSize.setFont(sFont.deriveFont(20f));
            fifthSize.setFont(sFont.deriveFont(20f));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());

//        defeatMessage.setBackground(new Color(60, 60, 60));
        message.setForeground(Color.white);
        group.add(fifthSize);
        group.add(firstSize);
        group.add(secondSize);
        group.add(thirdSize);
        group.add(fourthSize);
        add(message);
        add(firstSize);
        add(secondSize);
        add(thirdSize);
        add(fourthSize);
        add(fifthSize);
        add(OK);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NM.setSizeOfMap(size);
                sListener.switchTo("new map");
            }
        });

        firstSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = 8;
            }
        });

        secondSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = 10;
            }
        });

        thirdSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = 12;
            }
        });

        fourthSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = 14;
            }
        });

        fifthSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = 16;
            }
        });


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
