package abilities;

abstract public class ActiveAbility extends Ability{

    protected int magicCost;
    protected int EPCostPattern;
    protected int EPCost;
    protected int CDPattern;
    protected int CD;

    //Constructor

    public ActiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCostPattern, int CDPattern) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.magicCost = magicCost;
        this.EPCostPattern = EPCostPattern;
        this.CDPattern = CDPattern;
    }

    //Getters and Setters

    public int getEPCost() { return this.EPCost; }
    public int getMagicCost() { return this.magicCost; }
    public int getCD() { return this.CD; }

}
