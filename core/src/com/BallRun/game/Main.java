package com.BallRun.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** The first thing that runs
 * Created by HeyJD on 17/04/2015.
 */
public class Main extends Game {
    public static final float CAMERA_WIDTH = 1920;
    public static final float CAMERA_HEIGHT = 1080;

    private MainMenu mainMenu;
    public SpriteBatch batch;
    OrthographicCamera cam;

    @Override
    public void create() {
        Assets.load();
        SaveFile.load();

        batch = new SpriteBatch();
        mainMenu = new MainMenu(this);

        cam = new OrthographicCamera(CAMERA_WIDTH,CAMERA_HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        //Go to main menu
        setScreen(mainMenu);
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
