package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ratchanon.CastleWarKnight.components.*;
import com.ratchanon.CastleWarKnight.systems.RenderingSystem;
import com.ratchanon.CastleWarKnight.utils.EntityAsset;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static ArrayList<Rectangle> castleBoundList;
    public static int level = 1;
    public static int enemyCount;
    public static long playerPoint = 5;
    public final Random random;
    public int state;
    private PooledEngine engine;
    private long sumLevelScore;

    private ArrayList<Long> enemyPoints;

    public World(PooledEngine engine) {
        this.random = new Random();
        this.engine = engine;

        castleBoundList = new ArrayList<>();
        enemyCount = 0;
        sumLevelScore = playerPoint;
        enemyPoints = new ArrayList<>();
    }

    public static long nextLongBetween(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public void create() {
        Entity knight = createKnight(20 + Asset.castleLevel.getRegionWidth() / 2, 100, Asset.knight, EntityComponent.STATE_IDLE, false);

        createCamera();
        createBackground();
        createStage();

        this.state = WORLD_STATE_RUNNING;

    }

    public void createMainScreen() {
        createKnight(60, 95, Asset.knight, EntityComponent.STATE_RUN, false);
        createEntity(230, 95, Asset.golem, EntityComponent.STATE_RUN, false);
        createEntity(280, 195, Asset.bat, EntityComponent.STATE_RUN, false);
        createEntity(300, 95, Asset.witch, EntityComponent.STATE_RUN, false);
        createEntity(370, 95, Asset.wolf, EntityComponent.STATE_RUN, false);
    }

    public Entity createEntity(int x, int y, int initState, boolean flip) {
        enemyCount++;
        int randomEntity = random.nextInt(4);

        EntityAsset entityAsset;

        switch (randomEntity) {
            case 0:
                entityAsset = Asset.bat;
                break;
            case 1:
                entityAsset = Asset.golem;
                break;
            case 2:
                entityAsset = Asset.witch;
                break;
            default:
                entityAsset = Asset.wolf;
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
        AudioComponent audio = engine.createComponent(AudioComponent.class);

        entityComponent.entityType = EntityComponent.TYPE_ENEMY;

        int pointIndex = random.nextInt(enemyPoints.size());
        point.point = enemyPoints.get(pointIndex);
        enemyPoints.remove(pointIndex);

        animation.animations.put(EntityComponent.STATE_IDLE, entityAsset.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, entityAsset.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, entityAsset.death);

        audio.sound.put(EntityComponent.STATE_ATTACK, entityAsset.attackSound);
        audio.sound.put(EntityComponent.STATE_DEATH, entityAsset.deathSound);

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
        entity.add(audio);

        engine.addEntity(entity);

        return entity;
    }

    public Entity createEntity(int x, int y, EntityAsset anim, int initState, boolean flip) {
        System.out.println("Create Entity");
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        entityComponent.entityType = EntityComponent.TYPE_ENEMY;

        animation.animations.put(EntityComponent.STATE_IDLE, anim.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, anim.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, anim.death);
        animation.animations.put(EntityComponent.STATE_RUN, anim.run);

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

    public Entity createKnight(int x, int y, @NotNull EntityAsset entityAsset, int initState, boolean flip) {
        System.out.println("Create Knight" + playerPoint);
        Entity entity = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        DragableComponent drag = engine.createComponent(DragableComponent.class);
        PointComponent point = engine.createComponent(PointComponent.class);
        AudioComponent audio = engine.createComponent(AudioComponent.class);

        animation.animations.put(EntityComponent.STATE_IDLE, entityAsset.idle);
        animation.animations.put(EntityComponent.STATE_ATTACK, entityAsset.attack);
        animation.animations.put(EntityComponent.STATE_DEATH, entityAsset.death);
        animation.animations.put(EntityComponent.STATE_RUN, entityAsset.run);

        audio.sound.put(EntityComponent.STATE_ATTACK, entityAsset.attackSound);
        audio.sound.put(EntityComponent.STATE_DEATH, entityAsset.deathSound);

        point.point = playerPoint;

        bounds.bounds.height = EntityComponent.HEIGHT;
        bounds.bounds.width = EntityComponent.WIDTH + 100;

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
        entity.add(audio);

        engine.addEntity(entity);

        return entity;
    }

    private void createItem(int x, int y, Texture tex) {
        System.out.println("Create Items");
        Entity entity = engine.createEntity();

        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PointComponent point = engine.createComponent(PointComponent.class);
        AudioComponent audio = engine.createComponent(AudioComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);

        point.point = playerPoint / 2;
        sumLevelScore += playerPoint / 2;

        bounds.bounds.height = EntityComponent.HEIGHT;
        bounds.bounds.width = EntityComponent.WIDTH + 100;

        position.pos.set(x, y, 0);

        position.scale.set(1.5f, 1.5f);
        state.set(StateComponent.STATE_IDLE);

        texture.region = new TextureRegion(Asset.sword);


        entity.add(entityComponent);
        entity.add(bounds);
        entity.add(position);
        entity.add(texture);
        entity.add(point);
        entity.add(audio);
        entity.add(state);

        engine.addEntity(entity);
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

        position.scale.set(0.5f, 0.65f);
        position.pos.y = 0;

        if (World.level / 5 % 2 == 1) {
            texture.region = Asset.nightBackground;
        } else {
            texture.region = Asset.dayBackground;
        }

        entity.add(background);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);
    }

    private void createStage() {
        int casteLevel = (World.level % 4) + 2;
        System.out.println("Level" + World.level);
        System.out.println("CastleLevel" + casteLevel);

        if (random.nextInt() % 2 == 0) {
            createCastle(20 + Asset.castleLevel.getRegionWidth() / 2, 120, false, false);
            createCastle(20 + Asset.castleLevel.getRegionWidth() / 2, 120 + 128, false, true);
            createItem(20 + Asset.castleLevel.getRegionWidth() / 2, 120 + 128 - 20, Asset.sword);
        } else {
            createCastle(20 + Asset.castleLevel.getRegionWidth() / 2, 120, false, true);
        }

        for (int i = 0; i <= casteLevel; i++) {

            long score = nextLongBetween(sumLevelScore / 2, sumLevelScore);
            sumLevelScore += score;
            enemyPoints.add(score);
        }

        // Create Castle for knight


        for (int i = 0; i <= casteLevel; i++) {
            createCastle(Settings.WIDTH - Asset.castleLevel.getRegionWidth() / 2 - 20, 128 * i + 120, true, (i == casteLevel) ? true : false);
        }

    }

    private void createCastle(int x, int y, boolean isMonsterCastle, boolean isCastleTop) {
        System.out.println("Create Castle : " + x + " " + y);

        Entity entity = engine.createEntity();

        CastleComponent castle = engine.createComponent(CastleComponent.class);
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        texture.region = Asset.castleLevel;

        if (isCastleTop) {
            texture.isCastleTop = true;
        }

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
