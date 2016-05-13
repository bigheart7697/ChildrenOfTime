package abilities;

import units.Hero;
import units.Unit;

abstract public class Ability {
    protected String name;
    protected int currentXP;
    protected int level;
    protected int XPtoNextLevel;
    protected int XPGainPattern;
    protected Ability[] requiredAbility = new Ability[3];
    private String description;
    private Hero user;


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

    public int getXPtoNextLevel() {
        return XPtoNextLevel;
    }

    public void setRequiredAbility(Ability[] requiredAbility) {
        this.requiredAbility = requiredAbility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    abstract public void setTarget(Unit target);

    abstract public int getRemainingCD();

    abstract public int getEPCost();

    abstract public int getMagicCost();

    public int getLevel() {
        return level;
    }

    abstract public void setRemainingCD(int remainingCD);

    public Hero getUser() {
        return user;
    }

    public void setUser(Hero user) {
        this.user = user;
    }


//    other methods:

    public boolean hasRequiredAbility(Hero hero) {
        if(level == 0)
            return true;
        for (Ability ability : hero.getAbilities()) {
            if (requiredAbility[level - 1].getName().equals("") || ability.getName().equalsIgnoreCase(requiredAbility[level - 1].getName()) && requiredAbility[level - 1].getLevel() <= ability.getLevel())
                return true;
        }
        return false;
    }

    public abstract void cast();

    public void upgrade() {
        if (currentXP > XPtoNextLevel) {
            level++;
            currentXP -= XPtoNextLevel;
            XPtoNextLevel = XPGainPattern / 10;
            XPGainPattern = (XPGainPattern % 10) * 10;
        }
    }

}
