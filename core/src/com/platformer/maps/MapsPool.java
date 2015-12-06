package com.platformer.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.platformer.Constants;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;

/**
 * Created by Alvo on 05.12.2015.
 * Map pooling functionality.
 * Contains the preloaded list of all the maps available in the game.
 */
public class MapsPool {

    /**
     * All of the available maps.
     */
    private static final Array<Map> maps = new Array<>();

    /**
     * Initialize the maps pool.
     */
    public static void init() {
        for (MapResourceIdentifier mapIdentifier : getMapPaths()) {
            try {
                maps.add(new Map(mapIdentifier));
            } catch (MapObjectNotFoundException | MapLayerNotFoundException e) {
                Gdx.app.log("MapsPool.init", "Maps Pool initialization error! " + e.getMessage());
                return;
            }
        }
        Gdx.app.log("MapsPool.init", "Maps Pool initialization successful");
    }

    /**
     * Should return all available tmx map paths.
     * @return Array of available tmx map paths.
     */
    private static Array<MapResourceIdentifier> getMapPaths() {
        final Array<MapResourceIdentifier> paths = new Array<>();
        paths.add(Constants.TMX_MAP_JUNGLE);
        return paths;
    }

    /**
     * Obtain the map by the map name from the pool.
     * @param name Map name.
     * @return Map with the specified name, if it is exist in the pool.
     * @throws MapNotFoundException We have no map with such name in the pool, or pool is not initialized.
     */
    public static Map getMapByName(final String name) throws MapNotFoundException {
        for (Map map : maps) {
            if (map.getName().equals(name)) {
                return map;
            }
        }
        throw new MapNotFoundException(name);
    }

    public static Array<Map> getMaps() {
        return maps;
    }
}
