package com.BallRun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** The asset class
 * Created by HeyJD on 9/04/2015.
 */
public class Assets {
    public static AssetManager manager;

    //I should probably turn a few of these final
    public static Texture gameBackground;
    public static Texture menuBackground;
    public static Texture logoSheet;
    public static Texture buttonSheet;
    public static Texture instructions;
    public static Texture characterSheet;
    public static Texture TilesSpriteSheet;
    public static Texture ItemsSpriteSheet;

    //GameTiles
    public static TextureRegion block;
    public static TextureRegion blockLeftEdge;
    public static TextureRegion blockRightEdge;
    public static TextureRegion scoreBox;
    public static TextureRegion arrowItem;

    //GameItems
    public static TextureRegion cloud1;
    public static TextureRegion cloud2;
    public static TextureRegion cloud3;
    public static TextureRegion spikes;
    public static TextureRegion muteButton;
    public static TextureRegion unmuteButton;

    //Custom balls
    public static TextureRegion owl;
    public static TextureRegion pig;
    public static TextureRegion penguin;
    public static TextureRegion cow;
    public static TextureRegion sheep;
    public static TextureRegion hippo;

    //MenuItems
    public static TextureRegion logoMain;
    public static TextureRegion logoShadow;
    public static TextureRegion playButton;
    public static TextureRegion restartButton;
    public static TextureRegion mainMenuButton;
    public static TextureRegion creditsButton;
    public static TextureRegion settingsButton;
    public static TextureRegion resetButton;
    public static TextureRegion backButton;

    //Instruction phrases
    public static TextureRegion instruction1;
    public static TextureRegion instruction2;
    public static TextureRegion instruction3;

    //Highscore banner
    public static TextureRegion highscoreBanner;

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

    public static void load() {
        //Backgrounds
        gameBackground = loadTexture("background.png");
        gameBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        menuBackground = loadTexture("bg_castle.png");
        menuBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //GameTiles
        TilesSpriteSheet = loadTexture("tiles_spritesheet.png");
        block = new TextureRegion(TilesSpriteSheet, 504, 576, 70, 70);
        blockLeftEdge = new TextureRegion(TilesSpriteSheet, 576, 720, 70,70);
        blockRightEdge = new TextureRegion(TilesSpriteSheet, 576, 576, 70,70);
        scoreBox = new TextureRegion(TilesSpriteSheet, 0,432,70,70);
        arrowItem = new TextureRegion(TilesSpriteSheet, 288, 216, 70, 70);

        //Custom balls
        characterSheet = loadTexture("ballCharacters.png");
        owl = new TextureRegion(characterSheet, 0, 0, 128, 128);
        pig = new TextureRegion(characterSheet, 128, 0, 128, 128);
        penguin = new TextureRegion(characterSheet, 256, 0, 128, 128);
        cow = new TextureRegion(characterSheet, 0, 128, 128, 128);
        sheep = new TextureRegion(characterSheet, 128, 128, 128, 128);
        hippo = new TextureRegion(characterSheet, 256, 128, 128, 128);

        //GameItems
        ItemsSpriteSheet = loadTexture("items_spritesheet.png");
        cloud1 = new TextureRegion(ItemsSpriteSheet,0,0,128,71);
        cloud2 = new TextureRegion(ItemsSpriteSheet,0,73,128,71);
        cloud3 = new TextureRegion(ItemsSpriteSheet,0,146,128,71);
        spikes = new TextureRegion(ItemsSpriteSheet,347,0,70,70);
        muteButton = new TextureRegion(ItemsSpriteSheet,513,372,64,64);
        unmuteButton = new TextureRegion(ItemsSpriteSheet,513, 444,64,64);

        //MenuItems
        logoSheet = loadTexture("logoSheet.png");
        buttonSheet = loadTexture("buttonSheet.png");
        logoMain = new TextureRegion(logoSheet, 0, 0, 1024, 256);
        logoShadow = new TextureRegion(logoSheet, 0, 256, 1024, 256);
        playButton = new TextureRegion(buttonSheet, 0, 0, buttonWidth, buttonHeight);
        restartButton = new TextureRegion(buttonSheet, 0, buttonHeight*2, buttonWidth, buttonHeight);
        mainMenuButton = new TextureRegion(buttonSheet, 0, buttonHeight*4, buttonWidth, buttonHeight);
        creditsButton = new TextureRegion(buttonSheet, 0, buttonHeight*6, buttonWidth, buttonHeight);
        settingsButton = new TextureRegion(buttonSheet, 0, buttonHeight*8, buttonWidth, buttonHeight);
        resetButton = new TextureRegion(buttonSheet, 0, buttonHeight*10, buttonWidth, buttonHeight);
        backButton = new TextureRegion(buttonSheet, 0, buttonHeight*12, buttonWidth, buttonHeight);

        //Instructions
        instructions = loadTexture("instructions.png");
        instruction1 = new TextureRegion(instructions, 0, 0, instructions.getWidth(), instructions.getHeight()/3);
        instruction2 = new TextureRegion(instructions, 0, (instructions.getHeight()/3), instructions.getWidth(), instructions.getHeight()/3);
        instruction3 = new TextureRegion(instructions, 0, (instructions.getHeight()/3)*2, instructions.getWidth(), instructions.getHeight()/3);

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
    }

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static Sound loadSound (String file) {
        return Gdx.audio.newSound(Gdx.files.internal(file));
    }

    public static void dispose() {
        gameBackground.dispose();
        music.dispose();
        jumpSound.dispose();
        deathSound.dispose();
        logoSheet.dispose();
        buttonSheet.dispose();
        instructions.dispose();
        LCDFont.dispose();
        ComputerFont.dispose();
        characterSheet.dispose();
        TilesSpriteSheet.dispose();
        ItemsSpriteSheet.dispose();
        menuBackground.dispose();
        clickSound.dispose();
    }
}
