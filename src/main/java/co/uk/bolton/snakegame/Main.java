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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sachin
 */
public class Main implements KeyListener, WindowListener{
    private Frame frame = null;
    private Canvas canvas = null;
    private Graphics graph = null;
    private BufferStrategy strategy = null;
    private int height = 600;
    private int width = 600;
    private int gameSize = 40;
    private int[][] grid = null;
    private int[][] snake = null;
    public final static int FOOD_BONUS = 1;
    public final static int FOOD_MALUS = 2;
    public final static int BIG_FOOD_BONUS = 3;
    public final static int SNAKE = 4;
    public final static int SNAKE_HEAD = 5;
    
    public final static int EMPTY = 0;
    
    private int grow = 0;
    
    private long speed = 250;
    private long cycleTime = 0;
    private long sleepTime = 0;
    private int bonusTime = 0;
    
    private boolean running = true;
    private boolean game_over = false;
    private boolean paused = false;
    
    public Main() {
        super();
        frame = new Frame();
        canvas = new Canvas();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.mainLoop();
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
        renderGame();
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
    
    public void mainLoop() {
        while (running) {
            cycleTime = System.currentTimeMillis();
                                    
            renderGame();
            
            cycleTime = System.currentTimeMillis() - cycleTime;
            sleepTime = speed - cycleTime;
            
            if (sleepTime < 0) {
                sleepTime = 0;
            }
            
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        }
    }
    
    private void renderGame() {
        int gridUnit = height / gameSize;
        canvas.paint(graph);

        do {
            do {
                graph = strategy.getDrawGraphics();
                ((Graphics2D) graph).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graph.setColor(Color.WHITE);
                graph.fillRect(0, 0, width, height);

                int gridCase = EMPTY;

                for (int i = 0; i < gameSize; i++) {
                    for (int j = 0; j < gameSize; j++) {
                        gridCase = grid[i][j];

                        switch (gridCase) {
                            case SNAKE:
                                graph.setColor(new Color(17, 14, 218));
                                graph.fillOval(i * gridUnit, j * gridUnit,
                                        gridUnit, gridUnit);
                                break;
                            case SNAKE_HEAD:
                                graph.setColor(new Color(0, 0, 0));
                                graph.fillOval(i * gridUnit, j * gridUnit,
                                        gridUnit, gridUnit);
                                break;
                            case FOOD_BONUS:
                                graph.setColor(new Color(3, 171, 14));
                                graph.fillOval(i * gridUnit + gridUnit / 4, j
                                        * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            case FOOD_MALUS:
                                graph.setColor(new Color(244, 2, 31));
                                graph.fillOval(i * gridUnit + gridUnit / 4, j
                                        * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            case BIG_FOOD_BONUS:
                                graph.setColor(new Color(198, 39, 203));
                                graph.fillOval(i * gridUnit + gridUnit / 4, j
                                        * gridUnit + gridUnit / 4, gridUnit / 2,
                                        gridUnit / 2);
                                break;
                            default:
                                break;
                        }
                    }
                }

                graph.dispose();
            } while (strategy.contentsRestored());

            strategy.show();
            Toolkit.getDefaultToolkit().sync();
        } while (strategy.contentsLost());
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

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        running = false;
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
