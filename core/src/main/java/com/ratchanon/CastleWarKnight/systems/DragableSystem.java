package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.ratchanon.CastleWarKnight.Settings;
import com.ratchanon.CastleWarKnight.components.DragableComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

public class DragableSystem extends IteratingSystem {
    OrthographicCamera camera;
    private ComponentMapper<DragableComponent> dc;
    private ComponentMapper<TransformComponent> tc;

    public DragableSystem(OrthographicCamera camera) {
        super(Family.all(DragableComponent.class, TransformComponent.class).get());
        dc = ComponentMapper.getFor(DragableComponent.class);
        tc = ComponentMapper.getFor(TransformComponent.class);
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DragableComponent click = dc.get(entity);
        TransformComponent transform = tc.get(entity);

        click.bounds.x = transform.pos.x - 45;
        click.bounds.y = transform.pos.y - 45;

        if (click.canDrag) {
            Vector3 clickPosition = new Vector3(Gdx.input.getX(), Settings.HEIGHT - Gdx.input.getY(), 0);
            if (click.bounds.contains(clickPosition.x, clickPosition.y)) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    transform.pos = clickPosition;
                    click.isDrag = true;
                } else {
                    click.isDrag = false;
                }
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            } else {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        }
    }
}
