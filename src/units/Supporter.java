package units;

import abilities.PassiveAbility;
import abilities.Restorer;

public class Supporter extends Hero {

    //Constructor

    Supporter(String n) {
        super(n, 220, 80, 200, 5, 10, 5, 3);
        PassiveAbility QuickAsABunny = new PassiveAbility("Qick as a bunny", 0, 2, 34); // PassiveAbility constructor to be changed.
        PassiveAbility MagicLessons = new Restorer("Magic lessons", 0, 2, 34, "magic point", 50);
        this.abilities.add(QuickAsABunny);
        this.abilities.add(MagicLessons);
    }

    //Other methods

}
