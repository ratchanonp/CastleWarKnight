package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
    public static int STATE_IDLE = 0;
    public static int STATE_ATTACK = 1;
    public static int STATE_DEATH = 2;
    public float time = 0.0f;
    private int state = 0;

    public int get() {
        return state;
    }

    public void set(int newState) {
        if (state != newState) {
            state = newState;
            time = 0.0f;
        }
    }
}
