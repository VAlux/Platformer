package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.enums.ActorState;
import com.platformer.items.Inventory;
import com.platformer.maps.Map;
import com.platformer.stats.CharacterStats;

import static com.platformer.enums.ActorState.DEAD;
import static com.platformer.enums.ActorState.SPAWN;

public class Character extends RenderableEntity{

    private static final String TAG = Character.class.getSimpleName();

    protected Inventory inventory;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected Vector2 spawnPosition;
    protected CharacterStats stats;
    protected CharacterStats etalonStats;
    protected ActorState state;
    protected MapObjects specialObjects;
    protected MapObjects collidableObjects;
    protected boolean isOnGround;

    protected Character(Map map, float xPos, float yPos, int inventoryCapacity) {
        super(map, xPos, yPos);

        spawnPosition = new Vector2(xPos, yPos);
        specialObjects = map.getSpecObjectsLayer().getObjects();
        collidableObjects = map.getCollisionLayer().getObjects();

        velocity = new Vector2();
        acceleration = new Vector2();

        etalonStats = new CharacterStats();
        etalonStats.loadDefaults();
        stats = new CharacterStats();
        stats.copy(etalonStats);

        inventory = new Inventory(inventoryCapacity);
    }

    protected Character(Map map, Vector2 spawnPosition, int inventoryCapacity) {
        this(map, spawnPosition.x, spawnPosition.y, inventoryCapacity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void spawn() {
        this.bounds.setPosition(spawnPosition);
        position.set(bounds.x, bounds.y);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
        deactivateInventory();
        stats.copy(etalonStats);
        activateInventory();
    }

    @Override
    public void act(final float deltaTime) {
        acceleration.y = -stats.GRAVITY * deltaTime;
        velocity.add(acceleration);

        if (acceleration.x == 0)
            velocity.x *= stats.FRICTION;

        velocity.x = MathUtils.clamp(velocity.x, -stats.MAX_VELOCITY, stats.MAX_VELOCITY);
        move(deltaTime);
        stateTime += deltaTime;
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

    public boolean isActiveInventory() {
        return isActiveInventory;
    }

    public void move(final float deltaTime) {
        bounds.x += velocity.x * deltaTime;
        Rectangle collRect;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.x > 0)
                    bounds.x = collRect.x - bounds.width - 0.01f;
                else
                    bounds.x = collRect.x + collRect.width + 0.01f;

                velocity.x = 0;
            }
        }
        bounds.y += velocity.y * deltaTime;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                if (velocity.y > 0) {
                    bounds.y = collRect.y - bounds.height - 0.01f;
                }
                else {
                    bounds.y = collRect.y + collRect.height + 0.01f;
                    isOnGround = true;
                }
                velocity.y = 0;
            }
        }
        position.set(bounds.x, bounds.y);
    }

    public CharacterStats getStats() {
        return stats;
    }

    protected boolean isAlive(){
        return position.y > 0.0f && stats.health > 0 && state != DEAD;
    }

    @Override
    public Animation getCurrentAnimation() {
        return null;
    }
}