
package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, mute;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pausePressed) {
                pausePressed = false;
                if (mute == false) {
                    GamePanel.music.play(0, true);
                    GamePanel.music.loop();
                }

            } else {
                pausePressed = true;
                GamePanel.music.stop();
            }
        }
        if (code == KeyEvent.VK_M) {
            if (mute) {
                mute = false;
                GamePanel.music.play(0, true);
                GamePanel.music.loop();
            } else {
                mute = true;
                GamePanel.music.stop();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
