package de.hsaalen;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeApplication extends JFrame {

	Game game;
	GamePanel panel;

    public SnakeApplication() 
	{
		game = new Game();
		panel = new GamePanel( game );
        initUI( panel );
    }
    
    private void initUI( GamePanel panel ) 
	{    
        add( panel );
               
        setResizable( false );
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeApplication();
            ex.setVisible(true);
        });
    }
}
