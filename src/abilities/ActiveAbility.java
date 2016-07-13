package abilities;

abstract public class ActiveAbility extends Ability{

    protected int magicCost;
    protected int EPCostPattern;
    protected int EPCost;
    protected int CDPattern;
    protected int CD;
    private int remainingCD;

    //Constructor

    public ActiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCostPattern, int CDPattern) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.magicCost = magicCost;
        this.EPCostPattern = EPCostPattern;
        this.CDPattern = CDPattern;
    }

    //Getters and Setters

    @Override
    public int getEPCost() { return this.EPCost; }

    @Override
    public int getMagicCost() { return this.magicCost; }

    public int getCD() { return this.CD; }

    @Override
    public int getRemainingCD() {
        return remainingCD;
    }

    @Override
    public void setRemainingCD(int remainingCD) {
        this.remainingCD = remainingCD;
    }

    //other methods:

    @Override
    public void upgrade() {
        if (currentXP > XPtoNextLevel) {
            level++;
            currentXP -= XPtoNextLevel;
            XPtoNextLevel = XPGainPattern / 10;
            CD = CDPattern / (int)Math.pow(10.0, 3 - level) % 10;
            remainingCD = 0;
            XPGainPattern = (XPGainPattern % 10) * 10;
            EPCost = EPCostPattern / (int)Math.pow(10.0, (double)(3 - level)) % 10;
        }
        else {
            System.out.println("You don't have enough experience!");
        }
    }
}
