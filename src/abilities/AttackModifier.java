package abilities;

import units.*;
import battleMechanics.Battlefield;

import java.util.Random;

public class AttackModifier extends PassiveAbility {
    protected int critRate;
    protected int splashPattern;
    protected int chancePattern;
    protected Hero user;
    protected Enemy target;
    protected Battlefield battlefield;

//    constructors:

    public AttackModifier(String name, int level, int XPtoNextLevel, int XPGainPattern, int critRate, int critChance, int splashDmg, int splashPattern, int chancePattern, Enemy target, Hero user, Battlefield battlefield) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.critRate = critRate;
        this.splashPattern = splashPattern;
        this.chancePattern = chancePattern;
        this.target = target;
        this.user = user;
        this.battlefield = battlefield;
    }

//  other methods:

    @Override
    public void cast() {
        if (splashPattern != 0) {
            target.setHP(target.getHP() - user.getAttDmg());
            for(Enemy tmp : battlefield.getEnemies()) {
                if (tmp.equals(target) || level == 0)
                    continue;
                target.setHP(target.getHP() - user.getAttDmg() / 10 * (((splashPattern) / (int) Math.pow(10.0,(double)(level - 1))) % 10));
            }
        }
        if(chancePattern != 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            if(randomNumber < (chancePattern) / 10 * ((int) Math.pow(10.0,(double)(level - 1)) % 10)) {
                target.setHP(target.getHP() - user.getAttDmg() * critRate);
            }
            else {
                target.setHP(target.getHP() - user.getAttDmg());
            }
        }
    }
}
