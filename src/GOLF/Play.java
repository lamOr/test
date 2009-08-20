package GOLF;
import java.util.Random;
public class Play{
    private Ball ball;
    private Hole hole;
    private Player player;
    private Random random;
    private double highSpeed;

    public Play(){
        ball = new Ball(90.0,90.0);
        hole = new Hole();
        player = new Player();
        random = new Random();
        highSpeed = 0;
        generatePosition(true);
        generatePosition(false);
    }
    private void generatePosition(boolean isBall){
        if(isBall){
            do{
                 double lat = 51.168061+random.nextDouble()*2516/1000000;
                 double log = -115.563308+random.nextDouble()*7317/1000000;
                 ball.setPosition(lat,log);
            }while(hole.comparePosition(ball));
        }
        else{
            do{
                 double lat = 51.168061+random.nextDouble()*2516/1000000;
                 double log = -115.563308+random.nextDouble()*7317/1000000;
                 hole.setPosition(lat, log);
            }while(hole.comparePosition(ball));
        }
    }


    public void playGame(double ulat,double ulog,double speed,double direction){
         player.setUserPosition(ulat, ulog);
         player.setUserSpeed(speed);
         if(ball.comparePosition(player)){
             if(highSpeed < speed)
                highSpeed = speed;
             if(ball.inHitRange(player)){
                 ball.UpdateBallPosition(highSpeed,direction, player);
                 if(hole.comparePosition(ball)){
                     System.out.print("you are win");
                 }
                 else if(!ball.checkValidPosition(51.168081,51.170563,-115.563308,-115.555991)){
                     generatePosition(true);
                }
                highSpeed = 0;
            }
         }
    }
    public Point getBallPosition(){
        return ball.getBallPosition();
    }
    public Point getHolePosition(){
        return hole.getLocation();
    }
}
