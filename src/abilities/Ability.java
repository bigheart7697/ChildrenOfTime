package abilities;

import units.Unit;

abstract public class Ability {
    protected String name;
    protected int currentXP;
    protected int level;
    protected int XPtoNextLevel;
    protected int XPGainPatten;
    protected Ability requiredAbility;
    protected Unit target;

//    constructors:

    Ability(String name, int level, int XPtoNextLevel, int XPGainPatten, Ability requiredAbility) {
        this.name = name;
        this.level = level;
        this.XPtoNextLevel = XPtoNextLevel;
        this.XPGainPatten = XPGainPatten;
        this.requiredAbility = requiredAbility;
    }

//    getters and setters:

    public void getXP(int quantity) {
        currentXP = quantity;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

//    other methods:

    public abstract void cast();

}
