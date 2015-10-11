package com.platformer;

/**
 * Created by Alvo on 10.10.2015.
 */
public class Constants {
    //Atlas sources.
    public static final String ATL_CHARACTERS_1 = "tilesets/atlases/character-elven.png";
    public static final String ATL_CHARACTERS_2 = "tilesets/atlases/characters.png";
    public static final String ATL_EXPLOSION_SMALL = "tilesets/fx/explosion_small.png";

    //TMX sources.
    public static final String TMX_MAP_JUNGLE = "maps/jungle.tmx";

    //Generic Game properties.
    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 768;
    public static final boolean GAME_DEBUG_INFO_ENABLED = true;

    //Map object|layer tagging.
    public static final String MAP_WIDTH_PROPERTY_TAG = "width";
    public static final String MAP_HEIGHT_PROPERTY_TAG = "height";
    public static final String MAP_TILE_WIDTH_PROPERTY_TAG = "tilewidth";
    public static final String MAP_TILE_HEIGHT_PROPERTY_TAG = "tileheight";
    public static final String MAP_COLL_OBJECTS_LAYER_TAG = "collidable-objects";
    public static final String MAP_SPEC_OBJECTS_LAYER_TAG = "special-objects";
    public static final String MAP_SPAWN_OBJECT_TAG = "spawn-point";
    public static final String MAP_FOREGROUND_LAYER_TAG = "foreground";
    public static final String MAP_BACKGROUND_LAYER_TAG = "background";

    ///Game mechanics specifics.
    public static final float GM_COLLISION_GAP = 0.01f;
    public static final float GM_GRAVITY = 900.0f;
}
