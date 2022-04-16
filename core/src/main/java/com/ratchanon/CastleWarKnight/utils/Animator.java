package com.ratchanon.CastleWarKnight.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    public static Animation create(TextureRegion animationSheet, int frame) {
        TextureRegion[][] tmp = TextureRegion.split(animationSheet.getTexture(), animationSheet.getRegionWidth() / frame, animationSheet.getRegionHeight());
        return new Animation(0.1f, tmp[0]);
    }

}
