package com.platformer.items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.*;

public class Inventory {

    private static final String TAG = Inventory.class.getSimpleName();

    private HashMap<Long, Integer> inventory;
    private int maxCapacity;

    public Inventory(int maxCapacity) {
        this.inventory = new HashMap<Long, Integer>();
        this.maxCapacity = maxCapacity;
    }

    public Inventory(int maxCapacity, HashMap<Long, Integer> inventory) {
        this(maxCapacity);
        addItems(inventory);
    }

    public int size() {
        int size = 0;
        Set<Long> keys = inventory.keySet();
        for (Long itemID : keys) {
            ItemEntry entry = getItem(itemID);
            if (entry.item.isConsumable()) ++size; // consumable items take one slot regardless quantity.
            else size += entry.quantity;           // other items take one slot per item.
        }
        return size;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    private InventoryItem getInventoryItem(long itemID){
        if (inventory.get(itemID) != null)
            return ItemsPool.obtain(itemID);
        else{
            Gdx.app.log(TAG, "Item with ID = " + itemID + " was not found in inventory.");
            return null;
        }
    }

    private InventoryItem obtainItem(long itemID){
        InventoryItem item = ItemsPool.obtain(itemID);
        if (item == null) {
            Gdx.app.log(TAG, "Item with ID = " + itemID + " does not exist.");
            return null;
        }
        return item;
    }

    public ItemEntry[] getItems() {
        Set<Long> keys = inventory.keySet();
        ItemEntry[] items = new ItemEntry[keys.size()];
        Iterator<Long> it = keys.iterator();
        for (int i = 0; i < items.length && it.hasNext(); i++){
            long itemID = it.next();
            int qty = inventory.get(itemID);
            InventoryItem item = getInventoryItem(itemID);
            items[i] = new ItemEntry(item, qty);
        }
        return items;
    }

    public ItemEntry getItem(long itemID){
        InventoryItem item = getInventoryItem(itemID);
        return (item == null ? null : new ItemEntry(item, inventory.get(itemID)));
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
    private void setItem(long itemID, int quantity){
        Integer q = inventory.get(itemID);
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
    public void addItem(long itemID, int quantity){
        if (quantity > 0){
            int size = size();
            InventoryItem item = obtainItem(itemID);
            if (item == null) return;

            if (!item.isConsumable() && size + quantity > maxCapacity) {
                Gdx.app.log(TAG, "Can't add (" + quantity + ") items: max capacity (" + maxCapacity + ") will be exceed.");
                quantity = MathUtils.clamp(quantity, 1, maxCapacity - size);
            }
            setItem(itemID, quantity);
        }
    }

    /** Removes amount of specified item's instances if exists.
     * @param itemID Specified item which is going to be removed.
     * @param quantity Amount of item's instances to be removed.
     * Note: If quantity is greater than existed in inventory - item will be permanently deleted from inventory
     * */
    public void removeItem(long itemID, int quantity){
        setItem(itemID, -Math.abs(quantity));
    }

    /**
     * Permanently removes specified item if exists.
     * @param itemID Specified item's ID which is going to be removed.
     * */
    public void removeItem(long itemID){
       inventory.remove(itemID);
    }

    /** Removes all items from inventory. */
    public void removeAll(){
        this.inventory.clear();
    }

    @Override
    public String toString() {
        String str = "Inventory: (" + size() +"/" + maxCapacity + ")\n";
            for (ItemEntry i: getItems()){
                str += '\t' + i.item.getName();
                str += " x" + i.quantity;
                str += '\n';
            }
        return str;
    }

    /** Represents pair of item an its' quantity. Provides easy access to item and quantity and reduces calls to HashMap. */
    public class ItemEntry {

        public InventoryItem item;
        public int quantity;

        private ItemEntry(InventoryItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
    }
}
