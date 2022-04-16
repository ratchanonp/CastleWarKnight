package com.ratchanon.CastleWarKnight;


import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
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
        state = GAME_RUNNING;
        camera = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        camera.position.set(Settings.WIDTH / 2, Settings.HEIGHT / 2, 0);
        touchPoint = new Vector3();

        engine = new PooledEngine();
        world = new World(engine);

        engine.addSystem(new EntitySystem(world));
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
        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateGameOver() {
    }

    private void updateLevelEnd() {
    }

    private void updatePaused() {
    }

    private void updateReady() {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystems();
        }
    }

    private void updateRunning(float deltaTime) {

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos = touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            System.out.println(touchPos);

            engine.getSystem(KnightSystem.class).setPos(touchPos);
        }
    }

    private void drawUI() {
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);
        game.batcher.begin();
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_LEVEL_END:
                presentLevelEnd();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        game.batcher.end();
    }

    private void presentReady() {
    }

    private void presentRunning() {
        Asset.font.draw(game.batcher, "Game Running", 16, 860 - 20);
    }

    private void presentPaused() {
    }

    private void presentLevelEnd() {
    }

    private void presentGameOver() {
    }

    private void pauseSystems() {
        engine.getSystem(EntitySystem.class).setProcessing(false);
        engine.getSystem(BoundsSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
    }

    private void resumeSystems() {
        engine.getSystem(EntitySystem.class).setProcessing(true);
        engine.getSystem(BoundsSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        drawUI();
    }
}
