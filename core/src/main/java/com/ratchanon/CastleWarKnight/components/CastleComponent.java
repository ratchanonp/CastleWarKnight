package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;

public class CastleComponent implements Component {
    public static final int WIDTH = 192;
    public static final int HEIGHT = 128;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ROOF = 1;

    public int type = TYPE_NORMAL;

}
