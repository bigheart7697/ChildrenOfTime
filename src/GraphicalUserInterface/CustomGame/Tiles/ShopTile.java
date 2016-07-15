package GraphicalUserInterface.CustomGame.Tiles;

import itemMGMT.Item;

import java.util.ArrayList;

/**
 * Created by rezab on 11/07/2016.
 */
public class ShopTile extends Tiles {

    private ArrayList<String> items = new ArrayList<>();

    public void addItem(String item) {
        items.add(item);
    }

    public ArrayList<String> getItems() {
        return items;
    }
}
