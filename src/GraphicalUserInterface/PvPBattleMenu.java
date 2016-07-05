package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class PvPBattleMenu extends JComponent {

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.red);
        g2.fillRect(0,0,getWidth(), getHeight());


        g.drawImage(buffer, 0, 0, null);
    }

}
