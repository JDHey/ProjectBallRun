package com.BallRun.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/** This class takes care of the main gameplay mechanics during play
 *
 * Created by HeyJD on 9/04/2015.
 */
public class GameController {
    public static final float GAME_SPEED = 1014;

    public static final Vector2 blockGravity = new Vector2(-GAME_SPEED, 0);

    private static final float FADE_OUT_SPEED = 0.02f;
    private float drawAlpha = 1;

    private float backgroundSrcX = 0;

    public static final float BALL_MAX_FALL_VELOCITY = 3000;
    public static final float BALL_MAX_JUMP_VELOCITY = 1500;
    public static final float BALL_INITIAL_JUMP_VELOCITY = 700;
    private static final int BALL_SPAWN_X = 256;
    private static final int BALL_SPAWN_Y = 640;

    public final Ball ball;
    private List<Block> floor;
    public boolean pauseFloor = false;

    private List<Cloud> clouds;
    private float cloudSpawnTimer = 0;
    private List<Block> randomItems;

    public final Score score;

    //Generation stuff (should probably put this in a new class)
    private static final int BLOCK_GAP = 0;
    private static final int BLOCK_SPIKE = 1;
    private static final float MAX_DIFFICULTY = 0.5f; //0.5 is equal to 50% of the time it makes a gap, so don't make it higher
    private static final float DIFFICULTY_MULTIPLIER = 0.0005f; //Turn to 0.01 for a really tough time
    private float chanceToSpawn = 0;
    private float chanceToSpawnIncreaser = 0.05f;


    public GameController() {
        this.ball = new Ball(BALL_SPAWN_X, BALL_SPAWN_Y);
        this.floor = new ArrayList<Block>(); //I Should probably use a Queue
        this.score = new Score(Main.CAMERA_WIDTH - Score.WIDTH, Main.CAMERA_HEIGHT-Score.HEIGHT);
        this.randomItems = new ArrayList<Block>(); //Using a block, but setting the sprite to anything
        this.clouds = new ArrayList<Cloud>();

        generateStart();

        if (!SaveFile.isMute) {
            Assets.music.play();
        }
    }

    private void generateStart() {
        for(int i=0; i<= Main.CAMERA_WIDTH; i += Block.WIDTH) {
            floor.add(new Block(i,0));
        }
        randomItems.add(new Block(1000,128, Assets.arrowItem));
    }

    private void updateBall(float deltaTime) {
        //If the ball is half way out of the screen, kill();
        if (!ball.isDead() && ball.getY() <= 0-Ball.HEIGHT /2) {
            kill();
        }
        ball.updateMotion(deltaTime);
    }

    private void updateFloor(float deltaTime) {
        boolean ballGrounded = false;
        List<Block> removeBlockList = new ArrayList<Block>();

        for (Block block : getFloor()) {
            block.updateMotion(deltaTime);

            if (block.checkCollision(ball)) {
                //If the block is higher than 50% of the blocks height, teleport it to the top and call it grounded
                //If it is lower, let it fall
                if ((ball.getY() - block.getY()) > (Block.HEIGHT * 0.50)) {
                    ball.setY(block.getY() + Block.HEIGHT - 1); //Move it up so it's not stuck in the block
                    ballGrounded = true;
                } else {
                    kill();
                }
            }

            if (block.getX() < 0-Block.WIDTH) { //If outside of the screen
                removeBlockList.add(block);
            }
        }
        if (ballGrounded) { // If on the ground
            ball.setState(Ball.STATE_GROUNDED);
        } else if (ball.getState() != Ball.STATE_JUMPING) {
            ball.setState(Ball.STATE_FALLING); //Gets activated after falling off a block
        }

        // Called outside of the loop because you can't modify the list while it is iterating
        removeBlocks(removeBlockList);
    }

    private void updateRandomItems(float deltaTime) {
        Rectangle spikeCollision;
        for(Block item : randomItems) {
            item.updateMotion(deltaTime);
            if (item.isDeadly()) {
                //Custom collision checker
                //Makes it fit the visual size of the spikes, even though it's set to 128,128
                spikeCollision = item.getBoundingRectangle();
                spikeCollision.set(spikeCollision.getX()+10, spikeCollision.getY(), spikeCollision.getWidth()-10, spikeCollision.getHeight()/2);

                if (spikeCollision.overlaps(ball.getBoundingRectangle())) {
                    //Kill only if you touch the spikes (which is in the lower 50%)
                    kill();
                }
            }
        }
    }

    /** Spawns, updates, and removes clouds */
    private void updateClouds(float deltaTime) {
        List<Cloud> removeList = new ArrayList<Cloud>();
        for(Cloud cloud : clouds) {
            cloud.updateMotion(deltaTime);

            //Destroy if off screen
            if (cloud.getX() < -500) {
                removeList.add(cloud);
            }
        }
        //You can't remove during a foreach loop
        clouds.removeAll(removeList);

        if (cloudSpawnTimer <= 0) {
            cloudSpawnTimer = (float)Math.random()*20+1; //Reset timer to a random time
            clouds.add(new Cloud());
        } else {
            cloudSpawnTimer -= deltaTime*10;
        }
    }

    private void kill() {
        pauseFloor = true;
        if (!SaveFile.isMute) {
            Assets.music.stop(); //This freezes the GalaxyS5 half a second due to hardware problems
            Assets.deathSound.play();
        }
        ball.die();
    }

    private void removeBlocks(List<Block> list) {
        for (Block block : list) {
            floor.remove(block);
            addBlock();
        }
    }

