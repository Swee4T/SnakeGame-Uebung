package de.hsaalen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener 
{
    public final int tile_size_in_pixels = 10;
    public final int game_loop_duration_in_ms = 140;

	private Game game;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public GamePanel( Game game ) 
	{    
		this.game = game;

        addKeyListener( new TAdapter() );
        setBackground( Color.black );
        setFocusable( true );

        setPreferredSize( new Dimension( width_in_pixels(), height_in_pixels() ) );
        loadImages();
		start_game_loop_timer();
    }
    
    private void loadImages() 
	{

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

	public void start_game_loop_timer()
	{
        timer = new Timer(game_loop_duration_in_ms, this);
        timer.start();
	}

    @Override
    public void paintComponent( Graphics g ) 
	{
        super.paintComponent(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) 
	{    
        if ( game.inGame) 
		{
			IntPair apple_position_in_pixels = pixel_position_of_tile( game.apple_position );
			
            g.drawImage(apple, apple_position_in_pixels.x, apple_position_in_pixels.y, this);
            for (int i = 0; i < game.snake.length(); i++) 
			{
				IntPair position_in_pixels = pixel_position_of_tile( game.snake.position(i) );
                if (i == 0) 
				{
                    g.drawImage(head, position_in_pixels.x, position_in_pixels.y, this);
                } else 
				{
                    g.drawImage(ball, position_in_pixels.x, position_in_pixels.y, this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
		else 
		{
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) 
	{
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width_in_pixels() - metr.stringWidth(msg)) / 2, height_in_pixels() / 2);
    }

	public int width_in_pixels()
	{
		return game.width_in_tiles * tile_size_in_pixels;
	}

	public int height_in_pixels()
	{
		return game.height_in_tiles * tile_size_in_pixels;
	}

	public IntPair pixel_position_of_tile( IntPair position )
	{
		return new IntPair( position.x * tile_size_in_pixels, position.y * tile_size_in_pixels );
	}

    @Override
    public void actionPerformed(ActionEvent e) 
	{
		game.handle_round();				
        if  ( ! game.inGame )
            timer.stop();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) 
		{
            int key = e.getKeyCode();

            if ( key == KeyEvent.VK_LEFT )
				game.direction = Direction.left;
			
            if ( key == KeyEvent.VK_RIGHT )
				game.direction = Direction.right;
			
            if ( key == KeyEvent.VK_UP )
				game.direction = Direction.up;
			
            if ( key == KeyEvent.VK_DOWN )
				game.direction = Direction.down;
        }
    }
}
