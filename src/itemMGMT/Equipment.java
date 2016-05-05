package itemMGMT;

import units.Hero;

public class Equipment extends Item {
    protected Hero target;


    //Constructor

    Equipment(String n, int c, int isn, String ts, int e, Hero target) {
        super(n, c, isn, ts, e);
        this.target = target;
    }


    //Other Methods

    public void setBuff() {
        switch (this.targetStat) {
            case "HP":
                this.target.setMaxHP(this.target.getMaxHP() + this.effect);
                break;
            case "MP":
                this.target.setMaxMP(this.target.getMaxMP() + this.effect);
                break;
            case "EP":
                this.target.setMaxEP(this.target.getMaxEP() + this.effect);
                break;
            case "att":
                this.target.setAttDmg(this.target.getAttDmg() + this.effect);
        }
    }

    public void isRemoved() {
        switch (this.targetStat) {
            case "HP":
                this.target.setMaxHP(this.target.getMaxHP() - this.effect);
                break;
            case "MP":
                this.target.setMaxMP(this.target.getMaxMP() - this.effect);
                break;
            case "EP":
                this.target.setMaxEP(this.target.getMaxEP() - this.effect);
                break;
            case "att":
                this.target.setAttDmg(this.target.getAttDmg() - this.effect);
        }
    }

}

