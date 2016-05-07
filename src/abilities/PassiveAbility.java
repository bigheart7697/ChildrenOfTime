package abilities;

abstract public class PassiveAbility extends Ability{

    PassiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern) {
        super(name, level, XPtoNextLevel, XPGainPattern);
    }

    @Override
    public int getRemainingCD() {
        return 0;
    }

    @Override
    public void setRemainingCD(int remainingCD) {
    }

    @Override
    public int getMagicCost() { return 0; }

    @Override
    public int getEPCost() { return 0; }
}
