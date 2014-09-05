package com.platformer.items;


import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {

    private static final String TAG = Inventory.class.getSimpleName();
    HashMap<Long, Integer> inventory;

    public Inventory() {
        this.inventory = new HashMap<Long, Integer>();
    }

    public Inventory(HashMap<Long, Integer> inventory) {
        this();
        addItems(inventory);
    }

    public HashMap<Long, Integer> getItems() {
        return inventory;
    }

    public InventoryItem getItem(long itemID){
        if (inventory.get(itemID) != null)
            return ItemsPool.getItem(itemID);
        else{
            Gdx.app.log(TAG, "Item with ID = " + itemID + " was not found.");
            return null;
        }
    }

    /** Adds set of items to the inventory. */
    public void addItems(Map<Long, Integer> items){
        if(items != null && !items.isEmpty()){
            Set<Map.Entry<Long, Integer>> set = items.entrySet();
            for (Map.Entry<Long, Integer> pair : set){
                addItem(pair.getKey(), pair.getValue());
            }
        }
    }

    /** Private method to change items. */
    private void setItem(Long itemID, int quantity){
        Integer q = this.inventory.get(itemID);
        if (q == null) q = 0;
        if (q + quantity <= 0)
            inventory.remove(itemID);
        else
            inventory.put(itemID, q + quantity);
    }

    /** Adds one item with specified quantity.
     * @param itemID Specified item which is going to be added.
     * @param quantity Amount of item's instances to be removed.
     *  */
    public void addItem(Long itemID, int quantity){
        if (itemID != null && quantity > 0){
            setItem(itemID, quantity);
        }
    }

    /** Removes amount of specified item's instances if exists.
     * @param itemID Specified item which is going to be removed.
     * @param quantity Amount of item's instances to be removed.
     * Note: If quantity is greater than existed in inventory - item will be permanently deleted from inventory
     * */
    public void removeItem(Long itemID, int quantity){
        if (itemID != null && quantity < 0) {
            Integer q = this.inventory.get(itemID);
            if (q != null){
                if (quantity >= q)
                    this.inventory.remove(itemID);
                else
                    this.inventory.put(itemID, q + quantity);
            }
        }
    }

    /**
     * Permanently removes specified item if exists.
     * @param itemID Specified item's ID which is going to be removed.
     * */
    public void removeItem(long itemID){
        this.inventory.remove(itemID);
    }

    /**
     * Removes all items from inventory.
     */
    public void removeAll(){
        this.inventory.clear();
    }
}
