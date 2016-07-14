package GraphicalUserInterface.CustomGame.NewMap;

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
 * Created by rezab on 10/07/2016.
 */
public class StartingPoint extends JPanel {
    private BufferedImage BG;
    private Font sFont;

    private JButton OK = new JButton("OK");
    private JLabel message = new JLabel("Choose the starting point:");
    private JComboBox XCoordinate, YCoordinate;

    public StartingPoint(SimpleMenuListener sListener, NewMap NM) {

        Integer[] coordinateNumbers = new Integer[NM.getSizeOfMap()];
        for (int counter = 0; counter < NM.getSizeOfMap(); counter++) {
            coordinateNumbers[counter] = counter + 1;
        }

        XCoordinate = new JComboBox(coordinateNumbers);
        YCoordinate = new JComboBox(coordinateNumbers);

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            message.setFont(sFont.deriveFont(20f));
            message.setForeground(Color.white);

            XCoordinate.setFont(sFont.deriveFont(20f));
            XCoordinate.setBackground(new Color(60, 60, 60));
            XCoordinate.setForeground(Color.white);

            YCoordinate.setFont(sFont.deriveFont(20f));
            YCoordinate.setBackground(new Color(60, 60, 60));
            YCoordinate.setForeground(Color.white);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(message);
        add(XCoordinate);
        add(YCoordinate);

        add(OK);



        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NM.setXOfStartingPoint((Integer) XCoordinate.getSelectedItem());
                NM.setYOfStartingPoint((Integer) YCoordinate.getSelectedItem());
                sListener.switchTo("new map");
            }
        });



    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
