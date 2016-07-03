package com.platformer.maps;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Alvo on 20.12.2015.
 * Provides an interface access to the map layer abstraction.
 */
public class MapLayer {

    protected TiledMapTileLayer currentLayer;
    protected MapLayerType type;

    public MapLayer(MapLayerType type) {
        this.type = type;
    }

    public MapLayerType getType() {
        return this.type;
    }

    public TiledMapTileLayer getCurrentTileLayer() {
        return this.currentLayer;
    }
}
