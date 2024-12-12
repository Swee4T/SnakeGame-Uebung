package com.oskar;

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

public class GamePanel extends JPanel implements ActionListener {

    public final int width_in_dots = 30;
    public final int height_in_dots = 30;
    public final int dot_size_in_pixels = 10;
    public final int refreshrate_in_ms = 100;
    public final int initial_snake_size = 3;

    private final int max_snake_length = 900; // 900 = 30 dots(x-axis) * 30 dots (y-axis) = max. possible length of the snake
    public final int remaining_possible_tiles = 29;

    public Snake snake;

    private int current_snake_size;
    private int apple_x;
    private int apple_y;

    private Direction direction = Direction.right;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;


    public GamePanel() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(width_in_pixels(), height_in_pixels()));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    public void set_start_pos_of_snake() {
        snake = new Snake( 3, dot_size_in_pixels);
    }

    public void start_game_loop_timer() {
        timer = new Timer(refreshrate_in_ms, this);
        timer.start();
    }

    public void set_apple_at_new_random_position() {

        int r = (int) (Math.random() * remaining_possible_tiles);
        apple_x = ((r * dot_size_in_pixels));

        r = (int) (Math.random() * remaining_possible_tiles);
        apple_y = ((r * dot_size_in_pixels));
    }    

    private void initGame() {

        set_start_pos_of_snake();
        set_apple_at_new_random_position();
        start_game_loop_timer();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if (inGame) 
		{
			IntPair apple_position = new IntPair( apple_x, apple_y);
			IntPair apple_position_in_pixels = pixel_position_of_tile( apple_position );
			
            g.drawImage(apple, apple_position_in_pixels.x, apple_position_in_pixels.y, this);
            for (int i = 0; i < snake.length(); i++) 
			{
				IntPair position_in_pixels = pixel_position_of_tile( snake.position(i) );
                if (i == 0) 
				{
                    g.drawImage(head, position_in_pixels.x, position_in_pixels.y, this);
                } else 
				{
                    g.drawImage(ball, position_in_pixels.x, position_in_pixels.y, this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 18);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width_in_pixels() - metr.stringWidth(msg)) / 2, height_in_pixels() / 2);
    }

    private void checkApple() {

        if ((snake.head_position().x == apple_x ) && (snake.head_position().y == apple_y)) {
            snake.grow( direction);
            set_apple_at_new_random_position();
        }
    }


    private void move() {
        snake.move( direction );
    }

    private void checkCollision() {
        if (snake.is_snake_colliding(width_in_dots, height_in_dots)) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ( key == KeyEvent.VK_LEFT )
				direction = Direction.left;
			
            if ( key == KeyEvent.VK_RIGHT )
				direction = Direction.right;
			
            if ( key == KeyEvent.VK_UP )
				direction = Direction.up;
			
            if ( key == KeyEvent.VK_DOWN )
				direction = Direction.down;

        }
    }
}
