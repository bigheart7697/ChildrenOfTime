import abilities.*;
import itemMGMT.*;
import battleMechanics.*;
import player.op.Player;
import units.*;

import java.util.ArrayList;
import java.util.Scanner;

public class gameUI {

    private static void printEachTurnsInformation(Battlefield battlefield) {
        for (Hero h: battlefield.getHeroes()) {
            System.out.println(h.getName());
            System.out.println("Health: " + h.getHP() + "/" + h.getMaxHP());
            System.out.println("Magic: " + h.getMP() + "/" + h.getMaxMP());
            System.out.println("Energy Points: " + h.getEP());
            System.out.println("Attack Power: " + h.getAttDmg());
            boolean hasAbility = false;
            for (Ability a : h.getAbilities()) {
                if(a.getLevel() > 0)
                    hasAbility = true;
            }
            if (hasAbility) {
                System.out.println("Can cast:");
                for (ActiveAbility aa: h.getActAbs())
                    if (aa.getLevel() > 0)
                        System.out.println(aa.getName() + " for " + aa.getEPCost() + " energy points, " +
                                aa.getMagicCost() + " magic points and a " + aa.getCD() + " turn cooldown");
                for (Ability a : h.getAbilities()) {
                    if (a instanceof PassiveAbility && a.getLevel() > 0) {
                        System.out.println(a.getName());
                    }
                }
            }
            if (h.getItems().size() > 0) {
                System.out.println("Can use:");
                for (Item i: h.getItems())
                    System.out.println(i.getName());
            }
            System.out.println();
        }
        for (Unit enemy : battlefield.getEnemies()) {
            System.out.println(enemy.getName());
            System.out.println("Health: " + enemy.getHP() + "/" + enemy.getMaxHP());
        }
    }

    private static void printAbilityInformation(Battlefield battlefield, Player player) {
        for (Hero hero : battlefield.getHeroes()) {
            System.out.println(hero.getName());
            System.out.println("Health: " + hero.getHP() + "/" + hero.getMaxHP());
            System.out.println("Magic: " + hero.getMP() + "/" + hero.getMaxMP());
            System.out.println("Energy Points: " + hero.getEP());
            System.out.println("Attack Power: " + hero.getAttDmg());
            for (Ability ability : hero.getAbilities()) {
                System.out.print(ability.getName());
                if (ability.getLevel() == 0)
                    System.out.println(" : not acquired");
                else
                    System.out.println(" " + ability.getLevel());
            }
            System.out.println();
        }
        System.out.println("Your current experience is: " + player.getXP());
        System.out.println();
        System.out.println();
    }

    private static boolean shop(ArrayList<Item> availItems, Player player) {
        String playerCommand = "";
        boolean InvalidCommandSpecifier;
        Scanner sc = new Scanner(System.in);
        System.out.print("This shop offers you: ");
        for (Item i: availItems) {
            if (availItems.indexOf(i) == availItems.size() - 1) System.out.print(i.getName() + " for " + i.getCost() + " dollars." );
            else System.out.print(i.getName() + " for " + i.getCost() + " dollars, " );

        }
        System.out.println();


        for (Hero h: player.getHeroes()) {
            System.out.print(h.getName() + " has ");
            for (Item i: h.getItems()) {
                if (h.getItems().indexOf(i) == h.getItems().size() - 1) System.out.print(i.getName() + " worth " + (i.getCost()/2) + " dollars.");
                System.out.print(i.getName() + " worth " + (i.getCost()/2) + " dollars, ");
            }
            System.out.println();
        }

        System.out.println("Your current wealth is: " + player.getGold() + "\n");

        playerCommand = sc.nextLine();
        while (!playerCommand.equalsIgnoreCase("done")) {
            InvalidCommandSpecifier = true;
            if (playerCommand.equalsIgnoreCase("Again")) {
                InvalidCommandSpecifier = false;
                System.out.print("This shop offers you: ");
                for (Item i: availItems) {
                    if (availItems.indexOf(i) == availItems.size() - 1) System.out.print(i.getName() + " for " + i.getCost() + " dollars." );
                    else System.out.print(i.getName() + " for " + i.getCost() + " dollars, " );

                }
                System.out.println();


                for (Hero h: player.getHeroes()) {
                    System.out.print(h.getName() + " has ");
                    for (Item i: h.getItems()) {
                        if (h.getItems().indexOf(i) == h.getItems().size() - 1) System.out.print(i.getName() + " worth " + (i.getCost()/2) + " dollars.");
                        System.out.print(i.getName() + " worth " + (i.getCost()/2) + " dollars, ");
                    }
                    System.out.println();
                }

                System.out.println("Your current wealth is: " + player.getGold() + "\n");
            }
            for (Item i : availItems)
                if (playerCommand.equalsIgnoreCase(i.getName() + "?")) {
                    i.displayInfo();
                    InvalidCommandSpecifier = false;
                }

            for (Hero h: player.getHeroes())
                for (Item i: availItems) {
                    if (playerCommand.equalsIgnoreCase("Buy " + i.getName() + " for " + h.getName())) {
                        InvalidCommandSpecifier = false;
                        if (player.getGold() < i.getCost()) System.out.println("You don't have enough money.");
                        else {
                            player.setGold(player.getGold() - i.getCost());
                            h.buyItem(i);
                            if (i instanceof ImmediateEffect) ((ImmediateEffect) i).increaseCost();
                            System.out.println(player.getGold());
                        }
                    }
                }

            for (Hero h: player.getHeroes())
                for (Item i: availItems)
                    if (playerCommand.equalsIgnoreCase("Sell " + i.getName() + " of " + h.getName())) {
                        player.setGold(player.getGold() + i.getCost());
                        h.sellItem(i);
                        System.out.println(player.getGold());
                        InvalidCommandSpecifier = false;
                    }

            if (playerCommand.equalsIgnoreCase("Help")) {
                System.out.println("(item name) + “?” \uF0E0 (item description)\n" +
                        "“Buy “ + (item name) + “ for “ + (hero name)\uF0E0\n" +
                        "\uF0E0 (item name) “ bought successfully, your current wealth is: ” + (current money)\n" +
                        "Or:\n" +
                        "“You don’t have enough money”\n" +
                        "Or:\n" +
                        "(hero name) +“’s inventory is full”\n" +
                        "“Sell “ + (item name) + “ of” + (hero name)\uF0E0\n" +
                        "(item name) + “ successfully sold, your current wealth is: “ + (current money)");
                InvalidCommandSpecifier = false;
            }

            if (InvalidCommandSpecifier && !playerCommand.equalsIgnoreCase("Done"))
                System.out.println("Invalid command");
            playerCommand = sc.nextLine();
        }

        return true;
    }

