package com.BallRun.game;

import com.BallRun.game.MenuItems.MenuButton;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Used for the customization screen.
 * American-English to accommodate for the American audience.
 * Created by HeyJD on 26-01-17.
 */
public class CustomizationMenu implements Screen {
    private Main game;
    Stage stage;
    MenuButton startGameButton;
    MenuButton optionsButton;
    MenuButton exitButton;

    public CustomizationMenu(Main game) {
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
    public void resize(int width, int height) {    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(Assets.skinGreen);

        startGameButton = new MenuButton("Back", GameRenderer.class, game);
        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH,Main.CAMERA_HEIGHT);
        table.setFillParent(true);
//		table.debug();
        table.add(startGameButton).width(512).height(128);
        table.row();
        table.add(optionsButton).width(512).height(128).padTop(10);
        table.row();
        table.add(exitButton).width(512).height(128).padTop(10);

        stage.addActor(backImage);
        stage.addActor(table);
    }

    @Override
    public void hide() {    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
