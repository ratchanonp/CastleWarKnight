package com.ratchanon.CastleWarKnight.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ratchanon.CastleWarKnight.World;
import com.ratchanon.CastleWarKnight.components.*;

public class FightSystem extends EntitySystem {
    Entity attackWith = null;
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<PointComponent> pm;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<DragableComponent> dm;
    private ComponentMapper<AnimationComponent> am;
    private Engine engine;
    private World world;
    private int isFinishedAttack = 0;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;

    public FightSystem(World world) {
        this.world = world;

        bm = ComponentMapper.getFor(BoundsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        dm = ComponentMapper.getFor(DragableComponent.class);
        pm = ComponentMapper.getFor(PointComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        players = engine.getEntitiesFor(Family.all(EntityComponent.class, DragableComponent.class).get());
        enemies = engine.getEntitiesFor(Family.all(EntityComponent.class).exclude(DragableComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {


        for (int i = 0; i < players.size(); ++i) {
            Entity player = players.get(i);

            BoundsComponent playerBound = bm.get(player);
            StateComponent playerState = sm.get(player);
            DragableComponent playerDrag = dm.get(player);
            PointComponent playerPoint = pm.get(player);
            AnimationComponent playerAnim = am.get(player);


            for (int j = 0; j < enemies.size(); ++j) {
                Entity enemy = enemies.get(j);

                BoundsComponent enemyBound = bm.get(enemy);
                StateComponent enemyState = sm.get(enemy);

                if (!playerDrag.isDrag && playerBound.bounds.overlaps(enemyBound.bounds)) {
                    attackWith = enemy;
                } else {
                    enemyState.set(StateComponent.STATE_IDLE);
                }
            }

            if (attackWith != null && isFinishedAttack == 0) {
                playerDrag.canDrag = false;

                StateComponent attackWithState = sm.get(attackWith);
                AnimationComponent attackWithAnim = am.get(attackWith);
                PointComponent attackWithPoint = pm.get(attackWith);

                if (playerPoint.point > attackWithPoint.point) {
                    playerState.set(StateComponent.STATE_ATTACK);
                    if (playerAnim.animations.get(playerState.get()).isAnimationFinished(playerState.time)) {
                        isFinishedAttack = 1;
                        attackWithState.set(StateComponent.STATE_DEATH);
                        playerState.set(StateComponent.STATE_IDLE);
                    }
                } else {
                    attackWithState.set(StateComponent.STATE_ATTACK);
                    if (attackWithAnim.animations.get(attackWithState.get()).isAnimationFinished(attackWithState.time)) {
                        isFinishedAttack = 2;
                        playerState.set(StateComponent.STATE_DEATH);
                        attackWithState.set(StateComponent.STATE_IDLE);
                    }
                }


            } else {
                playerState.set(StateComponent.STATE_IDLE);
            }

            if (attackWith != null && isFinishedAttack != 0) {
                AnimationComponent attackWithAnim = am.get(attackWith);
                StateComponent attackWithState = sm.get(attackWith);

                if (attackWithAnim.animations.get(StateComponent.STATE_DEATH).isAnimationFinished(attackWithState.time) && isFinishedAttack == 1) {
                    PointComponent attackWithPoint = pm.get(attackWith);
                    playerPoint.point += attackWithPoint.point;

                    engine.removeEntity(attackWith);
                    attackWith = null;

                    isFinishedAttack = 0;
                    playerDrag.canDrag = true;
                }

                if (playerAnim.animations.get(StateComponent.STATE_DEATH).isAnimationFinished(playerState.time) && isFinishedAttack == 2) {
                    engine.removeEntity(player);
                    attackWith = null;

                    attackWithState.set(StateComponent.STATE_IDLE);
                    isFinishedAttack = 0;
                }

            }
        }
    }
}
