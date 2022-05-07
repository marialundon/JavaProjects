import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class ConwaysLife extends JFrame implements Runnable, MouseListener {

    // Member data
    private BufferStrategy strategy;
    private Graphics offscreenBuffer;
    private boolean isPlaying = false;
    private int buffer = 0;
    private boolean gameState[][][] = new boolean[40][40][2];
    private String[][] arraySave = new String[40][40];
    private String file = "C:\\Users\\Clare\\Documents\\JavaPrograms\\Conway.txt";
    char[] charArray = new char[1600];

    // Application entry point
    public static void main(String[] args) {
        ConwaysLife w = new ConwaysLife();
    }

    public ConwaysLife () 
    {
        // Display window centered on screen
        Dimension screensize =
        java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - 400;
        int y = screensize.height/2 - 400;
        setBounds(x, y, 800, 800);
        setVisible(true);
        this.setTitle("Conway's game of life");

        // Initialise double buffering
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        offscreenBuffer = strategy.getDrawGraphics();

        // Register JFrame to receive mouse events
        addMouseListener(this);

        // Initialising game state to be false
        for (x=0; x < 40; x++) {

            for (y=0; y< 40; y++) {

                gameState[x][y][1] = false;
                gameState[x][y][0] = false;
            }
        }

        // Create and start animation thread
        Thread t = new Thread(this);
        t.start();
   
    }

    // Threads entry point
    public void run() 
    {
        while (1==1) 
        {
            try {Thread.sleep(100);} 
            catch (InterruptedException e) {}

            // Play round of game
            if (isPlaying)
                playRound();

            // Request a repaint
            this.repaint();
        }
    }

    public void playRound()
    {
        // Initialising front and back buffers
        int frontBuffer = buffer;
        int backBuffer = (frontBuffer + 1) % 2;

        // Counting number of live neighbours each cell has
        for (int x=0; x < 40; x++) {

            for (int y=0; y < 40; y++) {
                
                int numLiveNeighbours = 0; 

                for (int xx=-1; xx <= 1; xx++) {

                    for (int yy=-1; yy <=1; yy++) {

                        numLiveNeighbours = incrementLiveNeighbours(x, y, xx, yy, numLiveNeighbours, frontBuffer);
                    }
                }
                // Applying game rules
                applyRules(x, y, frontBuffer, backBuffer, numLiveNeighbours);
            }
        }
        // Switching buffer
        buffer = backBuffer;
    }

    // Method to count number of live neighbours each cell has
    public int incrementLiveNeighbours(int x, int y, int xx, int yy, int numLiveNeighbours, int frontBuffer)
    {
        if (xx!=0 || yy!=0) {

            int xNeighbour = x+xx;
            int yNeighbour = y+yy;

            // Adjusts neighbour cells if greater than 39 or less than 0
            if (yNeighbour >= 40)
                yNeighbour = 0;
            else if (yNeighbour < 0)
                yNeighbour = 39;

            if (xNeighbour >= 40)
                xNeighbour = 0;
            else if (xNeighbour < 0)
                xNeighbour = 39;
            
            // Checking if neighbour cell is live and incrementing count
            if (gameState[xNeighbour][yNeighbour][frontBuffer] == true)
                numLiveNeighbours ++;
        }

        return numLiveNeighbours;
    }

    // Method to apply game rules to each cell
    public void applyRules(int x, int y, int frontBuffer, int backBuffer, int numLiveNeighbours)
    {
        // Checks cell in front buffer and applies relevant rules to cell in back buffer
        if (gameState[x][y][frontBuffer])
            {
                if (numLiveNeighbours < 2)
                    gameState[x][y][backBuffer] = false;

                else if (numLiveNeighbours >=2 && numLiveNeighbours < 4)
                    gameState[x][y][backBuffer] = true;

                else 
                    gameState[x][y][backBuffer] = false;
            }
    
        else
            {
                if(numLiveNeighbours == 3)
                    gameState[x][y][backBuffer] =true;

                else
                    gameState[x][y][backBuffer] =false;
            }
    }

    // Method to generate random game state
    private void randomGameState()
    {
        for(int x = 0; x < 40; x++)
        {
            for(int y = 0; y < 40; y++)
            {
                gameState[x][y][buffer] = (Math.random() < 0.5); // Returns true in c.50% of cases and false in others
            }
        }
    }

    public void mousePressed(MouseEvent e) 
    {
        if(!isPlaying)
        {
            // Getting coordinates of mouse click
            int x = e.getX();
            int y = e.getY();

            // Checks if click is where start button is located and sets game to playing if it is
            if(x >= 20 && x <= 80 && y >= 40 && y <= 70)
            {
                isPlaying = true;
                return;
            }

            // Checks if click is where random button is located and applies random game state if it is
            if(x >= 100 && x <= 190 && y >= 40 && y <= 70)
            {
                randomGameState();
                return;
            }

            // Checks if click is where save button is located and saves game state to file if it is
            if(x >= 210 && x <= 270 && y >= 40 && y <= 70)
            {
                // Writes game state to an array
                for(int i = 0; i < 40; i++)
                {
                    for(int j = 0; j < 40; j++)
                    {
                        if(gameState[i][j][buffer])
                        {
                            arraySave[i][j] = "1";
                        }
                        else
                        {
                            arraySave[i][j] = "0";
                        }
                    }
                }

                // Uses BufferedWriter to write array to text file at location and name specified at start of code
                try 
                {
                    BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));

                    for(int i = 0; i < 40; i++)
                    {
                        for(int j = 0; j < 40; j++)
                        {
                            bufferedwriter.write(arraySave[i][j]);
                        }
                    }
                    // Lets user know save completed successfully via message box
                    JOptionPane.showMessageDialog(null,"Save complete"); 
                    bufferedwriter.close();
                } 
                
                catch (IOException e1) 
                {}
                
                return;
            }

            // Uses BufferedReader to read array from text file at location and name specified at start of code and populate game state
            if(x >= 290 && x <= 350 && y >= 40 && y <= 70)
            {
                try 
                {
                    BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                    Scanner scanner = new Scanner (bufferedreader);
                    while (scanner.hasNext())
                    { 
                        int num = 0;
                        String state = scanner.next();
                        charArray = state.toCharArray();

                        for(int i = 0; i < 40; i++)
                        {
                            for(int j = 0; j < 40; j++)
                            {
                                if (charArray[num] == '1')
                                    gameState[i][j][buffer] = true;
                                else
                                    gameState[i][j][buffer] = false;
                                
                                num++;
                            }
                        }
                    }
                    // Let user know load completed successfully via message box
                    JOptionPane.showMessageDialog(null,"Load complete"); 
                    scanner.close();
                } 
                
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
        }
        
        // Determine which cell was clicked
        int x = e.getX()/20;
        int y = e.getY()/20;

        // Toggle the state of the cell
        gameState[x][y][buffer] = !gameState[x][y][buffer];
    
        // Request a repaint
        this.repaint();
    }
    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseClicked(MouseEvent e) { }

    // Paint method
    public void paint(Graphics g) 
    {
        g = offscreenBuffer;

        // Setting color and size of canvas
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 800);

        // If cell game state value is true then paint cell white
        g.setColor(Color.WHITE);
        for (int x=0; x < 40; x++) {

            for (int y=0; y<40; y++) {

                if (gameState[x][y][buffer])
                    g.fillRect(x*20, y*20, 20, 20);
            }
        }

        if (!isPlaying)
        {
            // Button for starting game
            g.setColor(Color.ORANGE);
            g.fillRect(20, 40, 60, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times", Font.PLAIN, 20));
            g.drawString("Start", 30, 60);

            // Button for random game state
            g.setColor(Color.ORANGE);
            g.fillRect(100, 40, 90, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times", Font.PLAIN, 20));
            g.drawString("Random", 106, 60);

            // Button for save
            g.setColor(Color.ORANGE);
            g.fillRect(210, 40, 60, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times", Font.PLAIN, 20));
            g.drawString("Save", 216, 60);

            // Button for load
            g.setColor(Color.ORANGE);
            g.fillRect(290, 40, 60, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times", Font.PLAIN, 20));
            g.drawString("Load", 300, 60);
        }

        strategy.show();
    }
}
