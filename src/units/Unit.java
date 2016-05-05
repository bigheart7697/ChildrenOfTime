package units;

import battleMechanics.Battlefield;

abstract class Unit {
    private String name;
    protected int maxHP;
    protected int attDmg;
    protected Battlefield field;

    Unit(String n, int hp, int dmg) {
        this.name = n; this.maxHP = hp; this.attDmg = dmg;
    }

    Unit() {
        System.out.println("Error: Inputs missing in Unit constructor");
    }

    public String getName() { return name; }

    public int getMaxHP() { return maxHP; }
    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getAttDmg() { return attDmg; }
    public void setAttDmg(int attDmg) { this.attDmg = attDmg; }

    public Battlefield getField() { return this.field; }
    public void setField(Battlefield f) { this.field = f; }

    public abstract void getTarget();
    public abstract void update();
}
