package units;

import abilities.Ability;
import abilities.PassiveAbility;
import abilities.SelfBoost;

public class Fighter extends Hero{

    //Constructor

    public Fighter(String n) {
        super(n, 200, 120, 120, 10, 5, 6, 2);
        PassiveAbility FightTraining = new SelfBoost("Fight training", 0, 2, 34, "attack power", 30);
        Ability[] FightTrainingsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            FightTrainingsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        FightTraining.setRequiredAbility(FightTrainingsRequiredAbility);

        PassiveAbility WorkOut = new SelfBoost("Work out", 0, 2, 34, "health point", 50);
        Ability[] WorkOutsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            WorkOutsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        WorkOut.setRequiredAbility(WorkOutsRequiredAbility);

        this.abilities.add(FightTraining);
        this.abilities.add(WorkOut);
    }

    //Other methods

}
