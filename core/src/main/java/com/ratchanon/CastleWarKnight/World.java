package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.ratchanon.CastleWarKnight.components.*;
import com.ratchanon.CastleWarKnight.systems.RenderingSystem;
import com.ratchanon.CastleWarKnight.utils.EntityAnimation;
import org.jetbrains.annotations.NotNull;

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
        Entity knight = createKnight(20 + Asset.castleLevel.getRegionWidth() / 2, 100, Asset.knight, EntityComponent.STATE_IDLE, false);
        Entity bat = createEntity(100, 200, Asset.bat, EntityComponent.STATE_IDLE, true);
        Entity wolf = createEntity(100, 300, Asset.wolf, EntityComponent.STATE_IDLE, true);
        Entity witch = createEntity(100, 400, Asset.witch, EntityComponent.STATE_IDLE, true);
        Entity golem = createEntity(100, 500, Asset.golem, EntityComponent.STATE_IDLE, true);

        createCamera();
        createBackground();
        createStage();

        this.state = WORLD_STATE_RUNNING;
        this.level = 1;
    }

    public Entity createEntity(int x, int y, @NotNull EntityAnimation anim, int initState, boolean flip) {
        System.out.println("Create Entity");
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        animation.animations.put(EntityComponent.STATE_IDLE, anim.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, anim.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, anim.death);

        bounds.bounds.height = EntityComponent.HEIGHT;
        bounds.bounds.width = EntityComponent.WIDTH;

        position.pos.set(x, y, 0);
        System.out.println("Entity X: " + position.pos.x + "Entity Y:" + position.pos.y);

        state.set(initState);
        position.scale.set(2.0f, 2.0f);

        texture.flip = flip;

        entity.add(animation);
        entity.add(entityComponent);
        entity.add(bounds);
        entity.add(position);
        entity.add(state);
        entity.add(texture);

        engine.addEntity(entity);

        return entity;
    }

    public Entity createKnight(int x, int y, @NotNull EntityAnimation anim, int initState, boolean flip) {
        System.out.println("Create Knight");
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        KnightComponent entityComponent = engine.createComponent(KnightComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        animation.animations.put(EntityComponent.STATE_IDLE, anim.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, anim.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, anim.death);

        bounds.bounds.height = EntityComponent.HEIGHT;
        bounds.bounds.width = EntityComponent.WIDTH;

        position.pos.set(x, y, 0);

        state.set(initState);
        position.scale.set(2.0f, 2.0f);

        texture.flip = flip;

        entity.add(animation);
        entity.add(entityComponent);
        entity.add(bounds);
        entity.add(position);
        entity.add(state);
        entity.add(texture);

        engine.addEntity(entity);

        return entity;
    }

    private void createCamera() {
        System.out.println("Create Camera");
        Entity entity = engine.createEntity();

        CameraComponent camera = new CameraComponent();
        camera.camera = engine.getSystem(RenderingSystem.class).getCamera();


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

    private void createStage() {
//        int casteLevel = level % 6 + 1;

        // Create Castle for knight
        createCastle(20 + Asset.castleLevel.getRegionWidth() / 2, 120);

        for (int i = 0; i <= 2; i++) {
            createCastle(Settings.WIDTH - Asset.castleLevel.getRegionWidth() / 2 - 20, 128 * i + 120);

        }

    }

    private void createCastle(int x, int y) {
        Entity entity = engine.createEntity();

        CastleComponent castle = engine.createComponent(CastleComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        texture.region = Asset.castleLevel;
        position.pos.set(x, y, 8);

        entity.add(castle);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);
    }
}
