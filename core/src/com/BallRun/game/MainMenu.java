package com.BallRun.game;

import com.BallRun.game.MenuItems.LogoItem;
import com.BallRun.game.MenuItems.MenuItem;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/** The main Menu
 * Created by HeyJD on 16/04/2015.
 */
public class MainMenu extends InputAdapter implements Screen {
    private Main game;

    private LogoItem logoItem;
    private MenuItem playButton;
    private MenuItem settingsButton;
    private MenuItem creditsButton;
    private MenuItem customizeButton;
    private MenuItem instruction1;
    private MenuItem instruction2;
    private MenuItem instruction3;

    private List<MenuItem> instructionList; //Just to make it easier to handle

    public MainMenu(Main game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);

        logoItem = new LogoItem();

        //Positioning needs to be improved upon
        playButton = new MenuItem(Assets.playButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        playButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y+(float)(Assets.buttonHeight*1));

        settingsButton = new MenuItem(Assets.settingsButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        settingsButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y-(float)(Assets.buttonHeight*1));

        creditsButton =  new MenuItem(Assets.creditsButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        creditsButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y-(float)(Assets.buttonHeight*3));

        customizeButton =  new MenuItem(Assets.creditsButton, MenuItem.BUTTON_MIDDLE_X, 0-Assets.buttonHeight);
        customizeButton.setGoalY(MenuItem.BUTTON_MIDDLE_Y+(float)(Assets.buttonHeight*0));

        instructionList = new ArrayList<MenuItem>();
        instruction1 = new MenuItem(Assets.instruction1, 0-Assets.instructions.getWidth(), playButton.getGoalY());
        instruction1.setGoalX(5);
        instructionList.add(instruction1);

        instruction2 = new MenuItem(Assets.instruction2, 0-Assets.instructions.getWidth()*2, playButton.getGoalY()-((playButton.getGoalY()-settingsButton.getGoalY())/2));
        instruction2.setGoalX(5);
        instructionList.add(instruction2);

        instruction3 = new MenuItem(Assets.instruction3, 0-Assets.instructions.getWidth()*3, settingsButton.getGoalY());
        instruction3.setGoalX(5);
        instructionList.add(instruction3);

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

    private void drawItems() {
        logoItem.draw(game.batch);
        playButton.draw(game.batch);
        settingsButton.draw(game.batch);
        creditsButton.draw(game.batch);
        customizeButton.draw(game.batch);

        for(MenuItem item : instructionList) {
            item.draw(game.batch);
        }
    }

    private void updateItems(float deltaTime) {
        logoItem.update(deltaTime);
        playButton.update(deltaTime);
        settingsButton.update(deltaTime);
        creditsButton.update(deltaTime);
        customizeButton.update(deltaTime);

        for(MenuItem item : instructionList) {
            item.update(deltaTime);
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
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Unproject it so it works with all resolutions
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX, screenY,0));

        if (playButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            playButton.setTouchDown(true);
        } else if (settingsButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            settingsButton.setTouchDown(true);
        } else if (creditsButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            creditsButton.setTouchDown(true);
        } else if (customizeButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            customizeButton.setTouchDown(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchCoords = game.cam.unproject(new Vector3(screenX,screenY,0));

        if (playButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            playButton.playClickedSound();
            game.setScreen(new GameRenderer(game));
        } else if (settingsButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            settingsButton.playClickedSound();
            game.setScreen(new SettingsMenu(game));
        } else if (creditsButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            creditsButton.playClickedSound();
            game.setScreen(new CreditsMenu(game));
        }else if (customizeButton.getBoundingRectangle().contains(touchCoords.x, touchCoords.y)) {
            customizeButton.playClickedSound();
            game.setScreen(new MainMenu2(game));
        } else {
            playButton.setTouchDown(false);
            settingsButton.setTouchDown(false);
            creditsButton.setTouchDown(false);
            customizeButton.setTouchDown(false);
        }

        return true;
    }
}
