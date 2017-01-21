package com.BallRun.game;

import com.BallRun.game.MenuItems.Highscores;
import com.BallRun.game.MenuItems.MenuItem;
import com.BallRun.game.MenuItems.LogoItem;
import com.BallRun.game.MenuItems.MuteItem;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

/** Game over menu
 * Created by HeyJD on 19/04/2015.
 */
public class GameoverMenu implements Screen, InputProcessor {
    private Main game;
    private GameController gc;

    private LogoItem logo;
    private MenuItem restartButton;
    private MenuItem mainMenuButton;
    private MuteItem muteButton;
    private Highscores highscores;

    public GameoverMenu(Main game, GameController gc) {
        this.game = game;
        this.gc = gc; // Pass in the game controller so I can have a seemingly transparent menu
        Gdx.input.setInputProcessor(this);

        logo = new LogoItem();

        restartButton = new MenuItem(Assets.restartButton, MenuItem.BUTTON_MIDDLE_X, Main.CAMERA_HEIGHT);
        restartButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y + (Assets.buttonHeight/2));

        mainMenuButton = new MenuItem(Assets.mainMenuButton, MenuItem.BUTTON_MIDDLE_X, Main.CAMERA_HEIGHT);
        mainMenuButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y - (Assets.buttonHeight*2));

        muteButton = new MuteItem(Main.CAMERA_WIDTH, 0);
        muteButton.setSize(128,128);
        muteButton.setGoalX(Main.CAMERA_WIDTH-128);

        highscores = new Highscores(100, 650, gc.score);
        gc.fadeOut();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        drawItems();
        game.batch.end();

        updateItems(delta);
    }

    private void drawItems() {
        gc.drawAllItems(game.batch);
        logo.draw(game.batch);
        restartButton.draw(game.batch);
        mainMenuButton.draw(game.batch);
        highscores.draw(game.batch);
        muteButton.draw(game.batch);
    }

    private void updateItems(float deltaTime) {
        logo.update(deltaTime);
        restartButton.update(deltaTime);
        mainMenuButton.update(deltaTime);
        highscores.update(deltaTime);
        muteButton.update(deltaTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        highscores.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX, screenY,0));

        if (restartButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            restartButton.setTouchDown(true);
        } else if (mainMenuButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            mainMenuButton.setTouchDown(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX, screenY,0));

        if (restartButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            //restartButton.playClickedSound(); Doesn't sound nice on restart
            game.setScreen(new GameRenderer(game));
        } else if (mainMenuButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            mainMenuButton.playClickedSound();
            game.setScreen(new MainMenu(game));
        } else if (muteButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            muteButton.toggleMute();
            muteButton.playClickedSound();
        } else {
                //Do this for every button with a touchDown
                restartButton.setTouchDown(false);
                mainMenuButton.setTouchDown(false);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
