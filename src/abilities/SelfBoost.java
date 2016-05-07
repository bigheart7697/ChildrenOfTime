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
    public void cast() {
        if((whichStat == "magic point") && (XPGainPattern != 0)){
            getTarget().setMaxMP(getTarget().getMaxMP() + amount);
        }

        else if((whichStat == "health point") && XPGainPattern != 0) {
            getTarget().setMaxHP(getTarget().getMaxHP() + amount);
        }

        else if((whichStat == "attack power") && XPGainPattern != 0) {
            getTarget().setAttDmg(getTarget().getAttDmg() + amount);
        }

        else if((whichStat == "energy point") && XPGainPattern != 0) {
            getTarget().setEP(getTarget().getEP() + amount);
        }

    }



}
