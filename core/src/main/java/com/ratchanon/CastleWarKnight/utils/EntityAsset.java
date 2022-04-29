package com.ratchanon.CastleWarKnight.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;

public class EntityAsset {
    public Animation idle;
    public Animation attack;
    public Animation death;

    public Sound attackSound;
    public Sound deathSound;

    public Animation run;

    public EntityAsset(Animation idle, Animation attack, Animation death, Animation run, Sound attackSound, Sound deathSound) {
        this.idle = idle;
        this.attack = attack;
        this.death = death;
        this.run = run;

        this.attackSound = attackSound;
        this.deathSound = deathSound;

        this.setLoop();
    }

    public void setLoop() {
        idle.setPlayMode(Animation.PlayMode.LOOP);
        attack.setPlayMode(Animation.PlayMode.LOOP);
        death.setPlayMode(Animation.PlayMode.LOOP);
        run.setPlayMode(Animation.PlayMode.LOOP);
    }
}
