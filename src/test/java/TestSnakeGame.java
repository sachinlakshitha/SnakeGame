/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sachin
 */
import java.util.ArrayList;
import java.util.HashMap;

public class TestSnakeGame {
    int xMax = 16;
    int yMax = 16;

    class Cell {
        int x = 0;
        int y = 0;
        String status = "0";

        public Cell(int x, int y, String status) {
            this.x = x;
            this.y = y;
            this.status = status;
        }
    }

    public HashMap<Integer, HashMap<Integer, String>> getMap(int xMax, int yMax) {
        return getMap(xMax, yMax, new ArrayList<Cell>());
    }

    public HashMap<Integer, HashMap<Integer, String>> getMap(int xMax, int yMax, ArrayList<Cell> occupiedCells) {
        HashMap<Integer, HashMap<Integer, String>> map = new HashMap<Integer, HashMap<Integer, String>>();

        for (int x = 0; x < xMax; x += 1) {
            map.put(x, new HashMap<Integer, String>());

            for (int y = 0; y < yMax; y += 1) {
                map.get(x).put(y, "0");
            }
        }

        for (Cell cell : occupiedCells) {
            map.get(cell.x).put(cell.y, cell.status);
        }

        return map;
    }
    
}
