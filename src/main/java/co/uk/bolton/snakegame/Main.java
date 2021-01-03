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
    
    private int direction = -1;
    private int next_direction = -1;
    
    public final static int FOOD_BONUS = 1;
    public final static int FOOD_MALUS = 2;
    public final static int BIG_FOOD_BONUS = 3;
    public final static int SNAKE = 4;
    public final static int SNAKE_HEAD = 5;
    
    public final static int UP = 0;
    public final static int DOWN = 1;
    public final static int LEFT = 2;
    public final static int RIGHT = 3;
    
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
        placeBonus(FOOD_BONUS);
    }
    
    public void mainLoop() {
        while (running) {
            cycleTime = System.currentTimeMillis();
                        
            if (!paused && !game_over) {
                direction = next_direction;
                moveSnake();
            }
            
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
    
    private void moveSnake() {
        if (direction < 0) {
            return;
        }
        
        int ymove = 0;
        int xmove = 0;
        
        switch (direction) {
            case UP:
                xmove = 0;
                ymove = -1;
                break;
            case DOWN:
                xmove = 0;
                ymove = 1;
                break;
            case RIGHT:
                xmove = 1;
                ymove = 0;
                break;
            case LEFT:
                xmove = -1;
                ymove = 0;
                break;
            default:
                xmove = 0;
                ymove = 0;
                break;
        }

        int tempx = snake[0][0];
        int tempy = snake[0][1];

        int fut_x = snake[0][0] + xmove;
        int fut_y = snake[0][1] + ymove;

        if (fut_x < 0) {
            fut_x = gameSize - 1;
        }
        if (fut_y < 0) {
            fut_y = gameSize - 1;
        }
        if (fut_x >= gameSize) {
            fut_x = 0;
        }
        if (fut_y >= gameSize) {
            fut_y = 0;
        }

        snake[0][0] = fut_x;
        snake[0][1] = fut_y;

        grid[tempx][tempy] = EMPTY;

        int snakex, snakey, i;

        for (i = 1; i < gameSize * gameSize; i++) {
            if ((snake[i][0] < 0) || (snake[i][1] < 0)) {
                break;
            }
            
            grid[snake[i][0]][snake[i][1]] = EMPTY;
            snakex = snake[i][0];
            snakey = snake[i][1];
            snake[i][0] = tempx;
            snake[i][1] = tempy;
            tempx = snakex;
            tempy = snakey;
        }

        grid[snake[0][0]][snake[0][1]] = SNAKE_HEAD;
        
        for (i = 1; i < gameSize * gameSize; i++) {
            if ((snake[i][0] < 0) || (snake[i][1] < 0)) {
                break;
            }
            
            grid[snake[i][0]][snake[i][1]] = SNAKE;
        }

        bonusTime--;
        
        if (bonusTime == 0) {
            for (i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    if (grid[i][j] == BIG_FOOD_BONUS) {
                        grid[i][j] = EMPTY;
                    }
                }
            }
        }
        
        if (grow > 0) {
            snake[i][0] = tempx;
            snake[i][1] = tempy;
            grid[snake[i][0]][snake[i][1]] = SNAKE;
                        
            grow--;
        }
    }
    
    private void placeBonus(int bonus_type) {
        int x = (int) (Math.random() * 1000) % gameSize;
        int y = (int) (Math.random() * 1000) % gameSize;
        if (grid[x][y] == EMPTY) {
            grid[x][y] = bonus_type;
        } else {
            placeBonus(bonus_type);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        paused = false;

        switch (code) {
            case KeyEvent.VK_UP:
                if (direction != DOWN) {
                    next_direction = UP;
                }

                break;

            case KeyEvent.VK_DOWN:
                if (direction != UP) {
                    next_direction = DOWN;
                }

                break;

            case KeyEvent.VK_LEFT:
                if (direction != RIGHT) {
                    next_direction = LEFT;
                }

                break;

            case KeyEvent.VK_RIGHT:
                if (direction != LEFT) {
                    next_direction = RIGHT;
                }

                break;
  
            case KeyEvent.VK_ESCAPE:
                running = false;
                System.exit(0);
                break;

            case KeyEvent.VK_SPACE:
                if (!game_over) {
                    paused = true;
                }
                break;

            default:
                // Unsupported key
                break;
        }
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