    /** Add a block to the end of the list and align it
     *  It uses pseudo randomness.
     *  The chance to spawn a block N Block.Widths' away increases every time a block is spawned.
     *  i.e. The chance to make a gap increases the more it doesn't make a gap
     *  The chance increases based on the chanceToSpawnIncreaser. The increaser increases
     *  by a small amount every time a block is spawned. This means it gets harder the further
     *  you get in the game.
     */
    private void addBlock() {
        float rand = (float)Math.random();
        float lastBlockX = floor.get(floor.size() - 1).getX();
        float lastBlockY = floor.get(floor.size() - 1).getY();
        if (chanceToSpawn < rand) {
            floor.add(new Block(lastBlockX + Block.WIDTH * 1, lastBlockY));
            chanceToSpawn += chanceToSpawnIncreaser;
        } else {
            //Reassign rand, otherwise it's using pseudo-randomness
            rand = Math.round(Math.random()); //Either 0 or 1


            if (rand == BLOCK_GAP) {
                //Add gap
                addGap(lastBlockX);
            } else if (rand == BLOCK_SPIKE) {
                //Add spike
                boolean isSpace = false;
                //Make sure there is sufficient space to jump
                //This checks if the block 2 blocks behind exists (i.e. not a gap)
                if (floor.get(floor.size() - 1-2).getX() == (lastBlockX - Block.WIDTH*2)) {
                    //Make sure that there were no spikes near it, so you have space to jump
                    if (!(randomItems.get(randomItems.size()-1).getX() >= (lastBlockX - Block.WIDTH * 3))) {
                        isSpace = true;
                    }
                }

                if (isSpace) {
                    addSpike(lastBlockX, lastBlockY);
                } else {
                    addGap(lastBlockX);
                }
            } else {
                //Error, this should not happen
                throw new IllegalArgumentException("Tried to add nothing");
            }


        }

        if (chanceToSpawnIncreaser < MAX_DIFFICULTY) { chanceToSpawnIncreaser += DIFFICULTY_MULTIPLIER; }
    }

    /** Adds a gap */
    private void addGap(float lastBlockX) {
        int randHeight = (int)Math.round(Math.random());
        float randWidth = (float)(Math.random()*4f)+3.3f;
        Block block = new Block(lastBlockX + Block.WIDTH * randWidth, Block.HEIGHT * randHeight);

        //Change the sprites for the edges
        block.setRegion(Assets.blockLeftEdge);
        floor.get(floor.size()-1).setRegion(Assets.blockRightEdge);

        floor.add(block);
        chanceToSpawn = 0;
    }

    /** Adds a spike */
    private void addSpike(float lastBlockX, float lastBlockY) {
        randomItems.add(new Block(lastBlockX+Block.WIDTH, lastBlockY+Block.HEIGHT, Assets.spikes, true));
        floor.add(new Block(lastBlockX + Block.WIDTH , lastBlockY));
        chanceToSpawn = -0.15f; //Impossible chance of spawning for a small duration
    }

    /** Keeps all the updates in one neat spot
     *
     * @param deltaTime
     */
    public void updateAllItems(float deltaTime) {
        updateBall(deltaTime);

        if (!pauseFloor) {
            updateFloor(deltaTime);
            incrementBackgroundSrcX(deltaTime);
            updateScore(deltaTime);
            updateRandomItems(deltaTime);
            updateClouds(deltaTime);
        }
    }

    public void drawAllItems(SpriteBatch batch) {
        batch.draw(Assets.gameBackground, 0, 0, Math.round(getBackgroundSrcX()), 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));
        if (drawAlpha > 0) {
            for (Block block : getFloor()) {
                block.draw(batch, drawAlpha);
            }

            for (Block item : randomItems) {
                item.draw(batch, drawAlpha);
            }

            for (Cloud cloud : clouds) {
                cloud.draw(batch, drawAlpha);
            }

            ball.draw(batch, drawAlpha);
            score.draw(batch, drawAlpha);

            //Make sure it continues the transition all the way to 0 alpha
            if (drawAlpha != 1 && drawAlpha != 0) {
                fadeOut();
            }
        }

        if (drawAlpha < 1) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 1-drawAlpha); //set alpha to 1
            batch.draw(Assets.menuBackground, 0, 0, Math.round(Main.CAMERA_WIDTH), Math.round(Main.CAMERA_HEIGHT));
            batch.setColor(c.r, c.g, c.b, 1f);
        }
    }

    /** There is only a fadeOut, no fadeIn */
    public void fadeOut() {
        drawAlpha -= FADE_OUT_SPEED;
        if (drawAlpha > 1) { drawAlpha = 1; } else
        if (drawAlpha < 0) { drawAlpha = 0; }
    }

    public float getDrawAlpha() {
        return drawAlpha;
    }

    public List<Block> getFloor() {
        return floor;
    }

    private void incrementBackgroundSrcX(float deltaTime) {
        //I multiply by 1000 so it doesn't noticeably (if it's noticeable at all) reset too often
        if (backgroundSrcX >= Assets.gameBackground.getWidth()*1000) {
            backgroundSrcX = backgroundSrcX - Assets.gameBackground.getWidth(); //Reset it so it doesn't overflow
        }

        //Because blockGravity.x is negative, I do -=. This makes sure backgroundSrcX is positive
        backgroundSrcX -= blockGravity.x * deltaTime;
    }

    private void updateScore(float deltaTime) {
        score.incrementScore(deltaTime);
    }

    public float getBackgroundSrcX() {
        return backgroundSrcX;
    }

    public void dispose() {
        score.dispose();
    }
}
