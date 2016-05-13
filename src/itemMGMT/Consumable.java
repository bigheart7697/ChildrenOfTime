package itemMGMT;

import units.Hero;

public class Consumable extends Usable {
    private int numberInStash;
    private Hero target;
    private String usageMessage;

    //Constructor

    public Consumable(String n, String ts, int e, String Desc) {
        super(n, 15, 1, ts, e, Desc);
        this.numberInStash = 3;
    }


    //Other Methods

    public void setUsageMessage(String um) { this.usageMessage = um; }
    public String getUsageMessage() { return this.usageMessage; }
    public void setTarget(Hero h) { this.target = h; }
    public Hero getTarget() { return this.target; }
    public void isUsed() { this.numberInStash --; }
    public boolean isFinished() { return this.numberInStash == 0;}
}
