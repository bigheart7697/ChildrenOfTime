package units;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.Random;

public class FinalBoss extends Enemy{

    public FinalBoss() {
        super("The Collector", 1000, 150);
        setDescription("Final Boss:\n" +
                "Burns 2 to 4 energy points of each hero and attacks 2 of them in each turn\n" +
                "Maximum health: 1000\n" +
                "Attack power when his current health is higher than 400: 150\n" +
                "Attack power when his current health is below 400: 250\n" +
                "Action messages:\n" +
                "“Collector just attacked “ + (target) + “ with ” + (attack power) + “ power”\n" +
                "“Collector just burned “ + (energy point count) + “ energy points from ” + (target)\n" +
                "“Collector has mutated”\n");
    }

    @Override
    public void action() {
        int attCount = 0;
        for (Hero h: this.field.getHeroes()) {
            if (!h.isDead) {
                int heroRand[] = new int[4];
                boolean isRepeated = true;
                int rand = (int) (Math.random() * 2) + 2;
                h.setEP(h.getEP() - rand);
                outer:while (isRepeated) {
                    heroRand[attCount] = (int) (Math.random() * 4 + 1);
                    for (int cnt = 0; cnt < attCount; cnt++) {
                        if (heroRand[attCount] == heroRand[cnt])
                            continue outer;
                    }
                    isRepeated = false;
                }
                if (heroRand[attCount] % 2 == 1) {
                    h.setHP(h.getHP() - getAttDmg());
                    System.out.println("Collector just attacked " + target.getName() + " with " + getAttDmg() + " power");
                }
                attCount++;
                h.refreshStatus();
                System.out.println("Collector just burned " + rand + " energy points from " + target.getName());
            }
        }
    }

    @Override
    public void setTarget() {
        this.target = null; // Targets are accessed via battlefield in action method.
    }

    @Override
    public void update() {
        if (getHP() < 400) {
            setAttBuff();
            System.out.println("Collector has mutated");
        }
    }

    private void setAttBuff() { setAttDmg(getAttDmg() + 100); }

}
