package com.BallRun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameRenderer implements Screen, InputProcessor {
    Main game;

    GameController gc; //GameController

	public GameRenderer(Main game) {
        this.game = game;
        gc = new GameController();
        Gdx.input.setInputProcessor(this);
	}

    @Override
    public void render(float delta) {
        updateItems(Gdx.graphics.getRawDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        gc.drawAllItems(game.batch);
        game.batch.end();
    }

    private void updateItems(float delta) {
        gc.updateAll(delta);

        // If game is finished then change to the Gameover Menu
        if (gc.getCurrentGameState() == GameController.GameState.FINISHED) {
            game.setScreen(new GameoverMenu(game, gc));
        }
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
        gc.touchDown();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gc.touchUp();
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


    @Override
    public void show() {

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
        gc.dispose();
    }
}
