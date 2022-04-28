package com.ratchanon.CastleWarKnight.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    public static Animation<TextureRegion> create(TextureRegion animationSheet, int frame) {
        TextureRegion[] animationFrame = animationSheet.split(animationSheet.getRegionWidth() / frame, animationSheet.getRegionHeight())[0];
        return new Animation<>(0.1f, animationFrame);
    }

    public static Animation<TextureRegion> create(TextureRegion animationSheet, int frame, float frameDuration) {
        TextureRegion[] animationFrame = animationSheet.split(animationSheet.getRegionWidth() / frame, animationSheet.getRegionHeight())[0];
        return new Animation<>(frameDuration, animationFrame);
    }

}
