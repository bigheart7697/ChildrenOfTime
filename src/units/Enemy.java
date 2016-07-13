package units;

import java.awt.*;

abstract public class Enemy extends Unit {
    protected int version; // 0 is weak; 1 is able; 2 is mighty
    protected int id;
    protected Image image;

    //Constructor
    Enemy(String n, int h, int dmg) {
        super(n, h, dmg);
    }

//    getter and setters:

    public int getId() {
        return id;
    }
    public int getVersion() { return version; }
    //Other Methods
    abstract public String action();

    @Override
    public void update(){}

    public Image getImage() { return image; }
    public void setImage(Image i) { image = i; }
}
