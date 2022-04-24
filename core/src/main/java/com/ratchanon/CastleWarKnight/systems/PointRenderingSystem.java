package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.ratchanon.CastleWarKnight.Asset;
import com.ratchanon.CastleWarKnight.components.PointComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

import java.text.DecimalFormat;

public class PointRenderingSystem extends IteratingSystem {

    SpriteBatch batch;
    ComponentMapper<PointComponent> pm;
    ComponentMapper<TransformComponent> tm;

    public PointRenderingSystem(SpriteBatch batch) {
        super(Family.all(PointComponent.class, TransformComponent.class).get());
        pm = ComponentMapper.getFor(PointComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);

        this.batch = batch;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        PointComponent point = pm.get(entity);

        batch.begin();
        Asset.font.getData().setScale(0.5f);
        Asset.font.setColor(1, 1, 1, 1);
        Asset.font.draw(batch, formatValue(point.point), pos.pos.x - 45, pos.pos.y + 80, 90, Align.center, true);
        batch.end();
    }

    private String formatValue(float value) {
        String arr[] = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), arr[index]);
    }
}
