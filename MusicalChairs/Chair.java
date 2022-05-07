import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

// Class to display square chair on JPanel
public class Chair extends JPanel {

    int x;
    int y;

    public Chair (int x, int y) 
    {
        this.x = x;
        this.y = y;

        setOpaque(false);  
        setPreferredSize(new Dimension( 640, 500));  
    }

    public void paintComponent(Graphics g)
    {
        g.drawRect(x, y, 40, 40);
        g.setColor(Color.black);
    }
    
}
