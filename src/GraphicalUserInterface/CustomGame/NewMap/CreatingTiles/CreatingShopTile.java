package GraphicalUserInterface.CustomGame.NewMap.CreatingTiles;

import GraphicalUserInterface.CustomGame.NewMap.CreatingMap;
import GraphicalUserInterface.CustomGame.Tiles.KeyTile;
import GraphicalUserInterface.CustomGame.Tiles.ShopTile;
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
 * Created by rezab on 12/07/2016.
 */
public class CreatingShopTile extends JPanel {

    private BufferedImage BG;
    private Font sFont;
    private JLabel messsage = new JLabel("Check the items you want the shop to have:                                                                                                                                                          ");
    private JButton OK = new JButton("OK");
    private HashMap<String, JCheckBox> items = new HashMap<>();

    public CreatingShopTile(SimpleMenuListener sListener, CreatingMap CM) {


        try {
            sFont = Font.createFont(Font.TRUETYPE_FONT, new File("CustomGameMenuGraphics/game.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sFont);
            messsage.setFont(sFont.deriveFont(20f));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            BG = ImageIO.read(new File("CustomGameMenuGraphics/NM.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout());

        items.put("Toughen", new JCheckBox("Toughen"));
        items.put("Guide", new JCheckBox("Guide"));
        items.put("Defy", new JCheckBox("Defy"));
        items.put("Sword", new JCheckBox("Sword"));
        items.put("Energy boots", new JCheckBox("Energy Boots"));
        items.put("Armor", new JCheckBox("Armor"));
        items.put("Magic stick", new  JCheckBox("Magic stick"));
        items.put("Health potion", new JCheckBox("Health potion"));
        items.put("Magic potion", new JCheckBox("Magic potion"));
        items.get("Toughen").setFont(sFont.deriveFont(20f));
        items.get("Guide").setFont(sFont.deriveFont(20f));
        items.get("Defy").setFont(sFont.deriveFont(20f));
        items.get("Sword").setFont(sFont.deriveFont(20f));
        items.get("Energy boots").setFont(sFont.deriveFont(20f));
        items.get("Armor").setFont(sFont.deriveFont(20f));
        items.get("Magic stick").setFont(sFont.deriveFont(20f));
        items.get("Health potion").setFont(sFont.deriveFont(20f));
        items.get("Magic potion").setFont(sFont.deriveFont(20f));

        messsage.setForeground(Color.white);

        add(messsage);
        add(items.get("Toughen"));
        add(items.get("Guide"));
        add(items.get("Defy"));
        add(items.get("Sword"));
        add(items.get("Energy boots"));
        add(items.get("Armor"));
        add(items.get("Magic stick"));
        add(items.get("Health potion"));
        add(items.get("Magic potion"));

        add(OK);

        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShopTile tmp = new ShopTile();
                if (items.get("Toughen").isSelected()) tmp.addItem("Toughen");
                if (items.get("Guide").isSelected()) tmp.addItem("Guide");
                if (items.get("Defy").isSelected()) tmp.addItem("Defy");
                if (items.get("Sword").isSelected()) tmp.addItem("Sword");
                if (items.get("Energy boots").isSelected()) tmp.addItem("Energy boots");
                if (items.get("Armor").isSelected()) tmp.addItem("Armor");
                if (items.get("Magic stick").isSelected()) tmp.addItem("Magic stick");
                if (items.get("Health potion").isSelected()) tmp.addItem("Health potion");
                if (items.get("Magic potion").isSelected()) tmp.addItem("Magic potion");
//                CM.addShopTile(tmp);
                CM.setSelectedTile(tmp);
                sListener.switchTo("creating map");

            }
        });

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, getWidth(), getHeight(), null);
    }

}
