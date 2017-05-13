package game.vtkrishn.com.gameapplication.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vtkrishn on 5/5/2017.
 */

public class Board {
    Cell[][] cells;
    Map<Integer,Cell> map;

    public Board(){
        cells = new Cell[3][3];
        map = new HashMap<>();
    }

    public void placeMarker(int id,String mark){
        if(id == 1) {
            cells[0][0] = new Cell(0, 0, mark);
            map.put(1,cells[0][0]);
            return;
        }
        if(id == 2) {
            cells[0][1] = new Cell(0, 1, mark);
            map.put(2,cells[0][1]);
            return;
        }
        if(id == 3) {
            cells[0][2] = new Cell(0, 2, mark);
            map.put(3, cells[0][2]);
            return;
        }
        if(id == 4) {
            cells[1][0] = new Cell(1, 0, mark);
            map.put(4, cells[1][0]);
            return;
        }
        if(id == 5) {
            cells[1][1] = new Cell(1, 1, mark);
            map.put(5, cells[1][1]);
            return;
        }
        if(id == 6) {
            cells[1][2] = new Cell(1, 2, mark);
            map.put(6, cells[1][2]);
            return;
        }
        if(id == 7) {
            cells[2][0] = new Cell(2, 0, mark);
            map.put(7, cells[2][0]);
            return;
        }
        if(id == 8) {
            cells[2][1] = new Cell(2, 1, mark);
            map.put(8, cells[2][1]);
            return;
        }
        if(id == 9) {
            cells[2][2] = new Cell(2, 2, mark);
            map.put(9, cells[2][2]);
            return;
        }
    }

    public int optimizeMark(){


            return calculatedValue();

        //return 0;
    }

    private int calculatedValue(){
        int[][] visited = new int[3][3];
        int count =1;
        for(int i=0;i<cells.length;i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if(map.containsKey(count)) {
                    Cell c = map.get(count);
                    if(c.value.equals("H"))
                        visited[i][j] = -1;
                    else
                        visited[i][j] = 1;
                }
                else{
                    visited[i][j] = 0;
                }
                count++;
            }
        }

        int sum = 0;
        int c = 0;
        for(int i=0;i<cells.length;i++) {
            sum = 0;
            for (int j = 0; j < cells[0].length; j++) {
                sum = sum + visited[i][j];
                c++;
            }
            if(sum == -2) {
                if(map.containsKey(c))
                    continue;
                else {
                    placeMarker(c,"C");
                    return c; //return block
                }
            }

            if(sum == -3)
                return -1; //human wins

            if(sum == 0) {
                placeMarker(c+3,"C");
                return 9;
            }
        }
            return 0;
    }

}
