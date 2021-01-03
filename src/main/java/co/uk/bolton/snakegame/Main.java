/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.bolton.snakegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Sachin
 */
public class Main implements KeyListener{
    private Frame frame = null;
    private Canvas canvas = null;
    private Graphics graph = null;
    private BufferStrategy strategy = null;
    private int height = 600;
    private int width = 600;
    private int gameSize = 40;
    private int[][] grid = null;
    private int[][] snake = null;
    public final static int SNAKE = 4;
    public final static int SNAKE_HEAD = 5;
    
    public final static int EMPTY = 0;
    
    public Main() {
        super();
        frame = new Frame();
        canvas = new Canvas();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }
    
    public void init() {
        frame.setSize(width + 7, height + 27);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        canvas.setSize(width + 7, height + 27);
        frame.add(canvas);
        canvas.addKeyListener(this);
        frame.dispose();
        frame.validate();
        frame.setTitle("Snake Game");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        canvas.setIgnoreRepaint(true);
        canvas.setBackground(Color.WHITE);

        canvas.createBufferStrategy(2);

        strategy = canvas.getBufferStrategy();
        graph = strategy.getDrawGraphics();
        
        grid = new int[gameSize][gameSize];
        snake = new int[gameSize * gameSize][2];
        
        initGame();
    }
    
    private void initGame() {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                grid[i][j] = EMPTY;
            }
        }

        for (int i = 0; i < gameSize * gameSize; i++) {
            snake[i][0] = -1;
            snake[i][1] = -1;
        }

        snake[0][0] = gameSize / 2;
        snake[0][1] = gameSize / 2;
        grid[gameSize / 2][gameSize / 2] = SNAKE_HEAD;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
