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
public class DefeatMessage extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private JButton OK = new JButton("OK");
    private JTextArea defeatMessage = new JTextArea(10, 89);

    public DefeatMessage(SimpleMenuListener sListener, NewMap NM) {

        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            defeatMessage.setFont(sFont.deriveFont(20f));
            defeatMessage.setText("Enter the defeat message.");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new FlowLayout());
        JScrollPane scroller1 = new JScrollPane(defeatMessage);
        defeatMessage.setLineWrap(true);
        defeatMessage.setBackground(new Color(60, 60, 60));
        defeatMessage.setForeground(Color.white);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroller1);
        add(OK);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NM.setBreakMessage(defeatMessage.getText());
                sListener.switchTo("new map");
            }
        });
//        defeatMessage.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                defeatMessage.setText("");
//            }
//        });

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }

}
