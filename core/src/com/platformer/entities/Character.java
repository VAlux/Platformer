package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.platformer.items.Inventory;
import com.platformer.maps.Map;

public class Character extends Actor{

    private static final String TAG = Character.class.getSimpleName();
    protected Inventory inventory;

    protected Character(float X, float Y, int inventoryCapacity) {
        super(X, Y);
        inventory = new Inventory(inventoryCapacity);
    }

    protected Character(Map map, Vector2 spawnPosition, int inventoryCapacity) {
        super(map, spawnPosition);
        inventory = new Inventory(inventoryCapacity);
    }

    protected Character(Map map, float xPos, float yPos, int inventoryCapacity) {
        super(map, xPos, yPos);
        inventory = new Inventory(inventoryCapacity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void spawn() {
        super.spawn();
        deactivateInventory();
        stats.copy(etalonStats);
        activateInventory();
    }

    /// TODO: temp kostil...see descr below.
    // Deeply hidden bug, items from pool should be cloned and have its' own unique entry identifier (while still have ID which differ items from each other).
    // Temporary solution is to clear items' effects from actor.
    protected void deactivateInventory(){
        Gdx.app.log(TAG, "Re-initializing inventory of character with ID = " + this.getId());
        if (inventory == null || inventory.size() == 0) return;
        for (Inventory.ItemEntry itemEntry : inventory.getItems()){
            if (!itemEntry.item.isConsumable())
                for (int i = 0; i < itemEntry.quantity; ++i)
                    itemEntry.item.remove(this);
        }
        isActiveInventory = false;
    }

    protected boolean isActiveInventory = true;

    protected void activateInventory(){
        applyInventory();
        isActiveInventory = true;
    }

    private void applyInventory(){
        if (inventory == null || inventory.size() == 0) return;
        for (Inventory.ItemEntry itemEntry : inventory.getItems()){
            if (!itemEntry.item.isConsumable())
                for (int i = 0; i < itemEntry.quantity; ++i)
                    itemEntry.item.apply(this);
        }
    }
    public void pickItem(long id) {
        pickItem(id, 1);
    }

    public void pickItem(long id, int quantity){
        inventory.addItem(id, quantity);
        Inventory.ItemEntry itemEntry = inventory.getItem(id);
        if (itemEntry != null && !itemEntry.item.isConsumable())
            for (int i = 0; i < quantity; ++i)
                itemEntry.item.apply(this);
    }


    public void dropItem(long id) {
        dropItem(id, 1);
    }

    public void dropItem(long id, int quantity){
        Inventory.ItemEntry itemEntry = inventory.getItem(id);
        inventory.removeItem(id, quantity);
        if (itemEntry != null && !itemEntry.item.isConsumable())
            for (int i = 0; i < quantity; ++i)
                itemEntry.item.remove(this);
    }

    public void useItem(long id){
        useItem(id, 1);
    }
    public void useItem(long id, int quantity){
        Inventory.ItemEntry itemEntry = inventory.getItem(id);
        if (itemEntry != null && itemEntry.item.isConsumable()){
            int q = MathUtils.clamp(quantity, 1, itemEntry.quantity);
            for (int i = 0; i < q; i++) {
                itemEntry.item.apply(this);
            }
        }
        inventory.removeItem(id, quantity);
    }
}
