/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.bolton.snakegame;

import co.uk.bolton.snakegame.ui.Snake;

/**
 *
 * @author Sachin
 */
public class Main {
   public static void main(String[] args) {
        Snake main = new Snake();
        main.init();
        main.mainLoop();
    }
}
