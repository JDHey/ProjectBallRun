package com.BallRun.game.Screens;

import com.BallRun.game.Main;
import com.BallRun.game.MenuItems.Highscores;
import com.BallRun.game.MenuItems.MenuButton;
import com.BallRun.game.SaveFile;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The settings menu
 * Created by HeyJD on 01-02-17.
 */
public class SettingsMenu implements Screen {
    private Main game;
    Stage stage;
    MenuButton muteSoundButton;
    MenuButton resetScoresButton;
    MenuButton backButton;
    Highscores highscores;
    Label muteSoundLabel;

    public SettingsMenu(Main game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
//		Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        highscores = new Highscores(-250, 240);
        highscores.addAction(Actions.moveTo(200, 240, 2f, Interpolation.swing));

        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);

        stage.addActor(backImage);
        stage.addActor(getButtons());
        stage.addActor(highscores);
        stage.addActor(muteSoundLabel);
    }

    private Table getButtons() {
        Table table = new Table(Assets.skinGreen);

        backButton = new MenuButton("Back", MainMenu.class, game);

        setMuteButton();
        setResetScoresButton();

        table.setFillParent(true);
        table.add(muteSoundButton).width(512).height(128);
        table.row();
        table.add(resetScoresButton).width(512).height(128).padTop(10);
        table.row();
        table.add(backButton).width(512).height(128).padTop(10);

        return table;
    }

    private void setMuteButton() {
        muteSoundLabel = new Label("", Assets.skinGreen);
        muteSoundButton = new MenuButton((SaveFile.isMute() ? "Unmute" : "Mute") + " sound");
        muteSoundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveFile.setMute(!SaveFile.isMute());
                muteSoundButton.setText((SaveFile.isMute() ? "Unmute" : "Mute") + " sound");

                //Sets position and alpha then moves label up into stage
                muteSoundLabel.setText("Sound " + (SaveFile.isMute() ? "muted" : "unmuted"));
                muteSoundLabel.pack();
                muteSoundLabel.clearActions();
                muteSoundLabel.setPosition(stage.getWidth() / 2 - muteSoundLabel.getWidth() / 2, -200);
                muteSoundLabel.addAction(Actions.alpha(1)); //Reset alpha
                muteSoundLabel.addAction(
                        Actions.sequence(
                                Actions.moveTo(stage.getWidth() / 2 - muteSoundLabel.getWidth() / 2, 100, 2f, Interpolation.swing),
                                Actions.fadeOut(2f)
                        )
                );

                if (!SaveFile.isMute()) {
                    MenuButton.playClickedSound();
                }

                SaveFile.save();
            }
        });
    }

    public void setResetScoresButton() {
        resetScoresButton = new MenuButton("Reset scores");
        resetScoresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Only reset scores once
                if (!resetScoresButton.isDisabled()) {
                    MenuButton.playClickedSound();
                    highscores.resetScores();
                    resetScoresButton.setDisabled(true);

                    Label resetScoreLabel = new Label("Scores reset", Assets.skinGreen);
                    resetScoreLabel.setPosition(stage.getWidth() / 2 - resetScoreLabel.getWidth() / 2, -200);
                    resetScoreLabel.addAction(
                            Actions.sequence(
                                    Actions.moveTo(stage.getWidth() / 2 - resetScoreLabel.getWidth() / 2, 100, 2f, Interpolation.swing),
                                    Actions.fadeOut(2f)
                            )
                    );
                    stage.addActor(resetScoreLabel);
                }
            }
        });
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
    public void dispose() {
        stage.dispose();
    }
}
