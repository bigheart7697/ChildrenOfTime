package itemMGMT;

public class Consumable extends Usable {
    protected int numberInStash;

    //Constructor

    Consumable(String n, String ts, int e) {
        super(n, 15, 1, ts, e);
        this.numberInStash = 3;
    }
}
