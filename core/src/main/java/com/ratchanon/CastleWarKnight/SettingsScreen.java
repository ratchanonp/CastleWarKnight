package com.ratchanon.CastleWarKnight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SettingsScreen extends ScreenAdapter {
    CastleWarKnight game;
    OrthographicCamera camera;
    Vector2 touchPos;

    public SettingsScreen(CastleWarKnight game) {
        this.game = game;
        camera = new OrthographicCamera(480, 860);
        camera.position.set(480 / 2, 860 / 2, 0);
        touchPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());

        game.batcher.begin();

        float scaled = 1.5f;

        game.batcher.draw(Asset.newBackground, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        game.batcher.draw(Asset.gameSettingsBox, Settings.WIDTH / 2 - Asset.gameSettingsBox.getRegionWidth() * scaled / 2, Settings.HEIGHT / 2 - Asset.gameSettingsBox.getRegionHeight() * scaled / 2, Asset.gameSettingsBox.getRegionWidth() * scaled, Asset.gameSettingsBox.getRegionHeight() * scaled);

        Asset.font.setColor(0, 0, 0, 1);
        Asset.font.getData().setScale(0.55f);
        Asset.font.draw(game.batcher, "Music ", 170, 500);
        Rectangle musicBound = new Rectangle(270, Settings.HEIGHT - 500 - Asset.iconMute.getRegionHeight() / 2 + 10, Asset.iconMute.getRegionWidth(), Asset.iconMute.getRegionHeight());
        if (Settings.musicEnabled) {
            game.batcher.draw(Asset.iconSound, 270, 500 - Asset.iconMute.getRegionHeight() / 2 - 10);
        } else {
            game.batcher.draw(Asset.iconMute, 270, 500 - Asset.iconMute.getRegionHeight() / 2 - 10);
        }

        Asset.font.draw(game.batcher, "Sound ", 170, 425);
        Rectangle soundBound = new Rectangle(270, Settings.HEIGHT - 425 - Asset.iconMute.getRegionHeight() / 2 + 10, Asset.iconMute.getRegionWidth(), Asset.iconMute.getRegionHeight());
        if (Settings.soundEnabled) {
            game.batcher.draw(Asset.iconSound, 270, 425 - Asset.iconMute.getRegionHeight() / 2 - 10);
        } else {
            game.batcher.draw(Asset.iconMute, 270, 425 - Asset.iconMute.getRegionHeight() / 2 - 10);
        }

        if (soundBound.contains(touchPos)) {
            System.out.println("Container Sound");
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                Settings.soundEnabled = !Settings.soundEnabled;
            }
        }

        if (musicBound.contains(touchPos)) {
            System.out.println("Contain Music");
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                Settings.toggleMusic();
            }
        }

        Rectangle mainMenuBound = new Rectangle(Settings.WIDTH / 2 - Asset.mainMenu.getRegionWidth() / 2, Settings.HEIGHT - 275 - Asset.mainMenu.getRegionHeight(), Asset.mainMenu.getRegionWidth(), Asset.mainMenu.getRegionHeight());
        game.batcher.draw(Asset.mainMenu, Settings.WIDTH / 2 - Asset.mainMenu.getRegionWidth() / 2, 275);

        if (mainMenuBound.contains(touchPos)) {
            if (Gdx.input.justTouched()) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new MainMenuScreen(game));
            }
        }

        game.batcher.end();
    }
}
