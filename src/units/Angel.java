package units;

public class Angel extends Enemy {

    //Constructors

    public Angel(int version, int id) {
        super("Angel", 0,0);
        this.id = id;
        if (version == 0) {
            setMaxHP(150);
            setAttDmg(100);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(250);
            setAttDmg(150);
            this.version = version;
        }
    }

    public Angel(int version) {
        super("Angel", 0,0);
        this.id = 0;
        if (version == 0) {
            setMaxHP(150);
            setAttDmg(100);
            this.version = version;
        }
        if (version == 1) {
            setMaxHP(250);
            setAttDmg(150);
            this.version = version;
        }
    }

    @Override
    public void action() {
        this.target.setHP(this.target.getHP() + getAttDmg());
        if (this.target.getHP() > this.target.getMaxHP()) {
            this.target.setHP(this.target.getMaxHP());
            System.out.println("Angel just healed " + target.getName() + " with " + getAttDmg() + " health points");
        }
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
