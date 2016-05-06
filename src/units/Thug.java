package units;

public class Thug extends Enemy {

    //Constructors

    public Thug(int version, int id) {
        super("Thug", 0, 0);
        this.id = id;
        if (version == 0) {
            setMaxHP(200);
            setAttDmg(50);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(300);
            setAttDmg(90);
            this.version = version;
        }
        if (version == 2) {
            setMaxHP(400);
            setAttDmg(150);
            this.version = version;
        }
    }

    public Thug(int version) {
        super("Thug", 0, 0);
        this.id = 0;
        if (version == 0) {
            setMaxHP(200);
            setAttDmg(50);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(300);
            setAttDmg(90);
            this.version = version;
        }
        if (version == 2) {
            setMaxHP(400);
            setAttDmg(150);
            this.version = version;
        }
    }

    @Override
    public void setTarget() {
        int mhp = 0; // To find the hero with max hp and attack it
        for (Hero h: this.field.getHeroes()) {
            if (h.getHP() > mhp) {
                target = h;
                mhp = h.getHP();
            }
        }
    }

    @Override
    public void action() {
        this.target.setHP(this.target.getHP() - getAttDmg());
        this.target.refreshStatus();
    }

}
