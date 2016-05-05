package units;

import abilities.PassiveAbility;

public class Fighter extends Hero{

    //Constructors

    Fighter(String n) {
        super(n, 200, 120, 120, 10, 5, 6, 2);
        PassiveAbility FightTraining = new PassiveAbility(); // PassiveAbility constructor to be changed.
        PassiveAbility WorkOut = new PassiveAbility();
        this.abilities.add(FightTraining);
        this.abilities.add(WorkOut);
    }

    Fighter() {
        System.out.println("Error: inputs missing in Fighter constructor");
    }



    //Other methods

}
