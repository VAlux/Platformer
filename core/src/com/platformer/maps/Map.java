package com.platformer.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;

import static com.platformer.Constants.*;

/**
 * Useful wrapper around the TiledMap class.
 */
public final class Map {

    /**
     * Identification name of the map.
     */
    private String name;

    /**
     * The core tiled map.
     */
    private TiledMap map;

    /**
     * Width of the map in tiles.
     */
    private float mapWidth;

    /**
     * Height of the map in tiles.
     */
    private float mapHeight;

    /**
     * Width of the single map tile.
     */
    private float tileWidth;

    /**
     * Height of the single map tile
     */
    private float tileHeight;

    /**
     * Main spawn point.
     */
    private MapObject spawn;

    /**
     * Map position.
     */
    private Vector2 position;

    /**
     * Layer, that holds all of the map's static collidable objects.
     */
    private MapLayer collisionLayer;

    /**
     * Holds all of the map special objects, such as a spawn points.
     */
    private MapLayer specObjectsLayer;

    /**
     * Set of tiles, which are located on the foreground.
     * Rendered last, after the background set of tiles and characters are rendered.
     */
    private TiledMapTileLayer foregroundLayer;

    /**
     * Set of tiles, which are located on the background.
     * Rendered first, before the characters rendering and foreground layer rendering.
     */
    private TiledMapTileLayer backgroundLayer;

    /**
     * The list of the all of the map layers.
     */
    private Array<MapLayer> mapLayers;

    /**
     * Specialized layer container for parallax map background.
     */
    private ParallaxLayer parallaxBackground;

    /**
     * Set of collidable object on the map.
     */
    private Array<RectangleMapObject> mapCollidables;

    /**
     * Special objects on map.
     * Could be used to hold spawn points, etc.
     */
    private MapObjects specialObjects;

    /**
     * Map Bounds.
     */
    private Rectangle bounds;

    public Map(final MapResourceIdentifier identifier) throws MapObjectNotFoundException, MapLayerNotFoundException {
        map = new TmxMapLoader().load(identifier.getPathToFile());
        name = identifier.getName();
        loadMapProperties();
        loadMapLayers();
        loadMapObjects();
        position = new Vector2(0, 1.0f / (mapHeight * tileHeight));
        bounds = new Rectangle(position.x, position.y, mapWidth, mapHeight);
    }

    /**
     * Load Map Objects, such as spawn points, etc...
     * @throws MapObjectNotFoundException
     */
    private void loadMapObjects() throws MapObjectNotFoundException {
        specialObjects = specObjectsLayer.getObjects();
        spawn = specialObjects.get(MAP_SPAWN_OBJECT_TAG);
        mapCollidables = collisionLayer.getObjects().getByType(RectangleMapObject.class);
        validateObjectsLoading();
    }

    /**
     * Load all of the map layers.
     * @throws MapLayerNotFoundException
     */
    private void loadMapLayers() throws MapLayerNotFoundException {
        mapLayers = new Array<>();
        collisionLayer = loadMapLayer(MAP_COLL_OBJECTS_LAYER_TAG);
        specObjectsLayer = loadMapLayer(MAP_SPEC_OBJECTS_LAYER_TAG);
        foregroundLayer = (TiledMapTileLayer) loadMapLayer(MAP_FOREGROUND_LAYER_TAG);
        backgroundLayer = (TiledMapTileLayer) loadMapLayer(MAP_BACKGROUND_LAYER_TAG);
        //parallax background stuff.
        parallaxBackground = new ParallaxLayer(3);
        parallaxBackground.addLayer((TiledMapTileLayer) loadMapLayer(MAP_PARALLAX_1_LAYER_TAG));
        parallaxBackground.addLayer((TiledMapTileLayer) loadMapLayer(MAP_PARALLAX_2_LAYER_TAG));
        parallaxBackground.addLayer((TiledMapTileLayer) loadMapLayer(MAP_PARALLAX_3_LAYER_TAG));
    }

    /**
     * Load map properties, such as map width, height, etc...
     */
    private void loadMapProperties() {
        mapWidth = this.map.getProperties().get(MAP_WIDTH_PROPERTY_TAG, int.class);
        mapHeight = this.map.getProperties().get(MAP_HEIGHT_PROPERTY_TAG, int.class);
        tileWidth = this.map.getProperties().get(MAP_TILE_WIDTH_PROPERTY_TAG, int.class);
        tileHeight = this.map.getProperties().get(MAP_TILE_HEIGHT_PROPERTY_TAG, int.class);
    }

    /**
     * Just load the map layer from the map by the layer tag.
     * @param layerTag target layer tag.
     * @return loaded map layer.
     */
    private MapLayer loadMapLayer(final String layerTag) throws MapLayerNotFoundException {
        final MapLayer layer = map.getLayers().get(layerTag);
        if (layer == null) {
            throw new MapLayerNotFoundException(layerTag);
        }
        mapLayers.add(layer);
        return layer;
    }

    /**
     * Validation function ensures, that all of the needed map objects are loaded.
     * @throws MapObjectNotFoundException we have some of the objects loading problem.
     */
    private void validateObjectsLoading ()throws MapObjectNotFoundException {
        if (spawn == null)
            throw new MapObjectNotFoundException(MAP_SPAWN_OBJECT_TAG);
    }

    /**
     * Obtain the spawn point coordinates from the main spawn point object.
     * @return main spawn point coordinates.
     */
    public Vector2 getSpawnPoint(){
        final float xPos = spawn.getProperties().get("x", float.class);
        final float yPos = spawn.getProperties().get("y", float.class);
        return new Vector2(xPos, yPos);
    }

    public float getMapWidthTiles() {
        return mapWidth;
    }

    public float getMapWidth() {
        return mapWidth * tileWidth;
    }

    public float getMapHeight() {
        return mapHeight * mapHeight;
    }

    public float getMapHeightTiles() {
        return mapHeight;
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

    public TiledMap getTiledMap() {
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

    public Array<RectangleMapObject> getMapCollidables() {
        return mapCollidables;
    }

    public Array<MapLayer> getMapLayers() {
        return mapLayers;
    }

    public MapObjects getSpecialObjects() {
        return specialObjects;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public String getName() {
        return name;
    }

    public ParallaxLayer getParallaxBackground() {
        return parallaxBackground;
    }
}