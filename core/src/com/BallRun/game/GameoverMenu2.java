package com.BallRun.game;

import com.BallRun.game.MenuItems.Highscores2;
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
 * Created by HeyJD on 08-02-17.
 */
public class GameoverMenu2 implements Screen {
    private Main game;
    private GameController gc;

    private Stage stage;
    MenuButton restartButton;
    MenuButton backButton;
    Highscores2 highscores;
    LogoItem2 logoItem;

    public GameoverMenu2(Main game, GameController gc) {
        this.game = game;
        this.gc = gc;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        logoItem = new LogoItem2(stage.getWidth()/2-LogoItem2.WIDTH/2, 1100);
        logoItem.addAction(Actions.sequence(Actions.moveTo(stage.getWidth() / 2 - LogoItem2.WIDTH / 2, 760, 2f, Interpolation.swing), Actions.run(logoItem.runnableTurnOnShadow)));
        logoItem.setShadowPosition(stage.getWidth()/2-LogoItem2.WIDTH/2,755);

        Table table = new Table(Assets.skinGreen);

        highscores = new Highscores2(-250,250, (int) gc.score.getScore());
        highscores.addAction(Actions.delay(2f,Actions.moveTo(200,250,2f,Interpolation.swing)));

        restartButton = new MenuButton("Restart", GameRenderer.class, game);
        backButton = new MenuButton("Back", MainMenu2.class, game);
        Image backImage = new Image(Assets.menuBackground);
        backImage.setSize(Main.CAMERA_WIDTH,Main.CAMERA_HEIGHT);
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
    public void resize(int width, int height) {    }

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
