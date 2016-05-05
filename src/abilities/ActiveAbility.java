package abilities;

import units.Unit;

public abstract class ActiveAbility extends Ability{


    ActiveAbility(String name, int level, int XPtoNextLevel, int XPGainPatten, Ability requiredAbility) {
        super(name, level, XPtoNextLevel, XPGainPatten, requiredAbility);
    }

    abstract public void cast(Unit target);
}
