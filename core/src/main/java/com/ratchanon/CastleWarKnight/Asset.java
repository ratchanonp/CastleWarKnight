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
    public static TextureRegion background;
    public static TextureRegion logo;


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

    // Font
    public static BitmapFont font;

    public static Texture loadTexture(String path) {
        return new Texture(Gdx.files.internal(path));
    }

    public static void load() {
        itemsAtlas = new TextureAtlas(Gdx.files.internal("items.atlas"));
        background = itemsAtlas.findRegion("background");
        logo = itemsAtlas.findRegion("Logo");

        castleAtlas = new TextureAtlas(Gdx.files.internal("castle.atlas"));
        castleLevel = castleAtlas.findRegion("cUnit");
        castleRoof = castleAtlas.findRegion("roof");

        entityAtlas = new TextureAtlas(Gdx.files.internal("entities.atlas"));
        knight = new EntityAnimation(Animator.create(entityAtlas.findRegion("Knight/knightIdle"), 15),
                Animator.create(entityAtlas.findRegion("Knight/knightAttack"), 22),
                Animator.create(entityAtlas.findRegion("Knight/knightDeath"), 15));

        bat = new EntityAnimation(Animator.create(entityAtlas.findRegion("Bat/batIdle"), 8),
                Animator.create(entityAtlas.findRegion("Bat/batAttack"), 10),
                Animator.create(entityAtlas.findRegion("Bat/batDeath"), 10));


        golem = new EntityAnimation(Animator.create(entityAtlas.findRegion("Golem/golemIdle"), 12),
                Animator.create(entityAtlas.findRegion("Golem/golemAttack"), 16),
                Animator.create(entityAtlas.findRegion("Golem/golemDeath"), 28));


        witch = new EntityAnimation(Animator.create(entityAtlas.findRegion("Witch/witchIdle"), 7),
                Animator.create(entityAtlas.findRegion("Witch/witchAttack"), 18),
                Animator.create(entityAtlas.findRegion("Witch/witchDeath"), 12));


        wolf = new EntityAnimation(Animator.create(entityAtlas.findRegion("Wolf/wolfIdle"), 12),
                Animator.create(entityAtlas.findRegion("Wolf/wolfAttack"), 16),
                Animator.create(entityAtlas.findRegion("Wolf/wolfDeath"), 18));


        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        music = Gdx.audio.newMusic(Gdx.files.internal("song/Song_Skyrim_8-Bit.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        //music.play();
    }
}