    private static boolean battle(Player player, Battlefield battlefield, String primitiveInformation, ArrayList<Item> availItems) {
        boolean InvalidCommandSpecifier;
        String playerCommand;
        Scanner scanner = new Scanner(System.in);

        //Prints the primitive information then get the primitive commands of each turn.

        System.out.println(primitiveInformation);
        playerCommand = scanner.nextLine();

        while (!playerCommand.equalsIgnoreCase("Done")) {
            InvalidCommandSpecifier = true;

            if (playerCommand.equalsIgnoreCase("Again")) {
                System.out.println(primitiveInformation);
                InvalidCommandSpecifier = false;
            }
            else if (playerCommand.equalsIgnoreCase("Help")) {
                System.out.println("(Enemy Name) + “?” \uF0E0 (Enemy description)\n");
                InvalidCommandSpecifier = false;
            }

            if (InvalidCommandSpecifier)
                InvalidCommandSpecifier = player.enemyInformation(playerCommand, battlefield);

            if (InvalidCommandSpecifier && !playerCommand.equalsIgnoreCase("Done")) {
                System.out.println("Invalid command\n");
            }
            playerCommand = scanner.nextLine();
        }

        //Prints the abilities information and then get the commands relating to acquiring and upgrading abilities.

        printAbilityInformation(battlefield, player);
        playerCommand = scanner.nextLine();

        while (!playerCommand.equalsIgnoreCase("Done")) {
            InvalidCommandSpecifier = true;
            if (playerCommand.equalsIgnoreCase("Again")) {
                InvalidCommandSpecifier = false;
                printAbilityInformation(battlefield, player);
            }

            if (playerCommand.equalsIgnoreCase("Help")) {
                InvalidCommandSpecifier = false;
                System.out.println("(hero name) + “ “ +(ability name) + “?”\uF0E0 (ability description) + “\\n“ + (this upgrade description) + “\\n” + “You need “ + (required xp) + “ experience points”\n" +
                        "“Acquire “ + (ability name) + “ for “ + (hero name)\uF0E0\n" +
                        "(ability name) (“acquired”/”upgraded”) + “ successfully, your current experience is: ” + (current xp)\n" +
                        "Or:\n" +
                        "“Your experience is insufficient”\n" +
                        "Or:\n" +
                        "“This ability cannot be upgraded anymore”\n" +
                        "Or:\n" +
                        "“Required abilities aren’t acquired”\n");
            }


            if (InvalidCommandSpecifier)
                InvalidCommandSpecifier = player.herosAbilityInformation(playerCommand, battlefield);

            if (InvalidCommandSpecifier)
                InvalidCommandSpecifier = player.useXP(playerCommand, battlefield);

            if (InvalidCommandSpecifier && !playerCommand.equalsIgnoreCase("Done")) {
                System.out.println("Invalid command\n");
            }
            playerCommand = scanner.nextLine();
        }

        //Shop Implemented below
        shop(availItems, player);


        //The main battle will start here. The first "while" is for each turn and the second one is for each command.
        System.out.println("The battle begins!\n");
        while (battlefield.getEnemies().size() != 0) {
            printEachTurnsInformation(battlefield);
            playerCommand = scanner.nextLine();
            while (!playerCommand.equalsIgnoreCase("Done")) {
                InvalidCommandSpecifier = true;
                if (playerCommand.equalsIgnoreCase("Again")) {
                    printEachTurnsInformation(battlefield);
                    InvalidCommandSpecifier = false;
                }

                else if (playerCommand.equalsIgnoreCase("Help")) {
                    System.out.println("(Item Name) + “?” \uF0E0 (Item description)\n");
                    System.out.println("(Ability Name) + “?” \uF0E0 (Ability description)\n");
                    System.out.println("(Hero Name) + “?” \uF0E0 (Hero description)\n");
                    System.out.println("(Enemy Name) + “?” \uF0E0 (Enemy description)\n");
                    System.out.println("(hero name) + “ cast “ + (ability name) + “ on “ + (hero name / enemy name and id)  (ability success message)\nOr:\n" +
                            "“You don’t have enough magic points”\n" +
                            "Or:\n" +
                            "“You don’t have enough energy points”\n" +
                            "Or:\n" +
                            "“Your desired ability is still in cooldown”\n" +
                            "Or:\n" +
                            "“You have not yet acquired this ability”");
                    System.out.println("(hero name) + “ use “ + (item name) + “ on “ + (hero name / enemy name and id) (item success message)\nOr:\n" +
                            "“You don’t have enough magic points”\n" +
                            "Or:\n" +
                            "“You don’t have enough energy points”\n" +
                            "Or:\n" +
                            "“Your desired item is still in cooldown”\n" +
                            "Or:\n" +
                            "“You don’t have this item”");
                    System.out.println("(hero name) + “ attack “ + (enemy name and id) \uF0E0 (hero name) + “ has successfully attacked “ + (enemy name and id) + “ with “ + (attack power) + “ power”\nOr:\n" +
                            "“You don’t have enough energy points”\n");
                    InvalidCommandSpecifier = false;
                }


                if (InvalidCommandSpecifier)
                    InvalidCommandSpecifier = player.heroInformation(playerCommand, battlefield);
                if (InvalidCommandSpecifier)
                    InvalidCommandSpecifier = player.enemyInformation(playerCommand, battlefield);
                if (InvalidCommandSpecifier)
                    InvalidCommandSpecifier = player.abilityInformation(playerCommand, battlefield);
                if (InvalidCommandSpecifier)
                    InvalidCommandSpecifier = player.itemInformation(playerCommand, battlefield);
                if (InvalidCommandSpecifier) {
                    InvalidCommandSpecifier = player.attack(playerCommand, battlefield);
                    battlefield.removeDeadUnit();
                    if (battlefield.getEnemies().size() == 0) {
                        System.out.println("Victory! You’ve defeated all of your enemies\n");
                        return true;
                    }
                }
                if (InvalidCommandSpecifier) {
                    InvalidCommandSpecifier = player.castAbility(playerCommand, battlefield);
                    battlefield.removeDeadUnit();
                    if (battlefield.getEnemies().size() == 0) {
                        System.out.println("Victory! You’ve defeated all of your enemies\n");
                        return true;
                    }
                }
                //If some changes needed for use Item do them bellow:
                if (InvalidCommandSpecifier)
                    InvalidCommandSpecifier = player.useItem(playerCommand, battlefield);

                if (InvalidCommandSpecifier && !playerCommand.equalsIgnoreCase("Done")) {
                    System.out.println("Invalid command\n");
                }

                playerCommand = scanner.nextLine();
            }

//            Enemies take their actions
            for (Enemy enemy : battlefield.getEnemies()) {
                enemy.setTarget();
                enemy.action();
                for (Hero hero : battlefield.getHeroes()) {
                    if (hero.getHP() <= 0) {
                        player.useIMPotion(hero);
                        if (hero.isDead()) {
                            System.out.println(hero.getName() + " is dead and so is the spirit of this adventure, Game Over!");
                            return false;
                        }
                        else {
                            System.out.println(hero.getName() + " is dying, immortality potion was used for reincarnation process, you now have "
                                    + player.getIMPotionRemaining() + "immortality potions left");
                            player.useIMPotion(hero);
                        }
                    }
                }
            }
            for (Hero hero : battlefield.getHeroes()) {
                for (ActiveAbility ability : hero.getActAbs()) {
                    ability.setRemainingCD(ability.getRemainingCD() - 1);
                    if (ability.getRemainingCD() < 0) {
                        ability.setRemainingCD(ability.getCD());
                    }
                }
            }
            battlefield.updateBattlefield();
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner playerInput = new Scanner(System.in);
        System.out.print("Enter your name: ");
        Player player = new Player(playerInput.nextLine(), 40, 15);

        System.out.println();

        Battlefield battlefield = new Battlefield();

        Hero Eley = new Fighter("Eley");
        Hero Chrome = new Fighter("Chrome");
        Hero Meryl = new Supporter("Meryl");
        Hero Bolti = new Supporter("Bolti");

//        Add description to the heroes:

        Eley.setDescription("Eley:\n" +
                "Class: Fighter\n" +
                "Ability 3: Overpowered attack\n" +
                "Attacks an enemy with N times power for 2 energy points and 50 magic points\n" +
                "Upgrade 1: N=1.2 for 2 xp points, needs Fight training upgrade 1\n" +
                "Upgrade 2: N=1.4 for 4 xp points, needs Fight training upgrade 2\n" +
                "Upgrade 3: N=1.6 for 6 xp points, needs Fight training upgrade 3\n" +
                "Ability 4: Swirling attack\n" +
                "While attacking, non-targeted enemies also take P percent of its damage\n" +
                "Upgrade 1: P=10 for 2 xp points, needs Work out upgrade 1\n" +
                "Upgrade 2: P=20 for 3 xp points\n" +
                "Upgrade 3: P=30 for 4 xp points\n");

        Chrome.setDescription("Chrome:\n" +
                "Class: Fighter\n" +
                "Ability 3: Sacrifice\n" +
                "Damages all the enemies with 3H power at the cost of H of his own \n health," +
                " needs 3 energy points, 60 magic points and has a 1 turn cooldown\n" +
                "Upgrade 1: H=40 for 2 xp points, needs Work out upgrade 1\n" +
                "Upgrade 2: H=50 for 3 xp points, needs Work out upgrade 2\n" +
                "Upgrade 3: H=60 for 4 xp points, needs Work out upgrade 3\n" +
                "Ability 4: Critical strike\n" +
                "Has a permanent P percent chance of doing an attack with double power (does not affect other abilities)\n" +
                "Upgrade 1: P=20 for 2 xp points, needs Fight training upgrade 1\n" +
                "Upgrade 2: P=30 for 3 xp points\n" +
                "Upgrade 3: P=40 for 4 xp points\n");

        Meryl.setDescription("Class: Supporter\n" +
                "Ability 3: Elixir\n" +
                "Refills H points of her own health or an ally’s, for 2 energy points and 60 magic points\n" +
                "Upgrade 1: H=100 for 2 xp points and takes 1 turn to cool down\n" +
                "Upgrade 2: H=150 for 3 xp points, takes 1 turn to cool down and needs Magic lessons upgrade 1\n" +
                "Upgrade 3: H=150 for 5 xp points, cools down instantly and needs Magic lessons upgrade 2\n" +
                "Ability 4: Caretaker\n" +
                "Gives 1 energy point to an ally for 30 magic points (this ep does not last until the end of \n" +
                "the battle and is only usable during the current turn)\n" +
                "Upgrade 1: takes 2 energy points and has a 1 turn cooldown for 2 xp points, needs Quick as a bunny upgrade 1\n" +
                "Upgrade 2: takes 2 energy points and cools down instantly for 3 xp points, needs Quick as a bunny upgrade 2\n" +
                "Upgrade 3 takes 1 energy point and cools down instantly for 5 xp points, needs Quick as a bunny upgrade 3\n");

        Bolti.setDescription("Bolti:\n" +
                "Class: Supporter\n" +
                "Ability 3: Boost\n" +
                "Gives X bonus attack power to himself or an ally, which lasts till the end of the battle, for \n" +
                "2 energy points and 50 magic points (this bonus attack power can stack up)\n" +
                "Upgrade 1: A=20 for 2 xp points and takes 1 turn to cool down\n" +
                "Upgrade 2: A=30 for 3 xp points and takes 1 turn to cool down\n" +
                "Upgrade 3: A=30 for 5 xp points and cools down instantly\n" +
                "Ability 4: Mana beam\n" +
                "Gives M magic points to himself or an ally for 1 energy point and 50 magic points\n" +
                "Upgrade 1: M=50 for 2 xp points and takes 1 turn to cool down, needs magic lessons upgrade 1\n" +
                "Upgrade 2: M=80 for 3 xp points and takes 1 turn to cool down, needs magic lessons upgrade 2\n" +
                "Upgrade 3: M=80 for 4 xp points and cools down instantly, needs magic lessons upgrade 3\n");


        //Add specific abilities for heroes here

        Attacker OverpoweredAttack = new Attacker("Overpowered attack", 0, 2, 46, 50, 222, 0, 246, 0, false ,Eley);
        OverpoweredAttack.setDescription("Overpowered attack\n" +
                "Attacks an enemy with N times power for 2 energy points and 50 magic points\n" +
                "Upgrade 1: N=1.2 for 2 xp points, needs Fight training upgrade 1\n" +
                "Upgrade 2: N=1.4 for 4 xp points, needs Fight training upgrade 2\n" +
                "Upgrade 3: N=1.6 for 6 xp points, needs Fight training upgrade 3\n" +
                "Success message: “Eley just did an overpowered attack on “ + (target) + “ with “ + (damage done) + “ damage”\n");
        Ability[] OverpoweredAttacksRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            OverpoweredAttacksRequiredAbility[cnt] = new SelfBoost("Fight training", cnt + 1, 0, 0, "", 0);
        OverpoweredAttack.setRequiredAbility(OverpoweredAttacksRequiredAbility);

        Attacker Sacrifice = new Attacker("Sacrifice", 0, 2, 34, 60, 333, 111, 0, 456, false, Chrome);
        Sacrifice.setDescription("Sacrifice\n" +
                "Damages all the enemies with 3H power at the cost of H of his own health, needs 3 energy points, 60 magic points and has a 1 turn cooldown\n" +
                "Upgrade 1: H=40 for 2 xp points, needs Work out upgrade 1\n" +
                "Upgrade 2: H=50 for 3 xp points, needs Work out upgrade 2\n" +
                "Upgrade 3: H=60 for 4 xp points, needs Work out upgrade 3\n" +
                "Success message: “Chrome just sacrificed himself to damage all his enemies with “ + (damage done) + “ power“\n");
        Ability[] SacrificesRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            SacrificesRequiredAbility[cnt] = new SelfBoost("Work out", cnt + 1, 0, 0, "", 0);
        Sacrifice.setRequiredAbility(SacrificesRequiredAbility);

        AttackModifier SwirlingAttack = new AttackModifier("Attack modifier", 0, 2, 34, 1, 123, 0, Eley, battlefield);
        SwirlingAttack.setDescription("Swirling attack\n" +
                "While attacking, non-targeted enemies also take P percent of its damage\n" +
                "Upgrade 1: P=10 for 2 xp points, needs Work out upgrade 1\n" +
                "Upgrade 2: P=20 for 3 xp points\n" +
                "Upgrade 3: P=30 for 4 xp points");
        Ability[] SwirlingAttacksRequiredAbility = new Ability[3];
        SwirlingAttacksRequiredAbility[0] = new SelfBoost("Work out", 1, 0, 0, "", 0);
        SwirlingAttacksRequiredAbility[1] = new SelfBoost("", 0, 0, 0, "", 0);
        SwirlingAttacksRequiredAbility[2] = new SelfBoost("", 0, 0, 0, "", 0);
        SwirlingAttack.setRequiredAbility(SwirlingAttacksRequiredAbility);

        AttackModifier CriticalStrike = new AttackModifier("Critical strike", 0, 2, 34, 2, 0, 234, Chrome, battlefield);
        CriticalStrike.setDescription("Critical strike\n" +
                "Has a permanent P percent chance of doing an attack with double power (does not affect other abilities)\n" +
                "Upgrade 1: P=20 for 2 xp points, needs Fight training upgrade 1\n" +
                "Upgrade 2: P=30 for 3 xp points\n" +
                "Upgrade 3: P=40 for 4 xp points\n");
        Ability[] CriticalStrikesRequiredAbility = new Ability[3];
        CriticalStrikesRequiredAbility[0] = new SelfBoost("Fight training", 1, 0, 0, "", 0);
        CriticalStrikesRequiredAbility[1] = new SelfBoost("", 0, 0, 0, "", 0);
        CriticalStrikesRequiredAbility[2] = new SelfBoost("", 0, 0, 0, "", 0);
        CriticalStrike.setRequiredAbility(CriticalStrikesRequiredAbility);

        Restorer Elixir = new Restorer("Elixir", 0, 2, 35, 60, 222, 110, "health point", 101515, Meryl);
        Eley.setDescription("Elixir\n" +
                "Refills H points of her own health or an ally’s, for 2 energy points and 60 magic points\n" +
                "Upgrade 1: H=100 for 2 xp points and takes 1 turn to cool down\n" +
                "Upgrade 2: H=150 for 3 xp points, takes 1 turn to cool down and needs Magic lessons upgrade 1\n" +
                "Upgrade 3: H=150 for 5 xp points, cools down instantly and needs Magic lessons upgrade 2\n" +
                "Success message: “Meryl just healed “ + (target) + “ with “ + (healing amount) + “ health points”\n");
        Ability[] ElixirsRequiredAbility = new Ability[3];
        ElixirsRequiredAbility[0] = new SelfBoost("", 0, 0, 0, "", 0);
        ElixirsRequiredAbility[1] = new SelfBoost("Magic lessons", 1, 0, 0, "", 0);
        ElixirsRequiredAbility[2] = new SelfBoost("Magic lessons", 1, 0, 0, "", 0);
        Elixir.setRequiredAbility(ElixirsRequiredAbility);

        Restorer Caretaker = new Restorer("Caretaker", 0, 2, 35, 30, 221, 100, "energy point", 111, Meryl);
        Caretaker.setDescription("Caretaker\n" +
                "Gives 1 energy point to an ally for 30 magic points (this ep does not last until the end of the battle and is only usable during the current turn)\n" +
                "Upgrade 1: takes 2 energy points and has a 1 turn cooldown for 2 xp points, needs Quick as a bunny upgrade 1\n" +
                "Upgrade 2: takes 2 energy points and cools down instantly for 3 xp points, needs Quick as a bunny upgrade 2\n" +
                "Upgrade 3 takes 1 energy point and cools down instantly for 5 xp points, needs Quick as a bunny upgrade 3\n" +
                "Success message: “Meryl just gave “ + (target) + “ 1 energy point”\n");
        Ability[] CaretakersRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            CaretakersRequiredAbility[cnt] = new SelfBoost("Quick as a bunny", cnt + 1, 0, 0, "", 0);
        Caretaker.setRequiredAbility(CaretakersRequiredAbility);

        Restorer Boost = new Restorer("Boost", 0, 2, 35, 50, 222, 110, "attack power", 233, Bolti);
        Boost.setDescription("Boost\n" +
                "Gives X bonus attack power to himself or an ally, which lasts till the end of the battle, for 2 energy points and 50 magic points (this bonus attack power can stack up)\n" +
                "Upgrade 1: A=20 for 2 xp points and takes 1 turn to cool down\n" +
                "Upgrade 2: A=30 for 3 xp points and takes 1 turn to cool down\n" +
                "Upgrade 3: A=30 for 5 xp points and cools down instantly\n" +
                "Success message: “Bolti just boosted “ + (target) + “ with “ + (A) + “ power”\n");
        Ability[] BoostsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            BoostsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
        Boost.setRequiredAbility(BoostsRequiredAbility);

        Restorer ManaBeam = new Restorer("Mana beam", 0, 2, 34, 50, 111, 110, "magic point", 588, Bolti);
        ManaBeam.setDescription("Mana beam\n" +
                "Gives M magic points to himself or an ally for 1 energy point and 50 magic points\n" +
                "Upgrade 1: M=50 for 2 xp points and takes 1 turn to cool down, needs magic lessons upgrade 1\n" +
                "Upgrade 2: M=80 for 3 xp points and takes 1 turn to cool down, needs magic lessons upgrade 2\n" +
                "Upgrade 3: M=80 for 4 xp points and cools down instantly, needs magic lessons upgrade 3\n" +
                "Success message: “Bolti just helped “ + (target) + “ with “ + (M) + “ magic points”\n");
        Ability[] ManaBeamsRequiredAbility = new Ability[3];
        for(int cnt = 0; cnt < 3; cnt++)
            ManaBeamsRequiredAbility[cnt] = new SelfBoost("Magic lessons", cnt + 1, 0, 0, "", 0);
        ManaBeam.setRequiredAbility(ManaBeamsRequiredAbility);

//        Adding abilities to the heroes:
        Eley.addAbility(OverpoweredAttack);
        Eley.addAbility(SwirlingAttack);
        Chrome.addAbility(Sacrifice);
        Chrome.addAbility(CriticalStrike);
        Meryl.addAbility(Elixir);
        Meryl.addAbility(Caretaker);
        Bolti.addAbility(Boost);
        Bolti.addAbility(ManaBeam);

        ArrayList<Unit> heroesArr = new ArrayList<>();
        heroesArr.add(Eley);
        heroesArr.add(Chrome);
        heroesArr.add(Meryl);
        heroesArr.add(Bolti);

        battlefield.addUnits(heroesArr);

        player.addHero(Eley);
        player.addHero(Chrome);
        player.addHero(Meryl);
        player.addHero(Bolti);

        //Shop and items added below
        ArrayList<Item> Shop = new ArrayList<>();

        //List of items created and added to shop here

        ImmediateEffect Toughen = new ImmediateEffect("Toughen", "HP", 20, "“Toughen”: +20 maximum health");
        ImmediateEffect Guide = new ImmediateEffect("Guide", "MP", 20, "“Guide”: +20 maximum magic");
        ImmediateEffect Defy = new ImmediateEffect("Defy", "att", 8, "“Defy”: +8 attack power");

        Equipment Sword = new Equipment("Sword", 25, 1, "att", 80, "“Sword”: +80 attack power, costs 25 dollars");
        Equipment EnergyBoots = new Equipment("Energy Boots", 20, 1, "EP", 1, "“Energy Boots”: +1 energy point, costs 20 dollars");
        Equipment Armor = new Equipment("Armor", 25, 1, "HP", 200, "“Armor”: +200 maximum health, costs 25 dollars");
        Equipment MagicStick = new Equipment("Magic Stick", 28, 1, "MP", 150, "“Magic stick”: +150 maximum magic, costs 28 dollars");

        Consumable HealthPotion = new Consumable("Health Potion", "HP", 100, "“Health potion”: +100 health points for the user or one of his/her allies, costs 15 dollars");
        Consumable MagicPotion = new Consumable("Magic Potion", "MP", 50, "“Magic potion”: +50 magic points for the user or one of his/her allies, costs 15 dollars");
        HealthPotion.setSuccessMessage1("just healed");
        HealthPotion.setSuccessMessage2("with 100 health points");
        MagicPotion.setSuccessMessage1("just offered");
        MagicPotion.setSuccessMessage2("50 magic points");

        Shop.add(Toughen);
        Shop.add(Guide);
        Shop.add(Defy);
        Shop.add(Sword);
        Shop.add(EnergyBoots);
        Shop.add(Armor);
        Shop.add(MagicStick);
        Shop.add(HealthPotion);
        Shop.add(MagicPotion);


        //Stage1: Hero and Class description

        System.out.println("Eley (Fighter) – Chrome (Fighter) – Meryl (Supporter) – Bolti (Supporter)\n");

        String inp = "";
        while (!inp.equalsIgnoreCase("Done")) {
            inp = playerInput.next();

            if (inp.equalsIgnoreCase("Again")) {
                System.out.println("Eley (Fighter) – Chrome (Fighter) – Meryl (Supporter) – Bolti (Supporter)\n");
            }

            else if (inp.equalsIgnoreCase("Help")) {
                System.out.println("(Class name) + “?” \uF0E0 (class description)\n" +
                        "(hero name) + “?” \uF0E0 (hero description)\n");
            }

            else if (inp.equalsIgnoreCase("Fighter?")) {
                System.out.println("All heroes have access to regular attack ability, " +
                        "which costs 2 energy points and damages an enemy with the amount of hero’s attack power.\n" +
                        "At the beginning of each turn, hero’s current health and current" +
                        " magic refills at the given rate.");
                System.out.println("Fighter class:\n" +
                        "Maximum health: 200\n" +
                        "Health refill rate: 10 percent of maximum health\n" +
                        "Maximum magic: 120\n" +
                        "Magic refill rate: 5 percent of maximum magic\n" +
                        "Attack power: 120\n" +
                        "Energy points: 6\n" +
                        "Inventory size: 2\n" +
                        "Ability 1: Fight training\n" +
                        "Permanently increases attack power\n" +
                        "Upgrade1: +30 attack power for 2 xp points\n" +
                        "Upgrade2: +30 attack power for 3 xp points\n" +
                        "Upgrade3: +30 attack power for 4 xp points\n" +
                        "Ability 2: Work out\n" +
                        "Permanently increases maximum health\n" +
                        "Upgrade 1: +50 maximum health for 2 xp points\n" +
                        "Upgrade 2: +50 maximum health for 3 xp points\n" +
                        "Upgrade 3: +50 maximum health for 4 xp points\n");
            }

            else if (inp.equalsIgnoreCase("Supporter?")) {
                System.out.println("All heroes have access to regular attack ability, " +
                        "which costs 2 energy points and damages an enemy with the amount of hero’s attack power.\n" +
                        "At the beginning of each turn, hero’s current health and current" +
                        " magic refills at the given rate.");
                System.out.println("Supporter class:\n" +
                        "Maximum health: 220\n" +
                        "Health refill rate: 5 percent of maximum health\n" +
                        "Maximum magic: 200\n" +
                        "Magic refill rate: 10 percent of maximum magic\n" +
                        "Attack power: 80\n" +
                        "Energy points: 5\n" +
                        "Inventory size: 3\n" +
                        "Ability 1: Quick as a bunny\n" +
                        "Permanently increases energy points\n" +
                        "Upgrade1: +1 energy point for 2 xp points\n" +
                        "Upgrade2: +1 energy point for 3 xp points\n" +
                        "Upgrade3: +1 energy point for 4 xp points\n" +
                        "Ability 2: Magic lessons\n" +
                        "Permanently increases maximum magic\n" +
                        "Upgrade 1: +50 maximum magic for 2 xp points\n" +
                        "Upgrade 2: +50 maximum magic for 3 xp points\n" +
                        "Upgrade 3: +50 maximum magic for 4 xp points\n");
            }

            else if (inp.equalsIgnoreCase("Eley?")) {
                System.out.println(Eley.getDescription());
            }

            else if (inp.equalsIgnoreCase("Chrome?")) {
                System.out.println(Chrome.getDescription());
            }

            else if (inp.equalsIgnoreCase("Meryl?")) {
                System.out.println(Meryl.getDescription());
            }

            else if (inp.equalsIgnoreCase("Bolti?")) {
                System.out.println(Bolti.getDescription());
            }

            else if (!inp.equalsIgnoreCase("Done"))
                System.out.println("Invalid command\n");

        }

        boolean winPreviousLevel;

//        ******
//        battle1
//        ******
        String primitiveInformation = "\nYou’ve entered the castle, it takes a while for your eyes to get used to the\ndarkness" +
                " but the horrifying halo of your enemies is vaguely visible. Angel’s\nunsettling" +
                " presence and the growling of thugs tell you that your first battle\nhas BEGUN!\n\n" + "\nYou've encountered 3 weak thug(s), 1 weak angel(s)\n";

        ArrayList<Unit> Enemies = new ArrayList<>();
        Enemies.add(new Thug(0,1));
        Enemies.add(new Thug(0,2));
        Enemies.add(new Thug(0,3));
        Enemies.add(new Angel(0));
        for (Unit enemy : Enemies)
            enemy.setField(battlefield);
        battlefield.addUnits(Enemies);

        winPreviousLevel = battle(player, battlefield, primitiveInformation, Shop);


//        ******
//        battle2
//        ******
        if (winPreviousLevel) {
            player.setGold(player.getGold() + 50);
            player.setXP(player.getXP() + 20);
            primitiveInformation = "As you wander into the hall you realize the surrounding doors can lead your destiny to\n"
                    + "something far worse than you expected. You know what’s anticipating you behind the only\n" +
                    "open door but there’s no other choice.\n\n" + "\nYou've encountered 2 able thugs, 1 weak angel, 1 weak tank\n";


            Enemies.clear();
            Enemies.add(new Thug(1, 1));
            Enemies.add(new Thug(1, 2));
            Enemies.add(new Angel(0));
            Enemies.add(new Tank(0));
            for (Unit enemy : Enemies)
                enemy.setField(battlefield);
            battlefield.addUnits(Enemies);

            winPreviousLevel = battle(player, battlefield, primitiveInformation, Shop);
        }

//        ******
//        battle3
//        ******
        if (winPreviousLevel) {
            player.setGold(player.getGold() + 60);
            player.setXP(player.getXP() + 25);
            primitiveInformation = "The door behind you is shut with a thunderous sound and you progress into the next hall" +
                    "holding the first key that you’ve found, hoping to seek the second one.\n\n" +
                    "\nYou've encountered two 1 able thug, 1 mighty thug, 1 able angel, 1 weak tank\n";


            Enemies.clear();
            Enemies.add(new Thug(1));
            Enemies.add(new Thug(2));
            Enemies.add(new Angel(0));
            Enemies.add(new Tank(0));
            for (Unit enemy : Enemies)
                enemy.setField(battlefield);
            battlefield.addUnits(Enemies);

            winPreviousLevel = battle(player, battlefield, primitiveInformation, Shop);
        }

//        ******
//        battle4
//        ******
        player.setGold(player.getGold() + 70);
        player.setXP(player.getXP() + 30);
        if (winPreviousLevel) {
            primitiveInformation = "Running with the second key in your hand, you unlock the door back to the first hall and"
                    + "use the first key to burst into your most terrifying nightmares.\n\n" +
                    "\nYou've encountered 2 mighty thugs, 1 able angel, 2 able tanks\n";


            Enemies.clear();
            Enemies.add(new Thug(2, 1));
            Enemies.add(new Thug(2, 2));
            Enemies.add(new Angel(1));
            Enemies.add(new Tank(1, 1));
            Enemies.add(new Tank(1, 2));
            for (Unit enemy : Enemies)
                enemy.setField(battlefield);
            battlefield.addUnits(Enemies);

            winPreviousLevel = battle(player, battlefield, primitiveInformation, Shop);
        }

//        ******
//        battle5
//        ******
        if (winPreviousLevel) {
            player.setGold(player.getGold() + 80);
            player.setXP(player.getXP() + 35);
            primitiveInformation = "You feel hopeless and exhausted as you stalk to the final door. What’s behind that door" +
                    "makes your hearts pound and your spines shake with fear, but you came here to do one" +
                    "thing and backing down is not an option.\n\n" +
                    "\nYou've encountered The collector\n";


            Enemies.clear();
            Enemies.add(new FinalBoss());
            for (Unit enemy : Enemies)
                enemy.setField(battlefield);
            battlefield.addUnits(Enemies);

            winPreviousLevel = battle(player, battlefield, primitiveInformation, Shop);
        }

//        ******
//        The final story
//        ******

        if (winPreviousLevel) {
            System.out.println("The collector falls down on his knees, he’s strained and desperate but still tries to" +
                    "drag himself toward Epoch. He knows his era has come to an end. The ancient time machine" +
                    "calls you to end the disorder and bring unity under its glorious wings, now it’s your" +
                    "turn to be the MASTERS OF TIME!");
        }
    }
}
