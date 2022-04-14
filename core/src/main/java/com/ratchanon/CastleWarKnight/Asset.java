package com.ratchanon.CastleWarKnight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ratchanon.CastleWarKnight.utils.Animator;

public class Asset {

    public static Texture background;
    public static Texture logo;

    public static TextureAtlas castleAtlas;
    public static TextureAtlas entityAtlas;

    // Castle
    public static TextureRegion castleLevel;
    public static TextureRegion castleRoof;

    // Knight
    public static Animation knightIdle;
    public static Animation knightAttack;
    public static Animation knightDeath;

    // Creature
    public static Animation batIdle;
    public static Animation batAttack;
    public static Animation batDeath;
    public static Animation golemIdle;
    public static Animation golemAttack;
    public static Animation golemDeath;
    public static Animation witchIdle;
    public static Animation witchAttack;
    public static Animation witchDeath;
    public static Animation wolfIdle;
    public static Animation wolfAttack;
    public static Animation wolfDeath;

    // Music and SoundEffect
    public static Music music;
    public static Sound grabSound;
    public static Sound fightSound;
    public static Sound diedSound;
    public static Sound gameOverSound;

    // Font
    public static BitmapFont font;

    public static Texture loadTexture(String path) {
        return new Texture(Gdx.files.internal(path));
    }

    public static void load() {
        background = loadTexture("background.png");
        logo = loadTexture("logo.png");

        castleAtlas = new TextureAtlas(Gdx.files.internal("castle.atlas"));
        castleLevel = castleAtlas.findRegion("cUnit");
        castleRoof = castleAtlas.findRegion("roof");

        entityAtlas = new TextureAtlas(Gdx.files.internal("entities.atlas"));
        knightIdle = Animator.create(entityAtlas.findRegion("Knight/knightIdle"), 15);
        knightAttack = Animator.create(entityAtlas.findRegion("Knight/knightAttack"), 22);
        knightDeath = Animator.create(entityAtlas.findRegion("Knight/knightDeath"), 15);

        batIdle = Animator.create(entityAtlas.findRegion("Bat/batIdle"), 8);
        batAttack = Animator.create(entityAtlas.findRegion("Bat/batAttack"), 10);
        batDeath = Animator.create(entityAtlas.findRegion("Bat/batDeath"), 10);

        golemIdle = Animator.create(entityAtlas.findRegion("Golem/golemIdle"), 12);
        golemAttack = Animator.create(entityAtlas.findRegion("Golem/golemAttack"), 16);
        golemDeath = Animator.create(entityAtlas.findRegion("Golem/golemDeath"), 28);

        witchIdle = Animator.create(entityAtlas.findRegion("Witch/witchIdle"), 7);
        witchAttack = Animator.create(entityAtlas.findRegion("Witch/witchAttack"), 18);
        witchDeath = Animator.create(entityAtlas.findRegion("Witch/witchDeath"), 12);

        wolfIdle = Animator.create(entityAtlas.findRegion("Wolf/wolfIdle"), 12);
        wolfAttack = Animator.create(entityAtlas.findRegion("Wolf/wolfAttack"), 16);
        wolfDeath = Animator.create(entityAtlas.findRegion("Wolf/wolfDeath"), 18);

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        music = Gdx.audio.newMusic(Gdx.files.internal("song/Song_Skyrim_8-Bit.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        //music.play();

        setLoop();

    }

    private static void setLoop() {
        knightIdle.setPlayMode(Animation.PlayMode.LOOP);
        knightAttack.setPlayMode(Animation.PlayMode.LOOP);
        knightDeath.setPlayMode(Animation.PlayMode.LOOP);

        batIdle.setPlayMode(Animation.PlayMode.LOOP);
        batAttack.setPlayMode(Animation.PlayMode.LOOP);
        batDeath.setPlayMode(Animation.PlayMode.LOOP);

        golemIdle.setPlayMode(Animation.PlayMode.LOOP);
        golemAttack.setPlayMode(Animation.PlayMode.LOOP);
        golemDeath.setPlayMode(Animation.PlayMode.LOOP);

        witchIdle.setPlayMode(Animation.PlayMode.LOOP);
        witchAttack.setPlayMode(Animation.PlayMode.LOOP);
        witchDeath.setPlayMode(Animation.PlayMode.LOOP);

        wolfIdle.setPlayMode(Animation.PlayMode.LOOP);
        wolfAttack.setPlayMode(Animation.PlayMode.LOOP);
        wolfDeath.setPlayMode(Animation.PlayMode.LOOP);
    }
}
