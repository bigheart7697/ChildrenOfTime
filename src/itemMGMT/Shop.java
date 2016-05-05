package itemMGMT;

import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<Item> items;

    //Constructor

    Shop(String n) {
        this.name = n;
        this.items = new ArrayList<>();
    }

    public void addItem(Item i){ this.items.add(i); }
    public void showItems() {
        for (Item i: this.items) {
            System.out.println(i.name + " Costs: " + i.cost);
        }
    }

}
