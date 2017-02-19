package com.BallRun.game;

import com.BallRun.game.MenuItems.LogoItem2;
import com.BallRun.game.MenuItems.MenuButton;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by HeyJD on 30-01-17.
 */
public class MainMenu2 implements Screen {
    private Main game;
    Stage stage;
    MenuButton startGameButton;
    MenuButton optionsButton;
    MenuButton exitButton;
    LogoItem2 logoItem;

    public MainMenu2(Main game) {
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

        logoItem = new LogoItem2(stage.getWidth()/2-LogoItem2.WIDTH/2, 1100);
        logoItem.addAction(Actions.sequence(Actions.moveTo(stage.getWidth() / 2 - LogoItem2.WIDTH / 2, 700, 1.5f, Interpolation.swing), Actions.run(logoItem.runnableTurnOnShadow)));
        logoItem.setShadowPosition(stage.getWidth()/2-LogoItem2.WIDTH/2,695);

        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH,Main.CAMERA_HEIGHT);

        stage.addActor(backImage);
        stage.addActor(getButtons());
        stage.addActor(logoItem);
    }

    private Table getButtons() {
        Table table = new Table(Assets.skinGreen);

        startGameButton = new MenuButton("Play", GameRenderer.class, game);
        optionsButton = new MenuButton("Options", SettingsMenu2.class, game);
        exitButton = new MenuButton("Credits", CreditsMenu2.class, game);
        table.setFillParent(true);
//		table.debug();
        table.add(startGameButton).width(512).height(128);
        table.row();
        table.add(optionsButton).width(512).height(128).padTop(10);
        table.row();
        table.add(exitButton).width(512).height(128).padTop(10);

        table.setPosition(0,-1000);
        table.addAction(Actions.moveTo(0,0,1f,Interpolation.circle));

        return table;
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
