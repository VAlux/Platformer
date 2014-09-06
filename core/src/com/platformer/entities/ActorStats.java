package com.platformer.entities;


import com.badlogic.gdx.math.MathUtils;

public class ActorStats {

    public int level;
    public int experience;

    public int score;

    public int health;
    public int maxHealth;
    public int energy;
    public int maxEnergy;

    public int offence;
    public int defense;

    public float GRAVITY;
    public float ACCELERATION;
    public float JUMP_VELOCITY;
    public float MAX_VELOCITY;
    public float FRICTION;

    public void loadDefaults(){
        level = 1;
        experience = 0;
        score = 0;

        maxHealth = 1000;
        health = maxHealth;
        maxEnergy = 500;
        energy = maxEnergy;

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
        experience = stats.experience;
        score = stats.score;

        health = stats.health;
        maxHealth = stats.maxHealth;
        energy = stats.energy;
        maxEnergy = stats.maxEnergy;

        offence = stats.offence;
        defense = stats.defense;

        GRAVITY = stats.GRAVITY;
        ACCELERATION = stats.ACCELERATION;
        JUMP_VELOCITY = stats.JUMP_VELOCITY;
        MAX_VELOCITY = stats.MAX_VELOCITY;
        FRICTION = stats.FRICTION;
    }
    
    public void addStats(final ActorStats stats){
        level = MathUtils.clamp(level + stats.level, 1, level + stats.level);
        experience = MathUtils.clamp(experience + stats.experience, 0, experience + stats.experience);
        score = MathUtils.clamp(score + stats.score, 0, score + stats.score);

        maxHealth = MathUtils.clamp(maxHealth + stats.maxHealth, 0, maxHealth + stats.maxHealth);
        health = MathUtils.clamp(health + stats.health + stats.maxHealth, 0, maxHealth);
        maxEnergy = MathUtils.clamp(maxEnergy + stats.maxEnergy, 0, maxEnergy + stats.maxEnergy);
        energy = MathUtils.clamp(energy + stats.energy + stats.maxEnergy, 0, maxEnergy);
        offence = MathUtils.clamp(offence + stats.offence, 1, offence + stats.offence);
        defense = MathUtils.clamp(defense + stats.defense, 1, defense + stats.defense);

        GRAVITY = MathUtils.clamp(GRAVITY + stats.GRAVITY, 100, 1500);
        ACCELERATION = MathUtils.clamp(ACCELERATION + stats.ACCELERATION, 1, 50);
        JUMP_VELOCITY = MathUtils.clamp(JUMP_VELOCITY + stats.JUMP_VELOCITY, 100, 1000);
        MAX_VELOCITY = MathUtils.clamp(MAX_VELOCITY + stats.MAX_VELOCITY, 100, 1000);
        FRICTION = MathUtils.clamp(FRICTION + stats.FRICTION, 0.001f, 5f);
    }

    public void subtractStats(final ActorStats stats){
        level = MathUtils.clamp(level - stats.level, 1, level - stats.level);
        experience = MathUtils.clamp(experience - stats.experience, 0, experience - stats.experience);
        score = MathUtils.clamp(score - stats.score, 0, score - stats.score);

        maxHealth = MathUtils.clamp(maxHealth - stats.maxHealth, 0, maxHealth - stats.maxHealth);
        health = MathUtils.clamp(health - stats.health, 0, maxHealth);
        maxEnergy = MathUtils.clamp(maxEnergy - stats.maxEnergy, 0, maxEnergy - stats.maxEnergy);
        energy = MathUtils.clamp(energy - stats.energy, 0, maxEnergy);
        offence = MathUtils.clamp(offence - stats.offence, 1, offence - stats.offence);
        defense = MathUtils.clamp(defense - stats.defense, 1, defense - stats.defense);

        GRAVITY = MathUtils.clamp(GRAVITY - stats.GRAVITY, 100, 1500);
        ACCELERATION = MathUtils.clamp(ACCELERATION - stats.ACCELERATION, 1, 50);
        JUMP_VELOCITY = MathUtils.clamp(JUMP_VELOCITY - stats.JUMP_VELOCITY, 100, 1000);
        MAX_VELOCITY = MathUtils.clamp(MAX_VELOCITY - stats.MAX_VELOCITY, 100, 1000);
        FRICTION = MathUtils.clamp(FRICTION - stats.FRICTION, 0.001f, 5f);
    }
    
    @Override
    public String toString() {
        return  "level = " + level +
                "\nhealth = " + health + "/" + maxHealth +
                "\nenergy = " + energy + "/" + maxEnergy +
                "\nexperience = " + experience +
                "\noffence = " + offence +
                "\ndefense = " + defense +
                "\nscore = " + score +
                "\nGRAVITY = " + GRAVITY +
                "\nACCELERATION = " + ACCELERATION +
                "\nJUMP_VELOCITY = " + JUMP_VELOCITY +
                "\nMAX_VELOCITY = " + MAX_VELOCITY +
                "\nFRICTION = " + FRICTION;
    }

}
