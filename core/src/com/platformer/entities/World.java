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

public class World extends Actor {

    public Map map;
    public ArrayList<Character> characters;
    public ArrayList<Mob> mobs;
    public Player player;

    public World() {
        loadMap();
        createCharacters();
        ItemsPool.initPool();
    }

    private void createCharacters(){
        characters = new ArrayList<Character>();
        createPlayer();
        createMobs();
    }

    private void createMobs(){
        mobs = new ArrayList<Mob>();

        //TODO: WTF? Fix that magic numbers below.
        SeekerMob smob = new SeekerMob(map, new Vector2(27.0f * 32.0f, 16.0f * 32.0f), characters);
        mobs.add(smob);

        for (Mob mob : mobs){
            characters.add(mob);
        }
    }

    private void createPlayer(){
        player = new Elf(map, 10);
        characters.add(player);
    }

    private void loadMap(){
        try {
            map = new Map("maps/greece.tmx");
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
        player.act(delta);

        for (Mob mob : mobs)
            mob.act(delta);
    }
}