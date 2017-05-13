package game.vtkrishn.com.gameapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import game.vtkrishn.com.gameapplication.model.Board;
import game.vtkrishn.com.gameapplication.model.Controller;

public class MainActivity extends AppCompatActivity {
    Controller controller;
    Board b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the controller and load the components
        controller = new Controller(this);
        controller.loadUIComponents();
    }

    public void onNewClick(View view){
        //initialize the board
        controller.startTheGame();
    }

    public void onCellClick(View view) {
        controller.propagate(view);
    }

}
