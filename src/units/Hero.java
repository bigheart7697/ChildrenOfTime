package units;

import abilities.*;
import itemMGMT.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract public class Hero extends Unit{
    protected int HPRefill;
    protected int maxMP;
    protected int MP, MPRefill;
    protected int EPMax, EP;
    protected Image heroImage;

    protected ArrayList<Item> inventory;
    protected HashMap<Consumable, Integer> usages;
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
        this.EPMax = epm;
        this.EP = epm;
        this.invSize = invs;
        this.XPGained = 0;
        this.initializeAbilities();
        this.initializeInventory();
        this.usages = new HashMap<>();
    }
    //Setting up ArrayLists

    private void initializeInventory() { this.inventory = new ArrayList<>(); }

    private void initializeAbilities() { this.abilities = new ArrayList<>(); }
    public void addAbility(Ability a) { this.abilities.add(a); }


    //Getters and Setters

    public int getMP() { return this.MP; }
    public void setMP(int m) { this.MP = m; }

    public int getMaxEP() { return this.EPMax; }
    public void setMaxEP(int e) { this.EPMax = e; }

    public int getMaxMP() { return maxMP; }
    public void setMaxMP(int maxMP) { this.maxMP = maxMP; }

    public int getEP() { return this.EP;}
    public void setEP(int e) { this.EP = e; }

    public Image getHeroImage() { return heroImage; }
    public void setHeroImage(Image i) { heroImage = i; }

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

//    public void attack() {
//        this.target.setHP(this.target.getHP() - getAttDmg());
//        this.EP -= 2;
//        this.target.refreshStatus();
//    }
//
//    public void magic(int abilityNo) {
//        Ability a = this.abilities.get(abilityNo);
//        if (a instanceof ActiveAbility) {
//            this.setTarget();
//            a.cast();
//            this.target.refreshStatus();
//        }
//        else System.out.println("The selected ability is passive.");
//    }

    public boolean buyItem(Item i) {
        if (i.getInvSpaceNeeded() + this.inventory.size() > this.invSize) {
            System.out.println(this.getName() + "'s inventory is full.");
            return false;
        }

        else {
            if (!(i instanceof ImmediateEffect)) {
                this.inventory.add(i);
                i.addOwner(this);
                if (i instanceof Consumable) {
                    usages.put((Consumable) i, ((Consumable) i).getUsageMax());
                }
            }
            this.itemAcquired(i);
            System.out.println(i.getName() + " bought successfully");
            return true;
        }
    }

    public void sellItem(Item i) {
        this.inventory.remove(i);
        i.removeOwner(this);
        if (i instanceof Consumable) {
            usages.remove(i);
        }
        this.itemRemoved(i);
        System.out.println(i.getName() + " successfully sold");
    }

    private void itemAcquired(Item i) {
        if (i instanceof ImmediateEffect || i instanceof Equipment) {
            switch (i.getTargetStat()) {
                case "HP":
                    this.setMaxHP(this.getMaxHP() + i.getEffect());
                    this.setHP(this.getMaxHP());
                    break;
                case "MP":
                    this.setMaxMP(this.getMaxMP() + i.getEffect());
                    this.setMP(this.getMaxMP());
                    break;
                case "EP":
                    this.setMaxEP(this.getMaxEP() + i.getEffect());
                    this.setEP(this.getMaxEP());
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

    public int getRemainingUsages(Consumable i) {
        return usages.get(i);
    }

    public void useItem(Item i) {
        if (!inventory.contains(i)) System.out.println("This hero doesn't have the desired item.");
        else if (i instanceof Consumable) {
            usages.put((Consumable) i, usages.get(i) - 1);
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
            if (usages.get(i) == 0) {
                this.inventory.remove(i);
                usages.remove(i);
                i.removeOwner(this);
                System.out.println(i.getName() + " is finished.");
            }
        }
        else System.out.println("Selected Item is not Consumable");
    }

    public void renew() {
        this.setHP(this.getMaxHP());
        this.setMP(this.getMaxMP());
        this.setEP(this.getMaxEP());
        for (Ability a : getAbilities()) {
            a.setRemainingCD(0);
        }
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
        this.EP = this.EPMax;
        refreshStatus();
    }

}
