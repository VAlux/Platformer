package com.platformer.stats;


import com.badlogic.gdx.math.MathUtils;

public class CharacterStats {

    public int level;

    public int health;
    public int energy;
    public int experience;

    public int maxEnergy;
    public int maxHealth;

    public int score;

    public int offence;
    public int defense;

    public float jumpVelocity;

    public void loadDefaults(){
        level = 1;
        health = maxHealth = 1000;
        energy = maxEnergy = 500;
        experience = 0;
        score = 0;

        offence = 50;
        defense = 10;

        jumpVelocity = 500.0f;
    }

    public void copy(final CharacterStats stats) {
        this.level = stats.level;
        this.experience = stats.experience;
        this.score = stats.score;
        this.health = stats.health;
        this.maxHealth = stats.maxHealth;
        this.energy = stats.energy;
        this.maxEnergy = stats.maxEnergy;
        this.offence = stats.offence;
        this.defense = stats.defense;
        this.jumpVelocity = stats.jumpVelocity;
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
                "\njumpVelocity = " + jumpVelocity;
    }
}