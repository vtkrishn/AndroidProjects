package game.vtkrishn.com.gameapplication.model;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import game.vtkrishn.com.gameapplication.R;

/**
 * Created by vtkrishn on 5/5/2017.
 */

public class Controller {
    /*

    restart the game if some one won
    end the game based one the logic

    */
    Button newButton;
    TextView gamePoints;
    GridLayout grid;
    Board board;
    int gamesPlayed;
    int humanPoints;
    int compPoints;

    Activity activity;
    public Controller(Activity activity){
        this.activity = activity;
    }
    public void loadUIComponents(){
        newButton = (Button)activity.findViewById(R.id.button);
        gamePoints = (TextView)activity.findViewById(R.id.textView);
        grid = (GridLayout)activity.findViewById(R.id.gridLayout);

        setVisibility(gamePoints,false);
        setVisibility(grid,false);
    }

    private void setVisibility(View view, boolean value){
        if(value)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.INVISIBLE);
    }

    public void startTheGame(){
        //show the grid
        setVisibility(grid,true);
        //start the board
        board = new Board();
        //initialize all the cells to nothing
        for(int i=1;i<10;i++) {
            int id = getResourceIdentifier(i);
            Button b = (Button)activity.findViewById(id);
            if (b != null)
                b.setText("");
            b.setClickable(true);
        }
        //show game points for games played more than once
        if(gamesPlayed >= 1){
            gamePoints.setText(gamesPlayed + ": " + "Human :  "+ humanPoints + ", Computer : "+ compPoints);
            setVisibility(gamePoints,true);
        }
        gamesPlayed++;
    }

    public void propagate(View view){
        //H is clicking
        setHumanCell(view,"X");

        //if(!isWinning("X")){
            //check where to place for computer
        setComputerCell("O");
        //}
        //if(!isWinning("O")){
            if(board.map.size() == 9) {
                displayMessage("Game Over");
                startTheGame();
            }
        //}
    }
    public void setHumanCell(View view, String value){
        //set the button cell to H
        setTheButtonCellToValue((Button) view, value);
        //mark the backup cell to H
        setCellValue(view,value);
    }

    private void setTheButtonCellToValue(Button view, String value) {
        Button cell = view;
        cell.setText(value);
        cell.setClickable(false);
    }

    private void setCellValue(View view,String marker){
        for(int i=0;i<10;i++){
            int id = getResourceIdentifier(i);
            if(view.getId() == id && activity.findViewById(id) != null){
                board.placeMarker(i,marker);
                break;
            }
            else
                continue;
        }
    }

    private int getResourceIdentifier(int i) {
        return activity.getResources().getIdentifier("id/button" + i, "button", activity.getPackageName());
    }

    public void setComputerCell(String marker){
        int cellValue = 0;
        if(board.map.size() == 1) {
             cellValue = placeComputerRandomly(marker);
        }
         else{
            //cellValue = naiveLogicForOptimalCellValue(marker);
            cellValue = minimax(board,2,marker);
        }

        int id = getResourceIdentifier(cellValue);
        Button cell = (Button) activity.findViewById(id);
        if (cell != null && (cell.getText() == null || cell.getText().equals(""))) {
            setTheButtonCellToValue(cell, marker);
        }
    }

    private int minimax(Board board, int depth,String marker){
        int bestCell = 0;
        if(depth == 9)
            return bestCell;
        if(marker.equals("O")){
            bestCell = Integer.MIN_VALUE;
            for(int i=0;i<10;i++){
                int val = minimax(board,depth+1,marker);
                bestCell = Math.max(bestCell,val);
            }
        }
        return bestCell;
    }

    private int naiveLogicForOptimalCellValue(String marker) {
        int cellValue;
        int index = 1;
        int sum = 0;
        int min1 = 0;
        int min2 = 0;
        int min3 = 0;
        int min4 = 0;
        int minIndex1 = 0;
        int minIndex2 = 0;
        int minIndex3 = 0;
        int minIndex4 = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!board.map.containsKey(index)){
                    if(min1 > sum) {
                        min1 = sum;
                        minIndex1 = index;
                    }
                }
                sum = sum + tryMarker(i,j);
                index++;
            }
            if(!board.map.containsKey(index) && min1 > sum) {
                min1 = sum;
                minIndex1 = index-1;
            }
            sum = 0;
        }

        sum = 0;
        min2 = 0;
        index = 1;
        for(int j=0;j<3;j++){
            for(int i=0;i<3;i++){
                if(!board.map.containsKey(index)){
                    if(min2 > sum) {
                        min2 = sum;
                        minIndex2 = index;
                    }
                }
                sum = sum + tryMarker(i,j);
                index+=3;
            }
            if(!board.map.containsKey(index) && min2 > sum) {
                min2 = sum;
                minIndex2 = index-1;
            }
            sum = 0;
        }

        sum = 0;
        min3 = 0;
        index = 1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i == j){
                    if(!board.map.containsKey(index)){
                        if(min3 > sum) {
                            min3 = sum;
                            minIndex3 = index;
                        }
                    }
                    sum = sum + tryMarker(i,j);
                    index+=4;
                }
            }
            if(!board.map.containsKey(index) && min3 > sum) {
                min3 = sum;
                minIndex3 = index;
            }
            sum = 0;
        }

        sum = 0;
        min4 = 0;
        index = 3;
        for(int i=0;i<3;i++){
            for(int j=2;j>=0;j--){
                if(i == j){
                    if(!board.map.containsKey(index)){
                        if(min4 > sum) {
                            min4 = sum;
                            minIndex4 = index;
                        }
                    }
                    sum = sum + tryMarker(i,j);
                    index+=2;
                }
            }
            if(!board.map.containsKey(index) && min4 > sum) {
                min4 = sum;
                minIndex4 = index;
            }
            sum = 0;
        }

        Map<Integer,Integer> temp = new HashMap();
        temp.put(min1,minIndex1);
        temp.put(min2,minIndex2);
        temp.put(min3,minIndex3);
        temp.put(min4,minIndex4);

        int mina = Math.min(min1,min2);
        int minb = Math.min(min3,min4);

        cellValue = temp.get(mina == 0 ? minb : (minb == 0 ? mina : Math.min(mina,minb)));

        for(int i=0;i<board.cells.length;i++){
            for(int j=0;j<board.cells[0].length;j++) {
                if(cellValue > 0 && !board.map.containsKey(cellValue)) {
                    board.placeMarker(cellValue,marker);
                }
                else
                    continue;
            }
        }
        return cellValue;
    }

    private int placeComputerRandomly(String marker){
        int random;
        for(int i=0;i<board.cells.length;i++){
            for(int j=0;j<board.cells[0].length;j++) {
                random = (int) (Math.random() * 9);
                if(random > 0 && !board.map.containsKey(random)) {
                    board.placeMarker(random,marker);
                    return random;
                }
                else
                    continue;
            }
        }
        return -1;
    }
    private int tryMarker(int i, int j){
        if(board.cells[i][j] == null)
            return 0;
        else if(board.cells[i][j].value.equals("X"))
            return -1;
        else if(board.cells[i][j].value.equals("O"))
            return 1;
        return -1;
    }

    public boolean isWinning(String marker){
        if(checkWinning(marker)){
            displayMessage("Human Wins. Game Over!, starting new game");
            humanPoints++;
        }
        else{
            displayMessage("Computer Wins. Game Over!, starting new game");
            compPoints++;
        }
        startTheGame();
        return true;
    }

    private boolean checkWinning(String marker){
        return tryWinning(marker,0,0);
    }

    private boolean tryWinning(String marker,int i, int j){
        if(i>2 || j>2 || board.cells[i][j] == null)
            return false;
        if(board.cells[i][j].value.equals(marker)){
            return tryWinning(marker,i+1,j) || tryWinning(marker,i,j+1) || tryWinning(marker,i+1,j+1);
        }
        return false;
    }

    public void displayMessage(String message){
        Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

