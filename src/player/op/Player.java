package player.op;

import units.Hero;

import java.util.ArrayList;

public class Player {
    private String name;
    private int XP;
    private int gold;
    private int IMPotion;
    private ArrayList<Hero> heroes;


    //Constructor

    public Player(String n, int startingGold, int initXP) {
        this.name = n;
        this.XP = initXP;
        this.gold = startingGold;
        this.IMPotion = 3;
        this.heroes = new ArrayList<>();
    }


    //Getters and Setters

    public String getName() { return name; }

    public int getXP() { return XP; }
    public void setXP(int XP) { this.XP = XP; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getIMPotionRemaining() { return IMPotion; }

    // Other methods

    public void addHero(Hero h) { heroes.add(h); }

    public void useIMPotion(Hero h) {
        if (IMPotion > 0) h.revived();
        IMPotion--;
    }

    public void move() {
        //Not used in this stage. Will be implemented in graphical phase.
    }

    public void buy() {
        //Code to be written. requires shop to be implemented.
    }

    public void useXP() {
        //Code to be written. requires UI to be implemented.
    }

    public void fight() {
        //Not used in this stage. Will be implemented in graphical phase.
    }

}
