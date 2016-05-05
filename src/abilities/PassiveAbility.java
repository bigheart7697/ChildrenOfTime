package abilities;

abstract public class PassiveAbility extends Ability{

    PassiveAbility(String name, int level, int XPtoNextLevel, int XPGainPatten) {
        super(name, level, XPtoNextLevel, XPGainPatten);
    }
}
