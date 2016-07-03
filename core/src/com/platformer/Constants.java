package com.platformer;

import com.platformer.maps.MapResourceIdentifier;

/**
 * Created by Alvo on 10.10.2015.
 * All in-game constants, static settings and String literals(Paths, Tags, Aliases).
 */
public class Constants {
    //Atlas sources.
    public static final String ATL_GREEN_ELF = "tilesets/atlases/character-elven.png";
    public static final String ATL_KIRBY = "tilesets/atlases/kirby1.png";
    public static final String ATL_EXPLOSION_SMALL = "tilesets/fx/explosion_small.png";

    //TMX sources.
    public static final MapResourceIdentifier TMX_MAP_JUNGLE = new MapResourceIdentifier("Jungle", "maps/jungle.tmx");

    //Generic Game properties.
    public static final int PROP_GAME_WIDTH = 1024;
    public static final int PROP_GAME_HEIGHT = 768;
    public static final float PROP_GAME_SCREEN_SCALE_FACTOR = 0.9f;
    public static final boolean PROP_DEBUG_INFO_ENABLED = true;

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
    public static final String MAP_PARALLAX_1_LAYER_TAG = "parallax1";
    public static final String MAP_PARALLAX_2_LAYER_TAG = "parallax2";
    public static final String MAP_PARALLAX_3_LAYER_TAG = "parallax3";

    ///General Game mechanics specifics.
    public static final float GM_COLLISION_GAP = 0.01f;
    public static final float GM_GRAVITY = 900.0f;
    public static final int GM_DEF_ACTOR_BOUNDS_SIZE = 32;
    public static final float GM_DEF_CAMERA_LERP_FACTOR = 3.0f;
    //Character-specific
    public static final int GM_WALL_JUMP_KICKBACK_ACC_FACTOR = 16;

    //Abilities
    public static final float GM_FIREBALL_COOLDOWN_TIME = 0.1f;
    public static final int GM_FIREBALL_ENERGY_COST = 10;
}