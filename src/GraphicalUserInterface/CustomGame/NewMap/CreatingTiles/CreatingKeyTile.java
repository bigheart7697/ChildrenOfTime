package GraphicalUserInterface.CustomGame.NewMap.CreatingTiles;

import GraphicalUserInterface.CustomGame.NewMap.CreatingMap;
import GraphicalUserInterface.CustomGame.Tiles.DoorTile;
import GraphicalUserInterface.CustomGame.Tiles.KeyTile;
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
 * Created by rezab on 12/07/2016.
 */
public class CreatingKeyTile extends JPanel {
    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private JTextArea keyNumber = new JTextArea(1, 82);

    public CreatingKeyTile(SimpleMenuListener sListener, CreatingMap CM) {
        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            keyNumber.setFont(sFont.deriveFont(20f));
            keyNumber.setText("Enter the key number number.");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        keyNumber.setLineWrap(true);
        keyNumber.setFont(sFont.deriveFont(20f));
        keyNumber.setBackground(new Color(60, 60, 60));
        keyNumber.setForeground(Color.white);
        add(keyNumber);


        add(OK);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keyNumber.getText().matches("[0-9]+")) {
                    KeyTile tmp = new KeyTile(Integer.parseInt(keyNumber.getText()));
                    CM.addKeyTile(tmp);
                    sListener.switchTo("creating map");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Your information is incomplete!");
                }

            }
        });
//        keyNumber.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                keyNumber.setText("");
//            }
//        });
//
//        story.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                story.setText("");
//            }
//        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }
}
