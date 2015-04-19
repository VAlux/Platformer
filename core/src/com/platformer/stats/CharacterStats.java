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
        level = stats.level;
        experience = stats.experience;
        score = stats.score;

        health = stats.health;
        maxHealth = stats.maxHealth;
        energy = stats.energy;
        maxEnergy = stats.maxEnergy;

        offence = stats.offence;
        defense = stats.defense;

        jumpVelocity = stats.jumpVelocity;
    }
    
    public void addStats(final CharacterStats stats){
        level = MathUtils.clamp(level + stats.level, 1, level + stats.level);
        experience = MathUtils.clamp(experience + stats.experience, 0, experience + stats.experience);
        score = MathUtils.clamp(score + stats.score, 0, score + stats.score);

        maxHealth = MathUtils.clamp(maxHealth + stats.maxHealth, 0, maxHealth + stats.maxHealth);
        health = MathUtils.clamp(health + stats.health + stats.maxHealth, 0, maxHealth);
        maxEnergy = MathUtils.clamp(maxEnergy + stats.maxEnergy, 0, maxEnergy + stats.maxEnergy);
        energy = MathUtils.clamp(energy + stats.energy + stats.maxEnergy, 0, maxEnergy);
        offence = MathUtils.clamp(offence + stats.offence, 1, offence + stats.offence);
        defense = MathUtils.clamp(defense + stats.defense, 1, defense + stats.defense);

        jumpVelocity = MathUtils.clamp(jumpVelocity + stats.jumpVelocity, 100, 1000);
    }

    public void subtractStats(final CharacterStats stats){
        level = MathUtils.clamp(level - stats.level, 1, level - stats.level);
        experience = MathUtils.clamp(experience - stats.experience, 0, experience - stats.experience);
        score = MathUtils.clamp(score - stats.score, 0, score - stats.score);

        maxHealth = MathUtils.clamp(maxHealth - stats.maxHealth, 0, maxHealth - stats.maxHealth);
        health = MathUtils.clamp(health - stats.health - stats.maxHealth, 0, maxHealth);
        maxEnergy = MathUtils.clamp(maxEnergy - stats.maxEnergy - stats.maxEnergy, 0, maxEnergy - stats.maxEnergy);
        energy = MathUtils.clamp(energy - stats.energy, 0, maxEnergy);
        offence = MathUtils.clamp(offence - stats.offence, 1, offence - stats.offence);
        defense = MathUtils.clamp(defense - stats.defense, 1, defense - stats.defense);

        jumpVelocity = MathUtils.clamp(jumpVelocity - stats.jumpVelocity, 100, 1000);
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
