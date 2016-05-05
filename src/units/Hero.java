package units;

import abilities.*;
import itemMGMT.Item;

import java.util.ArrayList;

abstract public class Hero extends Unit{
    protected int HPRefill;
    protected int maxMP;
    protected int MP, MPRefill;
    protected int EPmax, EP;

    protected ArrayList<Item> inventory;
    protected int invSize;

    protected int XPGained;

    protected ArrayList<Ability> abilities;

    //Constructor

    Hero(String n, int mhp, int dmg, int mmp, int hpr, int mpr, int epm, int invs) {
        super(n, mhp, dmg);
        this.maxMP = mmp;
        this.HPRefill = hpr;
        this.MPRefill = mpr;
        this.EPmax = epm;
        this.invSize = invs;
        this.XPGained = 0;
        this.initializeAbilities();
        this.intializeInventory();
    }

    //Setting up ArrayLists

    public void intializeInventory() { this.inventory = new ArrayList<>(); }
    public void addItem(Item i) {
        if (this.inventory.size() < this.invSize)
            this.inventory.add(i);
        else
            System.out.println("Not enough room in inventory");
    }

    public void initializeAbilities() { this.abilities = new ArrayList<>(); }
    public void addAbility(Ability a) { this.abilities.add(a); }


    //Getters and Setters

    public int getMP() { return this.MP; }
    public void setMP(int m) { this.MP = m; }

    public int getMaxMP() { return maxMP; }
    public void setMaxMP(int maxMP) { this.maxMP = maxMP; }

    public int getEP() { return this.EP;}
    public void setEP(int e) { this.EP = e; }


    //Other methods

    public void attack() {
        this.target.setHP(this.target.getHP() - getAttDmg());
        this.EP -= 2;
        this.target.refreshStatus();
    }

    public void magic(int abilityNo) {
        Ability a = this.abilities.get(abilityNo);
        if (a instanceof ActiveAbility) {
            this.getTarget();
            ((ActiveAbility) a).cast(this.target);
            this.target.refreshStatus();
        }
        else System.out.println("The selected ability is passive.");
    }

    public void item() {
        //Code to be written
    }

    public void addXP(int quantity, Ability target) {
        //Code to be written
    }

    @Override
    public void refreshStatus() {
        super.refreshStatus();
        if (this.getEP() < 0) this.setEP(0);
        if (this.getMP() < 0) this.setMP(0);
    }

    @Override
    public void getTarget() {
        //Code to be written
        //Requires player input to be completed
    }

    @Override
    public void update() {
        this.HP += this.HPRefill * this.maxHP / 100;
        this.MP += this.MPRefill * this.maxMP / 100;
        this.EP = this.EPmax;
    }

}
