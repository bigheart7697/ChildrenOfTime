package GraphicalUserInterface.CustomGame.NewMap.CreatingTiles;

import GraphicalUserInterface.CustomGame.NewMap.CreatingMap;
import GraphicalUserInterface.CustomGame.Tiles.DoorTile;
import GraphicalUserInterface.CustomGame.Tiles.StoryTile;
import GraphicalUserInterface.SimpleMenuListener;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rezab on 11/07/2016.
 */
public class CreatingDoorTile extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private JLabel dirLabel = new JLabel("Enter the door direction:");
    private HashMap<DoorTile.Direction, JRadioButton> direction = new HashMap<>() ;
    private JTextArea doorNumber = new JTextArea(1, 82);
    private ButtonGroup dirGroup = new ButtonGroup();
    private DoorTile.Direction dir = null;

    public CreatingDoorTile(SimpleMenuListener sListener, CreatingMap CM) {

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            doorNumber.setFont(sFont.deriveFont(20f));
            doorNumber.setText("Enter the door number.");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout());

        dirLabel.setFont(sFont.deriveFont(20f));
        dirLabel.setForeground(Color.white);

        direction.put(DoorTile.Direction.upward, new JRadioButton("upward"));
        direction.put(DoorTile.Direction.downward, new JRadioButton("downward"));
        direction.put(DoorTile.Direction.rightward, new JRadioButton("rightward"));
        direction.put(DoorTile.Direction.leftward, new JRadioButton("leftward"));

        dirGroup.add(direction.get(DoorTile.Direction.upward));
        dirGroup.add(direction.get(DoorTile.Direction.downward));
        dirGroup.add(direction.get(DoorTile.Direction.leftward));
        dirGroup.add(direction.get(DoorTile.Direction.rightward));

        doorNumber.setLineWrap(true);
        doorNumber.setFont(sFont.deriveFont(20f));
        doorNumber.setBackground(new Color(60, 60, 60));
        doorNumber.setForeground(Color.white);

        add(dirLabel);
        add(direction.get(DoorTile.Direction.upward));
        add(direction.get(DoorTile.Direction.downward));
        add(direction.get(DoorTile.Direction.rightward));
        add(direction.get(DoorTile.Direction.leftward));
        add(doorNumber);

        direction.get(DoorTile.Direction.upward).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dir = DoorTile.Direction.upward;
            }
        });

        direction.get(DoorTile.Direction.downward).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dir = DoorTile.Direction.downward;
            }
        });

        direction.get(DoorTile.Direction.leftward).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dir = DoorTile.Direction.leftward;
            }
        });

        direction.get(DoorTile.Direction.leftward).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dir = DoorTile.Direction.upward;
            }
        });

        add(OK);

        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doorNumber.getText().matches("[0-9]+") && dir != null) {
                    DoorTile tmp = new DoorTile(dir, Integer.parseInt(doorNumber.getText()));
                    CM.addDoorTile(tmp);
                    sListener.switchTo("creating map");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Your information is incomplete!");
                }

            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
