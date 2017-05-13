package game.vtkrishn.com.gameapplication.model;

/**
 * Created by vtkrishn on 5/5/2017.
 */

public class Cell {
    int row;
    int col;
    String value;

    public Cell(int x,int y,String value){
        this.row = x;
        this.col = y;
        this.value = value;
    }
}
