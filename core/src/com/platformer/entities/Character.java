package com.platformer.entities;

import com.badlogic.gdx.math.Vector2;
import com.platformer.items.Inventory;
import com.platformer.maps.Map;

public class Character extends Actor{

    protected Inventory inventory;

    protected Character(float X, float Y) {
        super(X, Y);
        inventory = new Inventory();
    }

    protected Character(Map map, Vector2 spawnPosition) {
        super(map, spawnPosition);
        inventory = new Inventory();
    }

    protected Character(Map map, float xPos, float yPos) {
        super(map, xPos, yPos);
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void pickItem(long id, int quantity){
        inventory.addItem(id, quantity);
        //InventoryItem i = inventory.getItem(id);
        //if (!i.isConsumable()) i.apply(this);      /// TODO: temp solution to apply item's effect on char
    }

    public void dropItem(long id, int quantity){
        //InventoryItem i = inventory.getItem(id);
        inventory.removeItem(id, quantity);
        //if (i != null && !i.isConsumable()) i.remove(this); /// TODO: temp solution to remove item's effect on char
    }
}
