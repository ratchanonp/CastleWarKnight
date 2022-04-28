package com.ratchanon.CastleWarKnight;

public class Settings {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 860;

    public static boolean soundEnabled = true;
    public static boolean musicEnabled = false;

    public static void toggleMusic() {
        musicEnabled = !musicEnabled;

        if (!musicEnabled) {
            Asset.music.pause();
        } else {
            Asset.music.play();
        }
    }

}
