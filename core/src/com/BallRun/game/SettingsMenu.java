package com.BallRun.game;

import com.BallRun.game.MenuItems.Highscores;
import com.BallRun.game.MenuItems.LogoItem;
import com.BallRun.game.MenuItems.MenuItem;
import com.BallRun.game.MenuItems.MuteItem;
import com.BallRun.game.Sprites.Assets;
import com.BallRun.game.Sprites.Score;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

/** The settings Menu
 * Created by HeyJD on 7/05/2015.
 */
public class SettingsMenu extends InputAdapter implements Screen {
    private static final String CONFIRM_MESSAGE = "Your scores have been reset.";
    private Main game;

    private LogoItem logo;
    private MenuItem backButton;
    private MenuItem resetButton;
    private MuteItem muteButton;

    private BitmapFont messageFont;
    private boolean isScoresRemoved;
    private Highscores highscores;

    public SettingsMenu(Main game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
        messageFont = Assets.LCDFont;
        messageFont.setScale(2);
        isScoresRemoved = false;

        logo = new LogoItem();

        resetButton = new MenuItem(Assets.resetButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        resetButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y);

        backButton = new MenuItem(Assets.backButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        backButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y-(float)(Assets.buttonHeight*1.5));

        muteButton = new MuteItem(Main.CAMERA_WIDTH, 0);
        muteButton.setSize(128,128);
        muteButton.setGoalX(Main.CAMERA_WIDTH-128);

        highscores = new Highscores(100, 650, new Score(0,0));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(Assets.menuBackground, 0, 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));
        drawItems();
        game.batch.end();

        updateItems(delta);
    }

    private void updateItems(float delta) {
        logo.update(delta);
        resetButton.update(delta);
        backButton.update(delta);
        highscores.update(delta);
        muteButton.update(delta);
    }

    private void drawItems() {
        logo.draw(game.batch);
        resetButton.draw(game.batch);
        backButton.draw(game.batch);
        highscores.draw(game.batch);
        muteButton.draw(game.batch);

        if (isScoresRemoved) {
            messageFont.draw(game.batch, CONFIRM_MESSAGE, (Main.CAMERA_WIDTH / 2) - (messageFont.getBounds(CONFIRM_MESSAGE).width/2), 128);
        }
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
        messageFont.dispose();
        highscores.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX, screenY,0));

        if (backButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            backButton.setTouchDown(true);
        } else if (resetButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            resetButton.setTouchDown(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX,screenY,0));

        if (backButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            backButton.playClickedSound();
            game.setScreen(new MainMenu(game));
        } else if (resetButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            resetButton.playClickedSound();
            if (!isScoresRemoved) {
                highscores.resetScores();
                isScoresRemoved = true;
            }
        } else if (muteButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            muteButton.toggleMute();
            muteButton.playClickedSound(); //Activate after toggling mute
        }
        resetButton.setTouchDown(false);
        backButton.setTouchDown(false);

        return true;
    }
}
