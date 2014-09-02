package com.platformer.entities;


public class ActorStats {

    public int level;
    public int health;
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

    public ActorStats() {
        level = 1;
        score = 0;
        health = 100;
        energy = 100;
        experience = 0;
        offence = 10;
        defense = 10;

        GRAVITY = 900.0f;
        ACCELERATION = 20.0f;
        JUMP_VELOCITY = 500.0f;
        MAX_VELOCITY = 500.0f;
        FRICTION = 0.9f;
    }
}
