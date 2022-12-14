package units;

import java.awt.*;

public class Tank extends Enemy {

    //Constructors

    public Tank(int version, Image i) {
        super("Tank", 0, 0);
        if (version == 0) {
            setName("Weak tank");
            setMaxHP(400);
            setHP(400);
            setAttDmg(30);
            this.version = version;
        }
        if (version == 1) {
            setName("Able tank");
            setMaxHP(500);
            setHP(500);
            setAttDmg(90);
            this.version = version;
        }
        setDescription("Tank:\n" +
                "Attacks every one of your heroes in each turn\n" +
                "Weak version: Attack Power=30, Maximum health=400\n" +
                "Able version: Attack Power=90, Maximum health=500\n" +
                "Action message: “Tank just damaged all of your heroes with “ + (attack power) + “ power”\n");
        setImage(i);
    }

    public Tank(int version, int id, Image i) {
        super("Tank" + id, 0, 0);
        this.id = id;
        if (version == 0) {
            setName("Weak tank " + id);
            setMaxHP(400);
            setHP(400);
            setAttDmg(30);
            this.version = version;
        }
        if (version == 1) {
            setName("Able tank " + id);
            setMaxHP(500);
            setHP(500);
            setAttDmg(90);
            this.version = version;
        }
        setDescription("Tank:\n" +
                "Attacks every one of your heroes in each turn\n" +
                "Weak version: Attack Power=30, Maximum health=400\n" +
                "Able version: Attack Power=90, Maximum health=500\n" +
                "Action message: “Tank just damaged all of your heroes with “ + (attack power) + “ power”\n");
        setImage(i);
    }

    @Override
    public String action() {
        for (Hero h: this.field.getHeroes()) {
            if (!h.isDead) {
                h.setHP(h.getHP() - getAttDmg());
                h.refreshStatus();
            }
        }
        return ("Tank just damaged all of your heroes with " + getAttDmg() + " power");
    }

    @Override
    public void setTarget() {
        this.target = null; // Targets are all of the heroes, attacked in method action.
    }
}
