/*
 * PlayManager
 * 
 * Draw the play area
 * Manage tetrominoes
 * Handles Gameplay action(deleting lines, adding scores,etc)
 */
package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Square;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;

public class PlayManager {
    // main play arae
    final int WIDTH = 360;
    final int HEIGHT = 600;

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // MINO
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // others
    public static int dropInterval = 60;// mino drops in every 60 frames
    boolean gameOver;

    // Effects
    boolean effectCountsOn;
    int effectConter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // score
    int level = 1;
    int lines;
    int scores;

    // constractor
    public PlayManager() {
        // main play area frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // mino starting position area

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // set the starting mino

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

    }

    private Mino pickMino() {
        // pick a random mino
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0:
                mino = new Mino_L1();
                break;
            case 1:
                mino = new Mino_L2();
                break;
            case 2:
                mino = new Mino_Square();
                break;
            case 3:
                mino = new Mino_Bar();
                break;
            case 4:
                mino = new Mino_T();
                break;
            case 5:
                mino = new Mino_Z1();
                break;
            case 6:
                mino = new Mino_Z2();
                break;
        }
        return mino;

    }

    public void update() {
        // check if the currentMino is Active
        if (currentMino.active == false) {
            // if the mino is not active , put it into the staticBlocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // check if the game is over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                // this means the currentMino immedietly collided a block and couldn't move at
                // all
                // so it's xy are the same with the nextMino's
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.se.play(2,false);

            }

            currentMino.deactivating = false;

            // replace the currentMino with the nextMino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // when a mino becomes inactive , check if line(s) can be deleted
            checkDelete();
        } else {
            currentMino.update();
        }

    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockcount = 0;
        int lineCounts = 0;
        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    // increase the count if there is a static block
                    blockcount++;
                }
            }

            x += Block.SIZE;
            if (x == right_x) {
                // if the blockcount hists 12,that means the current y line is all filled with
                // block .. so we can delete them or full line
                if (blockcount == 12) {
                    effectCountsOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // remove all the blocks in current y line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCounts++;
                    lines++;
                    // drop speed
                    // if the line scores hits a certain number, increase the drop the speed
                    // 1 is the fastest

                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    // a line has been deleted so need to slide down blocks that are above it
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        // if a block is above the current y, move it down by the block size
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockcount = 0;
                x = left_x;
                y += Block.SIZE;

            }
        }
        // add score
        if (lineCounts > 0) {
            GamePanel.se.play(1,false);
            int singleLineScore = 10 * level;
            scores += singleLineScore * lineCounts;
        }

    }

    public void draw(Graphics2D g2) {
        // draw play area frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));// this is the main frame white boundry 4 pixel
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // draw next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("Next", x + 60, y + 60);

        // Draw Score frame
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y); y += 70;
        g2.drawString("LINES: " + lines, x, y); y += 70;
        g2.drawString("SCORE: " + scores, x, y);

        // Draw the currentMino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

        // draw the next mino
        nextMino.draw(g2);

        // draw static Blocks
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).deaw(g2);
        }

        // draw effect
        if (effectCountsOn) {
            effectConter++;

            g2.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < effectY.size(); ++i) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }
            if (effectConter == 10) {
                effectCountsOn = false;
                effectConter = 0;
                effectY.clear();
            }
        }

        // draw pause and game over;
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);

        }

        else if (KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }

        // Draw the game Title
        x = 35;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Simple Tetris", x, y);

    }

}
