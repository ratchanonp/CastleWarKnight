package com.ratchanon.CastleWarKnight.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntMap;

public class AudioComponent implements Component {
    public IntMap<Sound> sound = new IntMap<Sound>();
}
