package com.BallRun.game.Screens;

import com.BallRun.game.GameController;
import com.BallRun.game.Log;
import com.BallRun.game.Main;
import com.BallRun.game.MenuItems.ScoreLabel;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Starts and renders out the game
 * Created by HeyJD on 28-02-17.
 */
public class GameRenderer extends InputAdapter implements Screen {
    private static final String INSTRUCTIONS_STRING_1 = "Tap to jump. Hold tap to jump higher. \n Try to beat your high score!";
    private static final String INSTRUCTIONS_STRING_2 = "Tap to start";
    private Main game;

    GameController gc; //GameController
    FPSLogger fpsLogger;
    Stage stage;
    Label instructionLabel1;
    Label instructionLabel2;
    ScoreLabel scoreLabel;

    boolean isGameStarted;

    public GameRenderer(Main game) {
        this.game = game;
        gc = new GameController();
        fpsLogger = new FPSLogger();

        Gdx.input.setInputProcessor(this);
        isGameStarted = false;
    }

    @Override
    public void render(float delta) {
        updateItems(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        gc.drawAllItems(game.batch);
        game.batch.end();

        if (Log.DEBUG) {
            fpsLogger.log();
        }
        stage.act(delta);
        stage.draw();
    }

    private void updateItems(float delta) {
        gc.updateAll(delta);
        scoreLabel.setText(String.valueOf(gc.getScore()));

        if (gc.getCurrentGameState() == GameController.GameState.PLAYING) {
            if (!isGameStarted) {
                //This code only runs once when the game starts
                if (instructionLabel1.getActions().size == 0) {
                    instructionLabel1.addAction(Actions.moveTo(-1500, 550, 1f, Interpolation.swingIn));
                } else {
                    instructionLabel1.clearActions();
                    instructionLabel1.addAction(Actions.moveTo(instructionLabel1.getX(), -250, 1f, Interpolation.swingIn));
                }

                if (instructionLabel2.getActions().size == 0) {
                    instructionLabel2.addAction(Actions.moveTo(-1500, 350, 1f, Interpolation.swingIn));
                } else {
                    instructionLabel2.clearActions();
                    instructionLabel2.addAction(Actions.moveTo(instructionLabel2.getX(), -250, 1f, Interpolation.swingIn));
                }

                //Show the score when game starts
                scoreLabel.addAction(Actions.delay(0.5f,
                        Actions.moveTo(stage.getWidth() - scoreLabel.getWidth(), stage.getHeight() - scoreLabel.getHeight(), 1f, Interpolation.swing)
                ));

                isGameStarted = true;
            }

            //If the ball dies then score label should hide itself if it hasn't already
            if (gc.getBall().isDead() && scoreLabel.getActions().size == 0) {
                scoreLabel.addAction(Actions.moveTo(stage.getWidth() - scoreLabel.getWidth(), stage.getHeight() + 300, 1f, Interpolation.swingIn));
            }
        } else if (gc.getCurrentGameState() == GameController.GameState.FINISHED) {

            //Change to the Gameover Menu
            game.setScreen(new GameoverMenu(game, gc));
        }
    }

    @Override
    public void show() {
        stage = new Stage(game.viewport);

        float middleScreenX;
        instructionLabel1 = new Label(INSTRUCTIONS_STRING_1, Assets.skinGreen);
        instructionLabel1.setAlignment(Align.center);
        instructionLabel1.setFontScale(2);
        middleScreenX = stage.getWidth() / 2 - instructionLabel1.getWidth() / 2;
        instructionLabel1.setPosition(middleScreenX, -250);
        instructionLabel1.addAction(
                Actions.moveTo(middleScreenX, 550, 1.5f, Interpolation.swing)
        );

        instructionLabel2 = new Label(INSTRUCTIONS_STRING_2, Assets.skinGreen);
        instructionLabel2.setAlignment(Align.center);
        instructionLabel2.setFontScale(2);
        middleScreenX = stage.getWidth() / 2 - instructionLabel2.getWidth() / 2;
        instructionLabel2.setPosition(middleScreenX, -250);
        instructionLabel2.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.moveTo(middleScreenX, 350, 1.5f, Interpolation.swing)
        ));

        scoreLabel = new ScoreLabel("0");
        scoreLabel.setPosition(stage.getWidth() - scoreLabel.getWidth(), stage.getHeight() + 300);

        stage.addActor(instructionLabel1);
        stage.addActor(instructionLabel2);
        stage.addActor(scoreLabel);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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
    public void dispose() {
        stage.dispose();
        gc.dispose();
    }

}
