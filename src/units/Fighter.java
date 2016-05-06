package units;

import abilities.PassiveAbility;
import abilities.SelfBoost;

public class Fighter extends Hero{

    //Constructor

    public Fighter(String n) {
        super(n, 200, 120, 120, 10, 5, 6, 2);
        PassiveAbility FightTraining = new SelfBoost("Fight training", 0, 2, 34, "attack power", 30);
        PassiveAbility WorkOut = new SelfBoost("Work out", 0, 2, 34, "health point", 50);
        this.abilities.add(FightTraining);
        this.abilities.add(WorkOut);
    }

    //Other methods

}
