package com.BallRun.game.MenuItems;

import com.BallRun.game.Main;
import com.BallRun.game.SaveFile;
import com.BallRun.game.Sprites.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.reflect.InvocationTargetException;

/**
 * Any menu button uses this class
 * Created by HeyJD on 29-01-17.
 */
public class MenuButton extends TextButton {
    public MenuButton(String text) {
        super(text, Assets.skinGreen);
    }

    public MenuButton(String text, Class screenClass, Game game) {
        super(text, Assets.skinGreen);
        setScreenLink(game, screenClass);
    }

    public void setScreenLink(final Game game, final Class screenClass) {

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Screen screen = null;
                try {
                    screen = (Screen)screenClass.getConstructor(Main.class).newInstance(game);
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                playClickedSound();
                game.setScreen(screen);
            }
        });
    }

    public static void playClickedSound() {
        if (!SaveFile.isMute()) {
            Assets.clickSound.play();
        }
    }



}
