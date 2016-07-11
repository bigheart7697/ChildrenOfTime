package GraphicalUserInterface.CustomGame.Tiles;

import itemMGMT.Item;

import java.util.ArrayList;

/**
 * Created by rezab on 11/07/2016.
 */
public class ShopTile {
    ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }
}
