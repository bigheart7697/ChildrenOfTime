package units;

abstract class Enemy extends Unit {
    protected int version; // 0 is weak; 1 is able; 2 is mighty
    protected String actionMsg;
    protected int id;

    //Constructor

    Enemy(String n, int h, int dmg) {
        super(n, h, dmg);

    }

    abstract public void action();

    @Override
    public void update() {
        //Probably not needed for enemy class so I'm gonna leave this empty :D
    }
}
