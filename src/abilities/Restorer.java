package abilities;


import units.Hero;
import units.Unit;

public class Restorer extends ActiveAbility {
    protected String whichState;
    protected int amountPattern;
    protected int amount;
    protected Hero user;
    protected Hero target;

//    consructor:


    public Restorer(String name, int level, int XPtoNextLevel, int XPGainPattern, int magicCost, int EPCost, int CDPattern, String whichState, int amountPattern, Hero user){
        super(name, level, XPtoNextLevel, XPGainPattern, magicCost, EPCost, CDPattern);
        this.whichState = whichState;
        this.amountPattern = amountPattern;
        this.user = user;
    }

//    getters and setters:

    @Override
    public void setTarget(Unit target) {
        this.target = (Hero)target;
    }

//    other methods:

    @Override
    public void cast() {
        EPCost = EPCostPattern / (int)Math.pow(10.0, (double)(3 - level)) % 10;
        if(user.getEP() < EPCost)
            System.out.println("You don't have enough energy point!");
        else if(user.getMP() < magicCost)
            System.out.println("You don't have enough magic point!");
        else {
            CD = (CDPattern / (int) Math.pow(10.0, (double) (3 - level))) % 10;
            setRemainingCD(CD + 1);
            if (whichState == "health point") {
                amount = (amountPattern / (int) Math.pow(10.0, (double) ((3 - level) * 2)) % 100) * 10;
                if (target.getHP() == target.getMaxHP())
                    System.out.println("Hero's HP is full!");
                else {
                    target.setHP(amount + target.getHP());
                    user.setEP(user.getEP() - EPCost);
                    user.setMP(user.getMP() - magicCost);
                }
                if (target.getHP() > target.getMaxHP())
                    target.setHP(target.getMaxHP());
            } else if (whichState == "energy point") {
//                In this part EPCost is the amount of enegy point which is given to other heros
//                And amountPattern shows the amountPattern of energy point which should be spent in this ability
                amount = amountPattern / (int) Math.pow(10.0, (double) (3 - level) % 10);
                if (target.getEP() == target.getMaxEP())
                    System.out.println("Hero's EP is full!");
                else {
                    target.setEP(amount + target.getEP());
                    user.setEP(user.getEP() - EPCost);
                    user.setMP(user.getMP() - magicCost);
                }
                if (target.getEP() > target.getMaxEP())
                    target.setEP(target.getMaxEP());
            } else if (whichState == "attack power") {
                amount = (amountPattern / (int) Math.pow(10.0, (double) (3 - level) % 10)) * 10;
                target.setAttDmg(amount + target.getAttDmg());
                user.setEP(user.getEP() - EPCost);
                user.setMP(user.getMP() - magicCost);
            } else if (whichState == "magic point") {
                amount = (amountPattern / (int) Math.pow(10.0, (double) (3 - level) % 10)) * 10;
                if (target.getMP() == target.getMaxMP())
                    System.out.println("Hero's MP is full!");
                else {
                    target.setMP(amount + target.getMP());
                    user.setEP(user.getEP() - EPCost);
                    user.setMP(user.getMP() - magicCost);
                }
                if (target.getMP() > target.getMaxMP())
                    target.setMP(target.getMaxMP());
            }
        }
    }
}
