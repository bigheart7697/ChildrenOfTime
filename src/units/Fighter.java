package units;

import abilities.Ability;
import abilities.PassiveAbility;
import abilities.SelfBoost;

public class Fighter extends Hero{

    //Constructor

    public Fighter(String n) {
        super(n, 200, 120, 120, 10, 5, 6, 2);
        PassiveAbility FightTraining = new SelfBoost("Fight training", 0, 2, 34, "attack power", 30);
        FightTraining.setDescription("Fight training " +
                "Permanently increases attack power " +
                "Upgrade1: +30 attack power for 2 xp points " +
                "Upgrade2: +30 attack power for 3 xp points " +
                "Upgrade3: +30 attack power for 4 xp points ");
        Ability[] FightTrainingsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            FightTrainingsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        FightTraining.setRequiredAbility(FightTrainingsRequiredAbility);

        PassiveAbility WorkOut = new SelfBoost("Work out", 0, 2, 34, "health point", 50);
        WorkOut.setDescription("Work out " +
                "Permanently increases maximum health " +
                "Upgrade 1: +50 maximum health for 2 xp points " +
                "Upgrade 2: +50 maximum health for 3 xp points " +
                "Upgrade 3: +50 maximum health for 4 xp points ");
        Ability[] WorkOutsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            WorkOutsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        WorkOut.setRequiredAbility(WorkOutsRequiredAbility);

        this.abilities.add(FightTraining);
        this.abilities.add(WorkOut);
    }

    //Other methods

}
