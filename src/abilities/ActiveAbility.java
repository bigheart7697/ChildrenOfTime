package abilities;

abstract public class ActiveAbility extends Ability{

    protected int magicCost;
    protected int EPCost;
    protected int CDPattern;
    protected int CD;


    public ActiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCost, int CDPattern) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.magicCost = magicCost;
        this.EPCost = EPCost;
        this.CDPattern = CDPattern;
    }

}
