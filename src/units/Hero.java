package units;

import abilities.*;
import itemMGMT.*;

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
        this.MP = mmp;
        this.HPRefill = hpr;
        this.MPRefill = mpr;
        this.EPmax = epm;
        this.EP = epm;
        this.invSize = invs;
        this.XPGained = 0;
        this.initializeAbilities();
        this.initializeInventory();
    }

    //Setting up ArrayLists

    private void initializeInventory() { this.inventory = new ArrayList<>(); }

    private void initializeAbilities() { this.abilities = new ArrayList<>(); }
    public void addAbility(Ability a) { this.abilities.add(a); }


    //Getters and Setters

    public int getMP() { return this.MP; }
    public void setMP(int m) { this.MP = m; }

    public int getMaxEP() { return this.EPmax; }
    private void setMaxEP(int e) { this.EPmax = e; }

    public int getMaxMP() { return maxMP; }
    public void setMaxMP(int maxMP) { this.maxMP = maxMP; }

    public int getEP() { return this.EP;}
    public void setEP(int e) { this.EP = e; }

    public ArrayList<ActiveAbility> getActAbs() {
        ArrayList<ActiveAbility> aa = new ArrayList<>();
        for (Ability a: this.abilities)
            if (a instanceof ActiveAbility) aa.add((ActiveAbility) a);
        return aa;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public ArrayList<Item> getItems() { return this.inventory; }


    //Other methods

    public void attack() {
        this.target.setHP(this.target.getHP() - getAttDmg());
        this.EP -= 2;
        this.target.refreshStatus();
    }

    public void magic(int abilityNo) {
        Ability a = this.abilities.get(abilityNo);
        if (a instanceof ActiveAbility) {
            this.setTarget();
            a.cast();
            this.target.refreshStatus();
        }
        else System.out.println("The selected ability is passive.");
    }

    public void buyItem(Item i) {
        if (i.getInvSpaceNeeded() + this.inventory.size() > this.invSize)
            System.out.println(this.getName() + "'s inventory is full.");

        else {
            if (!(i instanceof ImmediateEffect)) this.inventory.add(i);
            this.itemAcquired(i);
            System.out.print(i.getName() + " bought successfully, your current wealth is: "); //the wealth is then printed in gameUI :D

        }
    }

    public void sellItem(Item i) {
        this.inventory.remove(i);
        this.itemRemoved(i);
        System.out.println(i.getName() + " successfully sold, your current wealth is: "); // the wealth is printed in gameUI
    }

    private void itemAcquired(Item i) {
        if (i instanceof ImmediateEffect || i instanceof Equipment) {
            switch (i.getTargetStat()) {
                case "HP":
                    this.setMaxHP(this.getMaxHP() + i.getEffect());
                    break;
                case "MP":
                    this.setMaxMP(this.getMaxMP() + i.getEffect());
                    break;
                case "EP":
                    this.setMaxEP(this.getMaxEP() + i.getEffect());
                    break;
                case "att":
                    this.setAttDmg(this.getAttDmg() + i.getEffect());
                    break;
            }
        }
    }

    private void itemRemoved(Item i) {
        if (i instanceof Equipment) {
            switch (i.getTargetStat()) {
                case "HP":
                    this.setMaxHP(this.getMaxHP() - i.getEffect());
                    break;
                case "MP":
                    this.setMaxMP(this.getMaxMP() - i.getEffect());
                    break;
                case "EP":
                    this.setMaxEP(this.getMaxEP() - i.getEffect());
                    break;
                case "att":
                    this.setAttDmg(this.getAttDmg() - i.getEffect());
                    break;
            }
        }
    }

    public void useItem(Item i) {
        if (!inventory.contains(i)) System.out.println("This hero doesn't have the desired item.");
        else if (i instanceof Consumable) {
            ((Consumable) i).isUsed();
            switch (i.getTargetStat()) {
                case "HP":
                    ((Consumable) i).getTarget().setHP(((Consumable) i).getTarget().getHP() + i.getEffect());
                    refreshStatus();
                    break;
                case "MP":
                    if (((Consumable) i).getTarget() instanceof Hero){
                        Hero h = (Hero) ((Consumable) i).getTarget();
                        h.setMP(h.getMP() + i.getEffect());
                    }
                    refreshStatus();
                    break;
                case "EP":
                    if (((Consumable) i).getTarget() instanceof Hero) {
                        Hero h = (Hero) ((Consumable) i).getTarget();
                        h.setEP(h.getEP() + i.getEffect());
                    }
                    refreshStatus();
                    break;
            }
            if (((Consumable) i).isFinished()) this.inventory.remove(i);
        }
        else System.out.println("Selected Item is not Consumable");
    }

    @Override
    public void refreshStatus() {
        super.refreshStatus();
        if (this.getEP() < 0) this.setEP(0);
        if (this.getEP() > this.getMaxEP()) this.setEP(this.getMaxEP());
        if (this.getMP() < 0) this.setMP(0);
        if (this.getMP() > this.getMaxMP()) this.setMP(this.getMaxMP());
    }

    @Override
    public void setTarget() {
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
