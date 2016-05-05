package abilities;

abstract public class PassiveAbility extends Ability{

    PassiveAbility(String name, int level, int XPtoNextLevel, int XPGainPattern) {
        super(name, level, XPtoNextLevel, XPGainPattern);
    }
}
