package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class DragableComponent implements Component {
    public Rectangle bounds = new Rectangle(0, 0, 64 * 1.5f, 64 * 1.5f);
    public boolean canDrag = true;
    public boolean isDrag = false;
}
