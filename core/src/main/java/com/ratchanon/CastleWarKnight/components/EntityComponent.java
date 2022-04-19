package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;

public class EntityComponent implements Component {
    public static final int STATE_IDLE = 0;
    public static final int STATE_ATTACK = 1;
    public static final int STATE_DEATH = 2;

    public static final int HEIGHT = 64;
    public static final int WIDTH = 64;

    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_ENEMY = 2;

    public int entityType = TYPE_PLAYER;

    public long point = 0;
}
