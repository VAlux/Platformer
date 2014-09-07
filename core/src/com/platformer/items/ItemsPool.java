package com.platformer.items;

import com.badlogic.gdx.Gdx;
import com.platformer.abilities.effect.Effect;
import com.platformer.entities.ActorStats;

import java.util.Hashtable;

public class ItemsPool {
    private static final String TAG = ItemsPool.class.getSimpleName();
    private static Hashtable<Long, InventoryItem> itemsCollection;

    public interface IDs {  // simplify accessing items by IDs
        public static final long SCOUT_SWORD = 0L;
        public static final long ICE_SWORD = 1L;
        public static final long STONE_RING = 2L;
        public static final long JET_PACK = 3L;
        public static final long HEAL_POTION = 4L;
    }

    public static void initPool(){
        itemsCollection = new Hashtable<Long, InventoryItem>();
        ///TODO: load items database here
        itemsCollection.put(IDs.SCOUT_SWORD, new InventoryItem(IDs.SCOUT_SWORD, "Scout Sword") {
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

        itemsCollection.put(IDs.STONE_RING, new InventoryItem(IDs.STONE_RING, "Stone Ring") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats defStats = new ActorStats();
                defStats.defense = 10;
                defStats.maxHealth = 400;
                return defStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });

        itemsCollection.put(IDs.ICE_SWORD, new InventoryItem(IDs.ICE_SWORD, "Ice Sword") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats dmgStats = new ActorStats();
                dmgStats.offence = 19;
                dmgStats.ACCELERATION = -10;
                dmgStats.MAX_VELOCITY = -200;
                return dmgStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });

        itemsCollection.put(IDs.JET_PACK, new InventoryItem(IDs.JET_PACK, "Jet Pack") {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats dmgStats = new ActorStats();
                dmgStats.GRAVITY = -400;
                return dmgStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });

        itemsCollection.put(IDs.HEAL_POTION, new InventoryItem(IDs.HEAL_POTION, "Healing Potion", true) {
            @Override
            protected ActorStats createAffectedStats() {
                ActorStats dmgStats = new ActorStats();
                dmgStats.health = 400;
                return dmgStats;
            }

            @Override
            protected Effect[] createEffects() {
                return null;
            }
        });
    }

    public static InventoryItem obtain(long id) {
        if (itemsCollection == null){
            Gdx.app.error(TAG, "Items Pool wasn't be initialized.");
            return null;
        }
        InventoryItem item = itemsCollection.get(id);
        if (item == null) Gdx.app.log(TAG, "Item with ID = " + id + " was not found.");
        return item;
    }


}
