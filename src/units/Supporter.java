package units;

import abilities.PassiveAbility;

public class Supporter extends Hero {

    //Constructors

    Supporter(String n) {
        super(n, 220, 80, 200, 5, 10, 5, 3);
        PassiveAbility QuickAsABunny = new PassiveAbility(); // PassiveAbility constructor to be changed.
        PassiveAbility MagicLessons = new PassiveAbility();
        this.abilities.add(QuickAsABunny);
        this.abilities.add(MagicLessons);
    }

    Supporter() {
        System.out.println("Error: inputs missing in Fighter constructor");
    }



    //Other methods

}
