package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ratchanon.CastleWarKnight.World;
import com.ratchanon.CastleWarKnight.components.KnightComponent;
import com.ratchanon.CastleWarKnight.components.StateComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

public class KnightSystem extends IteratingSystem {
    private static final Family family = Family.all(KnightComponent.class, StateComponent.class, TransformComponent.class).get();
    private World world;

    private ComponentMapper<KnightComponent> km;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<TransformComponent> tm;

    public KnightSystem(World world) {
        super(family);

        this.world = world;
        km = ComponentMapper.getFor(KnightComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent t = tm.get(entity);
        StateComponent state = sm.get(entity);
        KnightComponent knight = km.get(entity);
    }
}
