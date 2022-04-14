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
        camera = new OrthographicCamera(720, 1280);
        camera.position.set(720 / 2, 1280 / 2, 0);
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
        game.batcher.draw(Asset.background, 0, 0, 720, 1280);
        game.batcher.draw(Asset.logo, 100, 1000);
        Asset.font.draw(game.batcher, "Tab Screen to begin", 720 / 2, 500, 0, Align.center, true);

        game.batcher.end();

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
