package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.Ability;
import com.platformer.screens.GameScreen;
import com.platformer.states.CharacterState;
import com.platformer.items.Inventory;
import com.platformer.maps.Map;
import com.platformer.stats.CharacterStats;

import java.util.ArrayList;

import static com.platformer.states.CharacterState.*;

public class Char extends RenderableEntity {

    private static final String TAG = Char.class.getSimpleName();

    protected Inventory inventory;
    protected boolean isActiveInventory = true;

    protected Map map;

    protected Vector2 spawnPosition;

    protected CharacterStats stats;
    protected CharacterStats etalonStats;
    protected CharacterState state;

    protected MapObjects specialObjects;
    protected MapObjects collidableObjects;

    protected ArrayList<Ability> abilities;

    protected boolean isOnGround;
    protected boolean isOnWall;
    protected boolean canWallJump;

    private boolean hasXCollision;
    private boolean hasYCollision;

    protected Char(float xPos, float yPos, int inventoryCapacity) {
        super(xPos, yPos);

        map = GameScreen.world.getMap();
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
        createAbilities();
    }

    protected Char(Vector2 spawnPosition, int inventoryCapacity) {
        this(spawnPosition.x, spawnPosition.y, inventoryCapacity);
    }

    protected void createAbilities() {
        abilities = new ArrayList<Ability>();
    }

    public void spawn() {
        this.bounds.setPosition(spawnPosition);
        position.set(bounds.x, bounds.y);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
        stats.copy(etalonStats);
    }


    @Override
    public void act(final float delta) {
        super.act(delta);

        for (Ability ability : abilities) {
            ability.act(delta);
        }

        move(delta);
        stateTime += delta;
    }

    /// TODO: temp kostil...see descr below.
    // Deeply hidden bug, items from pool should be cloned and have its' own unique entry identifier (while still have ID which differ items from each other).
    // Temporary solution is to clear items' effects from actor.
    public void deactivateInventory(){
        Gdx.app.log(TAG, "Re-initializing inventory of character with ID = " + this.getId());
        if (inventory == null || inventory.size() == 0) return;
        for (Inventory.ItemEntry itemEntry : inventory.getItems()){
            if (!itemEntry.item.isConsumable())
                for (int i = 0; i < itemEntry.quantity; ++i)
                    itemEntry.item.remove(this);
        }
        isActiveInventory = false;
    }

    public void activateInventory(){
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
        hasXCollision = hasYCollision = false;
        Rectangle collRect;
        for (RectangleMapObject object : collidableObjects.getByType(RectangleMapObject.class)) {
            collRect = object.getRectangle();
            if (bounds.overlaps(collRect)) {
                hasXCollision = true;
                if (velocity.x > 0)
                    bounds.x = collRect.x - bounds.width - 0.01f;
                else
                    bounds.x = collRect.x + collRect.width + 0.01f;

                velocity.x = 0;
                isOnWall = !isOnGround;
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
                    hasYCollision = true;
                    bounds.y = collRect.y + collRect.height + 0.01f;
                    isOnGround = true;
                    canWallJump = true;
                    isOnWall = false;
                    if (state == JUMP)
                        state = IDLE;
                }
                velocity.y = 0;
            }
        }
        if (!hasYCollision) {
            isOnGround = false;
        }
        position.set(bounds.x, bounds.y);
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    public CharacterStats getStats() {
        return stats;
    }

    protected boolean isAlive(){
        return position.y > 0.0f && stats.health > 0 && state != DEAD;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(float x, float y) {
        this.acceleration.set(x, y);
    }

    public Vector2 getSpawnPosition() {
        return spawnPosition;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean isOnWall() {
        return isOnWall;
    }

    public void setOnWall(boolean isOnWall) {
        this.isOnWall = isOnWall;
    }

    public boolean isCanWallJump() {
        return canWallJump;
    }

    public void setCanWallJump(boolean canWallJump) {
        this.canWallJump = canWallJump;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public boolean hasXCollision() {
        return hasXCollision;
    }

    public boolean hasYCollision() {
        return hasYCollision;
    }
}