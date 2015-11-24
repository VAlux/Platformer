package com.platformer.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.platformer.entities.RenderableEntity;
import com.platformer.maps.Map;
import com.platformer.screens.GameScreen;

/**
 * Created by Alvo on 23.11.2015.
 * Some useful methods to work with in-game maps.
 */
public class MapTools {

    /**
     * The static reference to the current world map.
     */
    private static final Map map = GameScreen.world.getMap();

    /**
     * Preloaded array for extracting tiles around actor.
     */
    private static final Array<Integer> tilesAround = new Array<>();

    /**
     * Method for extracting the tiles around the specified actor.
     * @param actor target actor.
     * @param width tiles amount in horizontal.
     * @param height tiles amount in vertical.
     * @return an array of tiles around the actor.
     */
    public static Array<Integer> getTilesAroundActor(RenderableEntity actor, int width, int height) {
        //Actor location in tiles.
        final int actorXPos = (int) (actor.getPosition().x / map.getTileWidth());
        final int actorYPos = (int) (actor.getPosition().y / map.getTileHeight());
        //Map tile offsets, originating from the actor location and using width and height  params.
        final int initialMapXOffset = MathUtils.clamp(actorXPos - width, 0, map.getBackgroundLayer().getWidth());
        final int initialMapYOffset = MathUtils.clamp(actorYPos - height, 0, map.getBackgroundLayer().getHeight());

        tilesAround.clear();

        for (int mapXPos = initialMapXOffset; mapXPos < actorXPos + width; mapXPos++) {
            for (int mapYPos = initialMapYOffset; mapYPos < actorYPos + height; mapYPos++) {
                if (mapXPos == actorXPos && mapYPos == actorYPos) {
                    tilesAround.add(-1); // THE ACTOR.
                } else {
                    tilesAround.add(map.getBackgroundLayer().getCell(mapXPos, mapYPos).getTile().getId());
                }
            }
        }

        return tilesAround;
    }
}
