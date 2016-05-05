package units;

import abilities.PassiveAbility;
import abilities.Restorer;

public class Fighter extends Hero{

    //Constructor

    Fighter(String n) {
        super(n, 200, 120, 120, 10, 5, 6, 2);
        PassiveAbility FightTraining = new PassiveAbility(); // PassiveAbility constructor to be changed.
        PassiveAbility WorkOut = new Restorer("Work out", 0, 2, 34, "health point", 50);
        this.abilities.add(FightTraining);
        this.abilities.add(WorkOut);
    }

    //Other methods

}
