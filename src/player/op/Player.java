package player.op;

import abilities.Ability;
import battleMechanics.Battlefield;
import itemMGMT.Item;
import units.Enemy;
import units.Hero;
import units.Unit;

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

    // Other methods

    public void addHero(Hero h) { heroes.add(h); }

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
        //Code to be written. requires UI to be implemented.
        for (Hero hero : battlefield.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                if (playerCommand.equalsIgnoreCase("Acquire " + (ability.getName()) + " for " + (hero.getName()))) {
                    ability.setXP(this.getXP());
                    if (this.getXP() < ability.getXPtoNextLevel())
                        System.out.println("Your experience is insufficient");
                    else if (ability.getLevel() == 3)
                        System.out.println("This ability cannot be upgraded anymore");
                    else if (!ability.hasRequiredAbility(hero)) {
                        System.out.println("Required abilities aren’t acquired");
                    } else {
                        ability.upgrade();
                        this.setXP(ability.getCurrentXP());
                        System.out.println((ability.getName()) + "acquired/upgraded successfully, your current experience is: " + (this.getXP()));
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
                System.out.println(hero.getDescription());
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
                        if (ability.getMagicCost() > hero.getMP())
                            System.out.println("You don't have enough magic points");
                        else if (ability.getEPCost() > hero.getMP()) {
                            System.out.println("You don't have enough energy points");
                        }
                        else if (ability.getRemainingCD() > 0) {
                            System.out.println("Your desired ability is still in cooldown");
                        }
                        else if (ability.getLevel() == 0) {
                            System.out.println("You have not yet acquired this ability");
                        }
                        else {
                            ability.setTarget(unit);
                            ability.cast();
                            System.out.println(hero.getName() + " was casted " + ability.getName() + " on " + unit.getName() + " successfully!");
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
                        enemy.setHP(enemy.getHP() - hero.getAttDmg());
                        System.out.println(hero.getName() + " has successfully attacked " + enemy.getName() + " with " + hero.getAttDmg() + "power");
                    }
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
