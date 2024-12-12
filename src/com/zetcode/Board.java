package com.zetcode;

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

public class Board extends JPanel implements ActionListener {

    private final int board_width_in_pixels = 300;
    private final int board_height_in_pixels = 300;
    private final int dot_size_in_pixels = 10;
    private final int max_snake_length = 900; // 900 = 30 dots(x-axis) * 30 dots (y-axis) = max. possible length of the snake
    private final int remaining_possible_tiles = 29;
    private final int refreshrate_in_ms = 100;
    public final int initial_snake_size = 3;

    private final int x[] = new int[max_snake_length];
    private final int y[] = new int[max_snake_length];

    private int current_snake_size;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {
        
        initBoard();
    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(board_width_in_pixels, board_height_in_pixels));
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

    public void start_game_loop_timer() {
        timer = new Timer(refreshrate_in_ms, this);
        timer.start();
    }

    public void set_start_pos_of_snake() {
        current_snake_size = initial_snake_size;

            for (int z = 0; z < current_snake_size; z++) {
                x[z] = 50 - z * 10;
                y[z] = 50;
            }
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
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < current_snake_size; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (board_width_in_pixels - metr.stringWidth(msg)) / 2, board_height_in_pixels / 2);
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            current_snake_size++;
            set_apple_at_new_random_position();
        }
    }

    private void move() {

        for (int z = current_snake_size; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= dot_size_in_pixels;
        }

        if (rightDirection) {
            x[0] += dot_size_in_pixels;
        }

        if (upDirection) {
            y[0] -= dot_size_in_pixels;
        }

        if (downDirection) {
            y[0] += dot_size_in_pixels;
        }
    }

    private void checkCollision() {

        for (int z = current_snake_size; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= board_height_in_pixels) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= board_width_in_pixels) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    private void set_apple_at_new_random_position() {

        int r = (int) (Math.random() * remaining_possible_tiles);
        apple_x = ((r * dot_size_in_pixels));

        r = (int) (Math.random() * remaining_possible_tiles);
        apple_y = ((r * dot_size_in_pixels));
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

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
