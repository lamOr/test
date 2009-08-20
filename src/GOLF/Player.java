/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GOLF;

/**
 *
 * @author Administrator
 */
public class Player {
    private Point playerPosition;
    private double speedOfUser;
     //backupBall.setUserPosition(userLat, userLog);

    public void setUserPosition(double ulat,double ulog){
        playerPosition = new Point(ulat,ulog);
    }
    public void setUserPosition(Point point){
        playerPosition = new Point(point);
    }
    public void setUserSpeed(double speed){
        this.speedOfUser=speed;
    }

    public double playerLat(){
        return playerPosition.getLat();
    }

    public double playerLog(){
        return playerPosition.getLog();
    }
    public double speedOfUser(){
        return this.speedOfUser;
    }
}
