package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.ratchanon.CastleWarKnight.Asset;
import com.ratchanon.CastleWarKnight.components.AudioComponent;
import com.ratchanon.CastleWarKnight.components.StateComponent;

public class AudioSystem extends IteratingSystem {

    private ComponentMapper<AudioComponent> am;
    private ComponentMapper<StateComponent> sm;

    public AudioSystem() {
        super(Family.all(AudioComponent.class, StateComponent.class).get());

        am = ComponentMapper.getFor(AudioComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AudioComponent audio = am.get(entity);
        StateComponent state = sm.get(entity);

        Sound sfx = audio.sound.get(state.get());

        if (sfx != null && state.time == 0) {
            Asset.playSound(sfx, 0.1f);
        }
    }
}
