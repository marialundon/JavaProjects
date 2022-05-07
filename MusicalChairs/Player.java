import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class Player extends JPanel implements Runnable{

    int x;
    int y;
    int radius;
    private Color color;
    private static int movementPer40ms = 5;

    // Constructor for player
    public Player(int x,int y,int radius, Color color) 
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        setOpaque(false);  
        setPreferredSize(new Dimension( 640, 500));  
    }

    // Method to paint player
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(x-radius, y-radius, radius*2,radius*2);
        
    }

    // Method to move player around track 5 pixels every 40ms - changes direction at edge of track
    public void run()   
    {  
        while (true) { 

            if (y == 80 && x >= 80 && x < 540){
                x += movementPer40ms;
            }
         
            else if (x == 540 && y < 380) {
                y += movementPer40ms;
            }

            else if (y >= 380 && x >= 100 ) {
                x -= movementPer40ms;
            }

            else  {
                y -= movementPer40ms;
            }

            repaint();

            try 
            {
                Thread.sleep( 40 );
            }

            catch ( InterruptedException exception ) 
            {
                System.err.println( exception.toString() );
            }
        }
    }
    
}

