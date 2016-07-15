package abilities;

import units.*;

public class SelfBoost extends PassiveAbility {
    String whichStat;
    int amount;
    protected Hero target;

//    constructors:

    public SelfBoost(String name, int level, int XPtoNextLevel, int XPGainPattern, String whichStat, int amount) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.whichStat = whichStat;
        this.amount = amount;
    }

//    getters and setters:

    @Override
    public void setTarget(Unit target) {
        this.target = (Hero)target;
    }

    public Hero getTarget(){
        return target;
    }


//    other methods:

    @Override
    public boolean cast() {
        if((whichStat == "magic point") && (level <= 3)){
            getUser().setMaxMP( getUser().getMaxMP() + amount);
            getUser().setMP(getUser().getMaxMP());
        }

        else if((whichStat == "health point") && level <= 3) {
            getUser().setMaxHP( getUser().getMaxHP() + amount);
            getUser().setHP(getUser().getMaxHP());
        }

        else if((whichStat == "attack power") && level <= 3) {
            getUser().setAttDmg( getUser().getAttDmg() + amount);
        }

        else if((whichStat == "energy point") && level <= 3) {
            getUser().setMaxEP(getUser().getMaxEP() + amount);
            getUser().setEP( getUser().getMaxEP());
        }
        return false;
    }

    @Override
    public void upgrade() {
        if (currentXP > XPtoNextLevel) {
            level++;
            currentXP -= XPtoNextLevel;
            XPtoNextLevel = XPGainPattern / 10;
            XPGainPattern = (XPGainPattern % 10) * 10;
            cast();
        }
    }


}
