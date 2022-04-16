package com.ratchanon.CastleWarKnight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class MainMenuScreen extends ScreenAdapter {

    CastleWarKnight game;
    OrthographicCamera camera;
    Vector3 touchPoint;

    public MainMenuScreen(CastleWarKnight game) {
        this.game = game;
        camera = new OrthographicCamera(480, 860);
        camera.position.set(480 / 2, 860 / 2, 0);
        touchPoint = new Vector3();
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.begin();
        game.batcher.draw(Asset.background, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        game.batcher.draw(Asset.logo, Settings.WIDTH / 2 - ((561 * 0.85f) / 2) + 10, 600, Asset.logo.getRegionWidth() * 0.85f, Asset.logo.getRegionHeight() * 0.85f);
        Asset.font.getData().setScale(0.85f);
        Asset.font.draw(game.batcher, "Tab Screen to begin", 0, 200, Settings.WIDTH, Align.center, true);

        game.batcher.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
