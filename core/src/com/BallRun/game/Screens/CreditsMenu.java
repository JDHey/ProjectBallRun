package com.BallRun.game.Screens;

import com.BallRun.game.Main;
import com.BallRun.game.MenuItems.MenuButton;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * The credits menu
 * Created by HeyJD on 01-02-17.
 */
public class CreditsMenu implements Screen {
    private static final String TITLE = "Credits";
    private static final String BODY = "All textures and sounds are under public domain \n All programming  -  Jayden Leslie";

    private Main game;
    Stage stage;
    MenuButton backButton;
    Label creditTitleLabel;
    Label creditBodyLabel;

    public CreditsMenu(Main game) {
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

        Table table = new Table(Assets.skinGreen);

        creditTitleLabel = new Label(TITLE, Assets.skinGreen);
        creditTitleLabel.setAlignment(Align.center);
        creditTitleLabel.setFontScale(3);
        creditBodyLabel = new Label(BODY, Assets.skinGreen);
        creditBodyLabel.setAlignment(Align.center);
        creditBodyLabel.setFontScale(1.5f);

        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);

        backButton = new MenuButton("Back", MainMenu.class, game);

        table.setFillParent(true);
        table.add(creditTitleLabel).height(128);
        table.row();
        table.add(creditBodyLabel).height(128).padTop(20);
        table.row();
        table.add(backButton).width(512).height(128).padTop(20);

        stage.addActor(backImage);
        stage.addActor(table);
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
