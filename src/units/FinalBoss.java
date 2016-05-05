package units;

public class FinalBoss extends Enemy{

    FinalBoss() { super("Collector", 1000, 150); }

    @Override
    public void action() {
        //Needs Battlefield to be implemented
    }

    @Override
    public void getTarget() {
        //Code to be written
    }

    @Override
    public void update() {
        if (getHP() < 400) setAttBuff();
    }

    private void setAttBuff() { setAttDmg(getAttDmg() + 100); }

}
