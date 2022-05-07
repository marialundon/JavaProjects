import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MusicalChairs
{
    // Making new track object
    private static Track track = new Track();

    // Making five chair objects to be located within track
    private static Chair chair1 = new Chair(140,200);
    private static Chair chair2 = new Chair(220,205);
    private static Chair chair3 = new Chair(300,210);
    private static Chair chair4 = new Chair(380,215);
    private static Chair chair5 = new Chair(460,220);

    // Making six player objects with starting locations around track
    private static Player player1 = new Player(500,80,20,Color.blue);
    private static Player player2 = new Player(200,80,20,Color.orange);
    private static Player player3 = new Player(400,80,20,Color.green);
    private static Player player4 = new Player(400,380,20,Color.yellow);
    private static Player player5 = new Player(200,380,20,Color.MAGENTA);
    private static Player player6 = new Player(300,380,20,Color.red);

    // Making a thread for each player object
    private static Thread t1 = new Thread(player1);
    private static Thread t2 = new Thread(player2);
    private static Thread t3 = new Thread(player3);
    private static Thread t4 = new Thread(player4);
    private static Thread t5 = new Thread(player5);
    private static Thread t6 = new Thread(player6);

    // Making new JFrame object
    public static JFrame frame = new JFrame();

    // Displaying JFrame and calling methods to add buttons to JFrame
    public static void main(String[]args)
    {
        Box box = Box.createVerticalBox();    
        frame.setResizable(false);   
        frame.setVisible(true);  
        frame.setSize(640,500);  
        box.add(createStartButton());
        box.add(createPauseButton());
        box.add(createResumeButton());
        frame.getContentPane().add(box);
    }

    // Method to arrange starting position for game and start player threads when button is pressed
    private static JButton createStartButton() 
    {
        JButton button = new JButton("Start game");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                // Adding chairs, players and track to JFrame
                chair2.add(chair1);
                chair3.add(chair2);
                chair3.add(chair2);
                chair4.add(chair3);
                chair5.add(chair4);
                player1.add(chair5);
                player2.add(player1);
                player3.add(player2);
                player4.add(player3);
                player5.add(player4);
                player6.add(player5);
                track.add(player6);
               
                frame.getContentPane().add(track);
                frame.revalidate();
                frame.repaint();

                // Starting player threads
                t1.start();
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();
               
            }
        });

        return button;
    }

    // Method to pause threads and re-locate five players to the 5 chairs when button is pressed
    public static JButton createPauseButton() 
    {
        JButton button = new JButton("Pause game");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                t1.suspend();
                t2.suspend();
                t3.suspend();
                t4.suspend();
                t5.suspend();

                player1.x = chair1.x + player1.radius;
                player1.y = chair1.y + player1.radius*2;
                

                player2.x = chair2.x + player2.radius;
                player2.y = chair2.y + player2.radius*2;
               

                player3.x = chair3.x + player3.radius;
                player3.y = chair3.y + player3.radius*2;
                

                player4.x = chair4.x + player4.radius;
                player4.y = chair4.y + player4.radius*2;
                

                player5.x = chair5.x + player5.radius;
                player5.y = chair5.y + player5.radius*2;
            }
        });

        return button;
    }
    
    // Method to resume thread action when button is pressed
    public static JButton createResumeButton() 
    {
        JButton button = new JButton("Resume game");
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                t1.resume();
                t2.resume();
                t3.resume();
                t4.resume();
                t5.resume(); 
            }
        });

        return button;

    }

}

