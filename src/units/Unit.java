package units;

import battleMechanics.Battlefield;

abstract public class Unit {
    private String name;
    protected boolean isDead;
    protected int HP, maxHP;
    protected int attDmg;
    protected Battlefield field;
    protected Unit target;
    private String description;

    //Constructor

    Unit(String n, int hp, int dmg) {
        this.name = n;
        this.maxHP = hp;
        this.HP = hp;
        this.attDmg = dmg;
        this.isDead = false;
    }


    //Getters and Setters

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public void refreshStatus() {
        if (this.getHP() <= 0) {
            this.died();
            this.setHP(0);
        }
        if (this.getHP() > this.getMaxHP()) this.setHP(this.getMaxHP());
    }
    public void died() { this.isDead = true; }
    public void revived() {
        this.isDead = false;
        setHP(maxHP / 2);
    }

    public boolean isDead() {
        return isDead;
    }

    public int getHP() { return this.HP; }
    public void setHP(int h) { this.HP = h; }

    public int getMaxHP() { return this.maxHP; }
    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getAttDmg() { return this.attDmg; }
    public void setAttDmg(int attDmg) { this.attDmg = attDmg; }

    public Battlefield getField() { return this.field; }
    public void setField(Battlefield f) { this.field = f; }

    public abstract void setTarget();
    public Unit getTarget() { return target; }
    public abstract void update();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
