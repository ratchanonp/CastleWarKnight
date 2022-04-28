package com.ratchanon.CastleWarKnight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ratchanon.CastleWarKnight.utils.Animator;
import com.ratchanon.CastleWarKnight.utils.EntityAnimation;

public class Asset {

    public static TextureAtlas itemsAtlas;
    public static TextureAtlas castleAtlas;
    public static TextureAtlas entityAtlas;
    public static TextureAtlas entityRunAtlas;

    public static TextureAtlas iconsAtlas;
    public static TextureRegion background;
    public static Texture newBackground;
    public static TextureRegion logo;

    public static TextureRegion gameOverBox;
    public static TextureRegion restart;
    public static TextureRegion restartHover;
    public static TextureRegion pauseButton;
    public static TextureRegion labelBackground;

    public static TextureRegion iconMute;
    public static TextureRegion iconSound;
    public static TextureRegion settings;
    public static TextureRegion settingsHover;
    public static TextureRegion start;
    public static TextureRegion startHover;
    public static TextureRegion mainMenu;
    public static TextureRegion mainMenuHover;
    public static TextureRegion resume;
    public static TextureRegion resumeHover;
    public static TextureRegion gamePauseBox;
    public static TextureRegion gameSettingsBox;
    public static TextureRegion gameStageCompleteBox;
    public static TextureRegion gameStageCompleteBoxHover;

    // Castle
    public static TextureRegion castleLevel;
    public static TextureRegion castleRoof;

    // Entity
    public static EntityAnimation knight;
    public static EntityAnimation bat;
    public static EntityAnimation wolf;
    public static EntityAnimation golem;
    public static EntityAnimation witch;

    // Music and SoundEffect
    public static Music music;
    public static Sound grabSound;
    public static Sound fightSound;
    public static Sound diedSound;
    public static Sound gameOverSound;

    public static Sound clickSound;

    // Font
    public static BitmapFont font;

    public static Texture loadTexture(String path) {
        return new Texture(Gdx.files.internal(path));
    }

    public static void load() {
        itemsAtlas = new TextureAtlas(Gdx.files.internal("items.atlas"));
        background = itemsAtlas.findRegion("background");
        newBackground = new Texture(Gdx.files.internal("demo.png"));
        logo = itemsAtlas.findRegion("Logo");
        gameOverBox = itemsAtlas.findRegion("GameOver");
        restart = itemsAtlas.findRegion("Restart");
        restartHover = itemsAtlas.findRegion("Restart_Clicked");
        pauseButton = itemsAtlas.findRegion("pause");
        labelBackground = itemsAtlas.findRegion("b1_long");

        iconsAtlas = new TextureAtlas(Gdx.files.internal("icons.atlas"));
        iconMute = iconsAtlas.findRegion("icon_mute");
        iconSound = iconsAtlas.findRegion("icon_sound");
        settings = iconsAtlas.findRegion("settings");
        settingsHover = iconsAtlas.findRegion("settings_clicked");
        start = iconsAtlas.findRegion("start");
        startHover = iconsAtlas.findRegion("start_clicked");
        resume = iconsAtlas.findRegion("resume");
        resumeHover = iconsAtlas.findRegion("resume_clicked");
        gamePauseBox = iconsAtlas.findRegion("GamePause");
        gameSettingsBox = iconsAtlas.findRegion("GameSettings");
        gameStageCompleteBox = iconsAtlas.findRegion("levelComplete_Stage");
        gameStageCompleteBoxHover = iconsAtlas.findRegion("levelComplete_Stage_clicked");
        mainMenu = iconsAtlas.findRegion("mainmenu");
        mainMenuHover = iconsAtlas.findRegion("mainmenu_clicked");


        castleAtlas = new TextureAtlas(Gdx.files.internal("castle.atlas"));
        castleLevel = castleAtlas.findRegion("cUnit");
        castleRoof = castleAtlas.findRegion("roof");

        entityAtlas = new TextureAtlas(Gdx.files.internal("entities.atlas"));
        entityRunAtlas = new TextureAtlas(Gdx.files.internal("entitiesRun.atlas"));
        knight = new EntityAnimation(Animator.create(entityAtlas.findRegion("Knight/knightIdle"), 15),
                Animator.create(entityAtlas.findRegion("Knight/knightAttack"), 22),
                Animator.create(entityAtlas.findRegion("Knight/knightDeath"), 15),
                Animator.create(entityRunAtlas.findRegion("KnightRun"), 8, 0.125f));

        bat = new EntityAnimation(Animator.create(entityAtlas.findRegion("Bat/batIdle"), 8),
                Animator.create(entityAtlas.findRegion("Bat/batAttack"), 10),
                Animator.create(entityAtlas.findRegion("Bat/batDeath"), 10),
                Animator.create(entityRunAtlas.findRegion("BatRun"), 8, 0.125f));


        golem = new EntityAnimation(Animator.create(entityAtlas.findRegion("Golem/golemIdle"), 12),
                Animator.create(entityAtlas.findRegion("Golem/golemAttack"), 16),
                Animator.create(entityAtlas.findRegion("Golem/golemDeath"), 28),
                Animator.create(entityRunAtlas.findRegion("GolemRun"), 7, 0.125f));


        witch = new EntityAnimation(Animator.create(entityAtlas.findRegion("Witch/witchIdle"), 7),
                Animator.create(entityAtlas.findRegion("Witch/witchAttack"), 18),
                Animator.create(entityAtlas.findRegion("Witch/witchDeath"), 12),
                Animator.create(entityRunAtlas.findRegion("WitchRun"), 8, 0.125f));


        wolf = new EntityAnimation(Animator.create(entityAtlas.findRegion("Wolf/wolfIdle"), 12),
                Animator.create(entityAtlas.findRegion("Wolf/wolfAttack"), 16),
                Animator.create(entityAtlas.findRegion("Wolf/wolfDeath"), 18),
                Animator.create(entityRunAtlas.findRegion("WolfRun"), 8, 0.125f));


        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        music = Gdx.audio.newMusic(Gdx.files.internal("song/Song_Skyrim_8-Bit.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);

        if (Settings.musicEnabled) {
            music.play();
        } else {
            music.pause();
        }

        clickSound = Gdx.audio.newSound(Gdx.files.internal("song/Audio/Others/Mouse Click.mp3"));


    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
