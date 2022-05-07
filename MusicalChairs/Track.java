import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

// Class to display rectngular track on JPanel
public class Track extends JPanel
{
    public Track()
    {
        setOpaque(false);  
        setPreferredSize(new Dimension( 640, 500));  
    }
    @Override
    public void paintComponent(Graphics g)
    {
        g.drawRect(100,100,440,300);
        g.setColor(Color.black);
    }

}
