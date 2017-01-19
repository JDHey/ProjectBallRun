package com.BallRun.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Just displays the score
 * Created by HeyJD on 22/04/2015.
 */
public class Score {
    public static final int WIDTH = 512;
    public static final int HEIGHT = 128;

    private Sprite sprite = new Sprite(Assets.scoreBox);
    private float score;
    private BitmapFont scoreFont = Assets.LCDFont;

    public Score(float x, float y) {
        this(x,y,0);
    }

    public Score(float x, float y, float score) {
        sprite.setPosition(x, y);
        sprite.setSize(WIDTH, HEIGHT);
        this.score = score;

        scoreFont.setScale(2);
    }

    public Score(Score score) {
        sprite.set(score.getSprite());
        this.score = score.getScore();
        scoreFont.setScale(2);
    }

    /** Origin is at the top left
     * sprite.getY()+HEIGHT-((HEIGHT-scoreFont.getCapHeight())/2)
     * ^ That line puts it in the centre of the HEIGHT of the sprite.
     * It finds the difference between the height of the font and the height of the sprite,
     * then divides it by 2.
     * @param batch
     */
    public void draw(SpriteBatch batch, float alpha) {
        sprite.draw(batch, alpha);
        scoreFont.setColor(1,1,1,alpha); //Change the font alpha
        scoreFont.draw(batch, String.valueOf(Math.round(score)), sprite.getX() + 60, sprite.getY() + HEIGHT - ((HEIGHT - scoreFont.getCapHeight()) / 2));
    }

    /** Draws with alpha 1 */
    public void draw(SpriteBatch batch) {
        draw(batch, 1);
    }

    /** Multiplies by 10 to make it more interesting
     * @param deltaTime
     */
    public void incrementScore(float deltaTime) {
        score += 10f * deltaTime;
    }

    public float getScore() {
        return score;
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public void setPosition(float posX, float posY) {
        sprite.setPosition(posX, posY);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dispose() {
        scoreFont.dispose();
    }
}
