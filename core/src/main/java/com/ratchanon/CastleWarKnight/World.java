package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Rectangle;
import com.ratchanon.CastleWarKnight.components.*;
import com.ratchanon.CastleWarKnight.systems.RenderingSystem;
import com.ratchanon.CastleWarKnight.utils.EntityAnimation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class World {
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static ArrayList<Rectangle> castleBoundList;
    public static int level;
    public final Random random;
    public int state;
    private PooledEngine engine;

    public World(PooledEngine engine) {
        this.random = new Random();
        this.engine = engine;

        castleBoundList = new ArrayList<>();
    }

    public void create() {
        Entity knight = createKnight(20 + Asset.castleLevel.getRegionWidth() / 2, 100, Asset.knight, EntityComponent.STATE_IDLE, false);

        createCamera();
        createBackground();
        createStage();

        this.state = WORLD_STATE_RUNNING;
        World.level = 4;
    }

    public Entity createEntity(int x, int y, int initState, boolean flip) {
        int randomEntity = random.nextInt(4);

        EntityAnimation anim;

        switch (randomEntity) {
            case 0:
                anim = Asset.bat;
                break;
            case 1:
                anim = Asset.golem;
                break;
            case 2:
                anim = Asset.witch;
                break;
            default:
                anim = Asset.wolf;
                break;
        }


        System.out.println("Create Entity");
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PointComponent point = engine.createComponent(PointComponent.class);

        point.point = 100;

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
        entity.add(point);

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
        DragableComponent drag = engine.createComponent(DragableComponent.class);
        PointComponent point = engine.createComponent(PointComponent.class);

        animation.animations.put(EntityComponent.STATE_IDLE, anim.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, anim.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, anim.death);

        point.point = 100;

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
        entity.add(drag);
        entity.add(point);

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
        int casteLevel = (World.level % 4) + 2;
        System.out.println("Level" + World.level);
        System.out.println("CastleLevel" + casteLevel);

        // Create Castle for knight
        createCastle(20 + Asset.castleLevel.getRegionWidth() / 2, 120, false);

        for (int i = 0; i <= casteLevel; i++) {
            createCastle(Settings.WIDTH - Asset.castleLevel.getRegionWidth() / 2 - 20, 128 * i + 120, true);

        }

    }

    private void createCastle(int x, int y, boolean isMonsterCastle) {
        System.out.println("Create Castle : " + x + " " + y);

        Entity entity = engine.createEntity();

        CastleComponent castle = engine.createComponent(CastleComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        texture.region = Asset.castleLevel;
        position.pos.set(x, y, 8);

        castleBoundList.add(new Rectangle(x - Asset.castleLevel.getRegionWidth() / 2,
                y - Asset.castleLevel.getRegionHeight() / 2,
                Asset.castleLevel.getRegionWidth(),
                Asset.castleLevel.getRegionHeight()));

        bounds.bounds.x = x - Asset.castleLevel.getRegionWidth() / 2;
        bounds.bounds.y = y - Asset.castleLevel.getRegionHeight() / 2;
        bounds.bounds.width = Asset.castleLevel.getRegionWidth();
        bounds.bounds.height = Asset.castleLevel.getRegionHeight();

        entity.add(castle);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);

        if (isMonsterCastle) {
            createEntity(x + 35, y - Asset.castleLevel.getRegionHeight() / 2 + 40, EntityComponent.STATE_IDLE, true);
        }
    }
}
