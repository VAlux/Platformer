package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.platformer.Constants;
import com.platformer.entities.characters.Elf;
import com.platformer.entities.enemies.SeekerMob;
import com.platformer.entities.projectiles.Projectile;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;
import com.platformer.maps.Map;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class World extends Actor {

    /**
     * World map.
     */
    private Map map;

    /**
     * Controllable player.
     */
    private Player player;

    /**
     * All of the actors in the world.
     */
    private CopyOnWriteArrayList<Actor> actors;

    /**
     * Sub-list of the actors list, contains only references to the renderable actors in the world.
     */
    private Array<RenderableEntity> renderableActors;

    /**
     * Sub-list of the actors list, contains only active projectiles in the world.
     */
    private Array<Projectile> projectiles;

    /**
     * Sub-list of the actors list, contains only references to the characters in the world.
     */
    private Array<Char> chars;

    /**
     * Sub-list of the actors list, contains only references to the mobs in the world.
     */
    private Array<Mob> mobs;

    /**
     * All of the objects, that could be collided.
     */
    private Array<Rectangle> collisionObjects;

    public World() {
        actors = new CopyOnWriteArrayList<>();
        renderableActors = new Array<>();
        chars = new Array<>();
        mobs = new Array<>();
        projectiles = new Array<>();
    }

    /**
     * World initialization sequence.
     * 1 -> load the map.
     * 2 -> create characters.
     * 3 -> collisions initialization.
     */
    public void init() {
        loadMap(Constants.TMX_MAP_JUNGLE);
        createCharacters();
        initCollisionsArray();
    }

    public void addRenderableActor(RenderableEntity actor) {
        addActor(actor);
        renderableActors.add(actor);
    }

    public void addProjectile(Projectile projectile) {
        addRenderableActor(projectile);
        projectiles.add(projectile);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void addCharacter(Char character) {
        addRenderableActor(character);
        chars.add(character);
    }

    public void addMob(Mob mob) {
        addCharacter(mob);
        mobs.add(mob);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public void removeRenderableActor(RenderableEntity actor) {
        renderableActors.removeValue(actor, false);
        removeActor(actor);
    }

    private void createCharacters(){
        createPlayer();
        createMobs();
    }

    private void createMobs(){
        //TODO: hardcoded testing location of the mob.
        SeekerMob seekerMob = new SeekerMob(new Vector2(27.0f * 32.0f, 16.0f * 32.0f));
        addMob(seekerMob);
    }

    private void createPlayer(){
        player = new Elf(map);
        addCharacter(player);
    }

    /**
     * Just load the mop from tmx file, using the specified path as a location.
     * Also here we handle all of the possible exceptions, which can occur during the map loading process.
     * @param mapPath path to the map tmx file.
     */
    private void loadMap(String mapPath){
        try {
            map = new Map(mapPath);
        } catch (MapObjectNotFoundException | MapLayerNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Sequence for the initialization of the collisions array.
     * 1 -> load all of the static map collision objects.
     * 2 -> load characters bounding rectangles as collidables.
     */
    private void initCollisionsArray() {
        //add all of the static map collision objects.
        collisionObjects = new Array<>(map.getMapCollidables().size);
        for (RectangleMapObject collidable : map.getMapCollidables()) {
            collisionObjects.add(collidable.getRectangle());
        }
        //add all of the characters as the collidables.
        for (Char character : chars) {
            collisionObjects.add(character.getBounds());
        }
    }

    public void addCollidableObject(Rectangle collidableRect) {
        collisionObjects.add(collidableRect);
    }

    @Override
    public void act(float delta) {
        actors.forEach(actor -> actor.act(delta));
    }

    @Override
    public void destroy() {
        actors.forEach(com.platformer.entities.Actor::destroy);
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public CopyOnWriteArrayList<Actor> getActors() {
        return actors;
    }

    public Array<RenderableEntity> getRenderableActors() {
        return renderableActors;
    }

    public Array<Char> getChars() {
        return chars;
    }

    public Array<Mob> getMobs() {
        return mobs;
    }

    public Array<Rectangle> getCollisionObjects() {
        return collisionObjects;
    }

    public Array<Projectile> getProjectiles() {
        return projectiles;
    }
}