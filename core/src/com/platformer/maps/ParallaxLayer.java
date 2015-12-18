package com.platformer.maps;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Alvo on 06.12.2015.
 * Containing multiple tiled map tile layers allowing easy access to them
 * and modeling the parallax effect.
 */
public class ParallaxLayer {

    private Array<TiledMapTileLayer> parallaxLayers;

    public ParallaxLayer(int amount) {
        parallaxLayers = new Array<>(amount);
    }

    public void addLayer(TiledMapTileLayer newLayer) {
        parallaxLayers.add(newLayer);
    }

    public Array<TiledMapTileLayer> getLayers() {
        return parallaxLayers;
    }

    public int getLayersAmount() {
        return parallaxLayers.size;
    }
}
