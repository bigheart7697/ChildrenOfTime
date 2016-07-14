package units;

import java.awt.*;

public class Angel extends Enemy {

    //Constructors

    public Angel(int version, int id, Image i) {
        super("Angel" + id, 0,0);
        this.id = id;
        if (version == 0) {
            setName("Weak angel " + id);
            setMaxHP(150);
            setHP(150);
            setAttDmg(100);
            this.version = version;
        }
        if (version == 1) {
            setName("Able angel " + id);
            setMaxHP(250);
            setHP(250);
            setAttDmg(150);
            this.version = version;
        }
        setDescription("Angel:\n" +
                "Heals one of her allies in each turn\n" +
                "Weak version: Healing Amount=100, Maximum health=150\n" +
                "Able version: Healing Amount =150, Maximum health=250\n" +
                "Action message: “Angel just healed “ + (target) + “ with “ + (healing amount) + “ health points”\n");
        setImage(i);
    }

    public Angel(int version, Image i) {
        super("Angel", 0,0);
        this.id = 0;
        if (version == 0) {
            setName("Weak angel");
            setMaxHP(150);
            setHP(150);
            setAttDmg(100);
            this.version = version;
        }
        if (version == 1) {
            setName("Able angel");
            setMaxHP(250);
            setHP(250);
            setAttDmg(150);
            this.version = version;
        }
        setDescription("Angel:\n" +
                "Heals one of her allies in each turn\n" +
                "Weak version: Healing Amount=100, Maximum health=150\n" +
                "Able version: Healing Amount =150, Maximum health=250\n" +
                "Action message: “Angel just healed “ + (target) + “ with “ + (healing amount) + “ health points”\n");
        setImage(i);
    }

    @Override
    public String action() {
        this.target.setHP(this.target.getHP() + getAttDmg());
        if (this.target.getHP() > this.target.getMaxHP()) this.target.setHP(this.target.getMaxHP());
        return ("Angel just healed " + target.getName() + " with " + getAttDmg() + " health points");
    }

    @Override
    public void setTarget() {
        int MAX_VAL = 2000;
        for (Enemy e: this.field.getEnemies()) {
            if (e.getHP() < MAX_VAL) {
                this.target = e;
                MAX_VAL = e.getHP();
            }
        }
    }
}
