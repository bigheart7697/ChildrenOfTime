package battleMechanics;

import units.Hero;
import units.Unit;
import units.Enemy;

import java.util.ArrayList;

public class Battlefield {
    private int turn;
    private ArrayList<Unit> units;

    //Constructor

    public Battlefield() {
        this.turn = 1;
        this.units = new ArrayList<>();
    }


    //Getters and Setters

    public int getTurn() { return turn; }

    //Setting up ArrayList

    public void addUnits(ArrayList<Unit> U) {
        this.units.addAll(U);
        for (Unit u: units) {
            u.setField(this);
        }
    }


    //Other methods

    public ArrayList<Hero> getHeroes() {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (Unit u: this.units) {
            if (u instanceof Hero) heroes.add((Hero) u);
        }
        return heroes;
    }

    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Unit u: this.units) {
            if (u instanceof Enemy) enemies.add((Enemy) u);
        }
        return enemies;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean removeDeadUnit() {
        boolean res = false;
        ArrayList<Unit> deadUnits = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getHP() <= 0) {
                System.out.println(unit.getName() + " has died");
                deadUnits.add(unit);
                res = true;
            }
        }
        units.removeAll(deadUnits);
        return res;
    }

    public void updateBattlefield() {
        this.turn ++;
        for (Unit u: this.units) {
            u.update();
        }
    }
}
