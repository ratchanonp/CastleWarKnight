package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.ratchanon.CastleWarKnight.Asset;
import com.ratchanon.CastleWarKnight.World;
import com.ratchanon.CastleWarKnight.components.DragableComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

import java.util.ArrayList;

public class TransformSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;

    private ComponentMapper<DragableComponent> dm;

    public TransformSystem() {
        super(Family.all(TransformComponent.class, DragableComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
        dm = ComponentMapper.getFor(DragableComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        DragableComponent drag = dm.get(entity);

        boolean isInCastle = false;

        ArrayList<Rectangle> castleBoundList = World.castleBoundList;

        for (Rectangle bounds : castleBoundList) {
            if (bounds.contains(pos.pos.x, pos.pos.y)) {
                if (!drag.isDrag) {

                    pos.pos.x = bounds.x + 60;
                    pos.pos.y = bounds.y + 40;
                }
                isInCastle = true;
                pos.isInCastle = true;
            } else {
                pos.isInCastle = false;
            }
        }

        if (!isInCastle && !drag.isDrag) {
            pos.pos.x = 20 + Asset.castleLevel.getRegionWidth() / 2;
            pos.pos.y = 100;

        }

    }
}
