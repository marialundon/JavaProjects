import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;   

class Ball extends JPanel implements Runnable   
{   
    private int x, y, dx, dy; // Variables for position and direction of ball
    private final int max_width = 380, max_height = 360, radius = 20; // Assigning values for height and width of panel and ball radius
    private Color colour; // Variable for colour of ball
    private static Color[] colors = {Color.red,Color.blue,Color.yellow, Color.black, Color.green, Color.magenta, Color.cyan, Color.orange, Color.pink, Color.gray}; // Array of 10 unique colours
    private static int numBalls = 5; // Number of balls to display 
    private static ArrayList <Thread> threadsList = new ArrayList <Thread> (); // Array to store threads
    private static ArrayList <Ball> ballsList = new ArrayList<Ball> (); // Array to store balls

    // Ball constructor
    public Ball(Color colour)   
    { 
        // Assigning random values for position and direction of ball
        x = (int)( Math.random() * 200) + 10;
        y = (int)( Math.random() * 200) + 10;
        dx = (int)( Math.random() * 5 + 2 );
        dy = (int)( Math.random() * 5 + 2 );
        this.colour = colour;

        setPreferredSize(new Dimension( max_width, max_height));  
        setOpaque(false);    
    }   

    public void run()   
    {  
        while (true) { 

            x += dx;
            y += dy;

            updateDirection(); // Calling method that reverses direction of ball if collision with another ball

            // Logic to bounce balls against side of panel

            if (x - radius < 0){
                dx = -dx;
                x = radius;
            }
         
            else if (x + radius > max_width) {
                dx = -dx;
                x = max_width - radius;
            }
         
            if (y - radius < 0 ) {
                dy = -dy;
                y = radius;
            }

            else if (y + radius > max_height) {
                dy = -dy;
                y = max_height - radius;
            }

            // Repaint ball with newly updated values
            repaint();

            // See update after 50 milliseconds
            try {Thread.sleep( 20 );
            }

            catch ( InterruptedException exception ) {
            System.err.println( exception.toString() );
            }
        }
    }

    // Drawing the ball
    public void paintComponent(Graphics g)   
    {   
        super.paintComponent(g);   
            g.setColor(colour);
            g.fillOval( x-radius, y-radius, radius*2, radius*2);
    }  
    
    // Method to check for collision between two balls and reversing directions of both if collision - part of calc sourced at - https://github.com/gtiwari333/java-collision-detection-source-code
    public static void collision(Ball a, Ball b) {
        double xDistance, yDistance;
        xDistance = a.x - b.x;
        yDistance = a.y - b.y;
        double distanceSq = xDistance * xDistance + yDistance * yDistance;
        if (distanceSq <= (a.radius + b.radius) * (a.radius + b.radius)) {
            double speedXocity = b.dx - a.dx;
            double speedYocity = b.dy - a.dy;
            double dotProduct = xDistance * speedXocity + yDistance * speedYocity;
            if (dotProduct > 0) {
                a.dx = -a.dx;
                a.dy = -a.dy;
                b.dx = -b.dx;
                b.dy = -b.dy;
            }
        }
    }
    
    // CHecking each ball in array against other balls to detect collision and reverse direction if needed using collision method 
    public void updateDirection()
    {
        for (int i = 0; i < ballsList.size(); i++) {
            for (int j = 0; j < ballsList.size(); j++) {
                if (i < j) 
                    collision(ballsList.get(i), ballsList.get(j));
            }
        }
    }

    public static void main(String[] args) 
    {
        // Setting up JFrame
        JFrame frame = new JFrame();
        frame.setResizable(false);   
        frame.setVisible(true);  
        frame.setSize(400,400);   

        // Adding balls to array based on number required and assigning each a unique colour from colours array (after 10 uniquely coloured balls the colors are reused)
        ballsList.add(new Ball(colors[0]));
        for (int i=1; i < numBalls; i++)
        {
            if (i < 10)
                ballsList.add(new Ball(colors[i]));

            else if (i >= 10)
                ballsList.add(new Ball(colors[0]));

            ballsList.get(i-1).add(ballsList.get(i));
        }
        
        // Displaying newly created balls in J Frame
        frame.getContentPane().add(ballsList.get(0));
        
        // Creating a thread for each ball created and adding each to threads array
        for (Ball b: ballsList){
            threadsList.add(new Thread(b));
        }
        // Iterating through threads array and starting each thread
        for (Thread t: threadsList){
            t.start();
        }
    }   
}
