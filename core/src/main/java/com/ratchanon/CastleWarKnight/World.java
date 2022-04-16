package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.ratchanon.CastleWarKnight.components.*;
import com.ratchanon.CastleWarKnight.systems.RenderingSystem;

import java.util.Random;

public class World {
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public final Random random;
    public int level;
    public int state;
    private PooledEngine engine;

    public World(PooledEngine engine) {
        this.random = new Random();
        this.engine = engine;
    }

    public void create() {
        Entity knight = createKnight();
        createCamera(knight);
        createBackground();

        this.state = WORLD_STATE_RUNNING;
        this.level = 1;
    }

    public Entity createKnight() {
        System.out.println("Create Knight");
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        KnightComponent knight = engine.createComponent(KnightComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        animation.animations.put(KnightComponent.STATE_IDLE, Asset.knightIdle);
        animation.animations.put(KnightComponent.STATE_ATTACK, Asset.knightAttack);
        animation.animations.put(KnightComponent.STATE_DEATH, Asset.knightDeath);

        bounds.bounds.height = KnightComponent.HEIGHT;
        bounds.bounds.width = KnightComponent.WIDTH;

        position.pos.set(100, 100, 0);

        state.set(KnightComponent.STATE_IDLE);

        entity.add(animation);
        entity.add(knight);
        entity.add(bounds);
        entity.add(position);
        entity.add(state);
        entity.add(texture);

        engine.addEntity(entity);

        return entity;
    }

    private void createCamera(Entity target) {
        System.out.println("Create Camera");
        Entity entity = engine.createEntity();

        CameraComponent camera = new CameraComponent();
        camera.camera = engine.getSystem(RenderingSystem.class).getCamera();
        camera.target = target;

        entity.add(camera);

        engine.addEntity(entity);
    }

    private void createBackground() {
        System.out.println("Create Background");
        Entity entity = engine.createEntity();

        BackgroundComponent background = engine.createComponent(BackgroundComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        texture.region = Asset.background;

        entity.add(background);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);
    }
}
