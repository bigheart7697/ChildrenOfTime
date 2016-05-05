package abilities;

import units.Unit;

abstract public class Ability {
    protected String name;
    protected int currentXP;
    protected int level;
    protected int XPtoNextLevel;
    protected int XPGainPattern;
    protected Ability requiredAbility;

//    constructors:

    Ability(String name, int level, int XPtoNextLevel, int XPGainPattern) {
        this.name = name;
        this.level = level;
        this.XPtoNextLevel = XPtoNextLevel;
        this.XPGainPattern = XPGainPattern;
    }

//    getters and setters:

    public void setXP(int quantity) {
        currentXP = quantity;
    }


    public int getCurrentXP(){
        return  currentXP;
    }


//    other methods:

    public abstract void cast();

}
