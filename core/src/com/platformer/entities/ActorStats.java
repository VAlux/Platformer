package com.platformer.entities;


public class ActorStats {

    public int level;
    public int maxHealth;
    public int health;
    public int maxEnergy;
    public int energy;
    public int experience;
    public int offence;
    public int defense;
    public int score;

    public float GRAVITY;
    public float ACCELERATION;
    public float JUMP_VELOCITY;
    public float MAX_VELOCITY;
    public float FRICTION;

    public void loadDefaults(){
        level = 1;
        score = 0;
        health = maxHealth = 1000;
        energy = maxEnergy = 500;
        experience = 0;
        offence = 50;
        defense = 10;

        GRAVITY = 900.0f;
        ACCELERATION = 20.0f;
        JUMP_VELOCITY = 500.0f;
        MAX_VELOCITY = 500.0f;
        FRICTION = 0.9f;
    }

    public void copy(final ActorStats stats) {
        level = stats.level;
        score = stats.score;
        health = maxHealth = stats.health;
        energy = maxEnergy = stats.energy;
        experience = stats.experience;
        offence = stats.offence;
        defense = stats.defense;

        GRAVITY = stats.GRAVITY;
        ACCELERATION = stats.ACCELERATION;
        JUMP_VELOCITY = stats.JUMP_VELOCITY;
        MAX_VELOCITY = stats.MAX_VELOCITY;
        FRICTION = stats.FRICTION;
    }

    @Override
    public String toString() {
        return     "level=" + level +
                "\n health=" + health +
                "\n energy=" + energy +
                "\n experience=" + experience +
                "\n offence=" + offence +
                "\n defense=" + defense +
                "\n score=" + score +
                "\n GRAVITY=" + GRAVITY +
                "\n ACCELERATION=" + ACCELERATION +
                "\n JUMP_VELOCITY=" + JUMP_VELOCITY +
                "\n MAX_VELOCITY=" + MAX_VELOCITY +
                "\n FRICTION=" + FRICTION;
    }
}
