package com.BallRun.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The asset class
 * Created by HeyJD on 9/04/2015.
 */
public class Assets {
    //public static AssetManager manager;

    public static Texture gameBackground;
    public static Texture menuBackground;
    public static Texture logoSheet;
    public static Texture characterSheet;
    public static Texture tilesSpriteSheet;
    public static Texture itemsSpriteSheet;

    //GameTiles
    public static TextureRegion block;
    public static TextureRegion blockLeftEdge;
    public static TextureRegion blockRightEdge;
    public static TextureRegion scoreBox;
    public static TextureRegion arrowItem;

    //Environment
    public static TextureRegion cloud1;
    public static TextureRegion cloud2;
    public static TextureRegion cloud3;
    public static TextureRegion grass;
    public static TextureRegion bush;
    public static TextureRegion cactus;
    public static TextureRegion water;
    public static TextureRegion lava;

    //GameSprites
    public static TextureRegion spike;
    public static TextureRegion coin;

    //Custom balls
    public static TextureRegion owl;
    public static TextureRegion pig;
    public static TextureRegion penguin;
    public static TextureRegion cow;
    public static TextureRegion sheep;
    public static TextureRegion hippo;

    //MenuItems/UI
    public static TextureRegion logoMain;
    public static TextureRegion logoShadow;
    public static TextureRegion playButton;
    public static TextureRegion restartButton;
    public static TextureRegion mainMenuButton;
    public static TextureRegion creditsButton;
    public static TextureRegion settingsButton;
    public static TextureRegion resetButton;
    public static TextureRegion backButton;
    public static TextureRegion muteButton;
    public static TextureRegion unmuteButton;
    public static TextureRegion customButton;

    //Instruction phrases
    public static TextureRegion instruction1;
    public static TextureRegion instruction2;
    public static TextureRegion instruction3;

    //Sounds
    public static Music music;
    public static Sound jumpSound;
    public static Sound deathSound;
    public static Sound clickSound;

    public static float jumpSoundVolume = 1f;
    public static int buttonWidth = 512;
    public static int buttonHeight = 128;

    //Fonts
    public static BitmapFont LCDFont;
    public static BitmapFont ComputerFont;

    //Skins
    public static Skin skinGreen;

    public static void load() {
        tilesSpriteSheet = loadTexture("images/tiles_spritesheet.png");
        itemsSpriteSheet = loadTexture("images/items_spritesheet.png");
        characterSheet = loadTexture("images/ballCharacters.png");

        //Backgrounds
        gameBackground = loadTexture("images/background.png");
        gameBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        menuBackground = loadTexture("images/bg_castle.png");
        menuBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //GameTiles
        block = new TextureRegion(tilesSpriteSheet, 504, 576, 70, 70);
        blockLeftEdge = new TextureRegion(tilesSpriteSheet, 576, 720, 70, 70);
        blockRightEdge = new TextureRegion(tilesSpriteSheet, 576, 576, 70, 70);
        scoreBox = new TextureRegion(tilesSpriteSheet, 0, 432, 70, 70);
        arrowItem = new TextureRegion(tilesSpriteSheet, 288, 216, 70, 70);

        //Custom balls
        owl = new TextureRegion(characterSheet, 0, 0, 128, 128);
        pig = new TextureRegion(characterSheet, 128, 0, 128, 128);
        penguin = new TextureRegion(characterSheet, 256, 0, 128, 128);
        cow = new TextureRegion(characterSheet, 0, 128, 128, 128);
        sheep = new TextureRegion(characterSheet, 128, 128, 128, 128);
        hippo = new TextureRegion(characterSheet, 256, 128, 128, 128);


        //Environment
        cloud1 = new TextureRegion(itemsSpriteSheet, 0, 0, 128, 71);
        cloud2 = new TextureRegion(itemsSpriteSheet, 0, 73, 128, 71);
        cloud3 = new TextureRegion(itemsSpriteSheet, 0, 146, 128, 71);
        grass = new TextureRegion(itemsSpriteSheet, 16, 396, 37, 37);
        bush = new TextureRegion(itemsSpriteSheet, 346, 187, 70, 27);
        cactus = new TextureRegion(itemsSpriteSheet, 376, 228, 38, 58);
        water = new TextureRegion(tilesSpriteSheet, 432, 576, 70, 70);
        lava = new TextureRegion(tilesSpriteSheet, 432, 706, 70, 70);

        //GameItems
        spike = new TextureRegion(itemsSpriteSheet, 347, 36, 70, 35);
        coin = new TextureRegion(itemsSpriteSheet, 288, 361, 70, 70);

        //MenuItems
        logoSheet = loadTexture("images/logoSheet.png");
        logoMain = new TextureRegion(logoSheet, 0, 0, 1024, 256);
        logoShadow = new TextureRegion(logoSheet, 0, 256, 1024, 256);

        //Music
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        music.setLooping(true);

        //Sounds
        jumpSound = loadSound("audio/jump.mp3");
        deathSound = loadSound("audio/death.wav");
        clickSound = loadSound("audio/click.wav");

        FreeTypeFontGenerator generatorQuartz = new FreeTypeFontGenerator(Gdx.files.internal("fonts/QuartzMS.TTF"));
        FreeTypeFontGenerator generatorComputer = new FreeTypeFontGenerator(Gdx.files.internal("fonts/zig.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;

        LCDFont = generatorQuartz.generateFont(parameter);
        ComputerFont = generatorComputer.generateFont(parameter);

        generatorQuartz.dispose();
        generatorComputer.dispose();

        skinGreen = loadSkin("skins/ui-green.json");
    }

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static Sound loadSound(String file) {
        return Gdx.audio.newSound(Gdx.files.internal(file));
    }

    public static Skin loadSkin(String file) {
        return new Skin(Gdx.files.internal(file));
    }

    public static void dispose() {
        gameBackground.dispose();
        music.dispose();
        jumpSound.dispose();
        deathSound.dispose();
        logoSheet.dispose();
        LCDFont.dispose();
        ComputerFont.dispose();
        characterSheet.dispose();
        tilesSpriteSheet.dispose();
        itemsSpriteSheet.dispose();
        menuBackground.dispose();
        clickSound.dispose();
        skinGreen.dispose();
    }
}
