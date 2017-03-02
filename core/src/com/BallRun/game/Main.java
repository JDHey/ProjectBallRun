package com.BallRun.game;

import com.BallRun.game.Screens.MainMenu;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** The first thing that runs
 * Created by HeyJD on 17/04/2015.
 */
public class Main extends Game {
    public static final float CAMERA_WIDTH = 1920;
    public static final float CAMERA_HEIGHT = 1080;

    private MainMenu mainMenu;
    public SpriteBatch batch;

    public Viewport viewport;
    OrthographicCamera cam;

    @Override
    public void create() {
        //Load assets and save first
        Assets.load();
        SaveFile.load();

        batch = new SpriteBatch();
        mainMenu = new MainMenu(this);

        cam = new OrthographicCamera(CAMERA_WIDTH,CAMERA_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        viewport = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, cam);

        //Go to main menu
        setScreen(mainMenu);
    }

    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {
        Assets.dispose();
        batch.dispose();
    }
}
