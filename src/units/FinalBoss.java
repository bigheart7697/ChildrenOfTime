package units;

public class FinalBoss extends Enemy{

    FinalBoss() { super("Collector", 1000, 150); }

    @Override
    public void action() {
        int attCount = 0;
        for (Hero h: this.field.getHeroes()) {
            if (!h.isDead) {
                int rand = (int) (Math.random() * 2) + 2;
                h.setEP(h.getEP() - rand);
                if (attCount < 2) {
                    h.setHP(h.getHP() - getAttDmg());
                    attCount++;
                }
                h.refreshStatus();
            }
        }
    }

    @Override
    public void setTarget() {
        this.target = null; // Targets are accessed via battlefield in action method.
    }

    @Override
    public void update() {
        if (getHP() < 400) setAttBuff();
    }

    private void setAttBuff() { setAttDmg(getAttDmg() + 100); }

}
