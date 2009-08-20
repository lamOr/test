package GOLF;


public class Ball {

    private Point ballPosition;
    private double distance;
    private final double inRange = 6;
    private final double hitRange = 3;
    private int typeOfClub;
    private double speedOfBall;
    private final double factor = 8;
    private Ball backupBall=null;


    public Ball(){
    }
    public Ball(Point point){
       ballPosition = new Point(point);
    }
    public Ball(double lat,double log){
        ballPosition = new Point(lat,log);
    }
    public void backUpBall(){
        if(backupBall==null)
         backupBall = new Ball();
         backupBall.setPosition(ballPosition);
    }
    public void setPosition(Point point){
       ballPosition.setPoint(point);
    }
   public void setPosition(double lat,double log){
       ballPosition.setLat(lat);
       ballPosition.setLog(log);
    }
    public double getLat(){
        return ballPosition.getLat();
    }
    public double getLog(){
         return ballPosition.getLog();
    }
    public Point getBallPosition(){
        return ballPosition;
    }
    public boolean comparePosition(Player player){
        boolean result=false;
        distance=DistanceCaculation.D_jw(ballPosition.getLat(),ballPosition.getLog(),player.playerLat(),player.playerLog());//unit is m
        if(distance<inRange)
            result=true;
        return result;
    }

    public boolean inHitRange(Player player){
        boolean result=false;
        distance=DistanceCaculation.D_jw(ballPosition.getLat(),ballPosition.getLog(),player.playerLat(),player.playerLog());//unit is m
        if(distance<hitRange)
            result=true;
        return result;
    }
    



    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    public int setClub(int typeOfClub){
        this.typeOfClub=typeOfClub;
        switch(this.typeOfClub){
          case 1:
             speedOfBall=1.1;
             break;
          case 2:
             speedOfBall=1.2;
             break;
          default:
            speedOfBall=1.0;
        }
        return this.typeOfClub;
    }

    public void UpdateBallPosition(double highSpeed,double direction,Player player){
        double dis = highSpeed*factor;
        System.out.println("dis="+dis);
       setPosition(DistanceCaculation.computation(ballPosition.getLat(),ballPosition.getLog(),direction,dis));
    }

    public boolean checkValidPosition(double blatS,double blatD,double blogS,double blogD){
        if((blatS<ballPosition.getLat()&&blatD>ballPosition.getLat())&&(blogS<ballPosition.getLog()&&blogD>ballPosition.getLog()))
                return true;
        return false;
    }
}
