package itemMGMT;

public class ImmediateEffect extends Usable {

    //Constructor

    public ImmediateEffect(String n, String ts, int e, String Desc) {
        super(n, 4, 0, ts, e, Desc);
    }

    public void increaseCost() {
        this.cost += 2;
    }
}
