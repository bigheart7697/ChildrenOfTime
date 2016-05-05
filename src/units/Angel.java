package units;

public class Angel extends Enemy {
    Angel(int version, String n, int h, int dmg) {
        super(n, h, dmg);
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
        else System.out.println("Error: wrong version input in angel constructor"); //for test purposes
    }

    @Override
    public void action() {
        this.target.setHP(this.target.getHP() + getAttDmg());
        if (this.target.getHP() > this.target.getMaxHP()) this.target.setHP(this.target.getMaxHP());
    }

    @Override
    public void getTarget() {
        int MAX_VAL = 2000;
        for (Enemy e: this.field.getEnemies()) {
            if (e.getHP() < MAX_VAL) {
                this.target = e;
                MAX_VAL = e.getHP();
            }
        }
    }
}
