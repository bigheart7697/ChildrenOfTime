package units;

abstract public class Enemy extends Unit {
    protected int version; // 0 is weak; 1 is able; 2 is mighty
    protected String actionMsg;
    protected int id;

    //Constructor
    Enemy(String n, int h, int dmg) {
        super(n, h, dmg);
    }

    //Other Methods
    abstract public void action();

    @Override
    public void update(){}
}
