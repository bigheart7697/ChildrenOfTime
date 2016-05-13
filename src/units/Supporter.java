package units;

import abilities.Ability;
import abilities.PassiveAbility;
import abilities.SelfBoost;

public class Supporter extends Hero {

    //Constructor

    public Supporter(String n) {
        super(n, 220, 80, 200, 5, 10, 5, 3);
        PassiveAbility QuickAsABunny = new SelfBoost("Quick as a bunny", 0, 2, 34, "energy point", 1);
        QuickAsABunny.setDescription("Quick as a bunny\n" +
                "Permanently increases energy points\n" +
                "Upgrade1: +1 energy point for 2 xp points\n" +
                "Upgrade2: +1 energy point for 3 xp points\n" +
                "Upgrade3: +1 energy point for 4 xp points\n");
        Ability[] QuickAsABunnysRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            QuickAsABunnysRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        QuickAsABunny.setRequiredAbility(QuickAsABunnysRequiredAbility);

        PassiveAbility MagicLessons = new SelfBoost("Magic lessons", 0, 2, 34, "magic point", 50);
        MagicLessons.setDescription("Magic lessons\n" +
                "Permanently increases maximum magic\n" +
                "Upgrade 1: +50 maximum magic for 2 xp points\n" +
                "Upgrade 2: +50 maximum magic for 3 xp points\n" +
                "Upgrade 3: +50 maximum magic for 4 xp points\n");
        Ability[] MagicLessonsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            MagicLessonsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        MagicLessons.setRequiredAbility(MagicLessonsRequiredAbility);
        this.abilities.add(QuickAsABunny);
        this.abilities.add(MagicLessons);
    }

    //Other methods

}
