package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.platformer.abilities.Ability;
import com.platformer.items.Inventory;
import com.platformer.states.CharacterOrientation;
import com.platformer.states.CharacterState;
import com.platformer.stats.CharacterStats;

import java.util.ArrayList;

import static com.platformer.states.CharacterOrientation.RIGHT;
import static com.platformer.states.CharacterState.*;

public class Char extends RenderableEntity {

    private static final String TAG = Char.class.getSimpleName();

    protected Inventory inventory;
    protected boolean isActiveInventory = true;

    protected Vector2 spawnPosition;

    protected CharacterStats stats;
    protected CharacterStats etalonStats;
    protected CharacterState state;
    protected CharacterOrientation orientation;

    protected ArrayList<Ability> abilities;

    protected boolean isOnWall;
    protected boolean canWallJump;

    protected Char(float xPos, float yPos, int inventoryCapacity) {
        super(xPos, yPos);
        spawnPosition = new Vector2(xPos, yPos);
        velocity = new Vector2();
        acceleration = new Vector2();

        etalonStats = new CharacterStats();
        etalonStats.loadDefaults();
        stats = new CharacterStats();
        stats.copy(etalonStats);
        orientation = RIGHT;

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
        bounds.setPosition(spawnPosition);
        position.set(bounds.x, bounds.y);
        velocity.set(0, 0);
        acceleration.set(0, 0);
        state = SPAWN;
        orientation = RIGHT;
        stats.copy(etalonStats);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);

        for (Ability ability : abilities) {
            ability.act(delta);
        }

        move(delta);
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

    @Override
    public void move(final float deltaTime) {
        super.move(deltaTime);
        if (hasXCollision) {
            isOnWall = !isOnGround;
        }
        if (hasYCollision){
            canWallJump = true;
            isOnWall = false;
            if (state == JUMP) {
                switchToIdleState();
            }
        }
    }

    /**
     * determine the character orientation i.e.: left or right and return the corresponding state.
     */
    public void switchToIdleState() {
        if (velocity.x > 0) {
            state = IDLE_RIGHT;
        } else {
            state = IDLE_LEFT;
        }
    }

    /**
     * Character jumping action.
     * Some logic to not to allow jumping if the character is not on the ground and
     * allowing only 1 wall jump.
     */
    public void jump() {
        if (isOnGround()) {
            velocity.y = stats.jumpVelocity;
            setOnGround(false);
            Gdx.app.log("input", "REGULAR JUMP");
        }
        if (isOnWall  && canWallJump) {
            velocity.y = stats.jumpVelocity;
            setCanWallJump(false);
            setOnWall(false);
            Gdx.app.log("input", "WALL JUMP");
        }
        setState(JUMP);
    }

    /**
     * Character is alive if It is not fallen below the map, or health is > 0, and It's state is not set to DEAD.
     * @return Is character alive?
     */
    protected boolean isAlive() {
        return position.y > 0.0f && stats.health > 0 && state != DEAD;
    }

    @Override
    public Animation getAnimation() {
        return null;
    }

    public CharacterStats getStats() {
        return stats;
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

    public CharacterOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(CharacterOrientation orientation) {
        this.orientation = orientation;
    }
}