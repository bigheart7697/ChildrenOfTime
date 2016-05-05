package abilities;

public abstract class PassiveAbility extends Ability{

    PassiveAbility(String name, int level, int XPtoNextLevel, int XPGainPatten, Ability requiredAbility) {
        super(name, level, XPtoNextLevel, XPGainPatten, requiredAbility);
    }
}
