package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.ratchanon.CastleWarKnight.Settings;
import com.ratchanon.CastleWarKnight.World;
import com.ratchanon.CastleWarKnight.components.KnightComponent;
import com.ratchanon.CastleWarKnight.components.StateComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

public class KnightSystem extends IteratingSystem {
    private static final Family family = Family.all(KnightComponent.class, StateComponent.class, TransformComponent.class).get();
    private World world;

    private Vector3 tmp = new Vector3();

    private ComponentMapper<KnightComponent> km;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<TransformComponent> tm;

    public KnightSystem(World world) {
        super(Family.all(KnightComponent.class, StateComponent.class, TransformComponent.class).get());

        this.world = world;
        km = ComponentMapper.getFor(KnightComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    public void setPos(Vector3 pos) {
        tmp = pos;
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

        if (t.pos != tmp && !tmp.equals(new Vector3(0, 0, 0))) {

            t.pos.set(tmp.x, Settings.HEIGHT - tmp.y, 0);

            if (t.pos.x < 0) {
                t.pos.set(0, t.pos.y, 0);
            }
            if (t.pos.x > Settings.WIDTH) {
                t.pos.set(Settings.WIDTH, t.pos.y, 0);
            }
            if (t.pos.y < 100) {
                t.pos.set(t.pos.x, 100, 0);
            }
            if (t.pos.x > Settings.HEIGHT) {
                t.pos.set(t.pos.x, Settings.HEIGHT, 0);
            }
        }
    }
}
