package pingpong.vtkrishn.com.pingpong;

/**
 * Created by vtkrishn on 5/23/2017.
 */

public class Ball {
    int speed;
    int x;
    int y;

    public Ball(){
        this.x = 0;
        this.y = 0;
        this.speed = 5;
    }

    public Ball(int x, int y , int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
