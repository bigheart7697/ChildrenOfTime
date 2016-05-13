package units;

public class Thug extends Enemy {

    //Constructors

    public Thug(int version, int id) {
        super("Thug" + id, 0, 0);
        this.id = id;
        if (version == 0) {
            setName("Weak thug" + id);
            setMaxHP(200);
            setHP(200);
            setAttDmg(50);
            this.version = version;
        }
        if (version == 1) {
            setName("Able thug" + id);
            setMaxHP(300);
            setHP(300);
            setAttDmg(90);
            this.version = version;
        }
        if (version == 2) {
            setName("Mighty thug" + id);
            setMaxHP(400);
            setHP(400);
            setAttDmg(150);
            this.version = version;
        }
        setDescription("Thug:\n" +
                "Attacks one of your heroes in each turn\n" +
                "Weak version: Attack Power=50, Maximum health=200\n" +
                "Able version: Attack Power=90, Maximum health=300\n" +
                "Mighty version: Attack Power=150, Maximum health=400\n" +
                "Action message: “Thug just attacked “ + (target) + “ with “ + (attack power) + “ power”\n");
    }

    public Thug(int version) {
        super("Thug", 0, 0);
        this.id = 0;
        if (version == 0) {
            setName("Weak thug");
            setMaxHP(200);
            setHP(200);
            setAttDmg(50);
            this.version = version;
        }
        if (version == 1) {
            setName("Able thug");
            setMaxHP(300);
            setHP(300);
            setAttDmg(90);
            this.version = version;
        }
        if (version == 2) {
            setName("Mighty thug");
            setMaxHP(400);
            setHP(400);
            setAttDmg(150);
            this.version = version;
        }
        setDescription("Thug:\n" +
                "Attacks one of your heroes in each turn\n" +
                "Weak version: Attack Power=50, Maximum health=200\n" +
                "Able version: Attack Power=90, Maximum health=300\n" +
                "Mighty version: Attack Power=150, Maximum health=400\n" +
                "Action message: “Thug just attacked “ + (target) + “ with “ + (attack power) + “ power”\n");
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
        System.out.println("Thug just attacked " + target.getName() + " with " + getAttDmg() + " power");
    }

}
