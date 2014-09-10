package com.platformer.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tools {
    public static TextureRegion[] extractAnimation(final TextureRegion[][] splittedTextureAtlas, final int startFrame, final int framesAmount){
        int frameIndex = 0;
        int framesAdded = 0;
        final TextureRegion[] frames = new TextureRegion[framesAmount];
        for (TextureRegion[] textureRegionRows : splittedTextureAtlas) {
            for (TextureRegion splittedTexture : textureRegionRows) {
                if (frameIndex++ >= startFrame && framesAdded < framesAmount)
                    frames[framesAdded++] = splittedTexture;
                else if (framesAdded == framesAmount)
                    return frames;
            }
        }
        return frames;
    }
}
