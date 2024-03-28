
package mino;

import java.awt.*;

public class Block extends Rectangle {
    public int x,y;
    public static final int SIZE =30; // 30x30 per block
    public Color c;
    public Block(Color c)
    {
        this.c=c;
    }
    public void deaw(Graphics2D g2)
    {
        int margine=2;
        g2.setColor(c);
        g2.fillRect(x+margine,y+margine,SIZE-(margine*2),SIZE-(margine*2));
    }
}
