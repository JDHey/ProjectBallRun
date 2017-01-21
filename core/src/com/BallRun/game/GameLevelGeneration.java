package com.BallRun.game;

import com.BallRun.game.Sprites.Assets;
import com.BallRun.game.Sprites.BasicScrollingSprite;
import com.BallRun.game.Sprites.Block;
import com.BallRun.game.Sprites.Cloud;
import com.BallRun.game.Sprites.EnvironmentSprite;
import com.BallRun.game.Sprites.Spike;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Handles the level generation
 * Created by HeyJD on 21-01-17.
 */
public class GameLevelGeneration {
    private boolean isPaused = false;

    private List<Block> floor;
    private List<BasicScrollingSprite> deadlySprites;
    private List<Cloud> clouds;
    private List<EnvironmentSprite> environmentSprites;
    private Queue<BasicScrollingSprite> queueOfCollidingSprites;
    private float cloudSpawnTimer = 0;

    private static final int BLOCK_GAP = 0;
    private static final int BLOCK_SPIKE = 1;
    private static final float MAX_DIFFICULTY = 0.5f; //0.5 is equal to 50% of the time it spawns a block it makes a gap, so don't make it higher
    private static final float DIFFICULTY_MULTIPLIER = 0.0005f; //Turn to 0.01 for a really tough time
    private float chanceToSpawn = 0;
    private float chanceToSpawnIncreaser = 0.05f;
    private float chanceToSpawnEnvironmentSprite = 0.1f; //From 0 to 1;

    public GameLevelGeneration() {
        this.floor = new ArrayList<Block>(); //I Should probably use a Queue
        this.deadlySprites = new ArrayList<BasicScrollingSprite>(); //Any deadly sprite
        this.clouds = new ArrayList<Cloud>();
        this.environmentSprites = new ArrayList<EnvironmentSprite>();
        this.queueOfCollidingSprites = new LinkedList<BasicScrollingSprite>();

        generateStart();
    }

    private void generateStart() {
        for(int i=0; i<= Main.CAMERA_WIDTH; i += Block.DEFAULT_WIDTH) {
            floor.add(new Block(i,0));
        }
        EnvironmentSprite arrowSprite = new EnvironmentSprite(1000, 128);
        arrowSprite.setRegion(Assets.arrowItem);
        environmentSprites.add(arrowSprite);
    }

    public void updateAll(float deltaTime, Rectangle ballBoundingRectangle) {
        if (!isPaused) {
            updateFloor(deltaTime, ballBoundingRectangle);
            updateDeadlySprites(deltaTime, ballBoundingRectangle);
            updateClouds(deltaTime);
            updateEnvironmentSprites(deltaTime);
        }
    }

    public void drawAll(SpriteBatch batch, float drawAlpha) {
        if (drawAlpha > 0) {
            for (Cloud cloud : clouds) {
                cloud.draw(batch, drawAlpha);
            }

            for (EnvironmentSprite sprite : environmentSprites) {
                sprite.draw(batch, drawAlpha);
            }

            for (Block block : floor) {
                block.draw(batch, drawAlpha);
            }

            //Make sure to draw deadly sprites last so they're always on top
            for (BasicScrollingSprite item : deadlySprites) {
                item.draw(batch, drawAlpha);
            }
        }
    }

    private void updateFloor(float deltaTime, Rectangle ballBoundingRectangle) {
        boolean ballGrounded = false;
        List<Block> removeBlockList = new ArrayList<Block>();

        for (Block block : floor) {
            block.updateMotion(deltaTime);

            if (block.getBoundingRectangle().overlaps(ballBoundingRectangle)) {
                queueOfCollidingSprites.offer(block);
            }

            if (block.getX() < 0-Block.DEFAULT_WIDTH) { //If outside of the screen
                removeBlockList.add(block);
            }
        }

        // Called outside of the loop because you can't modify the list while it is iterating
        removeBlocks(removeBlockList);
    }

    private void updateDeadlySprites(float deltaTime, Rectangle ballBoundingRectangle) {
        for(BasicScrollingSprite item : deadlySprites) {
            item.updateMotion(deltaTime);

            if (item.getBoundingRectangle().overlaps(ballBoundingRectangle)) {
                queueOfCollidingSprites.offer(item);
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

    private void updateEnvironmentSprites(float deltaTime) {
        for(EnvironmentSprite sprite : environmentSprites) {
            sprite.updateMotion(deltaTime);
        }
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
        float rand = (float) Math.random();
        float lastBlockX = floor.get(floor.size() - 1).getX();
        float lastBlockY = floor.get(floor.size() - 1).getY();
        if (chanceToSpawn < rand) {
            floor.add(new Block(lastBlockX + Block.DEFAULT_WIDTH * 1, lastBlockY));
            rand = (float) Math.random();
            if (rand < chanceToSpawnEnvironmentSprite) {
                //Thirty percent chance to spawn a piece of the environment
                addEnvironmentSprite(lastBlockX + Block.DEFAULT_WIDTH, lastBlockY);
            }
            chanceToSpawn += chanceToSpawnIncreaser;
        } else {
            //Reassign rand, otherwise it's using pseudo-randomness
            rand = Math.round(Math.random()); //Either 0 or 1

            if (rand == BLOCK_GAP) { //Add gap
                addGap(lastBlockX);
            } else if (rand == BLOCK_SPIKE) { //Add spike
                boolean isSpace = false;
                //Make sure there is sufficient space to jump
                //This checks if the block 2 blocks behind exists (i.e. not a gap)
                if (floor.get(floor.size() - 1-2).getX() == (lastBlockX - Block.DEFAULT_WIDTH *2)) {
                    if (!deadlySprites.isEmpty()) {
                        //Make sure that there were no spikes near it, so you have space to jump
                        if (!(deadlySprites.get(deadlySprites.size() - 1).getX() >= (lastBlockX - Block.DEFAULT_WIDTH * 3))) {
                            isSpace = true;
                        }
                    } else {
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
        Block block = new Block(lastBlockX + Block.DEFAULT_WIDTH * randWidth, Block.DEFAULT_HEIGHT * randHeight);

        //Change the sprites for the edges
        block.setRegion(Assets.blockLeftEdge);
        floor.get(floor.size()-1).setRegion(Assets.blockRightEdge);

        floor.add(block);
        chanceToSpawn = 0;
    }

    /** Adds a spike */
    private void addSpike(float lastBlockX, float lastBlockY) {
        deadlySprites.add(new Spike(lastBlockX + Block.DEFAULT_WIDTH, lastBlockY + Block.DEFAULT_HEIGHT));
        floor.add(new Block(lastBlockX + Block.DEFAULT_WIDTH, lastBlockY));
        chanceToSpawn = -0.15f; //Impossible chance of spawning for a small duration
    }

    /** Adds a piece of the environment */
    private void addEnvironmentSprite(float lastBlockX, float lastBlockY) {
        environmentSprites.add(new EnvironmentSprite(lastBlockX,lastBlockY + Block.DEFAULT_HEIGHT));
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Queue<BasicScrollingSprite> getQueueOfCollidingSprites() {
        return queueOfCollidingSprites;
    }
}