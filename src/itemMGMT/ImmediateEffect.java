package itemMGMT;

public class ImmediateEffect extends Usable {
    protected int addedCost;

    //Constructor

    ImmediateEffect(String n, String ts, int e) {
        super(n, 4, 0, ts, e);
    }

    public void increaseCost() {
        this.addedCost += 2;
    }
}
