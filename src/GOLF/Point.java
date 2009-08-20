/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GOLF;
//import javax.microedition.location.Coordinates;

/**
 *
 * @author Administrator
 */
public class Point {
    private double lat;
    private double log;
    public Point(){

    }
    public Point(double lat,double log){
        this.lat=lat;
        this.log=log;
    }
    public Point(Point point){
        setPoint(point);
    }
    /*
    public void setPoint(Coordinates coord){
        this.lat=coord.getLatitude();
        this.log=coord.getLongitude();
    }*/
    public void setPoint(Point point){
        this.lat= point.getLat();
        this.log= point.getLog();
    }
    public void setLat(double lat){
        this.lat=lat;
    }
    public void setLog(double log){
        this.log=log;
    }
    public double getLat(){
        return this.lat;
    }
    public double getLog(){
        return this.log;
    }
}

