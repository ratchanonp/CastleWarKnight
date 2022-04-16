package com.ratchanon.CastleWarKnight.utils;

import com.badlogic.gdx.graphics.g2d.Animation;

public class EntityAnimation {
    public Animation idle;
    public Animation attack;
    public Animation death;

    public void setLoop() {
        idle.setPlayMode(Animation.PlayMode.LOOP);
        attack.setPlayMode(Animation.PlayMode.LOOP);
        death.setPlayMode(Animation.PlayMode.LOOP);
    }
}
