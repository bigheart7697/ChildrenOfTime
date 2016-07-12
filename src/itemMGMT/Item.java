package itemMGMT;

import units.Hero;

import java.awt.*;
import java.util.ArrayList;

public class Item {
    protected String name;
    protected int cost;
    protected int invSpaceNeeded;
    protected String targetStat;
    protected int effect;
    private String description;
    private ArrayList<Hero> owners;

    //GUI Phase
    protected Image image;

    //Constructor

    Item(String n, int c, int isn, String ts, int e, String description) {
        this.name = n; this.cost = c; this.invSpaceNeeded = isn; this.targetStat = ts; this.effect = e;
        this.description = description; this.owners = null;
        owners = new ArrayList<>();
    }


    //Getters and Setters

    public ArrayList<Hero> getOwners() { return owners; }
    public void addOwner(Hero h) { owners.add(h); }
    public void removeOwner(Hero h) { owners.remove(h);}

    public String getTargetStat() { return this.targetStat; }
//    public void setTargetStat(String ts) { this.targetStat = ts; }

    public String getName() { return this.name; }

    public int getCost() { return this.cost; }
//    public void setCost(int c) { this.cost = c; }

    public int getInvSpaceNeeded() { return this.invSpaceNeeded; }
//    public void setInvSpaceNeeded(int isn) { this.invSpaceNeeded = isn; }

    public int getEffect() { return this.effect; }
//    public void setEffect(int e) { this.effect = e; }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    //Other Methods

    public void displayInfo() { System.out.println(this.description); }

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }

}
