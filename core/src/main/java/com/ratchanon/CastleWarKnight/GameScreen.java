package com.ratchanon.CastleWarKnight;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

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


    private int state;

    public GameScreen(CastleWarKnight game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
