package itemMGMT;

import units.Unit;

public class Consumable extends Usable {
    private int maxUsageLimit;
    private Unit target;
    private String successMessage1;
    private String successMessage2;

    //Constructor

    public Consumable(String n, String ts, int e, String Desc) {
        super(n, 15, 1, ts, e, Desc);
        this.maxUsageLimit = 3;
    }


    //Other Methods

    public void setTarget(Unit u) { this.target = u; }
    public Unit getTarget() { return this.target; }
    public int getUsageMax() { return maxUsageLimit; }
    public String getSuccessMessage1() { return this.successMessage1; }
    public void setSuccessMessage1(String s) { this.successMessage1 = s; }
    public String getSuccessMessage2() { return successMessage2; }
    public void setSuccessMessage2(String successMessage2) { this.successMessage2 = successMessage2; }
}
