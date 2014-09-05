package com.platformer.items;

import com.badlogic.gdx.Gdx;
import com.platformer.abilities.effect.Effect;
import com.platformer.entities.Actor;
import com.platformer.entities.ActorStats;

public abstract class InventoryItem implements ApplicableObject {
    private static final String TAG = InventoryItem.class.getSimpleName();

    /** Unique item identifier. */
    protected long id;

    /** Item's displayed name. */
    protected String name;

    /** Description text to show. */
    protected String description;

    /** Stores some affected stats. */
    protected ActorStats affectedStats;

    /** Stores effects which will be activated when item is equipped. */
    protected Effect[] effects;

    /** Flag intended to be sure that item was applied only once. */
    protected boolean isApplied;

    /** Flag indicates whether current item is consumable and should be removed after applying. */
    protected boolean isConsumable;


    protected InventoryItem(String name) {
        this(name, "");
    }

    protected InventoryItem(String name, String descr) {
        this.name = name;
        this.description = descr;

        affectedStats = createAffectedStats();
        effects = createEffects();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public boolean isConsumable() {
        return isConsumable;
    }

    /** Defines all stats which will be affected.
     * Note: if stat isn't affected value should be set to 0.
     * */
    protected abstract ActorStats createAffectedStats();

    /** Defines list of effects which will be applied. */
    protected abstract Effect[] createEffects();

    @Override
    public void apply(Actor actor){
        if (isApplied){
            Gdx.app.log(TAG, "Item (" + this.name + ") has been already applied to actor (" + actor.getId() + ").");
            return;
        }

        if (affectedStats == null){
            Gdx.app.error(TAG, "Invalid item: Affected stats was not initialized. Item can not be applied...");
            return;
        }

        if (actor != null && actor.getStats() != null) {
            isApplied = true;
            ///TODO: Define sum for stats to be able to add stats affected by current item

            if (effects != null && effects.length > 0){
                for (Effect e : effects){   // apply all instant effects.
                    if (e.isInstant()) e.apply(actor);
                }
            }
            else{
                Gdx.app.log(TAG, "Item hasn't got any effects.");
            }
        }
    }

    @Override
    public void remove(Actor actor){
        if (!isApplied){
            Gdx.app.log(TAG, "Item (" + this.name + ") wasn't be applied to actor (" + actor.getId() + ").");
            return;
        }

        if (affectedStats == null){
            Gdx.app.error(TAG, "Affected stats was not initialized: item can not be removed...");
            return;
        }

        if (actor != null && actor.getStats() != null) {
            isApplied = false;
            ///TODO: Define subtract for stats to be able to remove stats affected by current item

            if (effects != null && effects.length > 0){
                for (Effect e : effects){   // remove all effects.
                    e.remove(actor);
                }
            }
            else{
                Gdx.app.log(TAG, "Item hasn't got any effects.");
            }
        }
    }



}
