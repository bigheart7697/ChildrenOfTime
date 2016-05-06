import abilities.*;
import itemMGMT.*;
import battleMechanics.*;
import player.op.Player;
import units.*;

import java.util.ArrayList;
import java.util.Scanner;

public class gameUI {

    public static void main(String[] args) {
        Scanner playerInput = new Scanner(System.in);
        System.out.print("Enter your name: ");
        Player player = new Player(playerInput.next(), 40, 15);

        System.out.println();

        Battlefield battlefield = new Battlefield();

        Hero Eley = new Fighter("Eley");
        Hero Chrome = new Fighter("Chrome");
        Hero Meryl = new Supporter("Meryl");
        Hero Bolti = new Supporter("Bolti");


        //Add specific abilities for heroes here

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



        //Stage1: Hero and Class description

        System.out.println("Eley (Fighter) – Chrome (Fighter) – Meryl (Supporter) – Bolti (Supporter)\n");

        String inp = "";
        while (!inp.contentEquals("Done")) {
            inp = playerInput.next();

            if (inp.contentEquals("Again")) {
                System.out.println("Eley (Fighter) – Chrome (Fighter) – Meryl (Supporter) – Bolti (Supporter)\n");
            }

            else if (inp.contentEquals("Help")) {
                System.out.println("(Class name) + “?” \uF0E0 (class description)\n" +
                        "(hero name) + “?” \uF0E0 (hero description)\n");
            }

            else if (inp.contentEquals("Fighter?")) {
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

            else if (inp.contentEquals("Supporter?")) {
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

            else if (inp.contentEquals("Eley?")) {
                System.out.println("Eley:\n" +
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
            }

            else if (inp.contentEquals("Chrome?")) {
                System.out.println("Chrome:\n" +
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
            }

            else if (inp.contentEquals("Meryl?")) {
                System.out.println("Class: Supporter\n" +
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
            }

            else if (inp.contentEquals("Bolti?")) {
                System.out.println("Bolti:\n" +
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
            }

            else if (!inp.contentEquals("Done"))
                System.out.println("Invalid command\n");

        }


        //Stage2: Entering the castle, first battle

        System.out.println("\nYou’ve entered the castle, it takes a while for your eyes to get used to the\ndarkness" +
                " but the horrifying halo of your enemies is vaguely visible. Angel’s\nunsettling" +
                " presence and the growling of thugs tell you that your first battle\nhas BEGUN!\n");

        System.out.println("\nYou've encountered 3 weak thug(s), 1 weak angel(s)\n");

        ArrayList<Unit> Enemies = new ArrayList<>();
        Enemies.add(new Thug(0,1));
        Enemies.add(new Thug(0,2));
        Enemies.add(new Thug(0,3));
        Enemies.add(new Angel(0));
        battlefield.addUnits(Enemies);

        inp = "";
        while (!inp.contentEquals("Done")) {
            inp = playerInput.next();

            if (inp.contentEquals("Again")) {
                System.out.println("You've encountered 3 weak thug(s), 1 weak angel(s)\n");
            }

            else if (inp.contentEquals("Help")) {
                System.out.println("(Enemy Name) + “?” \uF0E0 (Enemy description)\n");
            }

            else if (inp.contentEquals("thug?")) {
                System.out.println("Thug:\n" +
                        "Attacks one of your heroes in each turn\n" +
                        "Weak version: Attack Power=50, Maximum health=200\n" +
                        "Able version: Attack Power=90, Maximum health=300\n" +
                        "Mighty version: Attack Power=150, Maximum health=400\n");
            }

            else if (inp.contentEquals("angel?")) {
                System.out.println("Angel:\n" +
                        "Heals one of her allies in each turn\n" +
                        "Weak version: Healing Amount=100, Maximum health=150\n" +
                        "Able version: Healing Amount =150, Maximum health=250\n");
            }

            else if (!inp.contentEquals("Done")) System.out.println("Invalid command\n");
        }


        System.out.println("The battle begins!\n");

        for (Hero h: battlefield.getHeroes()) {
            System.out.println(h.getName());
            System.out.println("Health: " + h.getHP() + "/" + h.getMaxHP());
            System.out.println("Magic: " + h.getMP() + "/" + h.getMaxMP());
            System.out.println("Energy Points: " + h.getEP());
            System.out.println("Attack Power: " + h.getAttDmg());
            if (h.getActAbs().size() > 0) {
                System.out.println("Can cast:");
                for (ActiveAbility aa: h.getActAbs())
                    System.out.println(aa.getName() + " for " + aa.getEPCost() + " energy points, " +
                            aa.getMagicCost() + " magic points and a " + aa.getCD() + " turn cooldown");
            }
            if (h.getItems().size() > 0) {
                System.out.println("Can use:");
                for (Item i: h.getItems())
                    System.out.println(i.getName());
            }
            System.out.println();
        }
    }
}
