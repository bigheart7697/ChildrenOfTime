package units;

abstract class Enemy extends Unit {
    protected int version; // 0 is weak; 1 is able; 2 is mighty
    protected int id;

    abstract public void action();

    @Override
    public void update() {

    }
}
