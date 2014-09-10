package com.platformer.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;

public class Map {

    private static final String WIDTH_PROPERTY_TAG = "width";
    private static final String HEIGHT_PROPERTY_TAG = "height";
    private static final String TILE_WIDTH_PROPERTY_TAG = "tilewidth";
    private static final String TILE_HEIGHT_PROPERTY_TAG = "tileheight";
    private static final String COLL_OBJECTS_LAYER_TAG = "collidable-objects";
    private static final String SPEC_OBJECTS_LAYER_TAG = "special-objects";
    private static final String SPAWN_OBJECT_TAG = "spawn-point";
    private static final String FOREGROUND_LAYER_TAG = "foreground";
    private static final String BACKGROUND_LAYER_TAG = "background";

    private TiledMap map;
    private float mapWidth;
    private float mapHeight;
    private float tileWidth;
    private float tileHeight;
    private MapObject spawn;
    private Vector2 position;
    private MapLayer collisionLayer;
    private MapLayer specObjectsLayer;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer backgroundLayer;


    public Map(final String path) throws MapObjectNotFoundException, MapLayerNotFoundException{
        this.map = new TmxMapLoader().load(path);
        mapWidth = this.map.getProperties().get(WIDTH_PROPERTY_TAG, int.class);
        mapHeight = this.map.getProperties().get(HEIGHT_PROPERTY_TAG, int.class);
        tileWidth = this.map.getProperties().get(TILE_WIDTH_PROPERTY_TAG, int.class);
        tileHeight = this.map.getProperties().get(TILE_HEIGHT_PROPERTY_TAG, int.class);

        ///layers loading
        collisionLayer = map.getLayers().get(COLL_OBJECTS_LAYER_TAG);
        specObjectsLayer = map.getLayers().get(SPEC_OBJECTS_LAYER_TAG);
        foregroundLayer = (TiledMapTileLayer) map.getLayers().get(FOREGROUND_LAYER_TAG);
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get(BACKGROUND_LAYER_TAG);
        validateLayersLoading();
        ///objects loading
        spawn = specObjectsLayer.getObjects().get(SPAWN_OBJECT_TAG);
        validateObjectsLoading();
        ///
        position = new Vector2(0, 1.0f / (mapHeight * tileHeight));
    }

    private void validateLayersLoading() throws MapLayerNotFoundException {
        if (collisionLayer == null)
            throw new MapLayerNotFoundException(COLL_OBJECTS_LAYER_TAG);
        else if(specObjectsLayer == null)
            throw new MapLayerNotFoundException(SPEC_OBJECTS_LAYER_TAG);
        else if(foregroundLayer == null)
            throw new MapLayerNotFoundException(FOREGROUND_LAYER_TAG);
        else if(backgroundLayer == null)
            throw new MapLayerNotFoundException(BACKGROUND_LAYER_TAG);
}

    private void validateObjectsLoading ()throws MapObjectNotFoundException {
        if (spawn == null)
            throw new MapObjectNotFoundException(SPAWN_OBJECT_TAG);
    }

    public Vector2 getSpawnPoint(){
        float xPos = spawn.getProperties().get("x", float.class);
        float yPos = spawn.getProperties().get("y", float.class);
        return new Vector2(xPos, yPos);
    }

    public float getMapWidthTiles() {
        return mapWidth;
    }

    public float getMapWidth() {
        return mapWidth * tileWidth;
    }

    public float getMapHeightTiles() {
        return mapHeight;
    }

    public float getMapHeight() {
        return mapHeight * mapHeight;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public TiledMap getMap() {
        return map;
    }

    public MapObject getSpawnObject() {
        return spawn;
    }

    public TiledMapTileLayer getForegroundLayer() {
        return foregroundLayer;
    }

    public TiledMapTileLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    public void dispose() {
        map.dispose();
    }

    public MapLayer getSpecObjectsLayer() {
        return specObjectsLayer;
    }

    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }
}