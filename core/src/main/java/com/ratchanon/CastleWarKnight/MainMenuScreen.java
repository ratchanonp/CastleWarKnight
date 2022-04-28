package com.ratchanon.CastleWarKnight;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ratchanon.CastleWarKnight.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainMenuScreen extends ScreenAdapter {

    CastleWarKnight game;
    OrthographicCamera camera;
    Vector2 touchPoint;

    PooledEngine engine;

    World world;

    float sourceX = 0f;

    Texture background;

    public MainMenuScreen(CastleWarKnight game) {
        this.game = game;
        camera = new OrthographicCamera(480, 860);
        camera.position.set(480 / 2, 860 / 2, 0);
        touchPoint = new Vector2();

        engine = new PooledEngine();
        world = new World(engine);

        engine.addSystem(new EntitySystem(world));
        engine.addSystem(new StateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.batcher));
        engine.addSystem(new BoundsSystem());


        world.createMainScreen();

        Asset.newBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

    }

    @Override
    public void render(float delta) {
        touchPoint.set(Gdx.input.getX(), Gdx.input.getY());

        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        sourceX -= 0.7;

        if (sourceX < -1 * Settings.WIDTH) {
            sourceX = 0f;
        }

        game.batcher.begin();
        game.batcher.draw(Asset.newBackground, sourceX, 0, Settings.WIDTH, Settings.HEIGHT);
        game.batcher.draw(Asset.newBackground, Settings.WIDTH + sourceX, 0, Settings.WIDTH, Settings.HEIGHT);
        game.batcher.draw(Asset.logo, Settings.WIDTH / 2 - ((561 * 0.85f) / 2) + 10, 600, Asset.logo.getRegionWidth() * 0.85f, Asset.logo.getRegionHeight() * 0.85f);

        float scaled = 1.25f;

        Rectangle startBound = new Rectangle(Settings.WIDTH / 2 - Asset.start.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.start.getRegionHeight() * scaled / 2, Asset.start.getRegionWidth() * scaled, Asset.start.getRegionHeight() * scaled);
        if (startBound.contains(touchPoint)) {
            game.batcher.draw(Asset.startHover, Settings.WIDTH / 2 - Asset.start.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.start.getRegionHeight() * scaled / 2, Asset.start.getRegionWidth() * scaled, Asset.start.getRegionHeight() * scaled);
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new GameScreen(game));
            }
        } else {
            game.batcher.draw(Asset.start, Settings.WIDTH / 2 - Asset.start.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.start.getRegionHeight() * scaled / 2, Asset.start.getRegionWidth() * scaled, Asset.start.getRegionHeight() * scaled);
        }


        Rectangle settingBound = new Rectangle(Settings.WIDTH / 2 - Asset.settings.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.settings.getRegionHeight() * scaled / 2 + 80, Asset.settings.getRegionWidth() * scaled, Asset.start.getRegionHeight() * scaled);
        if (settingBound.contains(touchPoint)) {
            game.batcher.draw(Asset.settingsHover, Settings.WIDTH / 2 - Asset.settingsHover.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.settingsHover.getRegionHeight() * scaled / 2 - 80, Asset.settingsHover.getRegionWidth() * scaled, Asset.settingsHover.getRegionHeight() * scaled);
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batcher.draw(Asset.settings, Settings.WIDTH / 2 - Asset.settings.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.settings.getRegionHeight() * scaled / 2 - 80, Asset.settings.getRegionWidth() * scaled, Asset.settings.getRegionHeight() * scaled);
        }
        game.batcher.end();


        update(delta);
    }

    private void update(float delta) {
        if (delta > 0.1f) delta = 0.1f;
        engine.update(delta);
    }
}
