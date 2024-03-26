package MultiplicationProject;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        MultipleTable frame = new MultipleTable();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 50, 400, 750);
        frame.setTitle("Button Demo");
    }

}
