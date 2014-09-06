package com.platformer.items;

import com.badlogic.gdx.Gdx;
import com.platformer.abilities.effect.Effect;
import com.platformer.abilities.effect.FreezeEffect;
import com.platformer.entities.ActorStats;

import java.util.Hashtable;

public class ItemsPool {
    private static final String TAG = ItemsPool.class.getSimpleName();
    private static Hashtable<Long, InventoryItem> itemsCollection;

    public static void initPool(){
        itemsCollection = new Hashtable<Long, InventoryItem>();
        ///TODO: load items database here
        itemsCollection.put(0L, new InventoryItem("Sword 0") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats dmgStats = new ActorStats();
                dmgStats.offence = 10;
                return dmgStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });

        itemsCollection.put(1L, new InventoryItem("Shield 0") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats defStats = new ActorStats();
                defStats.defense = 10;
                return defStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });

        itemsCollection.put(2L, new InventoryItem("Frost Sword 1") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats dmgStats = new ActorStats();
                dmgStats.offence = 7;
                return dmgStats;
            }

            @Override
            protected Effect[] createEffects() {
                return new Effect[]{new FreezeEffect(5f)};
            }
        });
    }

    public static InventoryItem getItem(long id) {
        if (itemsCollection == null){
            Gdx.app.error(TAG, "Items Pool wasn't be initialized.");
            return null;
        }
        InventoryItem item = itemsCollection.get(id);
        if (item == null) Gdx.app.log(TAG, "Item with ID = " + id + " was not found.");
        return item;
    }
}
