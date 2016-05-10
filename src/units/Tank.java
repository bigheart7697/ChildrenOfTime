package units;

public class Tank extends Enemy {

    //Constructors

    Tank(int version, String n, int h, int dmg) {
        super(n, h, dmg);
        if (version == 0) {
            setMaxHP(400);
            setAttDmg(30);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(500);
            setAttDmg(90);
            this.version = version;
        }
    }

    Tank(int version, String n, int h, int dmg, int id) {
        super(n, h, dmg);
        this.id = id;
        if (version == 0) {
            setMaxHP(400);
            setAttDmg(30);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(500);
            setAttDmg(90);
            this.version = version;
        }
    }

    @Override
    public void action() {
        for (Hero h: this.field.getHeroes()) {
            if (!h.isDead) {
                h.setHP(h.getHP() - getAttDmg());
                h.refreshStatus();
            }
        }
        System.out.println("Tank just damaged all of your heroes with " + getAttDmg() + " power");
    }

    @Override
    public void setTarget() {
        this.target = null; // Targets are all of the heroes, attacked in method action.
    }
}
