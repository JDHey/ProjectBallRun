package com.BallRun.game;

import com.BallRun.game.MenuItems.MenuItem;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

/** The credits Menu
 * Created by HeyJD on 7/06/2015.
 */
public class CreditsMenu extends InputAdapter implements Screen {
    private static final String TITLE = "Credits";
    private static final String BODY = "All textures are under public domain \n All programming  -  Jayden Leslie";
    private Main game;

    private MenuItem backButton;
    private MenuItem titleItem;

    private BitmapFont textFont;

    public CreditsMenu(Main game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        textFont = Assets.ComputerFont;
        textFont.setScale(4);

        titleItem = new MenuItem(Assets.block, (Main.CAMERA_WIDTH / 2) - (textFont.getBounds(TITLE).width/2), Main.CAMERA_HEIGHT);
        titleItem.setGoalY((Main.CAMERA_HEIGHT / 2) - (Assets.logoMain.getRegionHeight() / 2) + 400);
        backButton = new MenuItem(Assets.backButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        backButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y-(float)(Assets.buttonHeight*3.5));
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
        //logo.update(delta);
        titleItem.update(delta);
        backButton.update(delta);
    }

    private void drawItems() {
        //logo.draw(game.batch);
        backButton.draw(game.batch);
        textFont.setScale(1.7f);
        textFont.drawMultiLine(game.batch, BODY, (Main.CAMERA_WIDTH / 2) - (textFont.getMultiLineBounds(BODY).width/2), Main.CAMERA_HEIGHT/2);
        textFont.setScale(4);
        textFont.draw(game.batch, TITLE, titleItem.getX(), titleItem.getY()+ textFont.getBounds(TITLE).height);
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
        textFont.dispose();
        game.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX, screenY,0));

        if (backButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            backButton.setTouchDown(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX,screenY,0));

        if (backButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            backButton.playClickedSound();
            game.setScreen(new MainMenu(game));
        }
        backButton.setTouchDown(false);

        return true;
    }
}
