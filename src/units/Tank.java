package units;

public class Tank extends Enemy {

    //Constructors

    public Tank(int version) {
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
    }

    public Tank(int version, int id) {
        super("Tank" + id, 0, 0);
        this.id = id;
        if (version == 0) {
            setName("Weak tank" + id);
            setMaxHP(400);
            setHP(400);
            setAttDmg(30);
            this.version = version;
        }
        if (version == 1) {
            setName("Able tank" + id);
            setMaxHP(500);
            setHP(500);
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
