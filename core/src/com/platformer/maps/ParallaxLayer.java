package com.platformer.maps;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Alvo on 06.12.2015.
 * Containing multiple tiled map tile layers allowing easy access to them
 * and modeling the parallax effect.
 */
public class ParallaxLayer extends MapLayer {

    private Array<TiledMapTileLayer> parallaxLayers;
    private TiledMapTileLayer currentLayer;
    private int currentLayerIndex;

    public ParallaxLayer(int amount) {
        super(MapLayerType.PARALLAX_TILE_LAYER);
        parallaxLayers = new Array<>(amount);
        currentLayerIndex = 0;
    }

    public ParallaxLayer() {
        this(0);
    }

    /**
     * Add new layer to the parallax layers queue.
     * @param newLayer tile layer to add.
     */
    public void addLayer(TiledMapTileLayer newLayer) {
        parallaxLayers.add(newLayer);
    }

    /**
     * Get all the layers.
     * @return all of the parallax layers array.
     */
    public Array<TiledMapTileLayer> getLayers() {
        return parallaxLayers;
    }

    /**
     * Get the amount of the parallax tile layers.
     * @return parallax layers amount.
     */
    public int getLayersAmount() {
        return parallaxLayers.size;
    }

    public TiledMapTileLayer getNextLayer() {
        if (currentLayerIndex >= getLayersAmount()) {
            currentLayerIndex = 0;
        }
        currentLayer = parallaxLayers.get(currentLayerIndex++);
        return currentLayer;
    }

    @Override
    public TiledMapTileLayer getCurrentTileLayer() {
        return currentLayer;
    }
}
