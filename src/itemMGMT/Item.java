package itemMGMT;

public class Item {
    protected String name;
    protected int cost;
    protected int invSpaceNeeded;
    protected String targetStat;
    protected int effect;
    private String description;


    //Constructor

    Item(String n, int c, int isn, String ts, int e) {
        this.name = n; this.cost = c; this.invSpaceNeeded = isn; this.targetStat = ts; this.effect = e;
    }


    //Getters and Setters

    public String getTargetStat() { return this.targetStat; }
    public void setTargetStat(String ts) { this.targetStat = ts; }

    public String getName() { return this.name; }

    public int getCost() { return this.cost; }
    public void setCost(int c) { this.cost = c; }

    public int getInvSpaceNeeded() { return this.invSpaceNeeded; }
    public void setInvSpaceNeeded(int isn) { this.invSpaceNeeded = isn; }

    public int getEffect() { return this.effect; }
    public void setEffect(int e) { this.effect = e; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
