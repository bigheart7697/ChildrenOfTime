package abilities;

import units.*;

public class Restorer extends PassiveAbility {
    String whichStat;
    int amount;
    protected Hero target;

//    constructors:

    public Restorer(String name, int level, int XPtoNextLevel, int XPGainPatten, String whichStat, int amount) {
        super(name, level, XPtoNextLevel, XPGainPatten);
        this.whichStat = whichStat;
        this.amount = amount;
    }

//    getters and setters:

    public void setTarget(Hero target) {
        this.target = target;
    }

    public Hero getTarget(){
        return target;
    }

//    other methods:

    @Override
    public void cast() {
        if((whichStat == "magic point") && (XPGainPatten != 0)){
            getTarget().setMaxMP(getTarget().getMaxMP() + amount);
        }

        else if((whichStat == "health point") && XPGainPatten != 0) {
            getTarget().setMaxHP(getTarget().getMaxHP() + amount);
        }
        XPtoNextLevel = XPGainPatten / 10;
        XPGainPatten = (XPGainPatten % 10) * 10;

    }

}
