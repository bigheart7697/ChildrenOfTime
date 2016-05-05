package abilities;

import units.Unit;

abstract public class ActiveAbility extends Ability{

    protected int magicCost;
    protected int EPCost;
    protected int CD;


    ActiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCost, int CD) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.magicCost = magicCost;
        this.EPCost = EPCost;
        this.CD = CD;
    }

    abstract public void cast(Unit target);
}
