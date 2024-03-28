
package main;

//import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // set icon image*******************************************not_working*************************************
        // ImageIcon icon = new ImageIcon("terris_img.png");
        // window.setIconImage(icon.getImage());

        // Add GamePannel to the window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();

    }

}
