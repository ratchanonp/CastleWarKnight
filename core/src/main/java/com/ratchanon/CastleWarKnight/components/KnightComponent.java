package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;

public class KnightComponent implements Component {
    public static final int STATE_IDLE = 0;
    public static final int STATE_ATTACK = 1;
    public static final int STATE_DEATH = 2;

    public static final int HEIGHT = 64;
    public static final int WIDTH = 64;

    public long point = 0;
}
