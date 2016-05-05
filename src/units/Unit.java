package units;

import battleMechanics.Battlefield;

abstract public class Unit {
    private String name;
    protected boolean isDead;
    protected int HP, maxHP;
    protected int attDmg;
    protected Battlefield field;
    protected Unit target;

    //Constructor

    Unit(String n, int hp, int dmg) {
        this.name = n; this.maxHP = hp; this.attDmg = dmg; this.isDead = false;
    }


    //Getters and Setters

    public String getName() { return name; }

    public void checkLifeStatus() {
        if (this.getHP() <= 0) {
            this.died();
            this.setHP(0);
        }
    }
    public void died() { this.isDead = true; }
    public void revived() { this.isDead = false;}

    public int getHP() { return this.HP; }
    public void setHP(int h) { this.HP = h; }

    public int getMaxHP() { return this.maxHP; }
    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getAttDmg() { return this.attDmg; }
    public void setAttDmg(int attDmg) { this.attDmg = attDmg; }

    public Battlefield getField() { return this.field; }
    public void setField(Battlefield f) { this.field = f; }

    public abstract void getTarget();
    public abstract void update();
}
