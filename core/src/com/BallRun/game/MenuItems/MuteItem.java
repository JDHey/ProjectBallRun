package com.BallRun.game.MenuItems;

import com.BallRun.game.Assets;
import com.BallRun.game.Main;
import com.BallRun.game.SaveFile;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** The mute button
 * Created by HeyJD on 13/06/2015.
 */
public class MuteItem extends MenuItem {
    TextureRegion muteTexture = Assets.muteButton;
    TextureRegion unmuteTexture = Assets.unmuteButton;

    public MuteItem(float posX, float posY) {
        super(Assets.muteButton, posX, posY, Main.CAMERA_WIDTH, 0f);
        setMute(SaveFile.isMute);
    }

    private void setMute(boolean isMute) {
        if (isMute) {
            setRegion(unmuteTexture);
        } else {
            setRegion(muteTexture);
        }
        SaveFile.isMute = isMute;
    }

    public void toggleMute() {
        setMute(!SaveFile.isMute);
        SaveFile.save();
    }
}
