/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.bolton.snakegame;

import java.awt.Frame;

/**
 *
 * @author Sachin
 */
public class Main {
    private Frame frame = null;
    private int height = 600;
    private int width = 600;
    
    public Main() {
        super();
        frame = new Frame();
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }
    
    public void init() {
        frame.setSize(width + 7, height + 27);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.dispose();
        frame.validate();
        frame.setTitle("Snake Game");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
