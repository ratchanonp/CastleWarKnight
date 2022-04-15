package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.ratchanon.CastleWarKnight.systems.*;

public class GameScreen extends ScreenAdapter {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    CastleWarKnight game;

    OrthographicCamera camera;
    Vector3 touchPoint;
    World world;
    PooledEngine engine;


    private int state;

    public GameScreen(CastleWarKnight game) {

        this.game = game;
        state = GAME_READY;
        camera = new OrthographicCamera(720, 1280);
        camera.position.set(720 / 2, 1280 / 20, 0);
        touchPoint = new Vector3();

        engine = new PooledEngine();
        world = new World(engine);

        engine.addSystem(new KnightSystem(world));
        engine.addSystem(new CameraSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.batcher));

        engine.getSystem(BackgroundSystem.class).setCamera(engine.getSystem(RenderingSystem.class).getCamera());

        world.create();

        resumeSystems();

    }

    private void update(float delta) {
        if (delta > 0.1f) delta = 0.1f;

        engine.update(delta);
    }

    private void draw() {
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);
        game.batcher.begin();

        game.batcher.end();
    }

    private void pauseSystems() {
        engine.getSystem(KnightSystem.class).setProcessing(false);
        engine.getSystem(BoundsSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
    }

    private void resumeSystems() {
        engine.getSystem(KnightSystem.class).setProcessing(true);
        engine.getSystem(BoundsSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
    }

    @Override
    public void render(float delta) {

        update(delta);
        draw();
    }
}
