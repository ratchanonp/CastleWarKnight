package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ratchanon.CastleWarKnight.components.TextureComponent;
import com.ratchanon.CastleWarKnight.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem {
    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        comparator = (entityA, entityB) -> (int) Math.signum(transformM.get(entityB).pos.z - transformM.get(entityA).pos.z);

        this.batch = batch;

        cam = new OrthographicCamera(480, 860);
        cam.position.set(480 / 2, 860 / 2, 0);
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            if (tex.flip && !tex.region.isFlipX()) {
                tex.region.flip(true, false);
            }

            batch.draw(tex.region, t.pos.x - originX, t.pos.y - originY, originX, originY, width, height, t.scale.x, t.scale.y, 0);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
