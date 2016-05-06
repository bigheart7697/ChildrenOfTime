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
        this.turn = 0;
        this.units = new ArrayList<>();
    }


    //Getters and Setters

    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }


    //Setting up ArrayList

    public void addUnits(Unit u) { this.units.add(u); }
    public void addUnits(ArrayList<Unit> U) { this.units.addAll(U);}


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

    public void updateBattlefield() {
        this.turn ++;
        for (Unit u: this.units) {
            u.update();
        }
    }
}
