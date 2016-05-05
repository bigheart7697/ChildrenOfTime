package units;

public class Thug extends Enemy {

    Thug(int version, String n, int h, int dmg) {
        super(n, h, dmg);
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
        else System.out.println("Error: wrong version input in thug constructor"); //for test purposes
    }

    @Override
    public void getTarget() {
        //Code to be written
    }

    @Override
    public void action() {
        this.target.setHP(this.target.getHP() - getAttDmg());
        this.target.checkLifeStatus();
    }

}
