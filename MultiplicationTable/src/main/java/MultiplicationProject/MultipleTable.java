package MultiplicationProject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultipleTable extends JFrame {

    private Container c;
    private JLabel imgLabel, textLabel;
    private JTextArea ta;
    private JTextField tf;
    private JButton clearButton;
    private ImageIcon img;
    private Font f;
    private Cursor cursor;

    MultipleTable() {
        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.ORANGE);

        f = new Font("Calibri (Headings)", Font.BOLD, 20);// declear font
        cursor = new Cursor(Cursor.HAND_CURSOR);// declear hand cursor

        // Image Icon setup
        img = new ImageIcon(getClass().getResource("MultiTable.jpg"));
        imgLabel = new JLabel(img);
        imgLabel.setBounds(5, 10, img.getIconWidth(), img.getIconHeight());
        c.add(imgLabel);

        // Textlebel
        textLabel = new JLabel("Enter any number: ");
        textLabel.setBounds(20, 250, 180, 30);
        textLabel.setForeground(Color.BLUE);
        textLabel.setFont(f);
        c.add(textLabel);

        // textField
        tf = new JTextField();
        tf.setBounds(210, 250, 100, 40);
        tf.setBackground(Color.CYAN);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(f);
        c.add(tf);

        // button
        clearButton = new JButton("Clear");
        clearButton.setBounds(210, 295, 100, 35);
        clearButton.setCursor(cursor);
        clearButton.setFont(f);
        c.add(clearButton);

        // Text Area (all multiples)
        ta = new JTextArea();
        ta.setBounds(10, 340, 370, 340);
        ta.setFont(f);
        ta.setBackground(Color.CYAN);
        c.add(ta);

        // use action Listener to connect text Area

        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String value = tf.getText();
                if (value.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You didn't put any number");
                } else {
                    ta.setText("");// refresh the text area after every enter

                    int num = Integer.parseInt(tf.getText());// collect text field number and convert into integer
                    for (int i = 1; i <= 10; i++) {
                        int result = num * i;

                        String res = String.valueOf(result);
                        String Num = String.valueOf(num);
                        String increm = String.valueOf(i);

                        ta.append("     " + Num + " x " + increm + " = " + res + "\n");

                    }
                }

            }
        });
        // use action Listener to connect button
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ta.setText("");
            }
        });

    }

    public static void main(String[] args) {
        MultipleTable frame = new MultipleTable();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 50, 400, 750);
        frame.setTitle("Button Demo");
    }

}
