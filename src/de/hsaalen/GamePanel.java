package de.hsaalen;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener 
{
    private Game game;

    private Timer timer;
    private Image apple;
    private Image superApple;
    private Image head;
    private Image ball;
    private Image obstacleImg;
    
    public final int tile_size_in_pixels = 10;
    private final int game_loop_duration_in_ms = 140;

    public GamePanel(Game game) 
    {
        this.game = game;
        initGamePanel();
    }
    
    private void initGamePanel() 
    {
        loadImages();
        
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(width_in_pixels(), height_in_pixels()));
        
        initGame();
    }

    private void loadImages() 
    {
        ImageIcon imageIconApple = new ImageIcon("src/resources/apple.png");
        apple = imageIconApple.getImage().getScaledInstance(tile_size_in_pixels, tile_size_in_pixels, Image.SCALE_SMOOTH);
        superApple = imageIconApple.getImage().getScaledInstance(tile_size_in_pixels + 4, tile_size_in_pixels + 4, Image.SCALE_SMOOTH);

        ImageIcon imageIconHead = new ImageIcon("src/resources/head.png");
        head = imageIconHead.getImage().getScaledInstance(tile_size_in_pixels, tile_size_in_pixels, Image.SCALE_SMOOTH);

        ImageIcon imageIconDot = new ImageIcon("src/resources/dot.png");
        ball = imageIconDot.getImage().getScaledInstance(tile_size_in_pixels, tile_size_in_pixels, Image.SCALE_SMOOTH);
        obstacleImg = imageIconDot.getImage().getScaledInstance(tile_size_in_pixels, tile_size_in_pixels, Image.SCALE_SMOOTH);
    }

    private void initGame() 
    {
        timer = new Timer(game_loop_duration_in_ms, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (game.inGame)
        {
            drawObstacles(g);
            drawApple(g);
            drawSnake(g);
        }
        else
        {
            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObstacles(Graphics g) {
        for(Obstacle obstacle : game.getObstacles()) {
            IntPair pos = pixel_position_of_tile(obstacle.getPosition());
            g.drawImage(obstacleImg, pos.x, pos.y, this);
        }
    }

    private void drawApple(Graphics g) 
    {
        IntPair apple_position_in_pixels = pixel_position_of_tile(game.getApplePosition());
        Image currentApple = game.isCurrentAppleSuper() ? superApple : apple;
        g.drawImage(currentApple, apple_position_in_pixels.x, apple_position_in_pixels.y, this);
    }

    private void drawSnake(Graphics g) 
    {
        for (int z = 0; z < game.snake.length(); z++) 
        {
            if (z == 0) 
            {
                IntPair head_position_in_pixels = pixel_position_of_tile(game.snake.head_position());
                g.drawImage(head, head_position_in_pixels.x, head_position_in_pixels.y, this);
            } 
            else 
            {
                IntPair body_position_in_pixels = pixel_position_of_tile(
                    game.snake.position(z).x,
                    game.snake.position(z).y
                );
                g.drawImage(ball, body_position_in_pixels.x, body_position_in_pixels.y, this);
            }
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

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        game.handle_round();
        repaint();
    }
    
    private IntPair pixel_position_of_tile(IntPair position)
    {
        return new IntPair(position.x * tile_size_in_pixels, position.y * tile_size_in_pixels);
    }
    
    private IntPair pixel_position_of_tile(int x, int y)
    {
        return pixel_position_of_tile(new IntPair(x, y));
    }
    
    public int width_in_pixels()
    {
        return game.getWidthInTiles() * tile_size_in_pixels;
    }
    
    public int height_in_pixels()
    {
        return game.getHeightInTiles() * tile_size_in_pixels;
    }

    private class TAdapter extends KeyAdapter 
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (game.direction != Direction.right)) 
            {
                game.direction = Direction.left;
            }

            if ((key == KeyEvent.VK_RIGHT) && (game.direction != Direction.left)) 
            {
                game.direction = Direction.right;
            }

            if ((key == KeyEvent.VK_UP) && (game.direction != Direction.down)) 
            {
                game.direction = Direction.up;
            }

            if ((key == KeyEvent.VK_DOWN) && (game.direction != Direction.up)) 
            {
                game.direction = Direction.down;
            }
        }
    }
}