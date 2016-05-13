package player.op;

import abilities.Ability;
import abilities.ActiveAbility;
import abilities.PassiveAbility;
import battleMechanics.Battlefield;
import itemMGMT.Item;
import units.*;

import java.util.ArrayList;

public class Player {
    private String name;
    private int XP;
    private int gold;
    private int IMPotion;
    private ArrayList<Hero> heroes;


    //Constructor

    public Player(String n, int startingGold, int initXP) {
        this.name = n;
        this.XP = initXP;
        this.gold = startingGold;
        this.IMPotion = 3;
        this.heroes = new ArrayList<>();
    }


    //Getters and Setters

    public String getName() { return name; }

    public int getXP() { return XP; }
    public void setXP(int XP) { this.XP = XP; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getIMPotionRemaining() { return IMPotion; }
    public ArrayList<Hero> getHeroes() { return this.heroes; }

    // Other methods

    public void addHero(Hero h) { this.heroes.add(h); }

    public void useIMPotion(Hero h) {
        if (IMPotion > 0) h.revived();
        IMPotion--;
    }

    public void move() {
        //Not used in this stage. Will be implemented in graphical phase.
    }

    public void buy() {
        //Code to be written. requires shop to be implemented.
    }

    public boolean useXP(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                if (playerCommand.equalsIgnoreCase("Acquire " + (ability.getName()) + " for " + (hero.getName()))) {
                    ability.setXP(this.getXP());
                    if (this.getXP() < ability.getXPtoNextLevel()) {
                        System.out.println("Your experience is insufficient");
                        System.out.println();
                    }
                    else if (ability.getLevel() == 3) {
                        System.out.println("This ability cannot be upgraded anymore");
                        System.out.println();
                    }
                    else if (!ability.hasRequiredAbility(hero)) {
                        System.out.println("Required abilities aren’t acquired");
                        System.out.println();
                    } else {
                        ability.upgrade();
                        this.setXP(ability.getCurrentXP());
                        System.out.println((ability.getName()) + "acquired/upgraded successfully, your current experience is: " + (this.getXP()));
                        System.out.println();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public void fight() {
        //Not used in this stage. Will be implemented in graphical phase.
    }

    public boolean heroInformation(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            if (playerCommand.equalsIgnoreCase(hero.getName() + "?")) {
                if (hero instanceof Supporter) {
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

                if (hero instanceof Fighter) {
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
                    System.out.println();
                System.out.println(hero.getDescription());
                System.out.println("Health: " + hero.getHP() + "/" + hero.getMaxHP());
                System.out.println("Magic: " + hero.getMP() + "/" + hero.getMaxMP());
                System.out.println("Energy Points: " + hero.getEP());
                System.out.println("Attack Power: " + hero.getAttDmg());
                boolean hasAbility = false;
                for (Ability a : hero.getAbilities()) {
                    if(a.getLevel() > 0)
                        hasAbility = true;
                }
                if (hasAbility) {
                    System.out.println("Can cast:");
                    for (ActiveAbility aa: hero.getActAbs())
                        System.out.println(aa.getName() + " for " + aa.getEPCost() + " energy points, " +
                                aa.getMagicCost() + " magic points and a " + aa.getCD() + " turn cooldown");
                    for (Ability a : hero.getAbilities()) {
                        if (a instanceof PassiveAbility) {
                            System.out.println(a.getName());
                        }
                    }
                }
                if (hero.getItems().size() > 0) {
                    System.out.println("Can use:");
                    for (Item i: hero.getItems())
                        System.out.println(i.getName());
                }
                System.out.println();
                return false;
            }
        }
        return true;
    }

    public boolean itemInformation(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Item item : hero.getItems()) {
                if (playerCommand.equalsIgnoreCase(item.getName() + "?")) {
                    System.out.println(item.getDescription());
                    return false;
                }
            }
        }
        return true;
    }

    public boolean abilityInformation(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                if (playerCommand.equalsIgnoreCase(ability.getName() + "?")) {
                    System.out.println(ability.getDescription());
                    return false;
                }
            }
        }
        return true;
    }

    public boolean enemyInformation(String playerCommand, Battlefield battlefield) {
        for (Enemy enemy : battlefield.getEnemies()) {
            if (playerCommand.equals(enemy.getName() + "?")) {
                System.out.println(enemy.getDescription());
                System.out.println("Health: " + enemy.getHP() + "/" + enemy.getMaxHP());
                System.out.println();
                return false;
            }
        }
        return true;
    }

    public boolean herosAbilityInformation(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                if (playerCommand.equalsIgnoreCase((hero.getName() + " " + ability.getName() + "?"))) {
                    int tmp = ability.getLevel() + 1;
                    System.out.println(ability.getDescription() + "\n" +
                            "]f you want to upgrade it for " + hero.getName() + " to level " + tmp + "\n" + " You need " + ability.getXPtoNextLevel() + " experience points");
                    System.out.println();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean castAbility(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                for (Unit unit : battlefield.getUnits()) {
                    if (playerCommand.equalsIgnoreCase(hero.getName() + " cast " + ability.getName() + " on " + unit.getName())) {
                        if (ability.getMagicCost() > hero.getMP()) {
                            System.out.println("You don't have enough magic points");
                            System.out.println();
                        }
                        else if (ability.getEPCost() > hero.getMP()) {
                            System.out.println("You don't have enough energy points");
                            System.out.println();
                        }
                        else if (ability.getRemainingCD() > 0) {
                            System.out.println("Your desired ability is still in cooldown");
                            System.out.println();
                        }
                        else if (ability.getLevel() == 0) {
                            System.out.println("You have not yet acquired this ability");
                            System.out.println();
                        }
                        else {
                            ability.setTarget(unit);
                            ability.cast();
                            System.out.println(hero.getName() + " was casted " + ability.getName() + " on " + unit.getName() + " successfully!");
                            System.out.println();
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean attack(String playerCommand, Battlefield battlefield) {
        for (Hero hero : battlefield.getHeroes()) {
            for (Enemy enemy : battlefield.getEnemies()) {
                if (playerCommand.equalsIgnoreCase(hero.getName() + " attack " + enemy.getName())) {
                    if (2 > hero.getMP()) {
                        System.out.println("You don't have enough energy points");
                    } else {
                        hero.setEP(hero.getEP() - 2);
                        enemy.setHP(enemy.getHP() - hero.getAttDmg());
                        System.out.println(hero.getName() + " has successfully attacked " + enemy.getName() + " with " + hero.getAttDmg() + "power");
                    }
                    System.out.println();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean useItem(String playerCommand, Battlefield battlefield) {
//        Code to be implemented;
        return true;
    }

}
