package abilities;

import units.*;
import battleMechanics.Battlefield;

import java.util.Random;

public class AttackModifier extends PassiveAbility {
    protected int critRate;
    protected int splashPattern;
    protected int chancePattern;
    protected int critChance;
    protected int splashDmg;
    protected Hero user;
    protected Enemy target;
    protected Battlefield battlefield;

//    constructors:

    public AttackModifier(String name, int level, int XPtoNextLevel, int XPGainPattern, int critRate, int splashPattern, int chancePattern, Hero user, Battlefield battlefield) {
        super(name, level, XPtoNextLevel, XPGainPattern);
        this.critRate = critRate;
        this.splashPattern = splashPattern;
        this.chancePattern = chancePattern;
        this.user = user;
        this.battlefield = battlefield;
    }

//    getters and setters:

    @Override
    public void setTarget(Unit target) {
        this.target = (Enemy)target;
    }

//  other methods:

    @Override
    public void cast() {
        if (splashPattern != 0) {
            target.setHP(target.getHP() - user.getAttDmg());
            for(Enemy tmp : battlefield.getEnemies()) {
                if (tmp.equals(target) || level == 0)
                    continue;
                splashDmg = (((splashPattern) / (int) Math.pow(10.0,(double)(3 - level))) % 10);
                tmp.setHP(tmp.getHP() - user.getAttDmg() * splashDmg / 10);
            }
        }
        if(chancePattern != 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            critChance = ((int) Math.pow(10.0,(double)(3 - level)) % 10) * 10;
            if(randomNumber < (chancePattern) * critChance / 100) {
                target.setHP(target.getHP() - user.getAttDmg() * critRate);
            }
            else {
                target.setHP(target.getHP() - user.getAttDmg());
            }
        }
    }
}
