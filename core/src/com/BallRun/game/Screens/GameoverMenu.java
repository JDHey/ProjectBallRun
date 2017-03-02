package com.BallRun.game.Screens;

import com.BallRun.game.GameController;
import com.BallRun.game.Main;
import com.BallRun.game.MenuItems.Highscores;
import com.BallRun.game.MenuItems.LogoItem;
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
 * Screen gets shown when player dies
 * Created by HeyJD on 08-02-17.
 */
public class GameoverMenu implements Screen {
    private Main game;
    private GameController gc;

    private Stage stage;
    MenuButton restartButton;
    MenuButton backButton;
    Highscores highscores;
    LogoItem logoItem;

    public GameoverMenu(Main game, GameController gc) {
        this.game = game;
        this.gc = gc;
    }

    @Override
    public void show() {
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        logoItem = new LogoItem(stage.getWidth() / 2 - LogoItem.WIDTH / 2, 1100);
        logoItem.addAction(Actions.sequence(Actions.moveTo(stage.getWidth() / 2 - LogoItem.WIDTH / 2, 760, 2f, Interpolation.swing), Actions.run(logoItem.runnableTurnOnShadow)));
        logoItem.setShadowPosition(stage.getWidth() / 2 - LogoItem.WIDTH / 2, 755);

        Table table = new Table(Assets.skinGreen);

        highscores = new Highscores(-250, 240, gc.getScore());
        highscores.addAction(Actions.delay(2f, Actions.moveTo(200, 240, 2f, Interpolation.swing)));

        restartButton = new MenuButton("Restart", GameRenderer.class, game);
        backButton = new MenuButton("Back", MainMenu.class, game);
        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        table.setFillParent(true);
        table.add(restartButton).width(512).height(128);
        table.row();
        table.add(backButton).width(512).height(128).padTop(10);
        table.row();

        stage.addActor(backImage);
        stage.addActor(table);
        stage.addActor(highscores);
        stage.addActor(logoItem);


        stage.addAction(Actions.alpha(0.0f));
        stage.act(); //Sets the alpha to 0 instantly
        stage.addAction(Actions.fadeIn(2)); //Sets the alpha to fade in over time
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        gc.drawAllItems(game.batch);
        game.batch.end();
        stage.act(delta);
        stage.draw();
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
    public void dispose() {
        stage.dispose();
    }
}
