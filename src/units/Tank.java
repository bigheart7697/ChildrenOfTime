package units;

public class Tank extends Enemy {

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

    @Override
    public void action() {
        //Needs Battlefield to be implemented
    }

    @Override
    public void getTarget() {
        //Code to be written
    }
}
