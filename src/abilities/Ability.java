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

    public String getName() { return this.name; }


//    other methods:

    public abstract void cast();

    public void Upgrade() {
        if (currentXP > XPtoNextLevel) {
            level++;
            XPtoNextLevel = XPGainPattern / 10;
            XPGainPattern = (XPGainPattern % 10) * 10;
        }
        else {
            System.out.println("You don't have enough experience!");
        }
    }

}
