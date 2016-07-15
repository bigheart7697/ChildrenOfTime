package GraphicalUserInterface.CustomGame.Tiles;

import units.Enemy;

import java.util.ArrayList;

/**
 * Created by rezab on 11/07/2016.
 */
public class BattleTile extends Tiles {

    private ArrayList<String> enemies = new ArrayList<>();
    private int moneyPrize, experiencePrize;

    public BattleTile(int moneyPrize, int experiencePrize) {

        this.moneyPrize = moneyPrize;
        this.experiencePrize = experiencePrize;
    }

    public int getExperiencePrize() {
        return experiencePrize;
    }

    public void setExperiencePrize(int experiencePrize) {
        this.experiencePrize = experiencePrize;
    }

    public int getMoneyPrize() {
        return moneyPrize;
    }

    public void setMoneyPrize(int moneyPrize) {
        this.moneyPrize = moneyPrize;
    }

    public void addEnemy(String enemy) {
        enemies.add(enemy);
    }

    public ArrayList<String> getEnemies() {
        return enemies;
    }
}
