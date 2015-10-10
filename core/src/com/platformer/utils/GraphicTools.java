package com.platformer.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Some useful tools, which helps to comfortable work with graphics in libGDX.
 */
public class GraphicTools {

    /**
     * Extract the key frames from the texture atlas.
     * @param atlas source texture atlas.
     * @param begin start frame index.
     * @param amount frames amount to extract from the start frame index.
     * @return Array of texture regions.
     */
    public static TextureRegion[] extractAnimation(TextureRegion[][] atlas, int begin, int amount){
        int frameIndex = 0;
        int framesAdded = 0;
        final TextureRegion[] frames = new TextureRegion[amount];
        for (TextureRegion[] textureRegionRows : atlas) {
            for (TextureRegion splittedTexture : textureRegionRows) {
                if (frameIndex++ >= begin && framesAdded < amount)
                    frames[framesAdded++] = splittedTexture;
                else if (framesAdded == amount)
                    return frames;
            }
        }
        return frames;
    }

    /**
     * Flip the animation key frames.
     * @param animation source animation.
     * @param horizontal flip in horizontal direction?
     * @param vertical flip in vertical direction?
     * @return new flipped animation.
     */
    public static Animation flipAnimation(Animation animation, boolean horizontal, boolean vertical) {
        if (!horizontal && !vertical) return animation; // no need to flip, lol.
        final TextureRegion[] originalFrames = animation.getKeyFrames();
        TextureRegion[] flippedFrames = new TextureRegion[originalFrames.length];
        for (int i = 0; i < originalFrames.length; i++) {
            flippedFrames[i] = new TextureRegion(originalFrames[i]);
            flippedFrames[i].flip(horizontal, vertical);
        }
        return new Animation(animation.getFrameDuration(), flippedFrames);
    }
}