package units;

public class Thug extends Enemy {
    String actionMsg;

    Thug(int version) {
        this.version = version;
        if (version == 0) {
            this.maxHP = 200;
            this.attDmg = 50;
        }
        if (version == 1) {
            this.maxHP = 300;
            this.attDmg = 90;
        }
        if (version == 2) {
            this.maxHP = 400;
            this.attDmg = 150;
        }
        else System.out.println("Error: wrong version input in thug constructor");
    }

    @Override
    public void getTarget() {
        //Code to be written
    }

    @Override
    public void action() {
        //Code to be written
    }

}
