package abilities;

import units.Enemy;
import units.Hero;
import units.Unit;

public class Attacker extends ActiveAbility {
    protected int multiplier, multiplierPattern;
    protected int HPCost, HPCostPattern;
    protected boolean global;
    protected Hero user;
    protected Enemy target;

//    constructor:

    public Attacker(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCost, int CDPattern, int multiplierPattern, int HPCostPattern, boolean global, Hero user) {
        super(name, level, XPtoNextLevel, XPGainPattern, magicCost, EPCost, CDPattern);
        this.multiplierPattern = multiplierPattern;
        this.HPCostPattern = HPCostPattern;
        this.global = global;
        this.user = user;
    }

    public Attacker(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCost, int CDPattern, int multiplierPattern, int HPCostPattern, boolean global) {
        super(name, level, XPtoNextLevel, XPGainPattern, magicCost, EPCost, CDPattern);
        this.multiplierPattern = multiplierPattern;
        this.HPCostPattern = HPCostPattern;
        this.global = global;
    }

//    getters and setters:

    @Override
    public void setTarget(Unit target) {
        this.target = (Enemy)target;
    }

//    other methods:


    @Override
    public void cast() {
        EPCost = EPCostPattern / (int)Math.pow(10.0, (double)(3 - level)) % 10;
        user.setEP(user.getEP() - EPCost);
        user.setMP(user.getMP() - magicCost);
        if(multiplierPattern > 0) {
            multiplier = multiplierPattern / (int)Math.pow(10.0, (double)(3 - level)) % 10;
            target.setHP(target.getHP() - user.getAttDmg() - user.getAttDmg() * multiplier / 10);
        }
        if(HPCostPattern > 0) {
            HPCost = (HPCostPattern / (int)Math.pow(10.0, (double)(3 - level)) % 10) * 10;
            target.setHP(target.getHP() - 3 * HPCost);
            user.setHP(user.getHP() - HPCost);
        }
    }

    public void setUser(Hero user) {
        this.user = user;
    }
}
