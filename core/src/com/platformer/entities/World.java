package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.platformer.entities.characters.Elf;
import com.platformer.entities.enemies.SeekerMob;
import com.platformer.exceptions.MapLayerNotFoundException;
import com.platformer.exceptions.MapObjectNotFoundException;
import com.platformer.items.ItemsPool;
import com.platformer.maps.Map;

import java.util.ArrayList;
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
    private ArrayList<RenderableEntity> renderableActors;

    /**
     * Sub-list of the actors list, contains only references to the characters in the world.
     */
    private ArrayList<Char> chars;

    /**
     * Sub-list of the actors list, contains only references to the mobs in the world.
     */
    private ArrayList<Mob> mobs;

    public World() {
        actors = new CopyOnWriteArrayList<Actor>();
        renderableActors = new ArrayList<RenderableEntity>();
        chars = new ArrayList<Char>();
        mobs = new ArrayList<Mob>();
    }

    public void init() {
        loadMap("maps/jungle.tmx");
        createCharacters();
        ItemsPool.initPool();
    }

    public void addRenderableActor(RenderableEntity actor) {
        addActor(actor);
        renderableActors.add(actor);
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
        player = new Elf(map, 10);
        addCharacter(player);
    }

    private void loadMap(String mapPath){
        try {
            map = new Map(mapPath);
        } catch (MapObjectNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        } catch (MapLayerNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    @Override
    public void act(float delta) {
        for (Actor actor : actors) {
            actor.act(delta);
        }
    }

    @Override
    public void destroy() {
        for (Actor actor : actors) {
            actor.destroy();
        }
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

    public ArrayList<RenderableEntity> getRenderableActors() {
        return renderableActors;
    }

    public ArrayList<Char> getChars() {
        return chars;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }
}