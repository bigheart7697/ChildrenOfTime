package GraphicalUserInterface.CustomGame.NewMap.CreatingTiles;

import GraphicalUserInterface.CustomGame.NewMap.CreatingMap;
import GraphicalUserInterface.CustomGame.Tiles.BattleTile;
import GraphicalUserInterface.CustomGame.Tiles.StoryTile;
import GraphicalUserInterface.SimpleMenuListener;
import units.Angel;
import units.Tank;

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
public class CreatingStoryTile extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private JTextArea story = new JTextArea(10, 82);
    private JTextArea imageDirectory = new JTextArea(1, 82);

    public CreatingStoryTile(SimpleMenuListener sListener, CreatingMap CM) {

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);

            imageDirectory.setFont(sFont.deriveFont(20f));
            story.setFont(sFont.deriveFont(20f));
            story.setText("Enter the story.");
            imageDirectory.setText("Enter the image directory.");

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout());

        story.setLineWrap(true);
        imageDirectory.setLineWrap(true);
        story.setBackground(new Color(60, 60, 60));
        imageDirectory.setBackground(new Color(60, 60, 60));
        story.setForeground(Color.white);
        imageDirectory.setForeground(Color.white);

        add(story);
        add(imageDirectory);


        add(OK);

        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    StoryTile tmp = new StoryTile(story.getText(), imageDirectory.getText());
                    CM.addStoryTile(tmp);
                    sListener.switchTo("creating map");

            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }


}
