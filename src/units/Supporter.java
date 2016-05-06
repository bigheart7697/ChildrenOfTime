package units;

import abilities.PassiveAbility;
import abilities.SelfBoost;

public class Supporter extends Hero {

    //Constructor

    public Supporter(String n) {
        super(n, 220, 80, 200, 5, 10, 5, 3);
        PassiveAbility QuickAsABunny = new SelfBoost("Quick as a bunny", 0, 2, 34, "energy point", 1);
        PassiveAbility MagicLessons = new SelfBoost("Magic lessons", 0, 2, 34, "magic point", 50);
        this.abilities.add(QuickAsABunny);
        this.abilities.add(MagicLessons);
    }

    //Other methods

}
