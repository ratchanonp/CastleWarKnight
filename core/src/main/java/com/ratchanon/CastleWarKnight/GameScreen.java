package com.ratchanon.CastleWarKnight;


import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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

    ShapeRenderer shapeRenderer;


    private int state;

    public GameScreen(CastleWarKnight game) {

        this.game = game;
        state = GAME_RUNNING;
        camera = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        camera.position.set(Settings.WIDTH / 2, Settings.HEIGHT / 2, 0);
        touchPoint = new Vector3();

        engine = new PooledEngine();
        world = new World(engine);
        shapeRenderer = new ShapeRenderer();

        engine.addSystem(new EntitySystem(world));
        engine.addSystem(new CameraSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.batcher));
        engine.addSystem(new DragableSystem(camera));
        engine.addSystem(new TransformSystem());
        engine.addSystem(new PointRenderingSystem(game.batcher));
        engine.addSystem(new FightSystem(world));
        engine.addSystem(new AudioSystem());

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

        pauseSystems();

    }

    private void updateReady() {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystems();
        }
    }

    private void updateRunning(float deltaTime) {
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            pauseSystems();
        }

        if (World.enemyCount == 0) {
            state = GAME_LEVEL_END;
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
        game.batcher.draw(Asset.labelBackground, 20, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 40,
                0, 0,
                Asset.labelBackground.getRegionWidth() * 3, Asset.labelBackground.getRegionHeight() * 3,
                1, 1, 0f);
        Asset.font.setColor(1, 1, 1, 1);
        Asset.font.getData().setScale(0.55f);
        Asset.font.draw(game.batcher, "Level " + World.level, 35, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 5);
        game.batcher.draw(Asset.pauseButton, Settings.WIDTH - Asset.pauseButton.getRegionWidth() * 2f / 2 - 40, Settings.HEIGHT - Asset.pauseButton.getRegionHeight() * 2f / 2 - 40,
                0, 0,
                Asset.pauseButton.getRegionWidth() * 2f, Asset.pauseButton.getRegionHeight() * 2f,
                1, 1, 0f);

        Rectangle pauseButton = new Rectangle(Settings.WIDTH - Asset.pauseButton.getRegionWidth() / 2 - 40,
                Asset.pauseButton.getRegionHeight() / 2,
                Asset.pauseButton.getRegionWidth(),
                Asset.pauseButton.getRegionHeight());

        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        if (pauseButton.contains(touchPoint.x, touchPoint.y)) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                state = GAME_PAUSED;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Asset.playSound(Asset.clickSound);
            state = GAME_PAUSED;
        }


    }

    private void presentPaused() {
        game.batcher.draw(Asset.labelBackground, 20, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 40,
                0, 0,
                Asset.labelBackground.getRegionWidth() * 3, Asset.labelBackground.getRegionHeight() * 3,
                1, 1, 0f);
        Asset.font.setColor(1, 1, 1, 1);
        Asset.font.getData().setScale(0.55f);
        Asset.font.draw(game.batcher, "Level " + World.level, 35, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 5);
        game.batcher.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect(0, 0, Settings.WIDTH, Settings.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.batcher.begin();
        game.batcher.draw(Asset.gamePauseBox, Settings.WIDTH / 2 - Asset.gamePauseBox.getRegionWidth() * 1.5f / 2, Settings.HEIGHT / 2 - Asset.gamePauseBox.getRegionHeight() * 1.5f / 2, 0, 0, Asset.gamePauseBox.getRegionWidth() * 1.5f, Asset.gamePauseBox.getRegionHeight() * 1.5f, 1, 1, 0f);

        Rectangle resumeBound = new Rectangle(Settings.WIDTH / 2 - Asset.resume.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2, Asset.restart.getRegionWidth(), Asset.restart.getRegionHeight());
        Rectangle mainMenuBound = new Rectangle(Settings.WIDTH / 2 - Asset.resume.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2 + 80, Asset.restart.getRegionWidth(), Asset.restart.getRegionHeight());
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        if (Gdx.input.justTouched()) {
            if (resumeBound.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                state = GAME_RUNNING;
                resumeSystems();
            }
            if (mainMenuBound.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new MainMenuScreen(game));
            }
        }

        if (resumeBound.contains(touchPoint.x, touchPoint.y)) {
            game.batcher.draw(Asset.resumeHover, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2);
        } else {
            game.batcher.draw(Asset.resume, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2);
        }

        if (mainMenuBound.contains(touchPoint.x, touchPoint.y)) {
            game.batcher.draw(Asset.mainMenuHover, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2 - 80);
        } else {
            game.batcher.draw(Asset.mainMenu, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2 - 80);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Asset.playSound(Asset.clickSound);
            state = GAME_RUNNING;
            resumeSystems();
        }
    }

    private void presentLevelEnd() {
        game.batcher.draw(Asset.labelBackground, 20, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 40,
                0, 0,
                Asset.labelBackground.getRegionWidth() * 3, Asset.labelBackground.getRegionHeight() * 3,
                1, 1, 0f);
        Asset.font.setColor(1, 1, 1, 1);
        Asset.font.getData().setScale(0.55f);
        Asset.font.draw(game.batcher, "Level " + World.level, 35, Settings.HEIGHT - Asset.labelBackground.getRegionHeight() * 3 / 2 - 5);
        game.batcher.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect(0, 0, Settings.WIDTH, Settings.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.batcher.begin();
        Rectangle completeBoxBound = new Rectangle(Settings.WIDTH / 2 - Asset.gameStageCompleteBox.getRegionWidth() / 2,
                Settings.HEIGHT / 2 - Asset.gameStageCompleteBox.getRegionHeight() / 2,
                Asset.gameStageCompleteBox.getRegionWidth(),
                Asset.gameStageCompleteBox.getRegionHeight());

        if (completeBoxBound.contains(touchPoint.x, touchPoint.y)) {
            game.batcher.draw(Asset.gameStageCompleteBoxHover, Settings.WIDTH / 2 - Asset.gameStageCompleteBox.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.gameStageCompleteBox.getRegionHeight() / 2);
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                World.level++;
                game.setScreen(new GameScreen(game));
            }
        } else {
            game.batcher.draw(Asset.gameStageCompleteBox, Settings.WIDTH / 2 - Asset.gameStageCompleteBox.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.gameStageCompleteBox.getRegionHeight() / 2);
        }

    }

    private void presentGameOver() {
        game.batcher.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect(0, 0, Settings.WIDTH, Settings.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.batcher.begin();
        game.batcher.draw(Asset.gameOverBox, Settings.WIDTH / 2 - Asset.gameOverBox.getRegionWidth() * 1.5f / 2, Settings.HEIGHT / 2 - Asset.gameOverBox.getRegionHeight() * 1.5f / 2, 0, 0, Asset.gameOverBox.getRegionWidth() * 1.5f, Asset.gameOverBox.getRegionHeight() * 1.5f, 1, 1, 0f);
        Rectangle restartBounds = new Rectangle(Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2, Asset.restart.getRegionWidth(), Asset.restart.getRegionHeight());

        touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        if (Gdx.input.justTouched()) {
            if (restartBounds.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                World.level = 1;
                World.playerPoint = 5;
                System.out.println("Restart");
                game.setScreen(new GameScreen(game));
            }
        }

        if (restartBounds.contains(touchPoint.x, touchPoint.y)) {
            game.batcher.draw(Asset.restartHover, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2);
        } else {
            game.batcher.draw(Asset.restart, Settings.WIDTH / 2 - Asset.restart.getRegionWidth() / 2, Settings.HEIGHT / 2 - Asset.restart.getRegionHeight() / 2);
        }

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
        engine.getSystem(DragableSystem.class).setProcessing(true);
        engine.getSystem(TransformSystem.class).setProcessing(true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        drawUI();
    }
}
